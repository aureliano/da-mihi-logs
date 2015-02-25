package com.github.aureliano.damihilogs.config.input;

public class StandardInputConfig implements IConfigInput {

	private String encoding;
	private String id;
	
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
	public String getConfigurationId() {
		return id;
	}

	@Override
	public StandardInputConfig withConfigurationId(String id) {
		this.id = id;
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public StandardInputConfig clone() {
		return new StandardInputConfig()
			.withEncoding(this.encoding)
			.withConfigurationId(this.id);
	}
}