package com.github.aureliano.damihilogs.reader;

import java.util.List;
import java.util.Map;

import com.github.aureliano.damihilogs.config.input.IConfigInput;
import com.github.aureliano.damihilogs.listener.DataReadingListener;
import com.github.aureliano.damihilogs.matcher.IMatcher;
import com.github.aureliano.damihilogs.parser.IParser;

public interface IDataReader {

	public abstract IConfigInput getInputConfiguration();
	
	public abstract IDataReader withInputConfiguration(IConfigInput config);
	
	public abstract IMatcher getMatcher();
	
	public abstract IDataReader withMatcher(IMatcher matcher);
	
	public abstract IParser<?> getParser();
	
	public abstract IDataReader withParser(IParser<?> parser);
	
	public abstract List<DataReadingListener> getListeners();
	
	public abstract IDataReader withListeners(List<DataReadingListener> listeners);
	
	public abstract Object nextData();
	
	public abstract Map<String, Object> executionLog();
	
	public abstract void endResources();
	
	public abstract boolean keepReading();
}