package com.github.aureliano.defero.writer;

import com.github.aureliano.defero.config.output.IConfigOutput;

public class StandardDataWriter implements IDataWriter {

	public StandardDataWriter() {
		super();
	}

	@Override
	public IDataWriter withOutputConfiguration(IConfigOutput config) {
		return this;
	}

	@Override
	public void write(Object data) {
		System.out.println(data);
	}
}