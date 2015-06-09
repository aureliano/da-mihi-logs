package com.github.aureliano.damihilogs.executor.reader;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.github.aureliano.damihilogs.config.input.FileTailerInputConfig;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.helper.TimeHelper;

public class FileTailerDataReader extends AbstractDataReader {

	private FileTailerInputConfig fileTailerConfiguration;
	
	private RandomAccessFile randomAccessFile;
	private long fileLength;
	private long filePointer;
	private long initialTimeMillis;
	private boolean reachedEndOfFile;
	
	private Long tailDelay;
	private Long tailInterval;
	
	private static final Logger logger = Logger.getLogger(FileTailerDataReader.class);
	
	public FileTailerDataReader() {
		this.fileLength = this.filePointer = this.initialTimeMillis = 0;
		this.reachedEndOfFile = false;
	}
	
	@Override
	public void initializeResources() {
		this.initialize();
	}

	@Override
	public String nextData() {
		String data = this.readNextData();
		
		if (data != null) {
			return data;
		} else if (!this.reachedEndOfFile) {
			return null;
		}
		
		while (this.shouldExecute()) {
			long currentFileLength = this.currentFileLength();
			while ((this.fileLength == currentFileLength) && this.reachedEndOfFile && this.shouldExecute()) {
				try {
					Thread.sleep(this.tailDelay);
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

	@Override
	public Map<String, Object> executionLog() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("input.config." + this.fileTailerConfiguration.getConfigurationId() + ".last.line", super.lineCounter);
		
		return map;
	}
	
	@Override
	public String readLine() {
		try {
			String line = this.randomAccessFile.readLine();
			if (line == null) {
				reachedEndOfFile = true;
				return null;
			}
			
			return new String(line.getBytes(), this.fileTailerConfiguration.getEncoding());
		} catch (IOException ex) {
			throw new DaMihiLogsException(ex);
		}
	}

	@Override
	public void loadLastExecutionLog(Properties properties) { }

	@Override
	public void finalizeResources() {
		logger.debug(" >>> Flushing and closing stream reader.");
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

	private String readNextData() {
		String line = null;
		
		try {
			line = super.readNextLine();
			if (line == null) {
				return null;
			}
			
			this.filePointer = this.randomAccessFile.getFilePointer() + 1;
			
			return super.prepareLogEvent(line);
		} catch (IOException ex) {
			throw new DaMihiLogsException(ex);
		}
	}

	private void rotateFile() {
		logger.debug("File got smaller! Maybe file rotation? Reseting and reading from beginning.");
		this.fileLength = this.currentFileLength();
		this.filePointer = super.lineCounter = 0;
	}
	
	private boolean shouldExecute() {
		if (this.tailInterval == null) {
			return true;
		}
		
		long diff = System.currentTimeMillis() - this.initialTimeMillis;
		return (diff < this.tailInterval);
	}
	
	private long currentFileLength() {
		return new File(this.fileTailerConfiguration.getFile().getPath()).length();
	}
	
	private void initialize() {
		this.initialTimeMillis = System.currentTimeMillis();
		
		if (this.fileTailerConfiguration == null) {
			this.fileTailerConfiguration = (FileTailerInputConfig) super.inputConfiguration;
		}
		
		this.initializeRandomAccessFile();
		
		this.tailDelay = TimeHelper.convertToMilliseconds(this.fileTailerConfiguration.getTimeUnit(), this.fileTailerConfiguration.getTailDelay());
		if (this.fileTailerConfiguration.getTailInterval() != null) {
			this.tailInterval = TimeHelper.convertToMilliseconds(
				this.fileTailerConfiguration.getTimeUnit(), this.fileTailerConfiguration.getTailInterval());
		}
		
		logger.info("Reading data from " + this.fileTailerConfiguration.getFile().getPath());
		logger.info("Delay milliseconds: " + tailDelay);
		logger.info("Tail about " + this.tailInterval + " milliseconds.");
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
}