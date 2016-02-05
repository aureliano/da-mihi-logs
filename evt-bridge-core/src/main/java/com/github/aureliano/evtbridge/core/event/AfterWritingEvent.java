package com.github.aureliano.evtbridge.core.event;

import com.github.aureliano.evtbridge.core.config.IConfigOutput;

public class AfterWritingEvent {

	private IConfigOutput outputConfiguration;
	private boolean accepted;
	private Object data;
	
	public AfterWritingEvent(IConfigOutput outputConfiguration, boolean accepted, Object data) {
		this.outputConfiguration = outputConfiguration;
		this.accepted = accepted;
		this.data = data;
	}

	public IConfigOutput getOutputConfiguration() {
		return outputConfiguration;
	}
	
	public boolean isAccepted() {
		return accepted;
	}

	public Object getData() {
		return data;
	}
}