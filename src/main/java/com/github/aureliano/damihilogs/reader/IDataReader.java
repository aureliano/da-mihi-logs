package com.github.aureliano.damihilogs.reader;

import java.util.Map;
import java.util.Properties;

import com.github.aureliano.damihilogs.config.input.IConfigInput;

public interface IDataReader {

	public abstract IConfigInput getInputConfiguration();
	
	public abstract IDataReader withInputConfiguration(IConfigInput config);
	
	public abstract String nextData();
	
	public abstract Map<String, Object> executionLog();
	
	public abstract void endResources();
	
	public abstract boolean keepReading();
	
	public abstract void loadLastExecutionLog(Properties properties);
	
	public abstract Map<String, Object> getReadingProperties();
}