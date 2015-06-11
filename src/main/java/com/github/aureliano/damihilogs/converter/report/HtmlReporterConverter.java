package com.github.aureliano.damihilogs.converter.report;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.github.aureliano.damihilogs.converter.IConfigurationConverter;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.helper.StringHelper;
import com.github.aureliano.damihilogs.report.HtmlReporter;
import com.github.aureliano.damihilogs.report.ILoggerReporter;
import com.github.aureliano.damihilogs.report.ReportLanguage;
import com.github.aureliano.damihilogs.report.ReporterTypes;

public class HtmlReporterConverter implements IConfigurationConverter<ILoggerReporter> {

	private static final List<String> LANGUAGES = loadLanguages();
	
	public HtmlReporterConverter() {
		super();
	}
	
	@Override
	public ILoggerReporter convert(Map<String, Object> data) {
		return this.createHtmlReporter(data);
	}

	private ILoggerReporter createHtmlReporter(Map<String, Object> data) {
		HtmlReporter reporter = new HtmlReporter();
		String value = StringHelper.parse(data.get("title"));
		if (!StringHelper.isEmpty(value)) {
			reporter.withPageTitle(value);
		}
		
		value = StringHelper.parse(data.get("description"));
		if (!StringHelper.isEmpty(value)) {
			reporter.withDescription(value);
		}
		
		value = StringHelper.parse(data.get("outputDir"));
		if (!StringHelper.isEmpty(value)) {
			reporter.withOutputDir(new File(value));
		}
		
		value = StringHelper.parse(data.get("language"));
		if (!StringHelper.isEmpty(value)) {
			if (!LANGUAGES.contains(value.toUpperCase())) {
				throw new DaMihiLogsException("Property language was expected to be one of: " + Arrays.toString(ReportLanguage.values()) + " but got " + value);
			}
			reporter.withLanguage(ReportLanguage.valueOf(value.toUpperCase()));
		}
		
		return reporter;
	}
	
	@Override
	public String id() {
		return ReporterTypes.HTML.name();
	}

	private static List<String> loadLanguages() {
		List<String> languages = new ArrayList<String>();
		for (ReportLanguage language : ReportLanguage.values()) {
			languages.add(language.name());
		}
		
		return languages;
	}
}