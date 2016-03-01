package com.github.aureliano.evtbridge.app.command;

import com.github.aureliano.evtbridge.app.ErrorCode;
import com.github.aureliano.evtbridge.common.helper.StringHelper;
import com.github.aureliano.evtbridge.core.SchemaTypes;

public class SchemataCommand implements ICommand {

	public SchemataCommand() {}

	public ErrorCode execute() {
		String message = new StringBuilder()
			.append("Configuration schemata:")
			.append("\n - ")
			.append(this.schemata())
			.toString();
		
		System.out.println(message);
		return null;
	}

	public String id() {
		return Commands.SCHEMATA.getId();
	}
	
	private String schemata() {
		return StringHelper.join(SchemaTypes.values(), "\n - ").toLowerCase();
	}
}