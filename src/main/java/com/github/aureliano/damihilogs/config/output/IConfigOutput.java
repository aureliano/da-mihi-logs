package com.github.aureliano.damihilogs.config.output;

import com.github.aureliano.damihilogs.config.IConfiguration;
import com.github.aureliano.damihilogs.filter.IEventFielter;
import com.github.aureliano.damihilogs.parser.IParser;

public interface IConfigOutput extends IConfiguration {

	public abstract IParser<?> getParser();
	
	public abstract IConfigOutput withParser(IParser<?> parser);
	
	public abstract IEventFielter getFilter();
	
	public abstract IConfigOutput withFilter(IEventFielter filter);
	
	public abstract String outputType();
}