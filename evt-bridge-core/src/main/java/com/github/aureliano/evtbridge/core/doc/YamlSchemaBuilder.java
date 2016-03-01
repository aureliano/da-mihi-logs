package com.github.aureliano.evtbridge.core.doc;

import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.github.aureliano.evtbridge.common.exception.EventBridgeException;
import com.github.aureliano.evtbridge.core.SchemaTypes;

public class YamlSchemaBuilder extends SchemaBuilder<String> {

	private MapSchemaBuilder builder;

	public YamlSchemaBuilder() {
		this.builder = new MapSchemaBuilder();
	}

	@Override
	public String build(SchemaTypes schemaType) {
		switch (schemaType) {
		case ROOT:
			return this.convertMapToYaml(this.builder.build(schemaType));
		default:
			throw new EventBridgeException("Unsupported schema type '" + schemaType + "'");
		}
	}

	private String convertMapToYaml(Map<String, Object> map) {
		map.remove("$schema");
		Yaml yaml = new Yaml();
		
		return yaml.dumpAsMap(map);
	}
}