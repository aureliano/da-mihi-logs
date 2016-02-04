package com.github.aureliano.damihilogs.helper;

import junit.framework.Assert;

import org.junit.Test;

public class StringHelperTest {

	@Test
	public void testIsEmpty() {
		Assert.assertTrue(StringHelper.isEmpty(null));
		Assert.assertTrue(StringHelper.isEmpty(""));
		Assert.assertFalse(StringHelper.isEmpty("text"));
	}
	
	@Test
	public void testToString() {
		Assert.assertEquals("null", StringHelper.toString(null));
		Assert.assertEquals("text", StringHelper.toString("text"));
		Assert.assertEquals("23", StringHelper.toString(23));
	}
	
	@Test
	public void testParse() {
		Assert.assertEquals("30", StringHelper.parse(30));
		Assert.assertEquals("", StringHelper.parse(""));
		Assert.assertNull(StringHelper.parse(null));
	}
	
	@Test
	public void testCapitalize() {
		Assert.assertEquals("House", StringHelper.capitalize("house"));
		Assert.assertEquals("House", StringHelper.capitalize("HOUSE"));
		Assert.assertEquals("House", StringHelper.capitalize("HoUSE"));
		Assert.assertEquals("House of torments", StringHelper.capitalize("house OF tormentS"));
	}
	
	@Test
	public void testJoin() {
		Assert.assertEquals("12345", StringHelper.join(new Object[] {1, 2, 3, 4, 5}));
		Assert.assertEquals("1, 2, 3, 4, 5", StringHelper.join(new Object[] {1, 2, 3, 4, 5}, ", "));
	}
	
	@Test
	public void testIsNumeric() {
		Assert.assertTrue(StringHelper.isNumeric("1"));
		Assert.assertTrue(StringHelper.isNumeric("45"));
		Assert.assertTrue(StringHelper.isNumeric("-54"));
		Assert.assertTrue(StringHelper.isNumeric("+54"));
		Assert.assertTrue(StringHelper.isNumeric("25.97854"));
		Assert.assertTrue(StringHelper.isNumeric("-3365.8787"));
		Assert.assertTrue(StringHelper.isNumeric("+3365.8787"));
	}
}