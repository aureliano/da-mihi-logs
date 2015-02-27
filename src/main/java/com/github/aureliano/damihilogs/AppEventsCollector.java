package com.github.aureliano.damihilogs;

import java.io.File;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.github.aureliano.damihilogs.command.CollectEventsCommand;
import com.github.aureliano.damihilogs.config.EventCollectorConfiguration;
import com.github.aureliano.damihilogs.helper.LoggerHelper;
import com.github.aureliano.damihilogs.profile.Profiler;

public class AppEventsCollector {

	private static final Logger logger = Logger.getLogger(AppEventsCollector.class);
	
	private EventCollectorConfiguration configuration;
	private String collectorId;
	private CollectEventsCommand commandExecutor;
	
	public AppEventsCollector() {
		super();
	}
	
	public void execute() {
		if (this.configuration == null) {
			this.configuration = new EventCollectorConfiguration();
			logger.info("Using default event collector configuration.");
		}
		
		Profiler profiler = new Profiler();
		profiler.start();
		
		this.commandExecutor = new CollectEventsCommand(this.configuration);
		this.commandExecutor.execute(this.collectorId);
		
		if (this.configuration.isPersistExecutionLog()) {
			this.printLogToOutput(profiler);
		}
	}
	
	private void printLogToOutput(Profiler profiler) {
		Properties properties = Profiler.parse(Profiler.diff(profiler, profiler.stop()));
		
		for (Map<String, Object> executionLog : this.commandExecutor.getLogExecutions()) {
			for (String key : executionLog.keySet()) {
				properties.put(key, String.valueOf(executionLog.get(key)));
			}
		}
		
		File log = LoggerHelper.saveExecutionLog(this.collectorId, properties, true);
		logger.info("Execution log output saved at " + log.getPath());		
		logger.info("Execution successful!");
	}
	
	public EventCollectorConfiguration getConfiguration() {
		return configuration;
	}
	
	public AppEventsCollector withConfiguration(EventCollectorConfiguration configuration) {
		this.configuration = configuration;
		return this;
	}
	
	public String getCollectorId() {
		return collectorId;
	}
	
	public AppEventsCollector withCollectorId(String colectorId) {
		this.collectorId = colectorId;
		return this;
	}
}