package com.github.aureliano.defero.config;

import java.io.File;

public class InputConfig {

	private File file;
	private int startPosition;

	public InputConfig() {
		this.startPosition = 0;
	}

	public File getFile() {
		return file;
	}

	public InputConfig withFile(File file) {
		this.file = file;
		return this;
	}

	public InputConfig withFile(String path) {
		this.file = new File(path);
		return this;
	}
	
	public int getStartPosition() {
		return startPosition;
	}
	
	public InputConfig withStartPosition(int startPosition) {
		this.startPosition = startPosition;
		return this;
	}
}