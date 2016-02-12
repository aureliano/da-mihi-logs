package com.github.aureliano.evtbridge.core.filter;

public class DefaultEmptyFilter implements IEventFielter {

	public DefaultEmptyFilter() {
		super();
	}

	@Override
	public boolean accept(Object data) {
		return true;
	}
}