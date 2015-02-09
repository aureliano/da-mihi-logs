package com.github.aureliano.defero.config.input;

import java.io.File;

public class InputFileConfig implements IConfigInput {

	private File file;
	private int startPosition;
	private String encoding;

	public InputFileConfig() {
		this.startPosition = 0;
		this.encoding = "UTF-8";
	}
	
	@Override
	public String inputType() {
		return "FILE";
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
}