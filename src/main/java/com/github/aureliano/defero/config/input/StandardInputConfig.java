package com.github.aureliano.defero.config.input;

public class StandardInputConfig implements IConfigInput {

	private String encoding;
	
	public StandardInputConfig() {
		this.encoding = "UTF-8";
	}
	
	public String getEncoding() {
		return encoding;
	}
	
	public StandardInputConfig withEncoding(String encoding) {
		this.encoding = encoding;
		return this;
	}

	@Override
	public String inputType() {
		return "STANDARD";
	}
}