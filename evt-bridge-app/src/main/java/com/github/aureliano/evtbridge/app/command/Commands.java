package com.github.aureliano.evtbridge.app.command;

public enum Commands {

	HELP,
	VERSION,
	SCHEMATA,
	SCHEMA,
	MATCHER,
	PARSER;
	
	public String getId() {
		return this.name().toLowerCase();
	}
}