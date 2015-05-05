package com.github.aureliano.damihilogs.writer;

public class StandardDataWriter extends AbstractDataWriter {
	
	public StandardDataWriter() {
		super();
	}

	@Override
	public void write(String content) {
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
	
	@Override
	public void endResources() { }
}