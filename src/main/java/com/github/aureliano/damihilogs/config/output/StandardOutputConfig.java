package com.github.aureliano.damihilogs.config.output;

import java.util.Properties;

import com.github.aureliano.damihilogs.filter.IEventFielter;
import com.github.aureliano.damihilogs.parser.IParser;

public class StandardOutputConfig implements IConfigOutput {

	private IParser<?> parser;
	private IEventFielter filter;
	private Properties metadata;
	
	public StandardOutputConfig() {
		this.metadata = new Properties();
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
	
	public StandardOutputConfig clone() {
		return new StandardOutputConfig()
			.withMetadata(this.metadata);
	}
	
	@Override
	public StandardOutputConfig putMetadata(String key, String value) {
		this.metadata.put(key, value);
		return this;
	}
	
	@Override
	public String getMetadata(String key) {
		return this.metadata.getProperty(key);
	}
	
	protected StandardOutputConfig withMetadata(Properties properties) {
		this.metadata = properties;
		return this;
	}

	@Override
	public Properties getMetadata() {
		return this.metadata;
	}
}