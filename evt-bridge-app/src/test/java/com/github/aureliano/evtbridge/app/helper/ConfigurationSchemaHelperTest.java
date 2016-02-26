package com.github.aureliano.evtbridge.app.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Test;

public class ConfigurationSchemaHelperTest {

	@Test
	public void testLoadRootSchema() {
		Map<String, Object> map = ConfigurationSchemaHelper.loadRootSchema();
		
		assertEquals("event-collector-configuration.json", map.get("id"));
		assertNotNull(map.get("event_collection_scheduler"));
		assertNotNull(map.get("input"));
		assertNotNull(map.get("output"));
	}
	
	@Test
	public void testFetchSchemaNames() {
		List<String> schemata = ConfigurationSchemaHelper.fetchSchemaNames();
		
		assertEquals(4, schemata.size());
		assertTrue(schemata.contains("root"));
		assertTrue(schemata.contains("event_collection_scheduler"));
		assertTrue(schemata.contains("input"));
		assertTrue(schemata.contains("output"));
	}
	
	@Test
	public void testFetchSchema() {
		String schema = ConfigurationSchemaHelper.fetchSchema("input", null);
		assertTrue(schema.contains("\"title\": \"Input configuration.\","));
		
		schema = ConfigurationSchemaHelper.fetchSchema("input", "standard");
		assertTrue(schema.contains("\"title\": \"Input configuration for standard.\","));
	}
}