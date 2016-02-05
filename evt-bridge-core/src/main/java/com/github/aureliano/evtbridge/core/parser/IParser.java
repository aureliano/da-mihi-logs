package com.github.aureliano.evtbridge.core.parser;

public interface IParser<T> {

	public abstract T parse(String text);
}