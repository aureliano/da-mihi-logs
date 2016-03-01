package com.github.aureliano.evtbridge.core.doc;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.aureliano.evtbridge.core.SchemaTypes;

public class JsonSchemaBuilderTest {

	@Test
	public void testBuildRoot() throws JsonProcessingException {
		String json = new JsonSchemaBuilder().build(SchemaTypes.ROOT);
		
		assertTrue(json.contains("http://json-schema.org/draft-04/schema#"));
		assertTrue(json.contains("Events collector configuration schema."));
		assertTrue(json.contains("object"));
		
		assertTrue(json.contains("properties"));
		
		assertTrue(json.contains("collectorId"));
		assertTrue(json.contains("metadata"));
		assertTrue(json.contains("eventsCollectorListeners"));
		assertTrue(json.contains("scheduler"));
		assertTrue(json.contains("multiThreadingEnabled"));
		assertTrue(json.contains("persistExecutionLog"));
		assertTrue(json.contains("outputConfigs"));
		assertTrue(json.contains("inputConfigs"));
	}
}