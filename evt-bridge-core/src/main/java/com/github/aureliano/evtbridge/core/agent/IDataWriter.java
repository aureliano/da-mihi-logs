package com.github.aureliano.evtbridge.core.agent;

public interface IDataWriter extends IAgent {
	
	public abstract void initializeResources();
	
	public abstract void finalizeResources();
	
	public abstract Object parseEvent(String event);
	
	public abstract void write(Object data);
	
	public abstract boolean applyFilter(Object data);
	
	public abstract Object formatData(Object data);
	
	public abstract void executeBeforeWritingListeners(Object data);
	
	public abstract void executeAfterWritingListeners(Object data, boolean accepeted);
	
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");
}