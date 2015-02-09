package com.github.aureliano.defero.writer;

import com.github.aureliano.defero.config.output.IConfigOutput;
import com.github.aureliano.defero.config.output.StandardOutputConfig;

public class StandardDataWriter implements IDataWriter {

	private StandardOutputConfig outputConfiguration;
	
	public StandardDataWriter() {
		super();
	}
	
	@Override
	public IConfigOutput getOutputConfiguration() {
		return outputConfiguration;
	}

	@Override
	public IDataWriter withOutputConfiguration(IConfigOutput config) {
		this.outputConfiguration = (StandardOutputConfig) config;
		return this;
	}

	@Override
	public void write(Object data) {
		System.out.println(data);
	}
}