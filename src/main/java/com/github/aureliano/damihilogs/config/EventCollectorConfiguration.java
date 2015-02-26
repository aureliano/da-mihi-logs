package com.github.aureliano.damihilogs.config;

import java.util.ArrayList;
import java.util.List;

import com.github.aureliano.damihilogs.config.input.IConfigInput;
import com.github.aureliano.damihilogs.config.output.IConfigOutput;
import com.github.aureliano.damihilogs.formatter.IOutputFormatter;
import com.github.aureliano.damihilogs.listener.DataReadingListener;
import com.github.aureliano.damihilogs.listener.DataWritingListener;
import com.github.aureliano.damihilogs.matcher.IMatcher;
import com.github.aureliano.damihilogs.matcher.SingleLineMatcher;

public class EventCollectorConfiguration {

	private List<IConfigInput> inputConfigs;
	private List<IConfigOutput> outputConfigs;
	private IMatcher matcher;
	private IOutputFormatter outputFormatter;
	private List<DataReadingListener> dataReadingListeners;
	private List<DataWritingListener> dataWritingListeners;
	private boolean persistExecutionLog;
	private boolean multiThreadingEnabled;
	
	public EventCollectorConfiguration() {
		this.inputConfigs = new ArrayList<IConfigInput>();
		this.outputConfigs = new ArrayList<IConfigOutput>();
		
		this.matcher = new SingleLineMatcher();
		
		this.dataReadingListeners = new ArrayList<DataReadingListener>();
		this.dataWritingListeners = new ArrayList<DataWritingListener>();
		
		this.persistExecutionLog = true;
		this.multiThreadingEnabled = false;
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
	
	public IMatcher getMatcher() {
		return matcher;
	}
	
	public EventCollectorConfiguration withMatcher(IMatcher matcher) {
		this.matcher = matcher;
		return this;
	}
	
	public IOutputFormatter getOutputFormatter() {
		return outputFormatter;
	}
	
	public EventCollectorConfiguration withOutputFormatter(IOutputFormatter outputFormatter) {
		this.outputFormatter = outputFormatter;
		return this;
	}

	public List<DataReadingListener> getDataReadingListeners() {
		return dataReadingListeners;
	}

	public EventCollectorConfiguration withDataReadingListeners(List<DataReadingListener> dataReadingListeners) {
		this.dataReadingListeners = dataReadingListeners;
		return this;
	}
	
	public EventCollectorConfiguration addDataReadingListeners(DataReadingListener listener) {
		this.dataReadingListeners.add(listener);
		return this;
	}
	
	public List<DataWritingListener> getDataWritingListeners() {
		return dataWritingListeners;
	}
	
	public EventCollectorConfiguration withDataWritingListeners(List<DataWritingListener> dataWritingListeners) {
		this.dataWritingListeners = dataWritingListeners;
		return this;
	}
	
	public EventCollectorConfiguration addDataWritingListeners(DataWritingListener listener) {
		this.dataWritingListeners.add(listener);
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
}