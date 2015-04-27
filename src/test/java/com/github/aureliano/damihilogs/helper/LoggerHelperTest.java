package com.github.aureliano.damihilogs.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

public class LoggerHelperTest {

	@Test
	public void testSaveExecutionLogData() {
		Properties p = new Properties();
		p.put("profile.time.elapsed", "8 milliseconds");
		p.put("profile.processor.available", "8");
		p.put("profile.jvm.memory.free", "0.34 MiB");
		p.put("profile.jvm.memory.max", "910.25 MiB");
		p.put("profile.jvm.memomry.total", "61.38 MiB");
		p.put("profile.jvm.memomry.used", "23.1 MiB");
		
		String colectorId = "blow-up";
		File output = LoggerHelper.saveExecutionLogData(colectorId, p);
		Assert.assertNotNull(output);
		Assert.assertTrue(output.exists() && output.isFile());
		Assert.assertTrue(output.getName().startsWith("blow-up"));
		
		Properties p2 = this.loadProperties(output);
		
		Assert.assertEquals(p.get("profile.time.elapsed"), p2.get("profile.time.elapsed"));
		Assert.assertEquals(p.get("profile.processor.available"), p2.get("profile.processor.available"));
		Assert.assertEquals(p.get("profile.jvm.memory.free"), p2.get("profile.jvm.memory.free"));
		Assert.assertEquals(p.get("profile.jvm.memory.max"), p2.get("profile.jvm.memory.max"));
		Assert.assertEquals(p.get("profile.jvm.memomry.total"), p2.get("profile.jvm.memomry.total"));
		Assert.assertEquals(p.get("profile.jvm.memomry.used"), p2.get("profile.jvm.memomry.used"));
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