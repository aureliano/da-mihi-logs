package com.github.aureliano.defero.reader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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

public class FileDataReader implements IDataReader {

	private InputFileConfig inputConfiguration;
	private IMatcher matcher;
	private IParser<?> parser;
	private long lineCounter;
	private BufferedReader bufferedReader;
	private IEventFielter filter;	
	private List<DataReadingListener> listeners;
	
	private static final Logger logger = Logger.getLogger(FileDataReader.class.getName());
	
	private boolean markedToStop;
	private String unprocessedLine;
	
	public FileDataReader() {
		this.lineCounter = 0;
		this.markedToStop = false;
	}
	
	@Override
	public IConfigInput getInputConfiguration() {
		return inputConfiguration;
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
		return parser;
	}

	@Override
	public IDataReader withParser(IParser<?> parser) {
		this.parser = parser;
		return this;
	}
	
	@Override
	public List<DataReadingListener> getListeners() {
		return listeners;
	}
	
	@Override
	public IDataReader withListeners(List<DataReadingListener> listeners) {
		this.listeners = listeners;
		return this;
	}

	@Override
	public Object nextData() {
		this.initialize();
		String line = this.readNextLine();
		
		if (line == null) {
			this.markedToStop = true;
			return null;
		}
		
		this.prepareReading();
		Object data = null;
		boolean accepted = false;
		
		do {
			this.executeBeforeReadingMethodListeners();
			
			data = this.parser.parse(this.prepareLogEvent(line));
			if (data == null) {
				continue;
			}
			accepted = this.filter.accept(data);
			
			this.executeAfterReadingMethodListeners(data, accepted);
		} while (!accepted && (line = this.readNextLine()) != null);
		
		return (accepted) ? data : null;
	}
	
	@Override
	public long lastLine() {
		return this.lineCounter;
	}
	
	@Override
	public IEventFielter getFilter() {
		return filter;
	}
	
	@Override
	public IDataReader withFilter(IEventFielter filter) {
		this.filter = filter;
		return this;
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
	
	@Override
	public boolean keepReading() {
		return !this.markedToStop;
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
	
	private void prepareReading() {		
		while (this.inputConfiguration.getStartPosition() > this.lineCounter) {
			this.readNextLine();			
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
	
	private void initialize() {
		if (this.bufferedReader != null) {
			return;
		}
		
		if (this.filter == null) {
			this.filter = new DefaultEmptyFilter();
		}
		
		logger.info("Reading data from " + this.inputConfiguration.getFile().getPath());
		logger.info("Starting from line " + this.inputConfiguration.getStartPosition());
		logger.info("Data encondig: " + this.inputConfiguration.getEncoding());
		
		try {
			this.bufferedReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(
					this.inputConfiguration.getFile()), this.inputConfiguration.getEncoding()));
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
				line = this.bufferedReader.readLine();
				if (line != null) {
					this.lineCounter++;
				}
			}
			
			return line;
		} catch (IOException ex) {
			throw new DeferoException(ex);
		}
	}
}