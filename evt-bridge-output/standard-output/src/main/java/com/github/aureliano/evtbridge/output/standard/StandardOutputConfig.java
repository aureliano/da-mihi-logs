package com.github.aureliano.evtbridge.output.standard;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.github.aureliano.evtbridge.core.config.IConfigOutput;
import com.github.aureliano.evtbridge.core.config.OutputConfigTypes;
import com.github.aureliano.evtbridge.core.filter.IEventFielter;
import com.github.aureliano.evtbridge.core.formatter.IOutputFormatter;
import com.github.aureliano.evtbridge.core.helper.DataHelper;
import com.github.aureliano.evtbridge.core.listener.DataWritingListener;
import com.github.aureliano.evtbridge.core.parser.IParser;

public class StandardOutputConfig implements IConfigOutput {

	private IParser<?> parser;
	private IEventFielter filter;
	private Properties metadata;
	private IOutputFormatter outputFormatter;
	private List<DataWritingListener> dataWritingListeners;
	
	public StandardOutputConfig() {
		this.metadata = new Properties();
		this.dataWritingListeners = new ArrayList<>();
	}

	@Override
	public String id() {
		return OutputConfigTypes.STANDARD_OUTPUT.name();
	}

	@Override
	public IParser<?> getParser() {
		return this.parser;
	}

	@Override
	public StandardOutputConfig withParser(IParser<?> parser) {
		this.parser = parser;
		return this;
	}

	@Override
	public IEventFielter getFilter() {
		return this.filter;
	}

	@Override
	public StandardOutputConfig withFilter(IEventFielter filter) {
		this.filter = filter;
		return this;
	}

	@Override
	public IOutputFormatter getOutputFormatter() {
		return outputFormatter;
	}

	@Override
	public StandardOutputConfig withOutputFormatter(IOutputFormatter outputFormatter) {
		this.outputFormatter = outputFormatter;
		return this;
	}
	
	public List<DataWritingListener> getDataWritingListeners() {
		return dataWritingListeners;
	}
	
	public StandardOutputConfig withDataWritingListeners(List<DataWritingListener> dataWritingListeners) {
		this.dataWritingListeners = dataWritingListeners;
		return this;
	}
	
	public StandardOutputConfig addDataWritingListener(DataWritingListener listener) {
		this.dataWritingListeners.add(listener);
		return this;
	}
	
	public StandardOutputConfig clone() {
		return new StandardOutputConfig()
			.withParser(this.parser)
			.withFilter(this.filter)
			.withOutputFormatter(this.outputFormatter)
			.withDataWritingListeners(this.dataWritingListeners)
			.withMetadata(DataHelper.copyProperties(this.metadata));
	}
	
	@Override
	public StandardOutputConfig putMetadata(String key, String value) {
		this.metadata.put(key, value);
		return this;
	}
	
	@Override
	public String getMetadata(String key) {
		return this.metadata.getProperty(key);
	}
	
	protected StandardOutputConfig withMetadata(Properties properties) {
		this.metadata = properties;
		return this;
	}

	@Override
	public Properties getMetadata() {
		return this.metadata;
	}
}