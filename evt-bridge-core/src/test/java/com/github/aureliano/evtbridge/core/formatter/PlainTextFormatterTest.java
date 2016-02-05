package com.github.aureliano.evtbridge.core.formatter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PlainTextFormatterTest {

	@Test
	public void testFormat() {
		IOutputFormatter formatter = new PlainTextFormatter();
		assertEquals("null", formatter.format(null));
		assertEquals("Plain text", formatter.format("Plain text"));
		assertEquals("12345", formatter.format(12345));
	}
}