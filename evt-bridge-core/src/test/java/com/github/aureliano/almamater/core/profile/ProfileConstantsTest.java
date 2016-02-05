package com.github.aureliano.almamater.core.profile;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ProfileConstantsTest {

	@Test
	public void testMemFactor() {
		double expectedMemFactor = 1024.0 * 1024.0;
		double actualMemFactor = ProfileConstants.MEM_FACTOR;
		
		assertEquals(expectedMemFactor, actualMemFactor, 0);
	}
	
	@Test
	public void testDecimalFormat() {
		String expected = "#0.00";
		String actual = ProfileConstants.DECIMAL_FORMAT.toPattern();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testDateFormat() {
		String expected = "yyyy-MM-dd HH:mm:ss";
		String actual = ProfileConstants.DATE_FORMAT.toPattern();
		
		assertEquals(expected, actual);
	}
}