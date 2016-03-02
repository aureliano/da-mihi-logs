package com.github.aureliano.evtbridge.core.doc;

import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.github.aureliano.evtbridge.core.SchemaTypes;
import com.github.aureliano.evtbridge.core.config.InputConfigTypes;
import com.github.aureliano.evtbridge.core.schedule.SchedulerTypes;

public class YamlSchemaBuilder extends SchemaBuilder<String> {

	private MapSchemaBuilder builder;

	public YamlSchemaBuilder() {
		this.builder = new MapSchemaBuilder();
	}

	@Override
	public String build(SchemaTypes schemaType) {
		return this.convertMapToYaml(this.builder.build(schemaType));
	}
	
	@Override
	public String build(SchedulerTypes schedulerType) {
		return this.convertMapToYaml(this.builder.build(schedulerType));
	}
	
	@Override
	public String build(InputConfigTypes inputType) {
		return this.convertMapToYaml(this.builder.build(inputType));
	}

	private String convertMapToYaml(Map<String, Object> map) {
		map.remove("$schema");
		Yaml yaml = new Yaml();
		
		return yaml.dumpAsMap(map);
	}
}