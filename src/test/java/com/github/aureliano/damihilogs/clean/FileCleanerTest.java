package com.github.aureliano.damihilogs.clean;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;

import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.helper.FileHelperTest;

public class FileCleanerTest {
	
	private static final int SECOND_IN_MILLIS = 1 * 1000;
	private static final int MINUTE_IN_MILLIS = 60 * SECOND_IN_MILLIS;
	private static final int HOUR_IN_MILLIS = 60 * MINUTE_IN_MILLIS;
	private static final int DAY_IN_MILLIS = 24 * HOUR_IN_MILLIS;
	
	@Test
	public void testValidation() {
		try {
			new FileCleaner(null).validate();
			Assert.fail();
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Time seed for clean task not provided.", ex.getMessage());
		}
		
		try {
			FileCleaner c = new FileCleaner(null);
			c.removeFilesAfterDays(1);
			c.validate();
			
			Assert.fail();
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Directory for clean task not provided.", ex.getMessage());
		}
		
		try {
			FileCleaner c = new FileCleaner(new File("/non/existing/folder"));
			c.removeFilesAfterDays(1);
			c.validate();
			
			Assert.fail();
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Abstract path for clean task does not represent a directory. Dir was expected but got '/non/existing/folder'.", ex.getMessage());
		}
		
		FileCleaner c = new FileCleaner(new File("src/test/resources"));
		c.removeFilesAfterDays(1);
		c.validate();
	}
	
	@Test
	public void testClean() {
		String dirPath = FileHelperTest.createDirectoryStructureWithDelay(SECOND_IN_MILLIS);
		
		Assert.assertEquals(5, new File(dirPath).list().length);
		new FileCleaner(new File(dirPath)).removeFilesAfterSeconds(1).clean();
		Assert.assertEquals(2, new File(dirPath).list().length);
	}

	@Test
	public void testSecondsSeed() {
		FileCleaner c = new FileCleaner(new File(""));
		int expected = 1;
		c.removeFilesAfterSeconds(expected);
		
		Assert.assertEquals(new Long((long) expected * SECOND_IN_MILLIS), c.getSeed());
		
		expected = 15;
		c.removeFilesAfterSeconds(expected);
		Assert.assertEquals(new Long((long) expected * SECOND_IN_MILLIS), c.getSeed());
		
		expected = 39;
		c.removeFilesAfterSeconds(expected);
		Assert.assertEquals(new Long((long) expected * SECOND_IN_MILLIS), c.getSeed());
	}

	@Test
	public void testMinutesSeed() {
		FileCleaner c = new FileCleaner(new File(""));
		int expected = 1;
		c.removeFilesAfterMinutes(expected);
		
		Assert.assertEquals(new Long((long) expected * MINUTE_IN_MILLIS), c.getSeed());
		
		expected = 15;
		c.removeFilesAfterMinutes(expected);
		Assert.assertEquals(new Long((long) expected * MINUTE_IN_MILLIS), c.getSeed());
		
		expected = 39;
		c.removeFilesAfterMinutes(expected);
		Assert.assertEquals(new Long((long) expected * MINUTE_IN_MILLIS), c.getSeed());
	}

	@Test
	public void testHoursSeed() {
		FileCleaner c = new FileCleaner(new File(""));
		int expected = 1;
		c.removeFilesAfterHours(expected);
		
		Assert.assertEquals(new Long((long) expected * HOUR_IN_MILLIS), c.getSeed());
		
		expected = 6;
		c.removeFilesAfterHours(expected);
		Assert.assertEquals(new Long((long) expected * HOUR_IN_MILLIS), c.getSeed());
		
		expected = 74;
		c.removeFilesAfterHours(expected);
		Assert.assertEquals(new Long((long) expected * HOUR_IN_MILLIS), c.getSeed());
	}

	@Test
	public void testDaysSeed() {
		FileCleaner c = new FileCleaner(new File(""));
		int expected = 1;
		c.removeFilesAfterDays(expected);
		
		Assert.assertEquals(new Long(expected * DAY_IN_MILLIS), c.getSeed());
		
		expected = 31;
		c.removeFilesAfterDays(expected);
		Assert.assertEquals(new Long((long) expected * DAY_IN_MILLIS), c.getSeed());
		
		expected = 365;
		c.removeFilesAfterDays(expected);
		Assert.assertEquals(new Long((long) expected * DAY_IN_MILLIS), c.getSeed());
	}
	
	@Test
	public void testId() {
		Assert.assertEquals(CleanerTypes.FILE.name(), new FileCleaner(null).id());
	}
}