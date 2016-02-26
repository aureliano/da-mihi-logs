package com.github.aureliano.evtbridge.app.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.aureliano.evtbridge.app.command.HelpCommand;
import com.github.aureliano.evtbridge.app.command.ICommand;
import com.github.aureliano.evtbridge.app.command.VersionCommand;

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
	
	@Test
	public void testBuildCommand() {
		OptionSet options = CliHelper.buildOptionSet(new String[0]);
		ICommand command = CliHelper.buildCommand(new String[0], options);
		assertNull(command);
		
		options = CliHelper.buildOptionSet(new String[] { "-h" });
		command = CliHelper.buildCommand(new String[0], options);
		assertTrue(command instanceof HelpCommand);
		
		options = CliHelper.buildOptionSet(new String[] { "-v" });
		command = CliHelper.buildCommand(new String[0], options);
		assertTrue(command instanceof VersionCommand);
	}
	
	private void _assertCliOption(String shortcut, String option) {
		OptionSet options = CliHelper.buildOptionSet(new String[] { "-" + shortcut });
		assertTrue(options.has(option));
		
		options = CliHelper.buildOptionSet(new String[] { "--" + option });
		assertTrue(options.has(option));
	}
}