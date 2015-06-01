package com.github.aureliano.damihilogs;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.github.aureliano.damihilogs.config.output.IConfigOutput;
import com.github.aureliano.damihilogs.filter.IEventFielter;
import com.github.aureliano.damihilogs.formatter.IOutputFormatter;
import com.github.aureliano.damihilogs.listener.DataWritingListener;
import com.github.aureliano.damihilogs.parser.IParser;

public class CustomOutputConfig implements IConfigOutput {

	private Properties metadata;
	private IParser<?> parser;
	private IEventFielter filter;
	private IOutputFormatter outputFormatter;
	private List<DataWritingListener> dataWritingListeners;
	
	public CustomOutputConfig() {
		this.metadata = new Properties();
		this.dataWritingListeners = new ArrayList<DataWritingListener>();
	}
	
	@Override
	public CustomOutputConfig clone() {
		return new CustomOutputConfig();
	}

	@Override
	public CustomOutputConfig putMetadata(String key, String value) {
		this.metadata.setProperty(key, value);
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
	public IParser<?> getParser() {
		return this.parser;
	}

	@Override
	public IConfigOutput withParser(IParser<?> parser) {
		this.parser = parser;
		return this;
	}

	@Override
	public IEventFielter getFilter() {
		return this.filter;
	}

	@Override
	public IConfigOutput withFilter(IEventFielter filter) {
		this.filter = filter;
		return this;
	}

	@Override
	public IOutputFormatter getOutputFormatter() {
		return this.outputFormatter;
	}

	@Override
	public IConfigOutput withOutputFormatter(IOutputFormatter outputFormatter) {
		this.outputFormatter = outputFormatter;
		return this;
	}

	@Override
	public List<DataWritingListener> getDataWritingListeners() {
		return this.dataWritingListeners;
	}

	@Override
	public IConfigOutput withDataWritingListeners(List<DataWritingListener> dataWritingListeners) {
		this.dataWritingListeners = dataWritingListeners;
		return this;
	}

	@Override
	public IConfigOutput addDataWritingListener(DataWritingListener listener) {
		this.dataWritingListeners.add(listener);
		return this;
	}

	@Override
	public String outputType() {
		return "CUSTOM-OUTPUT";
	}
}