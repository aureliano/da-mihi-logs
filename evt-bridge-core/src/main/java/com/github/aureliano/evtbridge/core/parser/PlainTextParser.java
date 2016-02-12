package com.github.aureliano.evtbridge.core.parser;

import com.github.aureliano.evtbridge.common.helper.StringHelper;

/**
 * Line by line text parser.
 */
public class PlainTextParser implements IParser<String> {

	public PlainTextParser() {
		super();
	}

	@Override
	public String parse(String text) {
		return StringHelper.toString(text);
	}
}