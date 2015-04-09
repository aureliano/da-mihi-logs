package com.github.aureliano.damihilogs.report;

import junit.framework.Assert;

import org.junit.Test;

public class ReportLanguageTest {

	@Test
	public void testEnumValues() {
		Assert.assertEquals("en", ReportLanguage.ENGLISH.getAbbreviation());
		Assert.assertEquals("pt", ReportLanguage.PORTUGUESE.getAbbreviation());
	}
}