package com.github.aureliano.evtbridge.core.agent;

import com.github.aureliano.evtbridge.core.config.IConfigOutput;
import com.github.aureliano.evtbridge.core.config.IConfiguration;
import com.github.aureliano.evtbridge.core.event.AfterWritingEvent;
import com.github.aureliano.evtbridge.core.event.BeforeWritingEvent;
import com.github.aureliano.evtbridge.core.formatter.IOutputFormatter;
import com.github.aureliano.evtbridge.core.listener.DataWritingListener;

public abstract class AbstractDataWriter implements IDataWriter {

	protected IConfigOutput outputConfiguration;
	
	public AbstractDataWriter() {
		super();
	}
	
	@Override
	public IConfigOutput getConfiguration() {
		return outputConfiguration;
	}

	@Override
	public IDataWriter withConfiguration(IConfiguration configuration) {
		this.outputConfiguration = (IConfigOutput) configuration;
		return this;
	}

	@Override
	public Object parseEvent(String event) {
		return this.outputConfiguration.getParser().parse(event);
	}
	
	@Override
	public boolean applyFilter(Object data) {
		return this.outputConfiguration.getFilter().accept(data);
	}
	
	@Override
	public Object formatData(Object data) {
		IOutputFormatter formatter = this.outputConfiguration.getOutputFormatter();
		return (formatter == null) ? data : formatter.format(data);
	}
	
	@Override
	public void executeBeforeWritingListeners(Object data) {
		for (DataWritingListener listener : this.outputConfiguration.getDataWritingListeners()) {
			listener.beforeDataWriting(new BeforeWritingEvent(this.outputConfiguration, data));
		}
	}
	
	@Override
	public void executeAfterWritingListeners(Object data, boolean accepeted) {
		for (DataWritingListener listener : this.outputConfiguration.getDataWritingListeners()) {
			listener.afterDataWriting(new AfterWritingEvent(this.outputConfiguration, accepeted, data));
		}
	}
}