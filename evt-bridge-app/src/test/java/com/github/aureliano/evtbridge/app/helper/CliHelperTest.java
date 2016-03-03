package com.github.aureliano.evtbridge.app.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import com.github.aureliano.evtbridge.app.command.HelpCommand;
import com.github.aureliano.evtbridge.app.command.ICommand;
import com.github.aureliano.evtbridge.app.command.RunCommand;
import com.github.aureliano.evtbridge.app.command.SchemaCommand;
import com.github.aureliano.evtbridge.app.command.SchemataCommand;
import com.github.aureliano.evtbridge.app.command.VersionCommand;

import joptsimple.OptionParser;
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
		OptionParser parser = CliHelper.parseOptions();
		OptionSet options = parser.parse(new String[0]);
		ICommand command = CliHelper.buildAppCommand(options, Arrays.asList(""));
		assertNull(command);
		
		options = parser.parse(new String[] { "-h" });
		command = CliHelper.buildAppCommand(options, Arrays.asList("help"));
		assertTrue(command instanceof HelpCommand);
		
		options = parser.parse(new String[] { "-v" });
		command = CliHelper.buildAppCommand(options, Arrays.asList("version"));
		assertTrue(command instanceof VersionCommand);
		
		options = parser.parse(new String[] { "--schemata" });
		command = CliHelper.buildAppCommand(options, Arrays.asList("schemata"));
		assertTrue(command instanceof SchemataCommand);
		
		options = parser.parse(new String[] { "--schema", "-t", "input", "-n", "standard", "-f", "json" });
		command = CliHelper.buildAppCommand(options, Arrays.asList("schema"));
		assertTrue(command instanceof SchemaCommand);
		
		options = parser.parse(new String[] { "--run", "-c", "path/to/configuration/file" });
		command = CliHelper.buildAppCommand(options, Arrays.asList("run"));
		assertTrue(command instanceof RunCommand);
	}
	
	private void _assertCliOption(String shortcut, String option) {
		OptionParser parser = CliHelper.parseOptions();
		OptionSet options = parser.parse(new String[] { "-" + shortcut });
		assertTrue(options.has(option));
		
		options = parser.parse(new String[] { "--" + option });
		assertTrue(options.has(option));
	}
}