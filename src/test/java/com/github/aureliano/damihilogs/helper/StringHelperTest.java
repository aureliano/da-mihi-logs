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
}