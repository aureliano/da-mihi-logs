package com.github.aureliano.evtbridge.core.agent;

import com.github.aureliano.evtbridge.core.config.IConfigInput;
import com.github.aureliano.evtbridge.core.config.IConfiguration;
import com.github.aureliano.evtbridge.core.event.AfterReadingEvent;
import com.github.aureliano.evtbridge.core.event.BeforeReadingEvent;
import com.github.aureliano.evtbridge.core.event.StepParseEvent;
import com.github.aureliano.evtbridge.core.listener.DataReadingListener;

public abstract class AbstractDataReader implements IDataReader {

	protected IConfigInput inputConfiguration;
	protected long lineCounter;
	protected String unprocessedLine;
	protected boolean markedToStop;
	
	public AbstractDataReader() {
		this.lineCounter = 0;
		this.markedToStop = false;
	}
	
	@Override
	public String nextData() {
		String line = this.readNextLine();
		
		if (line == null) {
			this.markedToStop = true;
			return null;
		}
		
		return this.prepareLogEvent(line);
	}
	
	@Override
	public IConfigInput getConfiguration() {
		return this.inputConfiguration;
	}
	
	@Override
	public IDataReader withConfiguration(IConfiguration configuration) {
		this.inputConfiguration = (IConfigInput) configuration;
		return this;
	}

	@Override
	public boolean keepReading() {
		return !this.markedToStop;
	}
	
	protected String prepareLogEvent(String line) {
		int counter = 0;
		StringBuilder buffer = new StringBuilder(line);
		
		for (DataReadingListener listener : this.inputConfiguration.getDataReadingListeners()) {
			listener.stepLineParse(new StepParseEvent(this.inputConfiguration, (counter + 1), line, buffer.toString()));
		}
		
		String logEvent = this.inputConfiguration.getMatcher().endMatch(buffer.toString());
		if (!this.inputConfiguration.getMatcher().isMultiLine()) {
			return logEvent;
		}
		
		if (!this.inputConfiguration.getMatcher().matches(buffer.toString())) {
			return null;
		}
		
		while (logEvent == null) {
			line = this.readNextLine();
			buffer.append("\n").append(line);
			
			for (DataReadingListener listener : this.inputConfiguration.getDataReadingListeners()) {
				listener.stepLineParse(new StepParseEvent(this.inputConfiguration, (counter + 1), line, buffer.toString()));
			}
			
			logEvent = this.inputConfiguration.getMatcher().endMatch(buffer.toString());
		}
		
		this.unprocessedLine = line;
		return logEvent;
	}
	
	@Override
	public String readNextLine() {
		String line = null;
		if (this.unprocessedLine != null) {
			line = this.unprocessedLine;
			this.unprocessedLine = null;
		} else {
			line = this.readLine();
			if (line != null) {
				this.lineCounter++;
			}
		}
		
		return line;
	}
	
	@Override
	public void executeBeforeReadingListeners() {
		for (DataReadingListener listener : this.inputConfiguration.getDataReadingListeners()) {
			listener.beforeDataReading(new BeforeReadingEvent(this.inputConfiguration, this.lineCounter));
		}
	}
	
	@Override
	public void executeAfterReadingListeners(String data) {
		for (DataReadingListener listener : this.inputConfiguration.getDataReadingListeners()) {
			listener.afterDataReading(new AfterReadingEvent(this.inputConfiguration, this.lineCounter, data));
		}
	}
}