package com.github.aureliano.damihilogs.reader;

import java.util.Map;
import java.util.Properties;

import com.github.aureliano.damihilogs.IExecutor;

public interface IDataReader extends IExecutor {
	
	public abstract void initializeResources();
	
	public abstract void finalizeResources();
	
	public abstract String readNextLine();
	
	public abstract String readLine();
	
	public abstract String nextData();
	
	public abstract Map<String, Object> executionLog();
	
	public abstract boolean keepReading();
	
	public abstract void loadLastExecutionLog(Properties properties);
	
	public abstract void executeBeforeReadingListeners();
	
	public abstract void executeAfterReadingListeners(String data);
}