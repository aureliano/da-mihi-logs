package com.github.aureliano.damihilogs.converter.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.github.aureliano.damihilogs.converter.IConfigurationConverter;
import com.github.aureliano.damihilogs.helper.StringHelper;
import com.github.aureliano.damihilogs.report.ILoggerReporter;

public class ReporterConverter implements IConfigurationConverter<List<ILoggerReporter>> {

	public ReporterConverter() {
		super();
	}

	@Override
	public List<ILoggerReporter> convert(Map<String, Object> data) {
		List<Map<String, Object>> list = (List<Map<String, Object>>) data.get("reporters");
		if (list == null) {
			return Collections.emptyList();
		}
		
		List<ILoggerReporter> reporters = new ArrayList<ILoggerReporter>(list.size());
		
		for (Map<String, Object> map : list) {
			reporters.add(this.convertReporter(map));
		}
		
		return reporters;
	}

	private ILoggerReporter convertReporter(Map<String, Object> data) {
		String type = StringHelper.parse(data.get("type"));
		return ReporterConverterFactory.createConverter(type).convert(data);
	}
}