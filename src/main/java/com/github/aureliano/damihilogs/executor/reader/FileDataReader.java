package com.github.aureliano.damihilogs.executor.reader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.github.aureliano.damihilogs.config.input.FileInputConfig;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.helper.ConfigHelper;
import com.github.aureliano.damihilogs.helper.FileHelper;
import com.github.aureliano.damihilogs.inout.CompressMetadata;

public class FileDataReader extends AbstractDataReader {

	private FileInputConfig fileInputConfiguration;
	private BufferedReader bufferedReader;
	
	private static final Logger logger = Logger.getLogger(FileDataReader.class);
		
	public FileDataReader() {
		super();
	}

	@Override
	public void initializeResources() {
		this.initialize();
		this.prepareReading();
	}

	@Override
	public String readLine() {
		try {
			return this.bufferedReader.readLine();
		} catch (IOException ex) {
			throw new DaMihiLogsException(ex);
		}
	}
	
	@Override
	public void finalizeResources() {
		logger.debug(" >>> Flushing and closing stream reader.");
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

	@Override
	public Map<String, Object> executionLog() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("input.config." + this.fileInputConfiguration.getConfigurationId() + ".last.line", super.lineCounter);
		
		return map;
	}

	@Override
	public void loadLastExecutionLog(Properties properties) {
		this.fileInputConfiguration = (FileInputConfig) super.inputConfiguration;
		
		String key = "input.config." + this.fileInputConfiguration.getConfigurationId() + ".last.line";
		String value = properties.getProperty(key);
		if (value != null) {
			if ((this.fileInputConfiguration.isUseLastExecutionRecords()) &&
					(this.fileInputConfiguration.getStartPosition() == null)) {
				this.fileInputConfiguration.withStartPosition(Integer.parseInt(value));
			}
		}
		
		ConfigHelper.validateConfiguration(this.fileInputConfiguration);
	}
	
	private void prepareReading() {		
		while (this.fileInputConfiguration.getStartPosition() > super.lineCounter + 1) {
			String line = super.readNextLine();
			if (line == null) {
				return;
			}
		}
	}
	
	private void initialize() {
		this.fileInputConfiguration = (FileInputConfig) super.inputConfiguration;
		
		CompressMetadata decompressConfiguration = this.fileInputConfiguration.getDecompressFileConfiguration();
		if (decompressConfiguration != null) {
			FileHelper.decompress(decompressConfiguration);
			this.fileInputConfiguration.withFile(decompressConfiguration.getOutputFilePath());
		}
		
		logger.info("Reading data from " + this.fileInputConfiguration.getFile().getPath());
		logger.debug("Starting from line " + this.fileInputConfiguration.getStartPosition());
		logger.debug("Data encondig: " + this.fileInputConfiguration.getEncoding());
		
		try {
			this.bufferedReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(
					this.fileInputConfiguration.getFile()), this.fileInputConfiguration.getEncoding()));
		} catch (IOException ex) {
			throw new DaMihiLogsException(ex);
		}
	}
}