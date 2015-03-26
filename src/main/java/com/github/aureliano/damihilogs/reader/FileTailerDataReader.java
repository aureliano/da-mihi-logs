package com.github.aureliano.damihilogs.reader;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.github.aureliano.damihilogs.config.input.InputFileConfig;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.helper.ConfigHelper;

public class FileTailerDataReader extends AbstractDataReader {

	private InputFileConfig fileTailerConfiguration;
	
	private RandomAccessFile randomAccessFile;
	private long fileLength;
	private long filePointer;
	private long initialTimeMillis;
	
	private static final Logger logger = Logger.getLogger(FileTailerDataReader.class);
	
	public FileTailerDataReader() {
		this.fileLength = this.filePointer = this.initialTimeMillis = 0;
	}

	@Override
	public String nextData() {
		this.initialize();
		String data = this.readNextData();
		
		if (data != null) {
			return data;
		}
		
		while (this.shouldExecute()) {
			long currentFileLength = this.currentFileLength();
			while (this.fileLength == currentFileLength && this.shouldExecute()) {
				try {
					Thread.sleep(this.fileTailerConfiguration.getTailDelay());
					currentFileLength = this.currentFileLength();
				} catch (InterruptedException ex) {
					throw new DaMihiLogsException(ex);
				}
			}
			
			while (this.fileLength != currentFileLength) {
				if (this.fileLength > currentFileLength) {
					this.rotateFile();
				} else if (this.fileLength < currentFileLength) {
					this.initializeRandomAccessFile();
					return null;
				}
			}
		}
		
		super.markedToStop = true;
		return null;
	}

	private String readNextData() {
		String line = null;
		String data = null;
		
		try {
			line = this.readNextLine();
			if (line == null) {
				return null;
			}
			
			super.executeBeforeReadingMethodListeners();
			
			data = super.prepareLogEvent(line);
			super.executeAfterReadingMethodListeners(data);
			
			this.filePointer = this.randomAccessFile.getFilePointer() + 1;
			
			return data;
		} catch (IOException ex) {
			throw new DaMihiLogsException(ex);
		}
	}

	private void rotateFile() {
		logger.info("File got smaller! Maybe file rotation? Reseting and reading from beginning.");
		this.fileLength = this.currentFileLength();
		this.filePointer = super.lineCounter = 0;
	}

	@Override
	public void endResources() {
		logger.info(" >>> Flushing and closing stream reader.");
		if (this.randomAccessFile == null) {
			return;
		}
		
		try {
			this.randomAccessFile.close();
			this.randomAccessFile = null;
		} catch (IOException ex) {
			throw new DaMihiLogsException(ex);
		}
	}
	
	private boolean shouldExecute() {
		long diff = System.currentTimeMillis() - this.initialTimeMillis;
		return (diff < this.fileTailerConfiguration.getTailInterval());
	}
	
	private long currentFileLength() {
		return new File(this.fileTailerConfiguration.getFile().getPath()).length();
	}
	
	private void initialize() {
		if (this.initialTimeMillis > 0) {
			return;
		}
		
		this.initialTimeMillis = System.currentTimeMillis();
		
		this.initializeRandomAccessFile();
		if (this.fileTailerConfiguration == null) {
			this.fileTailerConfiguration = (InputFileConfig) super.inputConfiguration;
		}
		
		logger.info("Reading data from " + this.fileTailerConfiguration.getFile().getPath());
		logger.info("Delay milliseconds: " + this.fileTailerConfiguration.getTailDelay());
		logger.info("Tail about " + this.fileTailerConfiguration.getTailInterval() + " milliseconds.");
		logger.info("Data encondig: " + this.fileTailerConfiguration.getEncoding());
	}
	
	private void initializeRandomAccessFile() {
		try {
			this.randomAccessFile = new RandomAccessFile(this.fileTailerConfiguration.getFile(), "r");
			this.fileLength = this.fileTailerConfiguration.getFile().length();
			this.randomAccessFile.seek(this.filePointer);
			
			this.fileTailerConfiguration.withFile(new File(this.fileTailerConfiguration.getFile().getPath()));
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
				line = this.randomAccessFile.readLine();
				if (line != null) {
					line = new String(line.getBytes(), this.fileTailerConfiguration.getEncoding());
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
		super.readingProperties.put("input.config." + this.fileTailerConfiguration.getConfigurationId() + ".last.line", super.lineCounter);
		
		return super.readingProperties;
	}

	@Override
	public void loadLastExecutionLog(Properties properties) {
		this.fileTailerConfiguration = (InputFileConfig) super.inputConfiguration;
		
		String key = "input.config." + this.fileTailerConfiguration.getConfigurationId() + ".last.line";
		String value = properties.getProperty(key);
		if (value != null) {
			super.readingProperties.put(key, value);
			if ((this.fileTailerConfiguration.isUseLastExecutionRecords()) &&
					(this.fileTailerConfiguration.getStartPosition() == null)) {
				this.fileTailerConfiguration.withStartPosition(Integer.parseInt(value));
			}
		}
		
		ConfigHelper.inputConfigValidation(this.fileTailerConfiguration);
	}
}