package com.github.aureliano.defero;

import java.io.File;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import com.github.aureliano.defero.command.CollectEventsCommand;
import com.github.aureliano.defero.config.EventCollectorConfiguration;
import com.github.aureliano.defero.helper.LoggerHelper;
import com.github.aureliano.defero.profile.Profiler;

public class AppEventsCollector {

	private static final Logger logger = Logger.getLogger(AppEventsCollector.class.getName());
	
	private EventCollectorConfiguration configuration;
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
		this.commandExecutor.execute();
		
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
		
		File log = LoggerHelper.saveExecutionLog(properties, true);
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
}