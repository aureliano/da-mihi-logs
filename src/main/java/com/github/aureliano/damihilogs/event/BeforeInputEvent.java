package com.github.aureliano.damihilogs.event;

import com.github.aureliano.damihilogs.config.input.IConfigInput;

public class BeforeInputEvent {

	private IConfigInput inputConfiguration;
	
	public BeforeInputEvent(IConfigInput inputConfiguration) {
		this.inputConfiguration = inputConfiguration;
	}
	
	public IConfigInput getInputConfiguration() {
		return inputConfiguration;
	}
}