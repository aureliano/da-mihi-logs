package com.github.aureliano.damihilogs.writer;

import com.github.aureliano.damihilogs.config.IConfiguration;
import com.github.aureliano.damihilogs.config.output.IConfigOutput;
import com.github.aureliano.damihilogs.event.AfterWritingEvent;
import com.github.aureliano.damihilogs.event.BeforeWritingEvent;
import com.github.aureliano.damihilogs.filter.IEventFielter;
import com.github.aureliano.damihilogs.listener.DataWritingListener;
import com.github.aureliano.damihilogs.parser.IParser;

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
	public IParser<?> getParser() {
		return this.outputConfiguration.getParser();
	}
	
	@Override
	public IEventFielter getFilter() {
		return this.outputConfiguration.getFilter();
	}

	protected void executeBeforeWritingMethodListeners(Object data) {
		for (DataWritingListener listener : this.outputConfiguration.getDataWritingListeners()) {
			listener.beforeDataWriting(new BeforeWritingEvent(this.outputConfiguration, data));
		}
	}

	protected void executeAfterWritingMethodListeners(Object data, boolean accepeted) {
		for (DataWritingListener listener : this.outputConfiguration.getDataWritingListeners()) {
			listener.afterDataWriting(new AfterWritingEvent(this.outputConfiguration, accepeted, data));
		}
	}
}