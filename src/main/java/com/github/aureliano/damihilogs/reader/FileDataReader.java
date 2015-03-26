package com.github.aureliano.damihilogs.reader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.github.aureliano.damihilogs.config.input.InputFileConfig;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
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
			throw new DaMihiLogsException(ex);
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
		
		if (this.fileInputConfiguration.getStartPosition() == null) {
			this.fileInputConfiguration.withStartPosition(0);
		}
		
		logger.info("Reading data from " + this.fileInputConfiguration.getFile().getPath());
		logger.info("Starting from line " + this.fileInputConfiguration.getStartPosition());
		logger.info("Data encondig: " + this.fileInputConfiguration.getEncoding());
		
		try {
			this.bufferedReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(
					this.fileInputConfiguration.getFile()), this.fileInputConfiguration.getEncoding()));
		} catch (IOException ex) {
			throw new DaMihiLogsException(ex);
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
			throw new DaMihiLogsException(ex);
		}
	}

	@Override
	public Map<String, Object> executionLog() {
		super.readingProperties.put("input.config." + this.fileInputConfiguration.getConfigurationId() + ".last.line", super.lineCounter);
		
		return super.readingProperties;
	}

	@Override
	public void loadLastExecutionLog(Properties properties) {
		this.fileInputConfiguration = (InputFileConfig) super.inputConfiguration;
		
		String key = "input.config." + this.fileInputConfiguration.getConfigurationId() + ".last.line";
		String value = properties.getProperty(key);
		if (value != null) {
			super.readingProperties.put(key, value);
			if ((this.fileInputConfiguration.isUseLastExecutionRecords()) &&
					(this.fileInputConfiguration.getStartPosition() == null)) {
				this.fileInputConfiguration.withStartPosition(Integer.parseInt(value));
			}
		}
		
		ConfigHelper.inputConfigValidation(this.fileInputConfiguration);
	}
}