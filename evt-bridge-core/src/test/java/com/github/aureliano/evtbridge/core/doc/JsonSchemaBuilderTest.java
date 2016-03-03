package com.github.aureliano.evtbridge.core.doc;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.aureliano.evtbridge.core.SchemaTypes;
import com.github.aureliano.evtbridge.core.schedule.SchedulerTypes;

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
	public void testExecutePeriodicallyScheduler() {
		String json = new JsonSchemaBuilder().build(SchedulerTypes.EXECUTE_PERIODICALLY);

		assertTrue(json.contains("http://json-schema.org/draft-04/schema#"));
		assertTrue(json.contains("Schedule a task to execute periodically"));
		assertTrue(json.contains("object"));
		
		assertTrue(json.contains("  \"properties\" : {"));
		
		assertTrue(json.contains("\"delay\""));
		assertTrue(json.contains("\"period\""));
		assertTrue(json.contains("\"timeUnit\""));
	}

	@Test
	public void testExecuteOnceAtSpecificTimeScheduler() {
		String json = new JsonSchemaBuilder().build(SchedulerTypes.EXECUTE_ONCE_AT_SPECIFIC_TIME);

		assertTrue(json.contains("http://json-schema.org/draft-04/schema#"));
		assertTrue(json.contains("Schedule a task to a exute at specified date time."));
		assertTrue(json.contains("object"));
		
		assertTrue(json.contains("  \"properties\" : {"));
		
		assertTrue(json.contains("\"startupTime\""));
	}

	@Test
	public void testExecutePeriodicallyAtSpecificTimeScheduler() {
		String json = new JsonSchemaBuilder().build(SchedulerTypes.EXECUTE_PERIODICALLY_AT_SPECIFIC_TIME);

		assertTrue(json.contains("http://json-schema.org/draft-04/schema#"));
		assertTrue(json.contains("Schedule a task to execute periodically at specified time."));
		assertTrue(json.contains("object"));
		
		assertTrue(json.contains("  \"properties\" : {"));
		
		assertTrue(json.contains("\"hour\""));
		assertTrue(json.contains("\"minute\""));
		assertTrue(json.contains("\"second\""));
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
		assertTrue(json.contains("\"file_input\""));
		assertTrue(json.contains("\"file_tailer\""));
		assertTrue(json.contains("\"standard_input\""));
		assertTrue(json.contains("\"url\""));
		assertTrue(json.contains("\"jdbc_input\""));
	}

	@Test
	public void testBuildOutput() {
		String json = new JsonSchemaBuilder().build(SchemaTypes.OUTPUT);

		assertTrue(json.contains("http://json-schema.org/draft-04/schema#"));
		assertTrue(json.contains("Output configuration."));
		assertTrue(json.contains("object"));
		
		assertTrue(json.contains("  \"properties\" : {"));
		
		assertTrue(json.contains("    \"type\" : {"));
		assertTrue(json.contains("      \"anyOf\" : ["));
		assertTrue(json.contains("\"file_output\""));
		assertTrue(json.contains("\"standard_output\""));
		assertTrue(json.contains("\"jdbc_output\""));
		assertTrue(json.contains("\"elastic_search\""));
	}
}