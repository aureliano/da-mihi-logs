package com.github.aureliano.evtbridge.output.standard;

import com.github.aureliano.evtbridge.core.agent.AbstractDataWriter;
import com.github.aureliano.evtbridge.core.formatter.PlainTextFormatter;

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