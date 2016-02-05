package com.github.aureliano.evtbridge.core.helper;

import junit.framework.Assert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.aureliano.evtbridge.core.helper.StringHelper;

public class StringHelperTest {

	@Test
	public void testIsEmpty() {
		Assert.assertTrue(StringHelper.isEmpty(null));
		Assert.assertTrue(StringHelper.isEmpty(""));
		Assert.assertFalse(StringHelper.isEmpty("text"));
	}
	
	@Test
	public void testToString() {
		assertEquals("null", StringHelper.toString(null));
		assertEquals("text", StringHelper.toString("text"));
		assertEquals("23", StringHelper.toString(23));
	}
	
	@Test
	public void testParse() {
		assertEquals("30", StringHelper.parse(30));
		assertEquals("", StringHelper.parse(""));
		assertNull(StringHelper.parse(null));
	}
	
	@Test
	public void testCapitalize() {
		assertEquals("House", StringHelper.capitalize("house"));
		assertEquals("House", StringHelper.capitalize("HOUSE"));
		assertEquals("House", StringHelper.capitalize("HoUSE"));
		assertEquals("House of torments", StringHelper.capitalize("house OF tormentS"));
	}
	
	@Test
	public void testJoin() {
		assertEquals("12345", StringHelper.join(new Object[] {1, 2, 3, 4, 5}));
		assertEquals("1, 2, 3, 4, 5", StringHelper.join(new Object[] {1, 2, 3, 4, 5}, ", "));
	}
	
	@Test
	public void testIsNumeric() {
		assertTrue(StringHelper.isNumeric("1"));
		assertTrue(StringHelper.isNumeric("45"));
		assertTrue(StringHelper.isNumeric("-54"));
		assertTrue(StringHelper.isNumeric("+54"));
		assertTrue(StringHelper.isNumeric("25.97854"));
		assertTrue(StringHelper.isNumeric("-3365.8787"));
		assertTrue(StringHelper.isNumeric("+3365.8787"));
	}
}