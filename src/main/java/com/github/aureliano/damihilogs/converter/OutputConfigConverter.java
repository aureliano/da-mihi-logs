package com.github.aureliano.damihilogs.converter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.github.aureliano.damihilogs.config.output.ElasticSearchOutputConfig;
import com.github.aureliano.damihilogs.config.output.FileOutputConfig;
import com.github.aureliano.damihilogs.config.output.IConfigOutput;
import com.github.aureliano.damihilogs.config.output.StandardOutputConfig;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.filter.IEventFielter;
import com.github.aureliano.damihilogs.formatter.IOutputFormatter;
import com.github.aureliano.damihilogs.helper.DataHelper;
import com.github.aureliano.damihilogs.helper.ReflectionHelper;
import com.github.aureliano.damihilogs.helper.StringHelper;
import com.github.aureliano.damihilogs.listener.DataWritingListener;
import com.github.aureliano.damihilogs.parser.IParser;

public class OutputConfigConverter implements IConfigurationConverter<IConfigOutput> {

	protected static final String[] OUTPUT_CONFIG_TYPES = new String[] { "elasticSearch", "file", "Standard" };
	
	public OutputConfigConverter() {
		super();
	}

	@Override
	public IConfigOutput convert(Map<String, Object> data) {
		if (data.isEmpty()) {
			return null;
		}
		
		String type = StringHelper.parse(data.get("type"));
		
		if ("elasticSearch".equals(type)) {
			return this.createElasticSearchConfig(DataHelper.getAsHash(data, "properties"));
		} else if ("file".equals(type)) {
			return this.createFileConfig(DataHelper.getAsHash(data, "properties"));
		} else if ("standard".equals(type)) {
			return this.createStandardConfig(DataHelper.getAsHash(data, "properties"));
		} else {
			throw new DaMihiLogsException("Output config type '" + type + "' not supported. Expected one of: " + Arrays.toString(OUTPUT_CONFIG_TYPES));
		}
	}

	private ElasticSearchOutputConfig createElasticSearchConfig(Map<String, Object> data) {
		ElasticSearchOutputConfig conf = new ElasticSearchOutputConfig();
		
		this.configureObject(conf, data);
		
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
			.withMappingType(StringHelper.parse(data.get("type")));
	}

	private FileOutputConfig createFileConfig(Map<String, Object> data) {
		FileOutputConfig conf = new FileOutputConfig();
		
		this.configureObject(conf, data);
		String value = StringHelper.parse(data.get("file"));
		if (!StringHelper.isEmpty(value)) {
			conf.withFile(value);
		}
		
		value = StringHelper.parse(data.get("append"));
		if (!StringHelper.isEmpty(value)) {
			conf.withAppend(Boolean.parseBoolean(value.toLowerCase()));
		}
		
		value = StringHelper.parse(data.get("encoding"));
		if (!StringHelper.isEmpty(value)) {
			conf.withEncoding(value);
		}
		
		return conf;
	}

	private StandardOutputConfig createStandardConfig(Map<String, Object> data) {
		StandardOutputConfig conf = new StandardOutputConfig();
		
		this.configureObject(conf, data);
		
		return conf;
	}
	
	private void configureObject(IConfigOutput conf, Map<String, Object> data) {
		String value = StringHelper.parse(data.get("parser"));
		if (!StringHelper.isEmpty(value)) {
			conf.withParser((IParser<?>) ReflectionHelper.newInstance(value));
		}
		
		value = StringHelper.parse(data.get("filter"));
		if (!StringHelper.isEmpty(value)) {
			conf.withFilter((IEventFielter) ReflectionHelper.newInstance(value));
		}
		
		value = StringHelper.parse(data.get("formatter"));
		if (!StringHelper.isEmpty(value)) {
			conf.withOutputFormatter((IOutputFormatter) ReflectionHelper.newInstance(value));
		}
		
		if (data.get("dataWritingListeners") != null) {
			List<String> listeners = (List<String>) data.get("dataWritingListeners");
			for (String listener : listeners) {
				conf.addDataWritingListener((DataWritingListener) ReflectionHelper.newInstance(listener));
			}
		}
		
		if (data.get("metadata") != null) {
			Properties properties = DataHelper.mapToProperties((Map<String, Object>) data.get("metadata"));
			for (Object key : properties.keySet()) {
				conf.putMetadata(key.toString(), properties.getProperty(key.toString()));
			}
		}
	}
}