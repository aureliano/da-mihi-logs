package com.github.aureliano.damihilogs.writer;

import java.util.List;

import com.github.aureliano.damihilogs.config.output.IConfigOutput;
import com.github.aureliano.damihilogs.event.AfterWritingEvent;
import com.github.aureliano.damihilogs.event.BeforeWritingEvent;
import com.github.aureliano.damihilogs.filter.IEventFielter;
import com.github.aureliano.damihilogs.formatter.IOutputFormatter;
import com.github.aureliano.damihilogs.listener.DataWritingListener;
import com.github.aureliano.damihilogs.parser.IParser;

public abstract class AbstractDataWriter implements IDataWriter {

	protected IConfigOutput outputConfiguration;
	protected IOutputFormatter outputFormatter;
	protected List<DataWritingListener> listeners;
	
	public AbstractDataWriter() {
		super();
	}
	
	@Override
	public IConfigOutput getOutputConfiguration() {
		return outputConfiguration;
	}

	@Override
	public IDataWriter withOutputConfiguration(IConfigOutput config) {
		this.outputConfiguration = config;
		return this;
	}

	@Override
	public IParser<?> getParser() {
		return this.outputConfiguration.getParser();
	}
	
	@Override
	public List<DataWritingListener> getListeners() {
		return listeners;
	}
	
	@Override
	public IDataWriter withListeners(List<DataWritingListener> listeners) {
		this.listeners = listeners;
		return this;
	}
	
	@Override
	public IOutputFormatter getOutputFormatter() {
		return this.outputFormatter;
	}
	
	@Override
	public IDataWriter withOutputFormatter(IOutputFormatter outputFormatter) {
		this.outputFormatter = outputFormatter;
		return this;
	}
	
	@Override
	public IEventFielter getFilter() {
		return this.outputConfiguration.getFilter();
	}

	protected void executeBeforeWritingMethodListeners(Object data) {
		for (DataWritingListener listener : this.listeners) {
			listener.beforeDataWriting(new BeforeWritingEvent(this.outputConfiguration, data));
		}
	}

	protected void executeAfterWritingMethodListeners(Object data, boolean accepeted) {
		for (DataWritingListener listener : this.listeners) {
			listener.afterDataWriting(new AfterWritingEvent(this.outputConfiguration, accepeted, data));
		}
	}
}