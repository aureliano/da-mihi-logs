package com.github.aureliano.evtbridge.core.doc;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.aureliano.evtbridge.common.exception.EventBridgeException;
import com.github.aureliano.evtbridge.core.SchemaTypes;
import com.github.aureliano.evtbridge.core.data.ObjectMapperSingleton;

public class JsonSchemaBuilder extends SchemaBuilder<String> {

	private MapSchemaBuilder builder;
	
	public JsonSchemaBuilder() {
		this.builder = new MapSchemaBuilder();
	}

	@Override
	public String build(SchemaTypes schemaType) {
		return this.convertMapToJson(this.builder.build(schemaType));
	}
	
	private String convertMapToJson(Map<String, Object> map) {
		ObjectMapper mapper = ObjectMapperSingleton.instance().getObjectMapper();
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
		} catch (JsonProcessingException ex) {
			throw new EventBridgeException(ex);
		}
	}
}