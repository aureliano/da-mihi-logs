package com.github.aureliano.damihilogs.converter.output;

import java.util.Map;

import com.github.aureliano.damihilogs.config.output.ElasticSearchOutputConfig;
import com.github.aureliano.damihilogs.config.output.OutputConfigTypes;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.helper.StringHelper;

public class ElasticSearchOutputConverter extends AbstractOutputConverter<ElasticSearchOutputConfig> {

	public ElasticSearchOutputConverter() {
		super();
	}

	@Override
	public ElasticSearchOutputConfig convert(Map<String, Object> data) {
		ElasticSearchOutputConfig conf = new ElasticSearchOutputConfig();
		
		super.configureObject(conf, data);
		
		String value = StringHelper.parse(data.get("port"));
		if (!StringHelper.isEmpty(value)) {
			if (!value.toUpperCase().matches("\\d+")) {
				throw new DaMihiLogsException("Property port was expected to match \\d+ pattern in output url configuration.");
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