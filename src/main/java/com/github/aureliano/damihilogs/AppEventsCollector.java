package com.github.aureliano.damihilogs;

import java.io.File;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.github.aureliano.damihilogs.clean.ICleaner;
import com.github.aureliano.damihilogs.command.CollectEventsCommand;
import com.github.aureliano.damihilogs.config.EventCollectorConfiguration;
import com.github.aureliano.damihilogs.config.IConfiguration;
import com.github.aureliano.damihilogs.event.AfterCollectorsEvent;
import com.github.aureliano.damihilogs.event.BeforeCollectorsEvent;
import com.github.aureliano.damihilogs.executor.IExecutor;
import com.github.aureliano.damihilogs.helper.ConfigHelper;
import com.github.aureliano.damihilogs.helper.LoggerHelper;
import com.github.aureliano.damihilogs.helper.StringHelper;
import com.github.aureliano.damihilogs.listener.EventsCollectorListener;
import com.github.aureliano.damihilogs.profile.Profiler;
import com.github.aureliano.damihilogs.reg.ApiServiceRegistrator;
import com.github.aureliano.damihilogs.report.ILoggerReporter;

public class AppEventsCollector {

	private static final Logger logger = Logger.getLogger(AppEventsCollector.class);
	
	private EventCollectorConfiguration configuration;
	private CollectEventsCommand commandExecutor;
	
	public AppEventsCollector() {
		super();
	}
	
	public void execute() {
		if (this.configuration.getScheduler() == null) {
			this._execute();
			return;
		}
		
		this.configuration.getScheduler().prepareSchedulingForExecution(new Runnable() {			
			
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
		this.validateCleaners();
		
		if (this.configuration == null) {
			this.configuration = new EventCollectorConfiguration();
			logger.info("Using default event collector configuration.");
		}
		
		Profiler profiler = new Profiler();
		profiler.start();
		
		this.executeBeforeListeners();
		this.commandExecutor = new CollectEventsCommand(this.configuration);
		this.commandExecutor.execute();
		
		Properties executionLog = null;
		if (this.configuration.isPersistExecutionLog()) {
			executionLog = this.printLogToOutput(profiler);
		}
		
		this.buildReports();
		this.executeCleaners();
		
		this.executeAfterListeners(executionLog);
		logger.info("Events collector " + this.configuration.getCollectorId() + " has just finished.");
	}

	private synchronized void buildReports() {
		for (ILoggerReporter reporter : this.configuration.getReporters()) {
			try {
				reporter.buildReport();
			} catch (Exception ex) {
				logger.error("An exception ocurred when building report.", ex);
			}
		}
	}
	
	private void executeCleaners() {
		for (ICleaner cleaner : this.configuration.getCleaners()) {
			try {
				cleaner.clean();
			} catch (Exception ex) {
				logger.error("An exception ocurred when executing cleaner.", ex);
			}
		}
	}
	
	private void validateCleaners() {
		for (ICleaner cleaner : this.configuration.getCleaners()) {
			cleaner.validate();
		}
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
	
	private Properties printLogToOutput(Profiler profiler) {
		Properties properties = Profiler.parse(Profiler.diff(profiler, profiler.stop()));
		
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
	
	public AppEventsCollector withConfiguration(EventCollectorConfiguration configuration) {
		this.configuration = configuration;
		return this;
	}
	
	public AppEventsCollector registrateExecutor(Class<? extends IConfiguration> configuration, Class<? extends IExecutor> executor) {
		ApiServiceRegistrator.instance().registrate(configuration, executor);
		return this;
	}
}