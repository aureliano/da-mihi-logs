package com.github.aureliano.damihilogs.event;

import com.github.aureliano.damihilogs.exception.DeferoException;

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