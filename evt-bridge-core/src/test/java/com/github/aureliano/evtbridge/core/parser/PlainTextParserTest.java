package com.github.aureliano.evtbridge.core.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.aureliano.evtbridge.core.parser.IParser;
import com.github.aureliano.evtbridge.core.parser.PlainTextParser;

public class PlainTextParserTest {

	@Test
	public void testParse() {
		IParser<?> parser = new PlainTextParser();
		
		assertEquals("null", parser.parse(null));
		assertEquals("", parser.parse(""));
		assertEquals("Testing plain text parser.", parser.parse("Testing plain text parser."));
	}
}