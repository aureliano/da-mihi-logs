package com.github.aureliano.evtbridge.core.doc;

import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.github.aureliano.evtbridge.core.SchemaTypes;

public class YamlSchemaBuilder extends SchemaBuilder<String> {

	private MapSchemaBuilder builder;

	public YamlSchemaBuilder() {
		this.builder = new MapSchemaBuilder();
	}

	@Override
	public String build(SchemaTypes schemaType) {
		return this.convertMapToYaml(this.builder.build(schemaType));
	}

	private String convertMapToYaml(Map<String, Object> map) {
		map.remove("$schema");
		Yaml yaml = new Yaml();
		
		return yaml.dumpAsMap(map);
	}
}