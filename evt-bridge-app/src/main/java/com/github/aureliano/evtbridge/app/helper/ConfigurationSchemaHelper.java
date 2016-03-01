package com.github.aureliano.evtbridge.app.helper;

import com.github.aureliano.evtbridge.common.exception.EventBridgeException;
import com.github.aureliano.evtbridge.core.SchemaTypes;
import com.github.aureliano.evtbridge.core.doc.DocumentationSourceTypes;
import com.github.aureliano.evtbridge.core.doc.ISchemaBuilder;
import com.github.aureliano.evtbridge.core.doc.JsonSchemaBuilder;
import com.github.aureliano.evtbridge.core.doc.YamlSchemaBuilder;

public final class ConfigurationSchemaHelper {
	
	private ConfigurationSchemaHelper() {}
	
	public static String fetchSchema(String type, String name, String format) {
		final SchemaTypes schemaType = SchemaTypes.valueOf(type.toUpperCase());
		final DocumentationSourceTypes sourceType = DocumentationSourceTypes.valueOf(format.toUpperCase());
		
		return ConfigurationSchemaHelper.getSchemaBuilder(sourceType).build(schemaType);
	}
	
	private static ISchemaBuilder<String> getSchemaBuilder(DocumentationSourceTypes sourceType) {
		if (DocumentationSourceTypes.JSON.equals(sourceType)) {
			return new JsonSchemaBuilder();
		} else if (DocumentationSourceTypes.YAML.equals(sourceType)) {
			return new YamlSchemaBuilder();
		} else {
			throw new EventBridgeException("Unsupported source type '" + sourceType + "'.");
		}
	}
}