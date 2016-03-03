package com.github.aureliano.evtbridge.app.command;

public enum Commands {

	RUN,
	HELP,
	VERSION,
	SCHEMATA,
	SCHEMA,
	MATCHER,
	PARSER,
	FILTER,
	FORMATTER;
	
	public String getId() {
		return this.name().toLowerCase();
	}
}