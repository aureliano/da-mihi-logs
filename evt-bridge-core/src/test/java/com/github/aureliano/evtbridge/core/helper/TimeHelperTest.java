package com.github.aureliano.evtbridge.core.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class TimeHelperTest {

	@Test
	public void testConvertToMilliseconds() {
		assertEquals(500, TimeHelper.convertToMilliseconds(TimeUnit.MILLISECONDS, 500));
		assertEquals(5000, TimeHelper.convertToMilliseconds(TimeUnit.SECONDS, 5));
		assertEquals(420000, TimeHelper.convertToMilliseconds(TimeUnit.MINUTES, 7));
		assertEquals(7200000, TimeHelper.convertToMilliseconds(TimeUnit.HOURS, 2));
		assertEquals(259200000, TimeHelper.convertToMilliseconds(TimeUnit.DAYS, 3));
	}
	
	@Test
	public void testConvertToMillisecondsUnsupportedTimeUnit() {
		try {
			TimeHelper.convertToMilliseconds(TimeUnit.MICROSECONDS, 500);
		} catch (IllegalArgumentException ex) {
			assertEquals("Unsupported time unit. The smallest time unit supported is " +
					TimeUnit.MILLISECONDS + " but got " + TimeUnit.MICROSECONDS, ex.getMessage());
		}
		
		try {
			TimeHelper.convertToMilliseconds(TimeUnit.NANOSECONDS, 500);
		} catch (IllegalArgumentException ex) {
			assertEquals("Unsupported time unit. The smallest time unit supported is " +
					TimeUnit.MILLISECONDS + " but got " + TimeUnit.NANOSECONDS, ex.getMessage());
		}
	}
	
	@Test
	public void testIsValidTimeUnit() {
		assertTrue(TimeHelper.isValidTimeUnit("MILLISECONDS"));
		assertTrue(TimeHelper.isValidTimeUnit("milliseconds"));
		assertTrue(TimeHelper.isValidTimeUnit("Seconds"));
		assertTrue(TimeHelper.isValidTimeUnit("MinuteS"));
		assertTrue(TimeHelper.isValidTimeUnit("hours"));
		assertTrue(TimeHelper.isValidTimeUnit("DAYS"));
		assertTrue(TimeHelper.isValidTimeUnit("MICROSECONDS"));
		assertTrue(TimeHelper.isValidTimeUnit("nanoseconds"));
		
		assertFalse(TimeHelper.isValidTimeUnit("months"));
		assertFalse(TimeHelper.isValidTimeUnit("weeks"));
		assertFalse(TimeHelper.isValidTimeUnit("years"));
	}
	
	@Test
	public void testParseToDateTime() {
		Date date = TimeHelper.parseToDateTime("2015-06-10 09:27:35");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		assertEquals(2015, calendar.get(Calendar.YEAR));
		assertEquals(Calendar.JUNE, calendar.get(Calendar.MONTH));
		assertEquals(10, calendar.get(Calendar.DAY_OF_MONTH));
		assertEquals(9, calendar.get(Calendar.HOUR_OF_DAY));
		assertEquals(27, calendar.get(Calendar.MINUTE));
		assertEquals(35, calendar.get(Calendar.SECOND));
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
		assertEquals("2015-06-10 09:27:35", dateTime);
	}
}