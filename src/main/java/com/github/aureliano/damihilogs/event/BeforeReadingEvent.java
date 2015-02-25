package com.github.aureliano.defero.event;

import com.github.aureliano.defero.config.input.IConfigInput;

public class BeforeReadingEvent {

	private IConfigInput inputConfiguration;
	private long lineCounter;
	
	public BeforeReadingEvent(IConfigInput inputConfiguration, long lineCounter) {
		this.inputConfiguration = inputConfiguration;
		this.lineCounter = lineCounter;
	}

	public long getLineCounter() {
		return lineCounter;
	}

	public IConfigInput getInputConfiguration() {
		return inputConfiguration;
	}
}