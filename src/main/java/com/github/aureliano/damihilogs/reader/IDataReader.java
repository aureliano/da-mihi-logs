package com.github.aureliano.damihilogs.reader;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.github.aureliano.damihilogs.config.input.IConfigInput;
import com.github.aureliano.damihilogs.listener.DataReadingListener;

public interface IDataReader {

	public abstract IConfigInput getInputConfiguration();
	
	public abstract IDataReader withInputConfiguration(IConfigInput config);
	
	public abstract List<DataReadingListener> getListeners();
	
	public abstract IDataReader withListeners(List<DataReadingListener> listeners);
	
	public abstract String nextData();
	
	public abstract Map<String, Object> executionLog();
	
	public abstract void endResources();
	
	public abstract boolean keepReading();
	
	public abstract void loadLastExecutionLog(Properties properties);
	
	public abstract Map<String, Object> getReadingProperties();
}