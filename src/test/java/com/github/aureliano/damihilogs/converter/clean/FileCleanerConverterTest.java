package com.github.aureliano.damihilogs.converter.clean;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.clean.FileCleaner;
import com.github.aureliano.damihilogs.clean.ICleaner;

public class FileCleanerConverterTest {

	@Test
	public void testConvert() {
		Map<String, Object> after = new HashMap<String, Object>();
		after.put("timeUnit", "HOURS");
		after.put("value", 5);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", "file");
		map.put("removeFilesAfter", after);
		
		ICleaner cleaner = new FileCleanerConverter().convert(map);
		Assert.assertTrue(cleaner instanceof FileCleaner);
	}
}