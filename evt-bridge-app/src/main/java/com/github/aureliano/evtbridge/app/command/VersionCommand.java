package com.github.aureliano.evtbridge.app.command;

public class VersionCommand implements ICommand {

	public VersionCommand() {}
	
	public void execute() {
		System.out.println("Version not supported yet.");
	}
}