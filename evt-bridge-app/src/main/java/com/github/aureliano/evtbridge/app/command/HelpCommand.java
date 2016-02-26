package com.github.aureliano.evtbridge.app.command;

import com.github.aureliano.evtbridge.app.ErrorCode;

public class HelpCommand implements ICommand {

	public HelpCommand() {}
	
	public ErrorCode execute() {
		System.out.println("Help not supported yet.");
		return null;
	}
}