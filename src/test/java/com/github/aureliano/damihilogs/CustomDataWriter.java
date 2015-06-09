package com.github.aureliano.damihilogs;

import com.github.aureliano.damihilogs.executor.writer.AbstractDataWriter;

public class CustomDataWriter extends AbstractDataWriter {

	public CustomDataWriter() {
		super();
	}

	@Override
	public void initializeResources() {
		// Initialize any resource.
	}

	@Override
	public void finalizeResources() {
		// Finalize any resource you have open.
	}

	@Override
	public void write(Object data) {
		System.out.println(data);
	}
}