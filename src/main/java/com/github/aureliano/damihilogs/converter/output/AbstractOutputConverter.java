package com.github.aureliano.damihilogs.converter.output;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.github.aureliano.damihilogs.config.output.IConfigOutput;
import com.github.aureliano.damihilogs.converter.IConfigurationConverter;
import com.github.aureliano.damihilogs.filter.IEventFielter;
import com.github.aureliano.damihilogs.formatter.IOutputFormatter;
import com.github.aureliano.damihilogs.helper.DataHelper;
import com.github.aureliano.damihilogs.helper.ReflectionHelper;
import com.github.aureliano.damihilogs.helper.StringHelper;
import com.github.aureliano.damihilogs.listener.DataWritingListener;
import com.github.aureliano.damihilogs.parser.IParser;

public abstract class AbstractOutputConverter<T> implements IConfigurationConverter<T> {

	protected void configureObject(IConfigOutput conf, Map<String, Object> data) {
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