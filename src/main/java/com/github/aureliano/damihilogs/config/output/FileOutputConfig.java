package com.github.aureliano.damihilogs.config.output;

import java.io.File;

public class FileOutputConfig implements IConfigOutput {

	private File file;
	private boolean append;
	private String encoding;
	
	public FileOutputConfig() {
		this.append = false;
		this.encoding = "UTF-8";
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
	
	@SuppressWarnings("unchecked")
	public FileOutputConfig clone() {
		return new FileOutputConfig()
			.withAppend(this.append)
			.withEncoding(this.encoding)
			.withFile(this.file);
	}
}