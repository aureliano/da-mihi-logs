package com.github.aureliano.damihilogs.parser;

/**
 * Line by line text parser.
 */
public class PlainTextParser implements IParser<String> {

	public PlainTextParser() {
		super();
	}

	@Override
	public String parse(String text) {
		return text;
	}

	@Override
	public boolean accept(String text) {
		return true;
	}
}