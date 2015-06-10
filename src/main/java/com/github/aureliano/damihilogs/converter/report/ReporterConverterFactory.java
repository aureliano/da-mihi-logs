package com.github.aureliano.damihilogs.converter.report;

import java.util.Arrays;

import com.github.aureliano.damihilogs.converter.IConfigurationConverter;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.report.ILoggerReporter;
import com.github.aureliano.damihilogs.report.ReporterTypes;

public final class ReporterConverterFactory {

	private ReporterConverterFactory() {
		super();
	}

	public static IConfigurationConverter<ILoggerReporter> createConverter(String type) {
		if (ReporterTypes.HTML.name().equalsIgnoreCase(type)) {
			return new HtmlReporterConverter();
		} else {
			throw new DaMihiLogsException("Report type '" + type + "' not supported. Expected one of: " + Arrays.toString(ReporterTypes.values()));
		}
	}
}