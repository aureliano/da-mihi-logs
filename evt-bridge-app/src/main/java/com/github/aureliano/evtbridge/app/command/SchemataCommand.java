package com.github.aureliano.evtbridge.app.command;

import java.util.ArrayList;
import java.util.List;

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
		SchemaTypes[] types = SchemaTypes.values();
		List<String> schemata = new ArrayList<>(types.length);
		
		for (SchemaTypes type : types) {
			schemata.add(type.name().toLowerCase());
		}
		
		return StringHelper.join(schemata, "\n - ");
	}
}