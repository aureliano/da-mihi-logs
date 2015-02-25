package com.github.aureliano.defero.formatter;

import org.junit.Assert;
import org.junit.Test;

public class PlainTextFormatterTest {

	@Test
	public void testFormat() {
		IOutputFormatter formatter = new PlainTextFormatter();
		Assert.assertEquals("", formatter.format(null));
		Assert.assertEquals("Plain text", formatter.format("Plain text"));
		Assert.assertEquals("12345", formatter.format(12345));
	}
}