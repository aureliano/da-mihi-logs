package com.github.aureliano.evtbridge.app.command;

public enum Commands {

	HELP,
	VERSION,
	SCHEMATA,
	SCHEMA,
	MATCHER,
	PARSER,
	FILTER;
	
	public String getId() {
		return this.name().toLowerCase();
	}
}