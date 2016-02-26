package com.github.aureliano.evtbridge.app.command;

public enum Commands {

	HELP,
	VERSION,
	SCHEMATA;
	
	public String getId() {
		return this.name().toLowerCase();
	}
}