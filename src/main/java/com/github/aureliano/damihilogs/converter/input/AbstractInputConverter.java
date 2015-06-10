package com.github.aureliano.damihilogs.converter.input;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.github.aureliano.damihilogs.config.input.IConfigInput;
import com.github.aureliano.damihilogs.converter.IConfigurationConverter;
import com.github.aureliano.damihilogs.exception.IExceptionHandler;
import com.github.aureliano.damihilogs.helper.DataHelper;
import com.github.aureliano.damihilogs.helper.ReflectionHelper;
import com.github.aureliano.damihilogs.helper.StringHelper;
import com.github.aureliano.damihilogs.listener.DataReadingListener;
import com.github.aureliano.damihilogs.listener.ExecutionListener;
import com.github.aureliano.damihilogs.matcher.IMatcher;

public abstract class AbstractInputConverter<T> implements IConfigurationConverter<T> {

	protected void configureObject(IConfigInput conf, Map<String, Object> data) {
		conf.withConfigurationId(StringHelper.parse(data.get("id")));
		
		String value = StringHelper.parse(data.get("matcher"));
		if (!StringHelper.isEmpty(value)) {
			conf.withMatcher((IMatcher) ReflectionHelper.newInstance(value));
		}
		
		value = StringHelper.parse(data.get("useLastExecutionLog"));
		if (!StringHelper.isEmpty(value)) {
			conf.withUseLastExecutionRecords(Boolean.parseBoolean(value.toLowerCase()));
		}
		
		if (data.get("exceptionHandlers") != null) {
			List<String> handlers = (List<String>) data.get("exceptionHandlers");
			for (String handler : handlers) {
				conf.addExceptionHandler((IExceptionHandler) ReflectionHelper.newInstance(handler));
			}
		}
		
		if (data.get("dataReadingListeners") != null) {
			List<String> listeners = (List<String>) data.get("dataReadingListeners");
			for (String listener : listeners) {
				conf.addDataReadingListener((DataReadingListener) ReflectionHelper.newInstance(listener));
			}
		}
		
		if (data.get("executionListeners") != null) {
			List<String> listeners = (List<String>) data.get("executionListeners");
			for (String listener : listeners) {
				conf.addExecutionListener((ExecutionListener) ReflectionHelper.newInstance(listener));
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