package com.github.aureliano.damihilogs.reader;

import com.github.aureliano.damihilogs.config.IConfiguration;
import com.github.aureliano.damihilogs.config.input.IConfigInput;
import com.github.aureliano.damihilogs.event.AfterReadingEvent;
import com.github.aureliano.damihilogs.event.BeforeReadingEvent;
import com.github.aureliano.damihilogs.event.StepParseEvent;
import com.github.aureliano.damihilogs.listener.DataReadingListener;

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
			listener.stepLineParse(new StepParseEvent(counter + 1, line, buffer.toString()));
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
				listener.stepLineParse(new StepParseEvent((counter + 1), line, buffer.toString()));
			}
			
			logEvent = this.inputConfiguration.getMatcher().endMatch(buffer.toString());
		}
		
		this.unprocessedLine = line;
		return logEvent;
	}
	
	protected void executeBeforeReadingMethodListeners() {
		for (DataReadingListener listener : this.inputConfiguration.getDataReadingListeners()) {
			listener.beforeDataReading(new BeforeReadingEvent(this.inputConfiguration, this.lineCounter));
		}
	}
	
	protected void executeAfterReadingMethodListeners(Object data) {
		for (DataReadingListener listener : this.inputConfiguration.getDataReadingListeners()) {
			listener.afterDataReading(new AfterReadingEvent(this.lineCounter, data));
		}
	}
	
	protected abstract String readNextLine();
}