package com.github.aureliano.damihilogs.converter;

import java.util.Map;
import java.util.Properties;

import com.github.aureliano.damihilogs.config.EventCollectorConfiguration;
import com.github.aureliano.damihilogs.helper.DataHelper;
import com.github.aureliano.damihilogs.helper.StringHelper;

public class ConfigurationConverter implements IConfigurationConverter<EventCollectorConfiguration> {

	public ConfigurationConverter() {
		super();
	}
	
	@Override
	public EventCollectorConfiguration convert(Map<String, Object> data) {
		EventCollectorConfiguration configuration = new EventCollectorConfiguration()
			.withCollectorId(StringHelper.parse(data.get("id")));
		
		String value = StringHelper.parse(data.get("persistExecutionLog"));
		if (!StringHelper.isEmpty(value)) {
			configuration.withPersistExecutionLog(Boolean.parseBoolean(value));
		}
		
		value = StringHelper.parse(data.get("multiThreadingEnabled"));
		if (!StringHelper.isEmpty(value)) {
			configuration.withMultiThreadingEnabled(Boolean.parseBoolean(value));
		}
		
		if (data.get("metadata") != null) {
			Properties properties = DataHelper.mapToProperties((Map<String, Object>) data.get("metadata"));
			configuration.withMetadata(properties);
		}
		
		return configuration;
	}
}