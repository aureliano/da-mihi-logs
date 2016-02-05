package com.github.aureliano.evtbridge.core.filter;

public interface IEventFielter {

	public abstract boolean accept(Object data);
}