package com.github.aureliano.damihilogs.converter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.helper.StringHelper;
import com.github.aureliano.damihilogs.report.HtmlReporter;
import com.github.aureliano.damihilogs.report.ILoggerReporter;
import com.github.aureliano.damihilogs.report.ReportLanguage;

public class ReporterConverter implements IConfigurationConverter<ILoggerReporter> {
	
	protected static final String[] REPORT_TYPES = { "html" };
	protected static final List<String> LANGUAGES = loadLanguages();
	
	public ReporterConverter() {
		super();
	}

	@Override
	public ILoggerReporter convert(Map<String, Object> data) {
		String type = StringHelper.parse(data.get("type"));
		
		if ("html".equals(type)) {
			return this.createHtmlReporter(data);
		} else {
			throw new DaMihiLogsException("Report type '" + type + "' not supported. Expected one of: " + Arrays.toString(REPORT_TYPES));
		}
	}

	private ILoggerReporter createHtmlReporter(Map<String, Object> data) {
		String value = StringHelper.parse(data.get("outputDir"));
		if (StringHelper.isEmpty(value)) {
			throw new DaMihiLogsException("Property outputDir was expected in report configuration.");
		}
		
		HtmlReporter reporter = new HtmlReporter()
			.withPageTitle(StringHelper.parse(data.get("title")))
			.withDescription(StringHelper.parse(data.get("description")))
			.withOutputDir(new File(value));
		
		value = StringHelper.parse(data.get("language"));
		if (!StringHelper.isEmpty(value)) {
			if (!LANGUAGES.contains(value.toUpperCase())) {
				throw new DaMihiLogsException("Property language was expected to be one of: " + LANGUAGES + " but got " + value);
			}
			reporter.withLanguage(ReportLanguage.valueOf(value.toUpperCase()));
		}
		
		return reporter;
	}

	private static List<String> loadLanguages() {
		List<String> languages = new ArrayList<String>();
		for (ReportLanguage language : ReportLanguage.values()) {
			languages.add(language.name());
		}
		
		return languages;
	}
}