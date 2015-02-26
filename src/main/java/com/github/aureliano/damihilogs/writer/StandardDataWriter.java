package com.github.aureliano.damihilogs.writer;

import com.github.aureliano.damihilogs.filter.DefaultEmptyFilter;
import com.github.aureliano.damihilogs.formatter.PlainTextFormatter;

public class StandardDataWriter extends AbstractDataWriter {
	
	public StandardDataWriter() {
		super();
	}

	@Override
	public void write(Object data) {
		if (super.filter == null) {
			super.filter = new DefaultEmptyFilter();
		}
		
		if (super.outputFormatter == null) {
			super.outputFormatter = new PlainTextFormatter();
		}
		
		super.executeBeforeWritingMethodListeners(data);
		if (data == null) {
			return;
		}
		
		boolean accept = super.filter.accept(data);
		if (accept) {
			System.out.println(this.outputFormatter.format(data));
		}
		
		super.executeAfterWritingMethodListeners(data, accept);
	}
	
	@Override
	public void endResources() { }
}