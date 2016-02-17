package com.github.aureliano.evtbridge.core;

import java.io.File;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.github.aureliano.evtbridge.common.helper.StringHelper;
import com.github.aureliano.evtbridge.core.agent.IAgent;
import com.github.aureliano.evtbridge.core.command.CollectEventsCommand;
import com.github.aureliano.evtbridge.core.config.EventCollectorConfiguration;
import com.github.aureliano.evtbridge.core.config.IConfiguration;
import com.github.aureliano.evtbridge.core.event.AfterCollectorsEvent;
import com.github.aureliano.evtbridge.core.event.BeforeCollectorsEvent;
import com.github.aureliano.evtbridge.core.helper.ConfigHelper;
import com.github.aureliano.evtbridge.core.helper.LoggerHelper;
import com.github.aureliano.evtbridge.core.jdbc.ConnectionPool;
import com.github.aureliano.evtbridge.core.listener.EventsCollectorListener;
import com.github.aureliano.evtbridge.core.profile.IProfile;
import com.github.aureliano.evtbridge.core.profile.JvmProfile;
import com.github.aureliano.evtbridge.core.register.ApiServiceRegistrator;

public class EventsCollector {

private static final Logger logger = Logger.getLogger(EventsCollector.class);
	
	private EventCollectorConfiguration configuration;
	private CollectEventsCommand commandExecutor;
	
	public EventsCollector() {
		super();
	}
	
	public void execute() {
		if (this.configuration == null) {
			this.configuration = new EventCollectorConfiguration();
			logger.info("Using default event collector configuration.");
		}
		
		if (this.configuration.getScheduler() == null) {
			this._execute();
			
			if (ConnectionPool.isInitialized()) {
				ConnectionPool.instance().closeConnections();
			}
			
			return;
		}
		
		this.configuration.getScheduler().schedule(new Runnable() {			
			
			@Override
			public void run() {
				try {
					_execute();
				} catch (Exception ex) {
					logger.error(ex.getMessage(), ex);
				}
			}
		});
	}
	
	private void _execute() {
		this.configureThreadName();
		this.configureLogger();
		
		IProfile profile = new JvmProfile();
		profile.start();
		
		this.executeBeforeListeners();
		this.commandExecutor = new CollectEventsCommand(this.configuration);
		this.commandExecutor.execute();
		
		Properties executionLog = null;
		if (this.configuration.isPersistExecutionLog()) {
			executionLog = this.printLogToOutput(profile);
		}
		
		this.executeAfterListeners(executionLog);
		logger.info("Events collector " + this.configuration.getCollectorId() + " has just finished.");
	}
	
	private void configureThreadName() {
		if (StringHelper.isEmpty(this.configuration.getCollectorId())) {
			this.configuration.withCollectorId(ConfigHelper.newUniqueConfigurationName());
		}
		
		Thread.currentThread().setName("Thread-" + this.configuration.getCollectorId());
	}

	private void configureLogger() {
		System.setOut(LoggerHelper.createLoggingProxy(System.out, logger));
		System.setErr(LoggerHelper.createLoggingProxy(System.err, logger));
	}
	
	private Properties printLogToOutput(IProfile profiler) {
		Properties properties = profiler.intersection(profiler.stop()).parse();
		
		for (Map<String, Object> executionLog : this.commandExecutor.getLogExecutions()) {
			for (String key : executionLog.keySet()) {
				properties.put(key, String.valueOf(executionLog.get(key)));
			}
		}
		
		File log = LoggerHelper.saveExecutionLogData(this.configuration.getCollectorId(), properties, true);
		logger.info("Execution log output saved at " + log.getPath());
		
		return properties;
	}
	
	private void executeBeforeListeners() {
		for (EventsCollectorListener listener : this.configuration.getEventsCollectorListeners()) {
			BeforeCollectorsEvent event = new BeforeCollectorsEvent(this.configuration.getCollectorId(), this.configuration);
			listener.beforeExecution(event);
		}
	}

	private void executeAfterListeners(Properties executionLog) {
		for (EventsCollectorListener listener : this.configuration.getEventsCollectorListeners()) {
			AfterCollectorsEvent event = new AfterCollectorsEvent(this.configuration.getCollectorId(), this.configuration, executionLog);
			listener.afterExecution(event);
		}
	}
	
	public EventCollectorConfiguration getConfiguration() {
		return configuration;
	}
	
	public EventsCollector withConfiguration(EventCollectorConfiguration configuration) {
		this.configuration = configuration;
		return this;
	}
	
	public EventsCollector registrateExecutor(
			Class<? extends IConfiguration> configuration, Class<? extends IAgent> agent) {
		ApiServiceRegistrator.instance().registrate(configuration, agent);
		return this;
	}
}