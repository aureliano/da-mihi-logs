package com.github.aureliano.damihilogs.config.output;

import java.io.File;
import java.util.Properties;

import com.github.aureliano.damihilogs.filter.IEventFielter;
import com.github.aureliano.damihilogs.parser.IParser;

public class FileOutputConfig implements IConfigOutput {

	private File file;
	private boolean append;
	private String encoding;
	private IParser<?> parser;
	private IEventFielter filter;
	private Properties metadata;
	
	public FileOutputConfig() {
		this.append = false;
		this.encoding = "UTF-8";
		this.metadata = new Properties();
	}

	@Override
	public String outputType() {
		return "FILE";
	}

	public File getFile() {
		return file;
	}

	public FileOutputConfig withFile(File file) {
		this.file = file;
		return this;
	}
	
	public FileOutputConfig withFile(String path) {
		this.file = new File(path);
		return this;
	}

	public boolean isAppend() {
		return append;
	}

	public FileOutputConfig withAppend(boolean append) {
		this.append = append;
		return this;
	}

	public String getEncoding() {
		return encoding;
	}

	public FileOutputConfig withEncoding(String encoding) {
		this.encoding = encoding;
		return this;
	}

	@Override
	public IParser<?> getParser() {
		return this.parser;
	}

	@Override
	public IConfigOutput withParser(IParser<?> parser) {
		this.parser = parser;
		return this;
	}

	@Override
	public IEventFielter getFilter() {
		return this.filter;
	}

	@Override
	public IConfigOutput withFilter(IEventFielter filter) {
		this.filter = filter;
		return this;
	}
	
	public FileOutputConfig clone() {
		return new FileOutputConfig()
			.withAppend(this.append)
			.withEncoding(this.encoding)
			.withFile(this.file)
			.withMetadata(this.metadata);
	}
	
	@Override
	public FileOutputConfig putMetadata(String key, String value) {
		this.metadata.put(key, value);
		return this;
	}
	
	@Override
	public String getMetadata(String key) {
		return this.metadata.getProperty(key);
	}
	
	protected FileOutputConfig withMetadata(Properties properties) {
		this.metadata = properties;
		return this;
	}

	@Override
	public Properties getMetadata() {
		return this.metadata;
	}
}