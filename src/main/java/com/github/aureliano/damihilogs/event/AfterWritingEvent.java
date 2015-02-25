package com.github.aureliano.damihilogs.event;

import com.github.aureliano.damihilogs.config.output.IConfigOutput;

public class AfterWritingEvent {

	private IConfigOutput outputConfiguration;
	private Object data;
	
	public AfterWritingEvent(IConfigOutput outputConfiguration, Object data) {
		this.outputConfiguration = outputConfiguration;
		this.data = data;
	}

	public IConfigOutput getOutputConfiguration() {
		return outputConfiguration;
	}

	public Object getData() {
		return data;
	}
}