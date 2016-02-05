package com.github.aureliano.evtbridge.core.event;

import java.util.Map;

import com.github.aureliano.evtbridge.core.config.IConfigInput;

public class AfterInputEvent {

	private IConfigInput inputConfiguration;
	private final Map<String, Object> executionLog;
	
	public AfterInputEvent(IConfigInput inputConfiguration, Map<String, Object> executionLog) {
		this.inputConfiguration = inputConfiguration;
		this.executionLog = executionLog;
	}
	
	public IConfigInput getInputConfiguration() {
		return inputConfiguration;
	}

	public Map<String, Object> getExecutionLog() {
		return executionLog;
	}
}