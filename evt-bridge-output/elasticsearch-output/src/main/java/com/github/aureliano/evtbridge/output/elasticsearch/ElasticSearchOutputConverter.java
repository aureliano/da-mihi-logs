package com.github.aureliano.evtbridge.output.elasticsearch;

import java.util.Map;

import com.github.aureliano.evtbridge.common.helper.StringHelper;
import com.github.aureliano.evtbridge.core.config.AbstractConfigOutputConverter;
import com.github.aureliano.evtbridge.core.config.OutputConfigTypes;

public class ElasticSearchOutputConverter extends AbstractConfigOutputConverter<ElasticSearchOutputConfig> {

	public ElasticSearchOutputConverter() {
		super();
	}

	@Override
	public ElasticSearchOutputConfig convert(Map<String, Object> data) {
		ElasticSearchOutputConfig conf = new ElasticSearchOutputConfig();
		
		super.configureObject(conf, data);
		
		String value = StringHelper.parse(data.get("port"));
		if (!StringHelper.isEmpty(value)) {
			if (!StringHelper.isNumeric(value)) {
				throw new ElasticSearchOutputException("Property port was expected to match \\d+ pattern in output url configuration.");
			}
			conf.withPort(Integer.parseInt(value));
		}
		
		value = StringHelper.parse(data.get("printElasticSearchLog"));
		if (!StringHelper.isEmpty(value)) {
			conf.withPrintElasticSearchLog(Boolean.parseBoolean(value.toLowerCase()));
		}
		
		return conf
			.withHost(StringHelper.parse(data.get("host")))
			.withIndex(StringHelper.parse(data.get("index")))
			.withMappingType(StringHelper.parse(data.get("mappingType")));
	}
	
	@Override
	public String id() {
		return OutputConfigTypes.ELASTIC_SEARCH.name();
	}
}