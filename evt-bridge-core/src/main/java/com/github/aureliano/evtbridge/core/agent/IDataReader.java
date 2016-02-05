package com.github.aureliano.evtbridge.core.agent;

import java.util.Map;
import java.util.Properties;

public interface IDataReader extends IAgent {
	
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