package com.github.aureliano.evtbridge.core.filter;

public class EmptyFilter implements IEventFielter {

	public EmptyFilter() {
		super();
	}

	@Override
	public boolean accept(Object data) {
		return true;
	}
}