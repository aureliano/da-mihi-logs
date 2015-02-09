package com.github.aureliano.defero.parser;

/**
 * Line by line text parser.
 */
public class PlainText implements IParser<String> {

	public PlainText() {
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