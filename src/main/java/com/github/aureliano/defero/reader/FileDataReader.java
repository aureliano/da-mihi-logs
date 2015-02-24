package com.github.aureliano.defero.reader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.github.aureliano.defero.config.input.InputFileConfig;
import com.github.aureliano.defero.exception.DeferoException;
import com.github.aureliano.defero.filter.DefaultEmptyFilter;

public class FileDataReader extends AbstractDataReader {

	private InputFileConfig fileInputConfiguration;
	private BufferedReader bufferedReader;
	
	private static final Logger logger = Logger.getLogger(FileDataReader.class.getName());
		
	public FileDataReader() {
		super();
	}

	@Override
	public Object nextData() {
		this.initialize();
		
		this.prepareReading();
		String line = this.readNextLine();
		
		if (line == null) {
			super.markedToStop = true;
			return null;
		}
		Object data = null;
		boolean accepted = false;
		
		do {
			super.executeBeforeReadingMethodListeners();
			
			data = super.parser.parse(super.prepareLogEvent(line));
			if (data == null) {
				continue;
			}
			accepted = super.filter.accept(data);
			
			super.executeAfterReadingMethodListeners(data, accepted);
		} while (!accepted && (line = this.readNextLine()) != null);
		
		return (accepted) ? data : null;
	}
	
	@Override
	public void endResources() {
		logger.info(" >>> Flushing and closing stream reader.");
		if (this.bufferedReader == null) {
			return;
		}
		
		try {
			this.bufferedReader.close();
			this.bufferedReader = null;
		} catch (IOException ex) {
			throw new DeferoException(ex);
		}
	}
	
	private void prepareReading() {		
		while (this.fileInputConfiguration.getStartPosition() > super.lineCounter + 1) {
			String line = this.readNextLine();
			if (line == null) {
				return;
			}
		}
	}
	
	private void initialize() {
		if (this.bufferedReader != null) {
			return;
		}
		
		if (super.filter == null) {
			super.filter = new DefaultEmptyFilter();
		}
		
		this.fileInputConfiguration = (InputFileConfig) super.inputConfiguration;
		
		logger.info("Reading data from " + this.fileInputConfiguration.getFile().getPath());
		logger.info("Starting from line " + this.fileInputConfiguration.getStartPosition());
		logger.info("Data encondig: " + this.fileInputConfiguration.getEncoding());
		
		try {
			this.bufferedReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(
					this.fileInputConfiguration.getFile()), this.fileInputConfiguration.getEncoding()));
		} catch (IOException ex) {
			throw new DeferoException(ex);
		}
	}
	
	protected String readNextLine() {
		try {
			String line = null;
			if (super.unprocessedLine != null) {
				line = super.unprocessedLine;
				super.unprocessedLine = null;
			} else {
				line = this.bufferedReader.readLine();
				if (line != null) {
					super.lineCounter++;
				}
			}
			
			return line;
		} catch (IOException ex) {
			throw new DeferoException(ex);
		}
	}

	@Override
	public Map<String, Object> executionLog() {
		Map<String, Object> log = new HashMap<String, Object>();
		log.put("file.data.reader.last.line", super.lineCounter);
		
		return log;
	}
}