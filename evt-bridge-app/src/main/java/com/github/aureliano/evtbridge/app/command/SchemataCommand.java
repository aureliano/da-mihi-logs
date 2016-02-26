package com.github.aureliano.evtbridge.app.command;

import java.util.List;

import com.github.aureliano.evtbridge.app.ErrorCode;
import com.github.aureliano.evtbridge.app.helper.ConfigurationSchemaHelper;
import com.github.aureliano.evtbridge.common.helper.StringHelper;

public class SchemataCommand implements ICommand {

	public SchemataCommand() {}

	public ErrorCode execute() {
		List<String> schemata = ConfigurationSchemaHelper.fetchSchemaNames();
		String message = new StringBuilder()
			.append("Configuration schemata:")
			.append("\n - ")
			.append(StringHelper.join(schemata, "\n - "))
			.toString();
		
		System.out.println(message);
		return null;
	}

	public String id() {
		return Commands.SCHEMATA.getId();
	}
}