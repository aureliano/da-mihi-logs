package com.github.aureliano.defero.event;

import com.github.aureliano.defero.config.input.IConfigInput;

public class BeforeReadingEvent {

	private IConfigInput inputConfiguration;
	private long lineCounter;
	private int maxParseAttempts;
	
	public BeforeReadingEvent(IConfigInput inputConfiguration, long lineCounter, int maxParseAttempts) {
		this.inputConfiguration = inputConfiguration;
		this.lineCounter = lineCounter;
		this.maxParseAttempts = maxParseAttempts;
	}

	public long getLineCounter() {
		return lineCounter;
	}

	public int getMaxParseAttempts() {
		return maxParseAttempts;
	}

	public IConfigInput getInputConfiguration() {
		return inputConfiguration;
	}
}