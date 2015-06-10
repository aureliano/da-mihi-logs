package com.github.aureliano.damihilogs.converter.report;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.report.ReporterTypes;

public class ReporterConverterFactoryTest {

	@Test
	public void testCreateConverterNonexistent() {
		String type = "test";
		String errorMessage = "Report type '" + type + "' not supported. Expected one of: " + Arrays.toString(ReporterTypes.values());
		
		try {
			ReporterConverterFactory.createConverter(type);
			Assert.fail("An exception was expected.");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals(errorMessage, ex.getMessage());
		}
	}

	@Test
	public void testCreateConverter() {
		Assert.assertTrue(ReporterConverterFactory.createConverter(ReporterTypes.HTML.name()) instanceof HtmlReporterConverter);
		Assert.assertTrue(ReporterConverterFactory.createConverter(ReporterTypes.HTML.name().toLowerCase()) instanceof HtmlReporterConverter);
	}
}