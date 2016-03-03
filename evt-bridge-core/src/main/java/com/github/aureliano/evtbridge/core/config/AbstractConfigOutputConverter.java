package com.github.aureliano.evtbridge.core.config;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.github.aureliano.evtbridge.common.helper.ReflectionHelper;
import com.github.aureliano.evtbridge.common.helper.StringHelper;
import com.github.aureliano.evtbridge.core.filter.IEventFielter;
import com.github.aureliano.evtbridge.core.formatter.IOutputFormatter;
import com.github.aureliano.evtbridge.core.helper.DataHelper;
import com.github.aureliano.evtbridge.core.listener.DataWritingListener;
import com.github.aureliano.evtbridge.core.parser.IParser;

public abstract class AbstractConfigOutputConverter<T> implements IConfigurationConverter<T> {

	@SuppressWarnings("unchecked")
	protected void configureObject(IConfigOutput conf, Map<String, Object> data) {
		if (data == null) {
			return;
		}
		
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