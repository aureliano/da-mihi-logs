package com.github.aureliano.evtbridge.core.doc;

import java.util.Map;

import com.github.aureliano.evtbridge.common.exception.EventBridgeException;
import com.github.aureliano.evtbridge.core.SchemaTypes;
import com.github.aureliano.evtbridge.core.config.EventCollectorConfiguration;
import com.github.aureliano.evtbridge.core.config.IConfiguration;

public class MapSchemaBuilder extends SchemaBuilder<Map<String, Object>> {

	public MapSchemaBuilder() {}

	@Override
	public Map<String, Object> build(SchemaTypes schemaType) {
		switch (schemaType) {
		case ROOT:
			return buildRootSchema();
		default:
			throw new EventBridgeException("Unsupported schema type '" + schemaType + "'");
		}
	}

	private Map<String, Object> buildRootSchema() {
		if (super.schema != null) {
			super.schema.clear();
		}
		
		Class<? extends IConfiguration> configuration = EventCollectorConfiguration.class;
		super.configureSchemaHeader(configuration);
		super.configureSchemaProperties(configuration);
		
		return super.schema;
	}
}