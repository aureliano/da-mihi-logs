package com.github.aureliano.evtbridge.app;

import com.github.aureliano.evtbridge.app.command.HelpCommand;
import com.github.aureliano.evtbridge.app.command.ICommand;
import com.github.aureliano.evtbridge.app.helper.CliHelper;

public class Bootstrap {
	
	public Bootstrap() {
		super();
	}
	
	public static void main(String[] args) {
		new Bootstrap().startUp(args);
	}
	
	public void startUp(String[] args) {
		if (args.length == 0) {
			new HelpCommand().execute();;
		} else {
			this.executeCommand(args);
		}
	}
	
	private void executeCommand(String[] args) {
		ICommand command = CliHelper.buildCommand(args);
		if (command == null) {
			this.exit(ErrorCode.COMMAND_NOT_FOUND);
		}
		
		ErrorCode error = command.execute();
		if (error != null) {
			this.exit(error);
		}
	}
	
	private void exit(ErrorCode error) {
		System.exit(error.getCode());
	}
}