package com.github.aureliano.damihilogs.config.input;

import java.util.Properties;

public class StandardInputConfig implements IConfigInput {

	private String encoding;
	private String id;
	private boolean useLastExecutionRecords;
	private Properties metadata;
	
	public StandardInputConfig() {
		this.encoding = "UTF-8";
		this.metadata = new Properties();
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
	
	@Override
	public boolean isUseLastExecutionRecords() {
		return this.useLastExecutionRecords;
	}

	@Override
	public StandardInputConfig withUseLastExecutionRecords(boolean useLastExecutionRecords) {
		this.useLastExecutionRecords = useLastExecutionRecords;
		return this;
	}
	
	@Override
	public StandardInputConfig clone() {
		return new StandardInputConfig()
			.withEncoding(this.encoding)
			.withConfigurationId(this.id)
			.withMetadata(this.metadata);
	}
	
	@Override
	public StandardInputConfig putMetadata(String key, String value) {
		this.metadata.put(key, value);
		return this;
	}
	
	@Override
	public String getMetadata(String key) {
		return this.metadata.getProperty(key);
	}
	
	protected StandardInputConfig withMetadata(Properties properties) {
		this.metadata = properties;
		return this;
	}

	@Override
	public Properties getMetadata() {
		return this.metadata;
	}
}