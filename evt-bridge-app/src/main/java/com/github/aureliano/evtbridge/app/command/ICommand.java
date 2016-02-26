package com.github.aureliano.evtbridge.app.command;

import com.github.aureliano.evtbridge.app.ErrorCode;

public interface ICommand {

	public abstract ErrorCode execute();
	
	public abstract String id();
}