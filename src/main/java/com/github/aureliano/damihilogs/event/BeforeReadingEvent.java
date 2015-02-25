package com.github.aureliano.damihilogs.event;

import com.github.aureliano.damihilogs.config.input.IConfigInput;

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