package com.github.aureliano.defero.config;

import java.util.ArrayList;
import java.util.List;

import com.github.aureliano.defero.config.input.IConfigInput;
import com.github.aureliano.defero.config.output.IConfigOutput;
import com.github.aureliano.defero.filter.IEventFielter;
import com.github.aureliano.defero.formatter.IOutputFormatter;
import com.github.aureliano.defero.listener.DataReadingListener;
import com.github.aureliano.defero.listener.DataWritingListener;
import com.github.aureliano.defero.matcher.IMatcher;
import com.github.aureliano.defero.matcher.SingleLineMatcher;
import com.github.aureliano.defero.parser.IParser;
import com.github.aureliano.defero.parser.PlainTextParser;

public class EventCollectorConfiguration {

	private List<IConfigInput> inputConfigs;
	private List<IConfigOutput> outputConfigs;
	private IMatcher matcher;
	private IParser<?> parser;
	private IEventFielter filter;
	private IOutputFormatter outputFormatter;
	private List<DataReadingListener> dataReadingListeners;
	private List<DataWritingListener> dataWritingListeners;
	
	public EventCollectorConfiguration() {
		this.inputConfigs = new ArrayList<IConfigInput>();
		this.outputConfigs = new ArrayList<IConfigOutput>();
		
		this.matcher = new SingleLineMatcher();
		this.parser = new PlainTextParser();
		
		this.dataReadingListeners = new ArrayList<DataReadingListener>();
		this.dataWritingListeners = new ArrayList<DataWritingListener>();
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

	public IParser<?> getParser() {
		return parser;
	}

	public EventCollectorConfiguration withParser(IParser<?> parser) {
		this.parser = parser;
		return this;
	}
	
	public IEventFielter getFilter() {
		return filter;
	}
	
	public EventCollectorConfiguration withFilter(IEventFielter filter) {
		this.filter = filter;
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
}