package com.github.aureliano.defero.config.output;

public class StandardOutputConfig implements IConfigOutput {

	public StandardOutputConfig() {
		super();
	}

	@Override
	public String outputType() {
		return "STANDARD";
	}
}