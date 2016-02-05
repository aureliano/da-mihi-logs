package com.github.aureliano.evtbridge.core.agent;

import com.github.aureliano.evtbridge.core.config.IConfigInput;
import com.github.aureliano.evtbridge.core.config.IConfiguration;

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
		
		throw new UnsupportedOperationException("Not implemented yet.");
		//return this.prepareLogEvent(line);
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
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
	@Override
	public void executeAfterReadingListeners(String data) {
		throw new UnsupportedOperationException("Not implemented yet.");
	}
}