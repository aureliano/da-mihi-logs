package com.github.aureliano.damihilogs.writer;

import com.github.aureliano.damihilogs.formatter.PlainTextFormatter;

public class StandardDataWriter extends AbstractDataWriter {
	
	public StandardDataWriter() {
		super();
	}

	@Override
	public void write(String content) {
		if (super.outputFormatter == null) {
			super.outputFormatter = new PlainTextFormatter();
		}
		
		Object data = super.getParser().parse(content);
		super.executeBeforeWritingMethodListeners(data);
		if (data == null) {
			return;
		}
		
		boolean accept = super.getFilter().accept(data);
		if (accept) {
			System.out.println(this.outputFormatter.format(data));
		}
		
		super.executeAfterWritingMethodListeners(data, accept);
	}
	
	@Override
	public void endResources() { }
}