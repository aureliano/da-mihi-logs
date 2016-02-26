package com.github.aureliano.evtbridge.app.command;

public enum Commands {

	HELP,
	VERSION;
	
	public String getId() {
		return this.name().toLowerCase();
	}
}