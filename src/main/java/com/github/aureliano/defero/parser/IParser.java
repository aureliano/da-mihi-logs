package com.github.aureliano.defero.parser;

public interface IParser {

	public abstract Object parse(String text);
	
	public abstract boolean accept(String text);
}