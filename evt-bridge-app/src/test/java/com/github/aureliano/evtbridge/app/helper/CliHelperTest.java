package com.github.aureliano.evtbridge.app.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import joptsimple.OptionSet;

public class CliHelperTest {

	@Test
	public void testSupportedFileTypes() {
		String expected = "json, yaml";
		String actual = CliHelper.supportedFileTypes();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testBuildOptionSet() {
		this._assertCliOption("h", "help");
		this._assertCliOption("v", "version");
	}
	
	private void _assertCliOption(String shortcut, String option) {
		OptionSet options = CliHelper.buildOptionSet(new String[] { "-" + shortcut });
		assertTrue(options.has(option));
		
		options = CliHelper.buildOptionSet(new String[] { "--" + option });
		assertTrue(options.has(option));
	}
}