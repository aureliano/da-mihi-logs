package com.github.aureliano.defero.writer;

import java.util.List;

import com.github.aureliano.defero.config.output.IConfigOutput;
import com.github.aureliano.defero.formatter.IOutputFormatter;
import com.github.aureliano.defero.listener.DataWritingListener;

public interface IDataWriter {
	
	public abstract IConfigOutput getOutputConfiguration();

	public abstract IDataWriter withOutputConfiguration(IConfigOutput config);
	
	public abstract List<DataWritingListener> getListeners();
	
	public abstract IDataWriter withListeners(List<DataWritingListener> listeners);
	
	public abstract void write(Object data);
	
	public abstract void endResources();
	
	public abstract IOutputFormatter getOutputFormatter();
	
	public abstract IDataWriter withOutputFormatter(IOutputFormatter outputFormatter);
	
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");
}