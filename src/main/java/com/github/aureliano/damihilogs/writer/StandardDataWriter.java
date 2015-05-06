package com.github.aureliano.damihilogs.writer;

import com.github.aureliano.damihilogs.formatter.PlainTextFormatter;

public class StandardDataWriter extends AbstractDataWriter {
	
	public StandardDataWriter() {
		super();
	}

	@Override
	public void write(String content) {
		this.initialize();
		
		Object data = super.getParser().parse(content);
		super.executeBeforeWritingMethodListeners(data);
		if (data == null) {
			return;
		}
		
		boolean accept = super.getFilter().accept(data);
		if (accept) {
			System.out.println(super.getOutputConfiguration().getOutputFormatter().format(data));
		}
		
		super.executeAfterWritingMethodListeners(data, accept);
	}
	
	private void initialize() {
		if (super.getOutputConfiguration().getOutputFormatter() == null) {
			super.getOutputConfiguration().withOutputFormatter(new PlainTextFormatter());
		}
	}
	
	@Override
	public void endResources() { }
}