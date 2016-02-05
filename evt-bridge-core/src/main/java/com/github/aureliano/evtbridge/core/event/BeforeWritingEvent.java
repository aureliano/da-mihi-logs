package com.github.aureliano.evtbridge.core.event;

import com.github.aureliano.evtbridge.core.config.IConfigOutput;

public class BeforeWritingEvent {

	private IConfigOutput outputConfiguration;
	private Object data;
	
	public BeforeWritingEvent(IConfigOutput outputConfiguration, Object data) {
		this.outputConfiguration = outputConfiguration;
		this.data = data;
	}

	public IConfigOutput getOutputConfiguration() {
		return outputConfiguration;
	}

	public Object getData() {
		return data;
	}
}