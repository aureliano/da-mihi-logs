package com.github.aureliano.evtbridge.core.doc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.aureliano.evtbridge.core.SchemaTypes;

public class JsonSchemaBuilderTest {

	@Test
	public void testBuildRoot() throws JsonProcessingException {
		Map<String, Object> map = new JsonSchemaBuilder().build(SchemaTypes.ROOT);
		
		assertEquals("http://json-schema.org/draft-04/schema#", map.get("$schema"));
		assertEquals("Events collector configuration schema.", map.get("title"));
		assertEquals("object", map.get("type"));
		
		@SuppressWarnings("unchecked")
		Map<String, Object> properties = (Map<String, Object>) map.get("properties");
		assertEquals(8, properties.size());
		
		assertNotNull(properties.get("collectorId"));
		assertNotNull(properties.get("metadata"));
		assertNotNull(properties.get("eventsCollectorListeners"));
		assertNotNull(properties.get("scheduler"));
		assertNotNull(properties.get("multiThreadingEnabled"));
		assertNotNull(properties.get("persistExecutionLog"));
		assertNotNull(properties.get("outputConfigs"));
		assertNotNull(properties.get("inputConfigs"));
	}
}