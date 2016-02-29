package com.github.aureliano.evtbridge.core.doc;

import com.github.aureliano.evtbridge.common.exception.EventBridgeException;
import com.github.aureliano.evtbridge.core.SchemaTypes;

public final class SchemaBuilderFactory {

	private SchemaBuilderFactory() {}
	
	public static ISchemaBuilder createBuilder(DocumentationSourceTypes sourceType, SchemaTypes schemaType) {
		switch (sourceType) {
			case JSON : return new JsonSchemaBuilder();
			case YAML : return new YamlSchemaBuilder();
			default : throw new EventBridgeException("Unsupported source type '" + sourceType + "'.");
		}
	}
}