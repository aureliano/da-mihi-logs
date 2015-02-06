package com.github.aureliano.defero.reader;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import com.github.aureliano.defero.config.input.IConfigInput;
import com.github.aureliano.defero.config.input.InputFileConfig;
import com.github.aureliano.defero.exception.DeferoException;
import com.github.aureliano.defero.parser.IParser;

public class FileDataReader implements IDataReader {

	private InputFileConfig inputConfiguration;
	private IParser parser;
	private LineIterator lineIterator;
	private int lineCounter;
	
	public FileDataReader() {
		this.lineCounter = 0;
	}

	@Override
	public IDataReader withInputConfiguration(IConfigInput config) {
		this.inputConfiguration = (InputFileConfig) config;
		return this;
	}

	@Override
	public IDataReader withParser(IParser parser) {
		this.parser = parser;
		return this;
	}

	@Override
	public Object nextData() {
		this.initialize();
		
		if (!this.lineIterator.hasNext()) {
			LineIterator.closeQuietly(this.lineIterator);
			return null;
		}
		
		lineCounter++;
		
		while (this.inputConfiguration.getStartPosition() < lineCounter) {
			this.lineIterator.nextLine();
			lineCounter++;
		}
		
		return this.parseData();
	}
	
	private String parseData() {
		int maxParseTries = 10000;
		int counter = 0;
		StringBuilder buffer = new StringBuilder(this.lineIterator.nextLine());
		
		while (!this.parser.accept(buffer.toString())) {
			if (maxParseTries >= ++counter) {
				throw new DeferoException("Max parse tries overflow.");
			}
			
			buffer.append("\n").append(this.lineIterator.nextLine());
		}
		
		return buffer.toString();
	}
	
	private void initialize() {
		if (this.lineIterator != null) {
			return;
		}
		
		try {
			this.lineIterator = FileUtils.lineIterator(
					this.inputConfiguration.getFile(), this.inputConfiguration.getEncoding());
		} catch (IOException ex) {
			throw new DeferoException(ex);
		}
	}
}