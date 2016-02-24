package com.github.aureliano.evtbridge.app.helper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CliHelperTest {

	@Test
	public void testSupportedFileTypes() {
		String expected = "json, yaml";
		String actual = CliHelper.supportedFileTypes();
		
		assertEquals(expected, actual);
	}
}