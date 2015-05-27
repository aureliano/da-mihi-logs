package com.github.aureliano.damihilogs.writer;

import com.github.aureliano.damihilogs.IExecutor;
import com.github.aureliano.damihilogs.filter.IEventFielter;
import com.github.aureliano.damihilogs.parser.IParser;

public interface IDataWriter extends IExecutor {
	
	public abstract IParser<?> getParser();
	
	public abstract void write(String content);
	
	public abstract void endResources();
	
	public abstract IEventFielter getFilter();
	
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");
}