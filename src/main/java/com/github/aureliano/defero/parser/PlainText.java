package com.github.aureliano.defero.parser;

/**
 * Line by line text parser.
 */
public class PlainText implements IParser {

	public PlainText() {
		super();
	}

	@Override
	public String parse(String text) {
		return text;
	}
}