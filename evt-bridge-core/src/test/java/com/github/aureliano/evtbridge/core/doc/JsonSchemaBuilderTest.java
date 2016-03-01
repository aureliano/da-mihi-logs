package com.github.aureliano.evtbridge.core.doc;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.aureliano.evtbridge.core.SchemaTypes;

public class JsonSchemaBuilderTest {

	@Test
	public void testBuildRoot() {
		String json = new JsonSchemaBuilder().build(SchemaTypes.ROOT);

		assertTrue(json.contains("http://json-schema.org/draft-04/schema#"));
		assertTrue(json.contains("Events collector configuration schema."));
		assertTrue(json.contains("object"));
		
		assertTrue(json.contains("\"properties\" : {"));
		
		assertTrue(json.contains("\"collectorId\" : {"));
		assertTrue(json.contains("\"metadata\" : {"));
		assertTrue(json.contains("\"eventsCollectorListeners\" : {"));
		assertTrue(json.contains("\"scheduler\" : {"));
		assertTrue(json.contains("\"multiThreadingEnabled\" : {"));
		assertTrue(json.contains("\"persistExecutionLog\" : {"));
		assertTrue(json.contains("\"outputConfigs\" : {"));
		assertTrue(json.contains("\"inputConfigs\" : {"));
	}

	@Test
	public void testBuildScheduler() {
		String json = new JsonSchemaBuilder().build(SchemaTypes.SCHEDULER);

		assertTrue(json.contains("http://json-schema.org/draft-04/schema#"));
		assertTrue(json.contains("Scheduling execution configuration."));
		assertTrue(json.contains("object"));
		
		assertTrue(json.contains("  \"properties\" : {"));
		
		assertTrue(json.contains("    \"type\" : {"));
		assertTrue(json.contains("      \"anyOf\" : ["));
		assertTrue(json.contains("\"execute_once_at_specific_time\""));
		assertTrue(json.contains("\"execute_periodically_at_specific_time\""));
		assertTrue(json.contains("\"execute_periodically\""));
	}

	@Test
	public void testBuildInput() {
		String json = new JsonSchemaBuilder().build(SchemaTypes.INPUT);

		assertTrue(json.contains("http://json-schema.org/draft-04/schema#"));
		assertTrue(json.contains("Input configuration."));
		assertTrue(json.contains("object"));
		
		assertTrue(json.contains("  \"properties\" : {"));
		
		assertTrue(json.contains("    \"type\" : {"));
		assertTrue(json.contains("      \"anyOf\" : ["));
		assertTrue(json.contains("\"external_command\""));
		assertTrue(json.contains("\"file\""));
		assertTrue(json.contains("\"file_tailer\""));
		assertTrue(json.contains("\"standard\""));
		assertTrue(json.contains("\"url\""));
		assertTrue(json.contains("\"jdbc\""));
	}
}