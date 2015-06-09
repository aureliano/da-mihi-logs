package com.github.aureliano.damihilogs.converter.clean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.github.aureliano.damihilogs.clean.ICleaner;
import com.github.aureliano.damihilogs.converter.IConfigurationConverter;
import com.github.aureliano.damihilogs.helper.StringHelper;

public class CleanerConverter implements IConfigurationConverter<List<ICleaner>> {

	public CleanerConverter() {
		super();
	}

	@Override
	public List<ICleaner> convert(Map<String, Object> data) {
		List<Map<String, Object>> list = (List<Map<String, Object>>) data.get("cleaners");
		if (list == null) {
			return Collections.emptyList();
		}
		
		List<ICleaner> cleaners = new ArrayList<ICleaner>(list.size());
		
		for (Map<String, Object> map : list) {
			cleaners.add(this.convertCleaner(map));
		}
		
		return cleaners;
	}
	
	private ICleaner convertCleaner(Map<String, Object> data) {
		String type = StringHelper.parse(data.get("type"));
		return CleanerConverterFactory.createConverter(type).convert(data);
	}
}