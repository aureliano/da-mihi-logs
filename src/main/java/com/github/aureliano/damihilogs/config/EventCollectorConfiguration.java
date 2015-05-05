package com.github.aureliano.damihilogs.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.github.aureliano.damihilogs.config.input.IConfigInput;
import com.github.aureliano.damihilogs.config.output.IConfigOutput;
import com.github.aureliano.damihilogs.schedule.EventCollectionSchedule;

public class EventCollectorConfiguration implements IConfiguration {

	private List<IConfigInput> inputConfigs;
	private List<IConfigOutput> outputConfigs;
	private boolean persistExecutionLog;
	private boolean multiThreadingEnabled;
	private Properties metadata;
	private EventCollectionSchedule scheduler;
	
	public EventCollectorConfiguration() {
		this.inputConfigs = new ArrayList<IConfigInput>();
		this.outputConfigs = new ArrayList<IConfigOutput>();
		
		this.persistExecutionLog = true;
		this.multiThreadingEnabled = false;
		
		this.metadata = new Properties();
	}

	public List<IConfigInput> getInputConfigs() {
		return inputConfigs;
	}

	public EventCollectorConfiguration addInputConfig(IConfigInput inputConfig) {
		this.inputConfigs.add(inputConfig);
		return this;
	}
	
	public List<IConfigOutput> getOutputConfigs() {
		return outputConfigs;
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
}