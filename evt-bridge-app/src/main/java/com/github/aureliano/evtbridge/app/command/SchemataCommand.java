package com.github.aureliano.evtbridge.app.command;

import com.github.aureliano.evtbridge.app.ErrorCode;

public class SchemataCommand implements ICommand {

	public SchemataCommand() {}

	public ErrorCode execute() {
		throw new RuntimeException("Not implemented yet.");
	}

	public String id() {
		return Commands.SCHEMATA.getId();
	}
}