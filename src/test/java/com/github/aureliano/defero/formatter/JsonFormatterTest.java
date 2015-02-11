package com.github.aureliano.defero.formatter;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class JsonFormatterTest {

	@Test
	public void testFormat() {
		IOutputFormatter formatter = new JsonFormatter();
		Assert.assertEquals("", formatter.format(null));
		
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("firstName", "Aureliano");
		map.put("lastName", "França");
		map.put("score", 10);
		
		Assert.assertEquals("{\"firstName\":\"Aureliano\",\"lastName\":\"França\",\"score\":10}", formatter.format(map));
	}
}