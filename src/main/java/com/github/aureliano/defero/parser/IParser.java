package com.github.aureliano.defero.parser;

public interface IParser<T> {

	public abstract T parse(String text);
	
	public abstract boolean accept(String text);
}