package com.github.aureliano.evtbridge.core.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.github.aureliano.evtbridge.annotation.doc.SchemaConfiguration;
import com.github.aureliano.evtbridge.annotation.doc.SchemaProperty;
import com.github.aureliano.evtbridge.core.listener.EventsCollectorListener;
import com.github.aureliano.evtbridge.core.schedule.IScheduler;
import com.github.aureliano.evtbridge.core.schedule.SchedulerTypes;

@SchemaConfiguration(
	schema = "http://json-schema.org/draft-04/schema#",
	title = "Events collector configuration schema.",
	type = "object"
)
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

	@SchemaProperty(
		property = "inputConfigs",
		types = "array",
		description = "Input configurations.",
		reference = InputConfigTypes.class
	)
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

	@SchemaProperty(
		property = "outputConfigs",
		types = "array",
		description = "Output configurations.",
		reference = OutputConfigTypes.class
	)
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
	
	@SchemaProperty(
		property = "persistExecutionLog",
		types = "boolean",
		description = "Whether it has to persist to disk log execution or not.",
		defaultValue = "false"
	)
	public boolean isPersistExecutionLog() {
		return persistExecutionLog;
	}
	
	public EventCollectorConfiguration withPersistExecutionLog(boolean persistExecutionLog) {
		this.persistExecutionLog = persistExecutionLog;
		return this;
	}
	
	@SchemaProperty(
		property = "multiThreadingEnabled",
		types = "boolean",
		description = "Whether it has to enable multi-threading. That means: for each input a new thread will be created.",
		defaultValue = "false"
	)
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

	@SchemaProperty(
		property = "scheduler",
		types = "object",
		description = "Schedule events collector for execution.",
		reference = SchedulerTypes.class
	)
	public IScheduler getScheduler() {
		return scheduler;
	}

	@SchemaProperty(
		property = "eventsCollectorListeners",
		types = "array",
		description = "Register listeners."
	)
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
	@SchemaProperty(
		property = "metadata",
		types = "object",
		description = "A key-value hash <string, string> in order to exchange metadata between configurations (main, inputs and outputs)."
	)
	public Properties getMetadata() {
		return this.metadata;
	}
	
	@Override
	public EventCollectorConfiguration clone() {
		throw new UnsupportedOperationException("Unsupported clone operation.");
	}
	
	@SchemaProperty(
		property = "collectorId",
		types = "string",
		description = "Event collector id.",
		defaultValue = "Auto-generated id."
	)
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