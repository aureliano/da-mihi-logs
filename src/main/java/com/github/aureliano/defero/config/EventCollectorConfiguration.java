package com.github.aureliano.defero.config;

import java.util.ArrayList;
import java.util.List;

import com.github.aureliano.defero.config.input.IConfigInput;
import com.github.aureliano.defero.config.output.IConfigOutput;
import com.github.aureliano.defero.listener.DataReadingListener;
import com.github.aureliano.defero.listener.DataWritingListener;
import com.github.aureliano.defero.parser.IParser;

public class EventCollectorConfiguration {

	private IConfigInput inputConfig;
	private IConfigOutput outputConfig;
	private IParser<?> parser;
	private List<DataReadingListener> dataReadingListeners;
	private List<DataWritingListener> dataWritingListeners;
	
	public EventCollectorConfiguration() {
		this.dataReadingListeners = new ArrayList<DataReadingListener>();
		this.dataWritingListeners = new ArrayList<DataWritingListener>();
	}

	public IConfigInput getInputConfig() {
		return inputConfig;
	}

	public EventCollectorConfiguration withInputConfig(IConfigInput inputConfig) {
		this.inputConfig = inputConfig;
		return this;
	}
	
	public IConfigOutput getOutputConfig() {
		return outputConfig;
	}
	
	public EventCollectorConfiguration withOutputConfig(IConfigOutput outputConfig) {
		this.outputConfig = outputConfig;
		return this;
	}

	public IParser<?> getParser() {
		return parser;
	}

	public EventCollectorConfiguration withParser(IParser<?> parser) {
		this.parser = parser;
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
}