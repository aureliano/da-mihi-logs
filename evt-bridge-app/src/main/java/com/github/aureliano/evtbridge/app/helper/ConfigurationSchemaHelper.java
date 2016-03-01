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
		
		if (SchemaTypes.ROOT.equals(schemaType)) {
			return ConfigurationSchemaHelper.getSchemaBuilder(sourceType).build(schemaType);
		}
		
		throw new EventBridgeException("Not supported yet.");
		
		/*if ("root".equals(type)) {
			schemaPath = FileHelper.buildPath(CONFIGURATION_SCHEMA_DIR, rootSchema.get("id").toString());
		} else {
			schemaPath = FileHelper.buildPath(CONFIGURATION_SCHEMA_DIR, findSchemaId(rootSchema, type, name));
		}
		
		try {
			return FileHelper.readResource(schemaPath);
		} catch (Exception ex) {
			throw new EventBridgeException(ex, "Could not load schema file " + schemaPath);
		}*/
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