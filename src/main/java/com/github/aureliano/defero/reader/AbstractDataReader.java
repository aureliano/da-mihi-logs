package com.github.aureliano.defero.reader;

import java.util.List;

import com.github.aureliano.defero.config.input.IConfigInput;
import com.github.aureliano.defero.event.AfterReadingEvent;
import com.github.aureliano.defero.event.BeforeReadingEvent;
import com.github.aureliano.defero.event.StepParseEvent;
import com.github.aureliano.defero.filter.IEventFielter;
import com.github.aureliano.defero.listener.DataReadingListener;
import com.github.aureliano.defero.matcher.IMatcher;
import com.github.aureliano.defero.parser.IParser;

public abstract class AbstractDataReader implements IDataReader {

	protected IConfigInput inputConfiguration;
	protected IMatcher matcher;
	protected IParser<?> parser;
	protected long lineCounter;
	protected IEventFielter filter;	
	protected List<DataReadingListener> listeners;
	protected String unprocessedLine;
	protected boolean markedToStop;
	
	public AbstractDataReader() {
		this.lineCounter = 0;
		this.markedToStop = false;
	}
	
	@Override
	public IConfigInput getInputConfiguration() {
		return this.inputConfiguration;
	}

	@Override
	public IDataReader withInputConfiguration(IConfigInput config) {
		this.inputConfiguration = config;
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
	public boolean keepReading() {
		return !this.markedToStop;
	}
	
	protected String prepareLogEvent(String line) {
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
	
	protected void executeBeforeReadingMethodListeners() {
		for (DataReadingListener listener : this.listeners) {
			listener.beforeDataReading(new BeforeReadingEvent(this.inputConfiguration, this.lineCounter));
		}
	}
	
	protected void executeAfterReadingMethodListeners(Object data, boolean accepted) {
		for (DataReadingListener listener : this.listeners) {
			listener.afterDataReading(new AfterReadingEvent(this.lineCounter, accepted, data));
		}
	}
	
	protected abstract String readNextLine();
}