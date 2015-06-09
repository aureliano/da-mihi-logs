package com.github.aureliano.damihilogs.converter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.clean.CleanerTypes;
import com.github.aureliano.damihilogs.clean.FileCleaner;
import com.github.aureliano.damihilogs.clean.ICleaner;
import com.github.aureliano.damihilogs.clean.LogCleaner;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;

public class CleanerConverterTest {

	private CleanerConverter converter;
	
	public CleanerConverterTest() {
		this.converter = new CleanerConverter();
	}
	
	@Test
	public void testCreateCleanerConverterErrorNonExistent() {
		try {
			this.converter.convert(new HashMap<String, Object>());
			Assert.fail("An exception was expected.");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Cleaner type 'null' not supported. Expected one of: " + Arrays.toString(CleanerTypes.values()), ex.getMessage());
		}
	}
	
	@Test
	public void testCreateCleanerConverter() {
		Map<String, Object> after = new HashMap<String, Object>();
		after.put("timeUnit", "HOURS");
		after.put("value", 5);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", "file");
		map.put("removeFilesAfter", after);
		
		ICleaner cleaner = this.converter.convert(map);
		Assert.assertTrue(cleaner instanceof FileCleaner);
	}
	
	@Test
	public void testCreateLogCleanerConverterErrorTime() {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> after = new HashMap<String, Object>();
		after.put("timeUnit", "HOURS");
		map.put("type", "log");
		map.put("removeLogDataFilesAfter", after);
		
		try {
			this.converter.convert(map);
			Assert.fail("An exception was expected.");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Property removeLogDataFilesAfter => value was expected to match \\d+ pattern in cleaner configuration.", ex.getMessage());
		}
		
		Map<String, Object> logData = new HashMap<String, Object>();
		logData.put("timeUnit", "HOURS");
		logData.put("value", 5);
		
		Map<String, Object> logEcho = new HashMap<String, Object>();
		logEcho.put("timeUnit", "HOURS");
		
		map.put("removeLogDataFilesAfter", logData);
		map.put("removeLogEchoFilesAfter", logEcho);
		
		try {
			this.converter.convert(map);
			Assert.fail("An exception was expected.");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Property removeLogEchoFilesAfter => value was expected to match \\d+ pattern in cleaner configuration.", ex.getMessage());
		}
	}
	
	@Test
	public void testCreateLogCleanerConverter() {
		Map<String, Object> after = new HashMap<String, Object>();
		after.put("timeUnit", "HOURS");
		after.put("value", 5);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", "log");
		map.put("removeFilesAfter", after);
		
		ICleaner cleaner = this.converter.convert(map);
		Assert.assertTrue(cleaner instanceof LogCleaner);
		
	}
}