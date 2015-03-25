package com.github.aureliano.damihilogs.config.input;

import java.io.File;
import java.util.Properties;

public class InputFileConfig implements IConfigInput {

	private File file;
	private int startPosition;
	private String encoding;
	private boolean tailFile;
	private long tailDelay;
	private long tailInterval;
	private String id;
	private boolean useLastExecutionRecords;
	private Properties metadata;

	public InputFileConfig() {
		this.startPosition = 0;
		this.encoding = "UTF-8";
		this.tailFile = false;
		this.tailDelay = 1000;
		this.tailInterval = 0;
		this.metadata = new Properties();
	}
	
	@Override
	public String getConfigurationId() {
		return id;
	}
	
	@Override
	public InputFileConfig withConfigurationId(String id) {
		this.id = id;
		return this;
	}

	public File getFile() {
		return file;
	}

	public InputFileConfig withFile(File file) {
		this.file = file;
		return this;
	}

	public InputFileConfig withFile(String path) {
		this.file = new File(path);
		return this;
	}
	
	public int getStartPosition() {
		return startPosition;
	}
	
	public InputFileConfig withStartPosition(int startPosition) {
		this.startPosition = startPosition;
		return this;
	}
	
	public String getEncoding() {
		return encoding;
	}
	
	public InputFileConfig withEncoding(String encoding) {
		this.encoding = encoding;
		return this;
	}

	public boolean isTailFile() {
		return this.tailFile;
	}
	
	public InputFileConfig withTailFile(boolean tailFile) {
		this.tailFile = tailFile;
		return this;
	}
	
	public long getTailDelay() {
		return tailDelay;
	}
	
	public InputFileConfig withTailDelay(long tailDelay) {
		this.tailDelay = tailDelay;
		return this;
	}
	
	public long getTailInterval() {
		return tailInterval;
	}
	
	public InputFileConfig withTailInterval(long tailInterval) {
		this.tailInterval = tailInterval;
		return this;
	}
	
	@Override
	public boolean isUseLastExecutionRecords() {
		return this.useLastExecutionRecords;
	}

	@Override
	public InputFileConfig withUseLastExecutionRecords(boolean useLastExecutionRecords) {
		this.useLastExecutionRecords = useLastExecutionRecords;
		return this;
	}
	
	@Override
	public InputFileConfig clone() {
		return new InputFileConfig()
			.withEncoding(this.encoding)
			.withFile(this.file)
			.withStartPosition(this.startPosition)
			.withTailDelay(this.tailDelay)
			.withTailFile(this.tailFile)
			.withTailInterval(this.tailInterval)
			.withConfigurationId(this.id)
			.withMetadata(this.metadata);
	}
	
	@Override
	public InputFileConfig putMetadata(String key, String value) {
		this.metadata.put(key, value);
		return this;
	}
	
	@Override
	public String getMetadata(String key) {
		return this.metadata.getProperty(key);
	}
	
	protected InputFileConfig withMetadata(Properties properties) {
		this.metadata = properties;
		return this;
	}

	@Override
	public Properties getMetadata() {
		return this.metadata;
	}
}