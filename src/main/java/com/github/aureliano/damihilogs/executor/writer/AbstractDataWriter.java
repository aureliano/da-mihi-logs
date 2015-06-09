package com.github.aureliano.damihilogs.executor.writer;

import com.github.aureliano.damihilogs.config.IConfiguration;
import com.github.aureliano.damihilogs.config.output.IConfigOutput;
import com.github.aureliano.damihilogs.event.AfterWritingEvent;
import com.github.aureliano.damihilogs.event.BeforeWritingEvent;
import com.github.aureliano.damihilogs.formatter.IOutputFormatter;
import com.github.aureliano.damihilogs.listener.DataWritingListener;

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