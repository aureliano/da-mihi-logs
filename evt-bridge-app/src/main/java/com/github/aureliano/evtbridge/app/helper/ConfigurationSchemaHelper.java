package com.github.aureliano.evtbridge.app.helper;

import com.github.aureliano.evtbridge.common.exception.EventBridgeException;
import com.github.aureliano.evtbridge.common.helper.StringHelper;
import com.github.aureliano.evtbridge.core.SchemaTypes;
import com.github.aureliano.evtbridge.core.config.InputConfigTypes;
import com.github.aureliano.evtbridge.core.config.OutputConfigTypes;
import com.github.aureliano.evtbridge.core.doc.DocumentationSourceTypes;
import com.github.aureliano.evtbridge.core.doc.ISchemaBuilder;
import com.github.aureliano.evtbridge.core.doc.JsonSchemaBuilder;
import com.github.aureliano.evtbridge.core.doc.YamlSchemaBuilder;
import com.github.aureliano.evtbridge.core.schedule.SchedulerTypes;

public final class ConfigurationSchemaHelper {
	
	private ConfigurationSchemaHelper() {}
	
	public static String fetchSchema(String type, String name, String format) {
		final SchemaTypes schemaType = SchemaTypes.valueOf(type.toUpperCase());
		final DocumentationSourceTypes sourceType = DocumentationSourceTypes.valueOf(format.toUpperCase());
		
		if (StringHelper.isEmpty(name)) {
			return ConfigurationSchemaHelper.getSchemaBuilder(sourceType).build(schemaType);
		} else {
			switch (schemaType) {
			case SCHEDULER:
				return ConfigurationSchemaHelper.getSchemaBuilder(sourceType)
						.build(SchedulerTypes.valueOf(name.toUpperCase()));
			case INPUT:
				return buildInputSchema(sourceType, name);
			case OUTPUT:
				return buildOutputSchema(sourceType, name);
			default:
				throw new EventBridgeException("Unsupported schema type: [" + type + "]");
			}
		}
	}
	
	private static String buildInputSchema(DocumentationSourceTypes sourceType, String name) {
		InputConfigTypes inputType = InputConfigTypes.valueOf(name.toUpperCase());
		AppHelper.initializeResources();
		
		return ConfigurationSchemaHelper.getSchemaBuilder(sourceType).build(inputType);
	}
	
	private static String buildOutputSchema(DocumentationSourceTypes sourceType, String name) {
		OutputConfigTypes outputType = OutputConfigTypes.valueOf(name.toUpperCase());
		AppHelper.initializeResources();
		
		return ConfigurationSchemaHelper.getSchemaBuilder(sourceType).build(outputType);
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