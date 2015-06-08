package com.github.aureliano.damihilogs.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.github.aureliano.damihilogs.clean.ICleaner;
import com.github.aureliano.damihilogs.config.input.IConfigInput;
import com.github.aureliano.damihilogs.config.output.IConfigOutput;
import com.github.aureliano.damihilogs.listener.EventsCollectorListener;
import com.github.aureliano.damihilogs.report.ILoggerReporter;
import com.github.aureliano.damihilogs.schedule.EventCollectionSchedule;

public class EventCollectorConfiguration implements IConfiguration {

	private String collectorId;
	private List<IConfigInput> inputConfigs;
	private List<IConfigOutput> outputConfigs;
	private boolean persistExecutionLog;
	private boolean multiThreadingEnabled;
	private Properties metadata;
	private EventCollectionSchedule scheduler;
	private List<ILoggerReporter> reporters;
	private List<ICleaner> cleaners;
	private List<EventsCollectorListener> eventsCollectorListeners;
	
	public EventCollectorConfiguration() {
		this.inputConfigs = new ArrayList<IConfigInput>();
		this.outputConfigs = new ArrayList<IConfigOutput>();
		
		this.persistExecutionLog = true;
		this.multiThreadingEnabled = false;
		
		this.metadata = new Properties();
		this.reporters = new ArrayList<ILoggerReporter>();
		this.cleaners = new ArrayList<ICleaner>();
		this.eventsCollectorListeners = new ArrayList<EventsCollectorListener>();
	}

	public List<IConfigInput> getInputConfigs() {
		return inputConfigs;
	}
	
	public EventCollectorConfiguration withInputConfigs(List<IConfigInput> inputConfigs) {
		this.inputConfigs = inputConfigs;
		return this;
	}

	public EventCollectorConfiguration addInputConfig(IConfigInput inputConfig) {
		this.inputConfigs.add(inputConfig);
		return this;
	}
	
	public List<IConfigOutput> getOutputConfigs() {
		return outputConfigs;
	}
	
	public EventCollectorConfiguration withOutputConfigs(List<IConfigOutput> outputConfigs) {
		this.outputConfigs = outputConfigs;
		return this;
	}
	
	public EventCollectorConfiguration addOutputConfig(IConfigOutput outputConfig) {
		this.outputConfigs.add(outputConfig);
		return this;
	}
	
	public boolean isPersistExecutionLog() {
		return persistExecutionLog;
	}
	
	public EventCollectorConfiguration withPersistExecutionLog(boolean persistExecutionLog) {
		this.persistExecutionLog = persistExecutionLog;
		return this;
	}
	
	public boolean isMultiThreadingEnabled() {
		return multiThreadingEnabled;
	}
	
	public EventCollectorConfiguration withMultiThreadingEnabled(boolean multiThreadingEnabled) {
		this.multiThreadingEnabled = multiThreadingEnabled;
		return this;
	}
	
	public EventCollectorConfiguration withMetadata(Properties properties) {
		this.metadata = properties;
		return this;
	}
	
	public EventCollectorConfiguration withScheduler(EventCollectionSchedule scheduler) {
		this.scheduler = scheduler;
		return this;
	}
	
	public EventCollectionSchedule getScheduler() {
		return scheduler;
	}
	
	public List<ILoggerReporter> getReporters() {
		return reporters;
	}
	
	public EventCollectorConfiguration addReporter(ILoggerReporter reporter) {
		this.reporters.add(reporter);
		return this;
	}
	
	public List<ICleaner> getCleaners() {
		return cleaners;
	}
	
	public EventCollectorConfiguration addCleaner(ICleaner cleaner) {
		this.cleaners.add(cleaner);
		return this;
	}
	
	public List<EventsCollectorListener> getEventsCollectorListeners() {
		return eventsCollectorListeners;
	}
	
	public EventCollectorConfiguration withEventsCollectorListeners(List<EventsCollectorListener> eventsCollectorListeners) {
		this.eventsCollectorListeners = eventsCollectorListeners;
		return this;
	}
	
	public EventCollectorConfiguration addEventsCollectorListeners(EventsCollectorListener eventsCollectorListener) {
		this.eventsCollectorListeners.add(eventsCollectorListener);
		return this;
	}
	
	@Override
	public EventCollectorConfiguration putMetadata(String key, String value) {
		this.metadata.put(key, value);
		return this;
	}
	
	@Override
	public String getMetadata(String key) {
		return this.metadata.getProperty(key);
	}
	
	@Override
	public Properties getMetadata() {
		return this.metadata;
	}
	
	@Override
	public EventCollectorConfiguration clone() {
		throw new UnsupportedOperationException("Clone not supported.");
	}
	
	public String getCollectorId() {
		return collectorId;
	}
	
	public EventCollectorConfiguration withCollectorId(String colectorId) {
		this.collectorId = colectorId;
		return this;
	}

	@Override
	public String id() {
		return "EVENT_COLLECTOR";
	}
}