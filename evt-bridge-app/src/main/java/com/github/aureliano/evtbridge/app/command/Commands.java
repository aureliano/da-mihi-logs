package com.github.aureliano.evtbridge.app.command;

public enum Commands {

	HELP,
	VERSION,
	SCHEMATA,
	SCHEMA;
	
	public String getId() {
		return this.name().toLowerCase();
	}
}