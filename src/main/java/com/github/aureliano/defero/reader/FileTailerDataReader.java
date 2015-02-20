package com.github.aureliano.defero.reader;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.logging.Logger;

import com.github.aureliano.defero.config.input.IConfigInput;
import com.github.aureliano.defero.config.input.InputFileConfig;
import com.github.aureliano.defero.event.AfterReadingEvent;
import com.github.aureliano.defero.event.BeforeReadingEvent;
import com.github.aureliano.defero.event.StepParseEvent;
import com.github.aureliano.defero.exception.DeferoException;
import com.github.aureliano.defero.filter.DefaultEmptyFilter;
import com.github.aureliano.defero.filter.IEventFielter;
import com.github.aureliano.defero.listener.DataReadingListener;
import com.github.aureliano.defero.matcher.IMatcher;
import com.github.aureliano.defero.parser.IParser;

public class FileTailerDataReader implements IDataReader {

	private InputFileConfig inputConfiguration;
	private IMatcher matcher;
	private IParser<?> parser;
	private IEventFielter filter;
	
	private RandomAccessFile randomAccessFile;
	private long fileLength;
	private long filePointer;
	private List<DataReadingListener> listeners;
	private long lineCounter;

	private static final Logger logger = Logger.getLogger(FileTailerDataReader.class.getName());
	
	private long initialTimeMillis;
	private boolean markedToStop;
	private String unprocessedLine;
	
	public FileTailerDataReader() {
		this.fileLength = this.filePointer = this.lineCounter = this.initialTimeMillis = 0;
		this.markedToStop = false;
	}

	@Override
	public IConfigInput getInputConfiguration() {
		return this.inputConfiguration;
	}

	@Override
	public IDataReader withInputConfiguration(IConfigInput config) {
		this.inputConfiguration = (InputFileConfig) config;
		return this;
	}
	
	@Override
	public IMatcher getMatcher() {
		return this.matcher;
	}
	
	@Override
	public IDataReader withMatcher(IMatcher matcher) {
		this.matcher = matcher;
		return this;
	}

	@Override
	public IParser<?> getParser() {
		return this.parser;
	}

	@Override
	public IDataReader withParser(IParser<?> parser) {
		this.parser = parser;
		return this;
	}

	@Override
	public List<DataReadingListener> getListeners() {
		return this.listeners;
	}

	@Override
	public IDataReader withListeners(List<DataReadingListener> listeners) {
		this.listeners = listeners;
		return this;
	}

	@Override
	public Object nextData() {
		this.initialize();
		Object data = this.readNextData();
		if (data != null) {
			return data;
		}
		
		while (this.shouldExecute()) {
			long currentFileLength = this.currentFileLength();
			while (this.fileLength == currentFileLength && this.shouldExecute()) {
				try {
					Thread.sleep(this.inputConfiguration.getTailDelay());
					currentFileLength = this.currentFileLength();
				} catch (InterruptedException ex) {
					throw new DeferoException(ex);
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
		
		this.markedToStop = true;
		return null;
	}

	private Object readNextData() {
		String line = null;
		Object data = null;
		boolean accepted = false;
		
		try {
			line = this.readNextLine();
			if (line == null) {
				return null;
			}
			
			do {
				this.executeBeforeReadingMethodListeners();
				
				data = this.parser.parse(this.prepareLogEvent(line));
				if (data == null) {
					continue;
				}
				accepted = this.filter.accept(data);
				
				this.executeAfterReadingMethodListeners(data, accepted);				
			} while (!accepted && (line = this.readNextLine()) != null);
			
			this.filePointer = this.randomAccessFile.getFilePointer() + 1;
			
			return (accepted) ? data : null;
		} catch (IOException ex) {
			throw new DeferoException(ex);
		}
	}

	private void rotateFile() {
		logger.info("File got smaller! Maybe file rotation? Reseting and reading from beginning.");
		this.fileLength = this.currentFileLength();
		this.filePointer = this.lineCounter = 0;
	}
	
	private void executeBeforeReadingMethodListeners() {
		logger.fine("Execute beforeDataReading listeners.");
		for (DataReadingListener listener : this.listeners) {
			listener.beforeDataReading(new BeforeReadingEvent(this.inputConfiguration, this.lineCounter));
		}
	}
	
	private void executeAfterReadingMethodListeners(Object data, boolean accepted) {
		logger.fine("Execute afterDataReading listeners.");
		for (DataReadingListener listener : this.listeners) {
			listener.afterDataReading(new AfterReadingEvent(this.lineCounter, accepted, data));
		}
	}
	
	private String prepareLogEvent(String line) {
		int counter = 0;
		StringBuilder buffer = new StringBuilder(line);
		
		for (DataReadingListener listener : this.listeners) {
			listener.stepLineParse(new StepParseEvent(counter + 1, line, buffer.toString()));
		}
		
		String logEvent = this.matcher.endMatch(buffer.toString());
		if (!this.matcher.isMultiLine()) {
			return logEvent;
		}
		
		if (!this.matcher.matches(buffer.toString())) {
			return null;
		}
		
		while (logEvent == null) {
			line = this.readNextLine();
			buffer.append("\n").append(line);
			
			for (DataReadingListener listener : this.listeners) {
				listener.stepLineParse(new StepParseEvent((counter + 1), line, buffer.toString()));
			}
			
			logEvent = this.matcher.endMatch(buffer.toString());
		}
		
		this.unprocessedLine = line;
		return logEvent;
	}

	@Override
	public long lastLine() {
		return this.lineCounter;
	}

	@Override
	public IEventFielter getFilter() {
		return this.filter;
	}

	@Override
	public IDataReader withFilter(IEventFielter filter) {
		this.filter = filter;
		return this;
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
			throw new DeferoException(ex);
		}
	}
	
	@Override
	public boolean keepReading() {
		return !this.markedToStop;
	}
	
	private boolean shouldExecute() {
		long diff = System.currentTimeMillis() - this.initialTimeMillis;
		return (diff < this.inputConfiguration.getTailInterval());
	}
	
	private long currentFileLength() {
		return new File(this.inputConfiguration.getFile().getPath()).length();
	}
	
	private void initialize() {
		if (this.initialTimeMillis > 0) {
			return;
		}
		
		this.initialTimeMillis = System.currentTimeMillis();
		
		if (this.filter == null) {
			this.filter = new DefaultEmptyFilter();
		}
		
		this.initializeRandomAccessFile();
		
		logger.info("Reading data from " + this.inputConfiguration.getFile().getPath());
		logger.info("Delay milliseconds: " + this.inputConfiguration.getTailDelay());
		logger.info("Tail about " + this.inputConfiguration.getTailInterval() + " milliseconds.");
		logger.info("Data encondig: " + this.inputConfiguration.getEncoding());
	}
	
	private void initializeRandomAccessFile() {
		try {
			this.randomAccessFile = new RandomAccessFile(this.inputConfiguration.getFile(), "r");
			this.fileLength = this.inputConfiguration.getFile().length();
			this.randomAccessFile.seek(this.filePointer);
			
			this.inputConfiguration.withFile(new File(this.inputConfiguration.getFile().getPath()));
		} catch (IOException ex) {
			throw new DeferoException(ex);
		}
	}
	
	private String readNextLine() {		
		try {
			String line = null;
			if (this.unprocessedLine != null) {
				line = this.unprocessedLine;
				this.unprocessedLine = null;
			} else {
				line = this.randomAccessFile.readLine();
				if (line != null) {
					line = new String(line.getBytes(), this.inputConfiguration.getEncoding());
					this.lineCounter++;
				}
			}
			
			return line;
		} catch (IOException ex) {
			throw new DeferoException(ex);
		}
	}
}