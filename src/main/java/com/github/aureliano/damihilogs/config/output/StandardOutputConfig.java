package com.github.aureliano.damihilogs.config.output;

import com.github.aureliano.damihilogs.filter.IEventFielter;
import com.github.aureliano.damihilogs.parser.IParser;

public class StandardOutputConfig implements IConfigOutput {

	private IParser<?> parser;
	private IEventFielter filter;
	
	public StandardOutputConfig() {
		super();
	}

	@Override
	public String outputType() {
		return "STANDARD";
	}

	@Override
	public IParser<?> getParser() {
		return this.parser;
	}

	@Override
	public IConfigOutput withParser(IParser<?> parser) {
		this.parser = parser;
		return this;
	}

	@Override
	public IEventFielter getFilter() {
		return this.filter;
	}

	@Override
	public IConfigOutput withFilter(IEventFielter filter) {
		this.filter = filter;
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public StandardOutputConfig clone() {
		return new StandardOutputConfig();
	}
}