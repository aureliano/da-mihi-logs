package com.github.aureliano.damihilogs.converter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.report.HtmlReporter;
import com.github.aureliano.damihilogs.report.ReportLanguage;

public class ReporterConverterTest {

	private ReporterConverter converter;
	
	public ReporterConverterTest() {
		this.converter = new ReporterConverter();
	}
	
	@Test
	public void testCreateHtmlReporterErrorNonExistent() {
		try {
			this.converter.convert(new HashMap<String, Object>());
			Assert.fail("An exception was expected.");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Report type 'null' not supported. Expected one of: " + Arrays.toString(ReporterConverter.REPORT_TYPES), ex.getMessage());
		}
	}
	
	@Test
	public void testCreateHtmlReporterErrorOutputDir() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", "html");
		
		try {
			this.converter.convert(map);
			Assert.fail("An exception was expected.");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Property outputDir was expected in report configuration.", ex.getMessage());
		}
	}
	
	@Test
	public void testCreateHtmlReporterErrorLanguage() {
		String language = "test";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", "html");
		map.put("outputDir", "src/test/resources");
		map.put("language", language);
		
		try {
			this.converter.convert(map);
			Assert.fail("An exception was expected.");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Property language was expected to be one of: " + ReporterConverter.LANGUAGES + " but got " + language, ex.getMessage());
		}
	}
	
	@Test
	public void testCreateHtmlReporter() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", "html");
		map.put("outputDir", "src/test/resources");
		
		HtmlReporter reporter = (HtmlReporter) this.converter.convert(map);
		Assert.assertEquals("src/test/resources", reporter.getOutputDir().getPath());
		Assert.assertEquals(ReportLanguage.ENGLISH, reporter.getLanguage());
		
		map.put("language", "portuguese");
		reporter = (HtmlReporter) this.converter.convert(map);
		Assert.assertEquals("src/test/resources", reporter.getOutputDir().getPath());
		Assert.assertEquals(ReportLanguage.PORTUGUESE, reporter.getLanguage());
	}
}