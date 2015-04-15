package com.github.aureliano.damihilogs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.github.aureliano.damihilogs.clean.ICleaner;
import com.github.aureliano.damihilogs.command.CollectEventsCommand;
import com.github.aureliano.damihilogs.config.EventCollectorConfiguration;
import com.github.aureliano.damihilogs.helper.LoggerHelper;
import com.github.aureliano.damihilogs.profile.Profiler;
import com.github.aureliano.damihilogs.report.ILoggerReporter;
import com.github.aureliano.damihilogs.schedule.EventCollectionSchedule;

public class AppEventsCollector {

	private static final Logger logger = Logger.getLogger(AppEventsCollector.class);
	
	private EventCollectorConfiguration configuration;
	private String collectorId;
	private CollectEventsCommand commandExecutor;
	private EventCollectionSchedule scheduler;
	private List<ILoggerReporter> reporters;
	private List<ICleaner> cleaners;
	
	public AppEventsCollector() {
		this.reporters = new ArrayList<ILoggerReporter>();
		this.cleaners = new ArrayList<ICleaner>();
	}
	
	public void execute() {if (this.scheduler == null) {
			this._execute();
			return;
		}
		
		this.scheduler.prepareSchedulingForExecution(new Runnable() {			
			
			@Override
			public void run() {
				_execute();
			}
		});
	}
	
	private void _execute() {
		Logger.getRootLogger().removeAppender("file");
		this.configureLogger();		
		
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
		
		this.buildReports();
		this.executeCleaners();
	}
	
	private void buildReports() {
		for (ILoggerReporter reporter : this.reporters) {
			try {
				reporter.buildReport();
			} catch (Exception ex) {
				logger.error("An exception ocurred when building report.", ex);
			}
		}
	}
	
	private void executeCleaners() {
		for (ICleaner cleaner : this.cleaners) {
			try {
				cleaner.clean();
			} catch (Exception ex) {
				logger.error("An exception ocurred when executing cleaner.", ex);
			}
		}
	}

	private void configureLogger() {
		LoggerHelper.configureFileAppenderLogger(this.collectorId);
		System.setOut(LoggerHelper.createLoggingProxy(System.out, logger));
		System.setErr(LoggerHelper.createLoggingProxy(System.err, logger));
	}
	
	private void printLogToOutput(Profiler profiler) {
		Properties properties = Profiler.parse(Profiler.diff(profiler, profiler.stop()));
		
		for (Map<String, Object> executionLog : this.commandExecutor.getLogExecutions()) {
			for (String key : executionLog.keySet()) {
				properties.put(key, String.valueOf(executionLog.get(key)));
			}
		}
		
		File log = LoggerHelper.saveExecutionLogData(this.collectorId, properties, true);
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
	
	public AppEventsCollector withScheduler(EventCollectionSchedule scheduler) {
		this.scheduler = scheduler;
		return this;
	}
	
	public EventCollectionSchedule getScheduler() {
		return scheduler;
	}
	
	public List<ILoggerReporter> getReporters() {
		return reporters;
	}
	
	public AppEventsCollector addReporter(ILoggerReporter reporter) {
		this.reporters.add(reporter);
		return this;
	}
	
	public List<ICleaner> getCleaners() {
		return cleaners;
	}
	
	public AppEventsCollector addCleaner(ICleaner cleaner) {
		this.cleaners.add(cleaner);
		return this;
	}
}