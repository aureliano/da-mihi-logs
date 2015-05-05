package com.github.aureliano.damihilogs.writer;

import com.github.aureliano.damihilogs.config.output.IConfigOutput;
import com.github.aureliano.damihilogs.filter.IEventFielter;
import com.github.aureliano.damihilogs.parser.IParser;

public interface IDataWriter {
	
	public abstract IConfigOutput getOutputConfiguration();

	public abstract IDataWriter withOutputConfiguration(IConfigOutput config);
	
	public abstract IParser<?> getParser();
	
	public abstract void write(String content);
	
	public abstract void endResources();
	
	public abstract IEventFielter getFilter();
	
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");
}