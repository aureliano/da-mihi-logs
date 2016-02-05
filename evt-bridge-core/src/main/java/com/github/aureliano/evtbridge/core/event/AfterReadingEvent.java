package com.github.aureliano.evtbridge.core.event;

import com.github.aureliano.evtbridge.core.config.IConfigInput;

public class AfterReadingEvent {

	private IConfigInput inputConfiguration;
	private long lineCounter;
	private Object data;
	
	public AfterReadingEvent(IConfigInput inputConfiguration, long lineCounter, Object data) {
		this.inputConfiguration = inputConfiguration;
		this.lineCounter = lineCounter;
		this.data = data;
	}

	public IConfigInput getInputConfiguration() {
		return inputConfiguration;
	}

	public long getLineCounter() {
		return lineCounter;
	}

	public Object getData() {
		return data;
	}
}