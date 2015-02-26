package com.github.aureliano.damihilogs.writer;

import java.util.List;

import com.github.aureliano.damihilogs.config.output.IConfigOutput;
import com.github.aureliano.damihilogs.filter.IEventFielter;
import com.github.aureliano.damihilogs.formatter.IOutputFormatter;
import com.github.aureliano.damihilogs.listener.DataWritingListener;

public interface IDataWriter {
	
	public abstract IConfigOutput getOutputConfiguration();

	public abstract IDataWriter withOutputConfiguration(IConfigOutput config);
	
	public abstract List<DataWritingListener> getListeners();
	
	public abstract IDataWriter withListeners(List<DataWritingListener> listeners);
	
	public abstract void write(Object data);
	
	public abstract void endResources();
	
	public abstract IOutputFormatter getOutputFormatter();
	
	public abstract IDataWriter withOutputFormatter(IOutputFormatter outputFormatter);
	
	public abstract IEventFielter getFilter();
	
	public abstract IDataWriter withFilter(IEventFielter filter);
	
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");
}