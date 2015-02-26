package com.github.aureliano.damihilogs.reader;

import java.util.List;
import java.util.Map;

import com.github.aureliano.damihilogs.config.input.IConfigInput;
import com.github.aureliano.damihilogs.listener.DataReadingListener;
import com.github.aureliano.damihilogs.matcher.IMatcher;

public interface IDataReader {

	public abstract IConfigInput getInputConfiguration();
	
	public abstract IDataReader withInputConfiguration(IConfigInput config);
	
	public abstract IMatcher getMatcher();
	
	public abstract IDataReader withMatcher(IMatcher matcher);
	
	public abstract List<DataReadingListener> getListeners();
	
	public abstract IDataReader withListeners(List<DataReadingListener> listeners);
	
	public abstract String nextData();
	
	public abstract Map<String, Object> executionLog();
	
	public abstract void endResources();
	
	public abstract boolean keepReading();
}