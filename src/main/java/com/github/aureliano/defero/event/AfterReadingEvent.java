package com.github.aureliano.defero.event;

public class AfterReadingEvent {

	private long lineCounter;
	private boolean accepted;
	private Object data;
	
	public AfterReadingEvent(long lineCounter, boolean accepted, Object data) {
		this.lineCounter = lineCounter;
		this.accepted = accepted;
		this.data = data;
	}

	public long getLineCounter() {
		return lineCounter;
	}
	
	public boolean isAccepted() {
		return accepted;
	}

	public Object getData() {
		return data;
	}
}