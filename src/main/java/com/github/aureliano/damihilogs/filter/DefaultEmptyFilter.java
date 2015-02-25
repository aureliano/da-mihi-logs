package com.github.aureliano.damihilogs.filter;

public class DefaultEmptyFilter implements IEventFielter {

	public DefaultEmptyFilter() {
		super();
	}

	@Override
	public boolean accept(Object data) {
		return true;
	}
}