package com.github.aureliano.evtbridge.app.command;

import com.github.aureliano.evtbridge.app.ErrorCode;
import com.github.aureliano.evtbridge.app.helper.ConfigurationSchemaHelper;
import com.github.aureliano.evtbridge.common.exception.EventBridgeException;
import com.github.aureliano.evtbridge.common.helper.StringHelper;
import com.github.aureliano.evtbridge.core.SchemaTypes;
import com.github.aureliano.evtbridge.core.doc.DocumentationSourceTypes;

public class SchemaCommand implements ICommand {

	private static final String DEFAULT_FORMAT = "json";
	
	private String type;
	private String name;
	private String format;
	
	public SchemaCommand() {}

	public ErrorCode execute() {
		if (StringHelper.isEmpty(this.format)) {
			this.format = DEFAULT_FORMAT;
		}
		
		ErrorCode errorCode = this.validate();
		if (errorCode == null) {
			System.out.println(ConfigurationSchemaHelper.fetchSchema(this.type, this.name, this.format));
		}
		
		return errorCode;
	}
	
	private ErrorCode validate() {
		if (StringHelper.isEmpty(this.type)) {
			throw new EventBridgeException("Parameter (-t --type) is required.");
		}
		
		try {
			SchemaTypes.valueOf(this.type.toUpperCase());
		} catch (IllegalArgumentException ex) {
			System.err.println("Invalid schema type parameter [" + this.type + "]");
			return ErrorCode.SCHEMA_PARAM_ERROR;
		}
		
		try {
			DocumentationSourceTypes.valueOf(this.format.toUpperCase());
		} catch (IllegalArgumentException ex) {
			System.err.println("Invalid schema format parameter [" + this.format+ "]");
			return ErrorCode.SCHEMA_PARAM_ERROR;
		}
		
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

	public String getFormat() {
		return format;
	}

	public SchemaCommand withFormat(String format) {
		this.format = format;
		return this;
	}
}