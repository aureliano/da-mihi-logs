package com.github.aureliano.defero.config;

import java.io.File;

public class InputConfig {

	private File file;

	public InputConfig() {
		super();
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
}