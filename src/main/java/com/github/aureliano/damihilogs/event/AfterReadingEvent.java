package com.github.aureliano.damihilogs.event;

public class AfterReadingEvent {

	private long lineCounter;
	private Object data;
	
	public AfterReadingEvent(long lineCounter, Object data) {
		this.lineCounter = lineCounter;
		this.data = data;
	}

	public long getLineCounter() {
		return lineCounter;
	}

	public Object getData() {
		return data;
	}
}