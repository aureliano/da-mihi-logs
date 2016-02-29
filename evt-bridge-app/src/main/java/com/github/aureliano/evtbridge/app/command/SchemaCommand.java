package com.github.aureliano.evtbridge.app.command;

import com.github.aureliano.evtbridge.app.ErrorCode;
import com.github.aureliano.evtbridge.app.helper.ConfigurationSchemaHelper;
import com.github.aureliano.evtbridge.common.exception.EventBridgeException;
import com.github.aureliano.evtbridge.common.helper.StringHelper;

public class SchemaCommand implements ICommand {

	private String type;
	private String name;
	
	public SchemaCommand() {}

	public ErrorCode execute() {
		if (StringHelper.isEmpty(this.type)) {
			throw new EventBridgeException("Parameter (-t --type) is required.");
		}
		
		System.out.println(ConfigurationSchemaHelper.fetchSchema(this.type, this.name));
		return null;
	}

	public String id() {
		return Commands.SCHEMA.getId();
	}

	public String getType() {
		return type;
	}

	public SchemaCommand withType(String type) {
		this.type = type;
		return this;
	}

	public String getName() {
		return name;
	}

	public SchemaCommand withName(String name) {
		this.name = name;
		return this;
	}
}