package com.github.aureliano.damihilogs.helper;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.junit.Test;

public class TimeHelperTest {

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
	
	@Test
	public void testIsValidTimeUnit() {
		Assert.assertTrue(TimeHelper.isValidTimeUnit("MILLISECONDS"));
		Assert.assertTrue(TimeHelper.isValidTimeUnit("milliseconds"));
		Assert.assertTrue(TimeHelper.isValidTimeUnit("Seconds"));
		Assert.assertTrue(TimeHelper.isValidTimeUnit("MinuteS"));
		Assert.assertTrue(TimeHelper.isValidTimeUnit("hours"));
		Assert.assertTrue(TimeHelper.isValidTimeUnit("DAYS"));
		Assert.assertTrue(TimeHelper.isValidTimeUnit("MICROSECONDS"));
		Assert.assertTrue(TimeHelper.isValidTimeUnit("nanoseconds"));
		
		Assert.assertFalse(TimeHelper.isValidTimeUnit("months"));
		Assert.assertFalse(TimeHelper.isValidTimeUnit("weeks"));
		Assert.assertFalse(TimeHelper.isValidTimeUnit("years"));
	}
	
	@Test
	public void testParseToDateTime() {
		Date date = TimeHelper.parseToDateTime("2015-06-10 09:27:35");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		Assert.assertEquals(2015, calendar.get(Calendar.YEAR));
		Assert.assertEquals(Calendar.JUNE, calendar.get(Calendar.MONTH));
		Assert.assertEquals(10, calendar.get(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(9, calendar.get(Calendar.HOUR_OF_DAY));
		Assert.assertEquals(27, calendar.get(Calendar.MINUTE));
		Assert.assertEquals(35, calendar.get(Calendar.SECOND));
	}
	
	@Test
	public void testFormatDateTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2015);
		calendar.set(Calendar.MONTH, Calendar.JUNE);
		calendar.set(Calendar.DAY_OF_MONTH, 10);
		calendar.set(Calendar.HOUR_OF_DAY, 9);
		calendar.set(Calendar.MINUTE, 27);
		calendar.set(Calendar.SECOND, 35);
		
		String dateTime = TimeHelper.formatDateTime(calendar.getTime());
		Assert.assertEquals("2015-06-10 09:27:35", dateTime);
	}
}