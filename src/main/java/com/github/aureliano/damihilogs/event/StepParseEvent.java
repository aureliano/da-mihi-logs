package com.github.aureliano.damihilogs.event;

import com.github.aureliano.damihilogs.config.input.IConfigInput;

public class StepParseEvent {

	private IConfigInput inputConfiguration;
	private int parseAttempts;
	private String line;
	private String currentData;
	
	public StepParseEvent(IConfigInput inputConfiguration, int parseAttempts, String line, String currentData) {
		this.inputConfiguration = inputConfiguration;
		this.parseAttempts = parseAttempts;
		this.line = line;
		this.currentData = currentData;
	}

	public IConfigInput getInputConfiguration() {
		return inputConfiguration;
	}

	public int getParseAttempts() {
		return parseAttempts;
	}

	public String getLine() {
		return line;
	}

	public String getCurrentData() {
		return currentData;
	}
}