package com.github.aureliano.evtbridge.core.doc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.aureliano.evtbridge.common.exception.EventBridgeException;
import com.github.aureliano.evtbridge.core.SchemaTypes;
import com.github.aureliano.evtbridge.core.config.EventCollectorConfiguration;
import com.github.aureliano.evtbridge.core.config.IConfiguration;
import com.github.aureliano.evtbridge.core.schedule.SchedulerTypes;

public class MapSchemaBuilder extends SchemaBuilder<Map<String, Object>> {

	public MapSchemaBuilder() {}

	@Override
	public Map<String, Object> build(SchemaTypes schemaType) {
		switch (schemaType) {
		case ROOT:
			return buildRootSchema();
		case SCHEDULER:
			return buildSchedulerSchema();
		default:
			throw new EventBridgeException("Unsupported schema type '" + schemaType + "'");
		}
	}

	private Map<String, Object> buildSchedulerSchema() {
		if (super.schema != null) {
			super.schema.clear();
		}
		
		super.schema.put("$schema", "http://json-schema.org/draft-04/schema#");
		super.schema.put("title", "Scheduling execution configuration.");
		super.schema.put("type", "object");
		
		List<Map<String, Object>> anyOf = new ArrayList<>();
		
		for (SchedulerTypes schedulerType : SchedulerTypes.values()) {
			Map<String, Object> refType = new HashMap<>();
			refType.put("$ref", schedulerType.name().toLowerCase());
			anyOf.add(refType);
		}
		
		Map<String, Object> type = new HashMap<>();
		type.put("anyOf", anyOf);
		
		Map<String, Object> properties = new HashMap<>();
		properties.put("type", type);
		super.schema.put("properties", properties);
		
		return super.schema;
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