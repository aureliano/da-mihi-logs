package com.github.aureliano.defero.config;

import com.github.aureliano.defero.config.input.IConfigInput;
import com.github.aureliano.defero.parser.IParser;

public class EventCollectorConfiguration {

	private IConfigInput inputConfig;
	private IParser parser;
	
	public EventCollectorConfiguration() {
		super();
	}

	public IConfigInput getInputConfig() {
		return inputConfig;
	}

	public EventCollectorConfiguration withInputConfig(IConfigInput inputConfig) {
		this.inputConfig = inputConfig;
		return this;
	}

	public IParser getParser() {
		return parser;
	}

	public EventCollectorConfiguration withParser(IParser parser) {
		this.parser = parser;
		return this;
	}
}