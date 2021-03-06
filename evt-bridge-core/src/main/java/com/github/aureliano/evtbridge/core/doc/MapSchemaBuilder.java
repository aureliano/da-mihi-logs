package com.github.aureliano.evtbridge.core.doc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.aureliano.evtbridge.common.exception.EventBridgeException;
import com.github.aureliano.evtbridge.core.SchemaTypes;
import com.github.aureliano.evtbridge.core.config.EventCollectorConfiguration;
import com.github.aureliano.evtbridge.core.config.IConfiguration;
import com.github.aureliano.evtbridge.core.config.InputConfigTypes;
import com.github.aureliano.evtbridge.core.config.OutputConfigTypes;
import com.github.aureliano.evtbridge.core.register.ApiServiceRegistrator;
import com.github.aureliano.evtbridge.core.schedule.ExecuteOnceAtSpecificTimeScheduler;
import com.github.aureliano.evtbridge.core.schedule.ExecutePeriodicallyAtSpecificTimeScheduler;
import com.github.aureliano.evtbridge.core.schedule.ExecutePeriodicallyScheduler;
import com.github.aureliano.evtbridge.core.schedule.SchedulerTypes;

public class MapSchemaBuilder extends SchemaBuilder<Map<String, Object>> {

	public MapSchemaBuilder() {}

	@Override
	public Map<String, Object> build(SchemaTypes schemaType) {
		switch (schemaType) {
		case ROOT:
			return this.buildSchema(EventCollectorConfiguration.class);
		case SCHEDULER:
			return this.buildSchedulerSchema();
		case INPUT:
			return this.buildInputSchema();
		case OUTPUT:
			return this.buildOutputSchema();
		default:
			throw new EventBridgeException("Unsupported schema type '" + schemaType + "'");
		}
	}
	
	@Override
	public Map<String, Object> build(SchedulerTypes schedulerType) {
		switch (schedulerType) {
		case EXECUTE_PERIODICALLY:
			return this.buildSchema(ExecutePeriodicallyScheduler.class);
		case EXECUTE_ONCE_AT_SPECIFIC_TIME:
			return this.buildSchema(ExecuteOnceAtSpecificTimeScheduler.class);
		case EXECUTE_PERIODICALLY_AT_SPECIFIC_TIME:
			return this.buildSchema(ExecutePeriodicallyAtSpecificTimeScheduler.class);
		default:
			throw new EventBridgeException("Unsupported scheduler type '" + schedulerType + "'");
		}
	}
	
	@Override
	public Map<String, Object> build(InputConfigTypes inputType) {
		return this.buildSchema(inputType.name());
	}
	
	@Override
	public Map<String, Object> build(OutputConfigTypes outputType) {
		return this.buildSchema(outputType.name());
	}

	private Map<String, Object> buildOutputSchema() {
		return this.buildAggregationSchema("Output configuration.", OutputConfigTypes.values());
	}

	private Map<String, Object> buildInputSchema() {
		return this.buildAggregationSchema("Input configuration.", InputConfigTypes.values());
	}

	private Map<String, Object> buildSchedulerSchema() {
		return this.buildAggregationSchema("Scheduling execution configuration.", SchedulerTypes.values());
	}
	
	private Map<String, Object> buildSchema(String configurationId) {
		ApiServiceRegistrator services = ApiServiceRegistrator.instance();
		Class<? extends IConfiguration> configuration = services.getConfiguration(configurationId);
		
		if (configuration == null) {
			throw new EventBridgeException("Could not find any registered configuration with id " + configurationId);
		}
		
		return this.buildSchema(configuration);
	}

	private Map<String, Object> buildSchema(Class<?> configuration) {
		if (super.schema != null) {
			super.schema.clear();
		}
		
		super.configureSchemaHeader(configuration);
		super.configureSchemaProperties(configuration);
		
		return super.schema;
	}
	
	private Map<String, Object> buildAggregationSchema(String title, Object[] values) {
		if (super.schema != null) {
			super.schema.clear();
		}
		
		super.schema.put("$schema", "http://json-schema.org/draft-04/schema#");
		super.schema.put("title", title);
		super.schema.put("type", "object");
		
		List<Map<String, Object>> anyOf = new ArrayList<>();
		
		for (Object value : values) {
			Map<String, Object> refType = new HashMap<>();
			refType.put("$ref", value.toString().toLowerCase());
			anyOf.add(refType);
		}
		
		Map<String, Object> type = new HashMap<>();
		type.put("anyOf", anyOf);
		
		Map<String, Object> properties = new HashMap<>();
		properties.put("type", type);
		super.schema.put("properties", properties);
		
		return super.schema;
	}
}