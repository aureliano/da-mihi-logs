package com.github.aureliano.evtbridge.app.command;

import java.lang.reflect.Method;

import com.github.aureliano.evtbridge.app.ErrorCode;
import com.github.aureliano.evtbridge.app.helper.ConfigurationSchemaHelper;
import com.github.aureliano.evtbridge.common.exception.EventBridgeException;
import com.github.aureliano.evtbridge.common.helper.StringHelper;
import com.github.aureliano.evtbridge.core.SchemaTypes;
import com.github.aureliano.evtbridge.core.config.InputConfigTypes;
import com.github.aureliano.evtbridge.core.config.OutputConfigTypes;
import com.github.aureliano.evtbridge.core.doc.DocumentationSourceTypes;
import com.github.aureliano.evtbridge.core.schedule.SchedulerTypes;

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
		SchemaTypes schemaType = null;
		
		try {
			schemaType = SchemaTypes.valueOf(this.type.toUpperCase());
		} catch (Exception ex) {
			System.err.println("Invalid schema type (-t --type) parameter [" + this.type + "]");
			return ErrorCode.SCHEMA_PARAM_ERROR;
		}
		
		ErrorCode error = this.validateName(schemaType);
		if (error != null) {
			return error;
		}
		
		try {
			DocumentationSourceTypes.valueOf(this.format.toUpperCase());
		} catch (IllegalArgumentException ex) {
			System.err.println("Invalid schema format (-f --format) parameter [" + this.format + "]");
			return ErrorCode.SCHEMA_PARAM_ERROR;
		}
		
		return null;
	}
	
	private ErrorCode validateName(SchemaTypes schemaType) {
		if (!StringHelper.isEmpty(this.name)) {
			switch (schemaType) {
			case SCHEDULER:
				return this.validateNameWithSchema(SchedulerTypes.class);
			case INPUT:
				return this.validateNameWithSchema(InputConfigTypes.class);
			case OUTPUT:
				return this.validateNameWithSchema(OutputConfigTypes.class);
			default:
				throw new EventBridgeException("Unsupported schema type: [" + this.type + "]");
			}
		}
		
		return null;
	}
	
	private ErrorCode validateNameWithSchema(Class<?> e) {
		try {
			Method method = e.getMethod("valueOf", new Class<?>[] { String.class });
			method.invoke(null, this.name.toUpperCase());
		} catch (Exception ex) {
			System.err.println("Invalid schema name (-n --name) parameter [" + this.name + "]");
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