package com.github.aureliano.damihilogs.writer;

import java.util.List;

import com.github.aureliano.damihilogs.config.output.IConfigOutput;
import com.github.aureliano.damihilogs.config.output.StandardOutputConfig;
import com.github.aureliano.damihilogs.event.AfterWritingEvent;
import com.github.aureliano.damihilogs.event.BeforeWritingEvent;
import com.github.aureliano.damihilogs.formatter.IOutputFormatter;
import com.github.aureliano.damihilogs.formatter.PlainTextFormatter;
import com.github.aureliano.damihilogs.listener.DataWritingListener;

public class StandardDataWriter implements IDataWriter {

	private StandardOutputConfig outputConfiguration;
	private IOutputFormatter outputFormatter;
	private List<DataWritingListener> listeners;
	
	public StandardDataWriter() {
		super();
	}
	
	@Override
	public IConfigOutput getOutputConfiguration() {
		return outputConfiguration;
	}

	@Override
	public IDataWriter withOutputConfiguration(IConfigOutput config) {
		this.outputConfiguration = (StandardOutputConfig) config;
		return this;
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
	public void write(Object data) {
		if (this.outputFormatter == null) {
			this.outputFormatter = new PlainTextFormatter();
		}
		
		if (data == null) {
			return;
		}
		
		this.executeBeforeWritingMethodListeners(data);
		System.out.println(this.outputFormatter.format(data));
		this.executeAfterWritingMethodListeners(data);
	}
	
	@Override
	public void endResources() { }

	private void executeBeforeWritingMethodListeners(Object data) {
		for (DataWritingListener listener : this.listeners) {
			listener.beforeDataWriting(new BeforeWritingEvent(this.outputConfiguration, data));
		}
	}

	private void executeAfterWritingMethodListeners(Object data) {
		for (DataWritingListener listener : this.listeners) {
			listener.afterDataWriting(new AfterWritingEvent(this.outputConfiguration, data));
		}
	}
}