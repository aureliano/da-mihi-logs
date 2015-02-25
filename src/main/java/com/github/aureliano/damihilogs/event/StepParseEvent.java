package com.github.aureliano.damihilogs.event;

public class StepParseEvent {

	private int parseAttempts;
	private String line;
	private String currentData;
	
	public StepParseEvent(int parseAttempts, String line, String currentData) {
		this.parseAttempts = parseAttempts;
		this.line = line;
		this.currentData = currentData;
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