package com.github.aureliano.damihilogs.converter.clean;

import java.util.Map;

import com.github.aureliano.damihilogs.clean.ICleaner;
import com.github.aureliano.damihilogs.converter.IConfigurationConverter;
import com.github.aureliano.damihilogs.helper.StringHelper;

public class CleanerConverter implements IConfigurationConverter<ICleaner> {

	public CleanerConverter() {
		super();
	}

	@Override
	public ICleaner convert(Map<String, Object> data) {
		String type = StringHelper.parse(data.get("type"));
		return CleanerConverterFactory.createConverter(type).convert(data);
	}
}