package com.github.aureliano.damihilogs.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class FileHelperTest {

	@Test
	public void testCreateLoggerFileName() {
		String dir = "log/exec";
		String collectorId = "execution";
		long timeMillis = 12345;
		
		String expected = "log/exec/execution_12345.log";
		String actual = FileHelper.createLoggerFileName(dir, collectorId, timeMillis);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testGetLastExecutionLogDataFileName() {
		final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
		String collectorId = "test";
		String fileName = collectorId + "_" + DATE_FORMAT.format(new Date()) + ".log";
		
		Assert.assertEquals(fileName, FileHelper.getLastExecutionLogDataFileName("test"));
	}
	
	@Test
	public void testReadFile() throws FileNotFoundException {
		String path = "src/test/resources/simple_file";
		String expected = "Ecce quam bonum et quam icundum habitare fratres in unum.";
		
		Assert.assertEquals(expected, FileHelper.readFile(path));
		Assert.assertEquals(expected, FileHelper.readFile(new File(path)));
		Assert.assertEquals(expected, FileHelper.readFile(new FileInputStream(new File(path))));
	}
	
	@Test
	public void testReadResource() {
		String resourceName = "simple_file";
		String expected = "Ecce quam bonum et quam icundum habitare fratres in unum.";
		
		Assert.assertEquals(expected, FileHelper.readResource(resourceName));
	}
}