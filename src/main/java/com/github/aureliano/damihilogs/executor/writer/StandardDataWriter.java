package com.github.aureliano.damihilogs.executor.writer;

import com.github.aureliano.damihilogs.formatter.PlainTextFormatter;

public class StandardDataWriter extends AbstractDataWriter {
	
	public StandardDataWriter() {
		super();
	}
	
	@Override
	public void initializeResources() {
		this.initialize();
	}

	@Override
	public void write(Object data) {
		System.out.println(data);
	}
	
	private void initialize() {
		if (super.getConfiguration().getOutputFormatter() == null) {
			super.getConfiguration().withOutputFormatter(new PlainTextFormatter());
		}
	}
	
	@Override
	public void finalizeResources() { }
}