package com.github.aureliano.damihilogs.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

public class LoggerHelperTest {

	@Test
	public void testSaveExecutionLogData() {
		Properties p = new Properties();
		p.put("profile.time.elapsed", "8 milliseconds");
		p.put("profile.processor.available", "8");
		p.put("profile.memory.free", "0.34 MB");
		p.put("profile.memory.max", "910.25 MB");
		p.put("profile.memomry.total", "61.38 MB");
		
		String colectorId = "blow-up";
		File output = LoggerHelper.saveExecutionLogData(colectorId, p);
		Assert.assertNotNull(output);
		Assert.assertTrue(output.exists() && output.isFile());
		Assert.assertTrue(output.getName().startsWith("blow-up"));
		
		Properties p2 = this.loadProperties(output);
		
		Assert.assertEquals(p.get("profile.time.elapsed"), p2.get("profile.time.elapsed"));
		Assert.assertEquals(p.get("profile.processor.available"), p2.get("profile.processor.available"));
		Assert.assertEquals(p.get("profile.memory.free"), p2.get("profile.memory.free"));
		Assert.assertEquals(p.get("profile.memory.max"), p2.get("profile.memory.max"));
		Assert.assertEquals(p.get("profile.memomry.total"), p2.get("profile.memomry.total"));
	}
	
	@Test
	public void testGetLastExecutionLogDataFileName() {
		final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
		String collectorId = "test";
		String fileName = collectorId + "_" + DATE_FORMAT.format(new Date()) + ".log";
		
		Assert.assertEquals(fileName, LoggerHelper.getLastExecutionLogDataFileName("test"));
	}
	
	@Test
	public void testCreateLoggerFileName() {
		String dir = "log/exec";
		String collectorId = "execution";
		long timeMillis = 12345;
		
		String expected = "log/exec/execution_12345.log";
		String actual = LoggerHelper.createLoggerFileName(dir, collectorId, timeMillis);
		
		Assert.assertEquals(expected, actual);
	}
	
	private Properties loadProperties(File file) {
		Properties p2 = new Properties();
		try {
			p2.load(new FileInputStream(file));
		} catch (IOException ex) {
			Assert.fail(ex.getMessage());
		}
		
		return p2;
	}
}