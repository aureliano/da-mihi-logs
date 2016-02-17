package com.github.aureliano.evtbridge.core.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.github.aureliano.evtbridge.core.listener.EventsCollectorListener;
import com.github.aureliano.evtbridge.core.schedule.IScheduler;

public class EventCollectorConfiguration implements IConfiguration {

	private String collectorId;
	private List<IConfigInput> inputConfigs;
	private List<IConfigOutput> outputConfigs;
	private boolean persistExecutionLog;
	private boolean multiThreadingEnabled;
	private Properties metadata;
	private IScheduler scheduler;
	private List<EventsCollectorListener> eventsCollectorListeners;
	
	public EventCollectorConfiguration() {
		this.inputConfigs = new ArrayList<IConfigInput>();
		this.outputConfigs = new ArrayList<IConfigOutput>();
		
		this.persistExecutionLog = true;
		this.multiThreadingEnabled = false;
		
		this.metadata = new Properties();
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
	
	public EventCollectorConfiguration withScheduler(IScheduler scheduler) {
		this.scheduler = scheduler;
		return this;
	}
	
	public IScheduler getScheduler() {
		return scheduler;
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
		throw new UnsupportedOperationException("Unsupported clone operation.");
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