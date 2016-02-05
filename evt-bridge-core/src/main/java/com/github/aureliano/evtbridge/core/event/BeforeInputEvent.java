package com.github.aureliano.evtbridge.core.event;

import com.github.aureliano.evtbridge.core.config.IConfigInput;

public class BeforeInputEvent {

	private IConfigInput inputConfiguration;
	
	public BeforeInputEvent(IConfigInput inputConfiguration) {
		this.inputConfiguration = inputConfiguration;
	}
	
	public IConfigInput getInputConfiguration() {
		return inputConfiguration;
	}
}