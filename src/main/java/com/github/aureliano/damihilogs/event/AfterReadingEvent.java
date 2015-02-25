package com.github.aureliano.damihilogs.event;

import com.github.aureliano.damihilogs.exception.DeferoException;

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
	
	public void setData(Object value) {
		if ((this.data == null) && (value == null)) {
			return;
		} else if (((this.data == null) && (value != null)) || ((this.data != null) && (value == null))) {
			this.data = value;
			return;
		}
		
		if (!this.data.getClass().equals(value.getClass())) {
			throw new DeferoException("You cannot set " + value.getClass().getName() + " to " + this.data.getClass().getName());
		}
	}
}