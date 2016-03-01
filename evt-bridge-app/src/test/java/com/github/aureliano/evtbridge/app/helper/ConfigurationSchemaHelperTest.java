package com.github.aureliano.evtbridge.app.helper;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ConfigurationSchemaHelperTest {
	
	@Test
	public void testFetchSchema() {
		String schema = ConfigurationSchemaHelper.fetchSchema("root", null, "json");
		assertTrue(schema.contains("\"title\" : \"Events collector configuration schema.\""));
	}
}