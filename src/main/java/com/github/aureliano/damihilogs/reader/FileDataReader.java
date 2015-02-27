package com.github.aureliano.damihilogs.reader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.github.aureliano.damihilogs.config.input.InputFileConfig;
import com.github.aureliano.damihilogs.exception.DeferoException;
import com.github.aureliano.damihilogs.helper.ConfigHelper;

public class FileDataReader extends AbstractDataReader {

	private InputFileConfig fileInputConfiguration;
	private BufferedReader bufferedReader;
	
	private static final Logger logger = Logger.getLogger(FileDataReader.class);
		
	public FileDataReader() {
		super();
	}

	@Override
	public String nextData() {
		this.initialize();
		
		this.prepareReading();
		String line = this.readNextLine();
		
		if (line == null) {
			super.markedToStop = true;
			return null;
		}
		
		String data = null;		
		super.executeBeforeReadingMethodListeners();
		
		data = super.prepareLogEvent(line);
		super.executeAfterReadingMethodListeners(data);
		
		return data;
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
		
		if (this.fileInputConfiguration == null) {
			this.fileInputConfiguration = (InputFileConfig) super.inputConfiguration;
		}
		
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
		log.put("input.config." + this.fileInputConfiguration.getConfigurationId() + ".last.line", super.lineCounter);
		
		return log;
	}

	@Override
	public void loadLastExecutionLog(Properties properties) {
		this.fileInputConfiguration = (InputFileConfig) super.inputConfiguration;
		
		if (!this.fileInputConfiguration.isUseLastExecutionRecords()) {
			return;
		}
		
		String value = properties.getProperty("input.config." + this.fileInputConfiguration.getConfigurationId() + ".last.line");
		if (value != null) {
			this.fileInputConfiguration.withStartPosition(Integer.parseInt(value));
		}
		
		ConfigHelper.inputConfigValidation(this.fileInputConfiguration);
	}
}