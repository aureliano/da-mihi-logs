package com.github.aureliano.damihilogs.helper;

import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.junit.Test;

public class TimeUnitHelperTest {

	@Test
	public void testConvertToMilliseconds() {
		Assert.assertEquals(500, TimeHelper.convertToMilliseconds(TimeUnit.MILLISECONDS, 500));
		Assert.assertEquals(5000, TimeHelper.convertToMilliseconds(TimeUnit.SECONDS, 5));
		Assert.assertEquals(420000, TimeHelper.convertToMilliseconds(TimeUnit.MINUTES, 7));
		Assert.assertEquals(7200000, TimeHelper.convertToMilliseconds(TimeUnit.HOURS, 2));
		Assert.assertEquals(259200000, TimeHelper.convertToMilliseconds(TimeUnit.DAYS, 3));
	}
	
	@Test
	public void testConvertToMillisecondsUnsupportedTimeUnit() {
		try {
			TimeHelper.convertToMilliseconds(TimeUnit.MICROSECONDS, 500);
		} catch (IllegalArgumentException ex) {
			Assert.assertEquals("Unsupported time unit. The smallest time unit supported is " +
					TimeUnit.MILLISECONDS + " but got " + TimeUnit.MICROSECONDS, ex.getMessage());
		}
		
		try {
			TimeHelper.convertToMilliseconds(TimeUnit.NANOSECONDS, 500);
		} catch (IllegalArgumentException ex) {
			Assert.assertEquals("Unsupported time unit. The smallest time unit supported is " +
					TimeUnit.MILLISECONDS + " but got " + TimeUnit.NANOSECONDS, ex.getMessage());
		}
	}
}