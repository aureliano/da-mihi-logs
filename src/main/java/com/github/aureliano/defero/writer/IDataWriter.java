package com.github.aureliano.defero.writer;

import java.util.List;

import com.github.aureliano.defero.config.output.IConfigOutput;
import com.github.aureliano.defero.listener.DataWritingListener;

public interface IDataWriter {
	
	public abstract IConfigOutput getOutputConfiguration();

	public abstract IDataWriter withOutputConfiguration(IConfigOutput config);
	
	public abstract List<DataWritingListener> getListeners();
	
	public abstract IDataWriter withListeners(List<DataWritingListener> listeners);
	
	public abstract void write(Object data);
}