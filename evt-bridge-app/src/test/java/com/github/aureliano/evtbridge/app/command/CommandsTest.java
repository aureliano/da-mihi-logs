package com.github.aureliano.evtbridge.app.command;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CommandsTest {

	@Test
	public void testCommands() {
		assertEquals(new HelpCommand().id(), Commands.HELP.getId());
		assertEquals(new VersionCommand().id(), Commands.VERSION.getId());
		assertEquals(new SchemataCommand().id(), Commands.SCHEMATA.getId());
	}
}