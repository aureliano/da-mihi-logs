package com.github.aureliano.defero.writer;

import com.github.aureliano.defero.config.output.IConfigOutput;

public interface IDataWriter {
	
	public abstract IConfigOutput getOutputConfiguration();

	public abstract IDataWriter withOutputConfiguration(IConfigOutput config);
	
	public abstract void write(Object data);
}