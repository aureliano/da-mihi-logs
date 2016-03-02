package com.github.aureliano.evtbridge.core.doc;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.aureliano.evtbridge.core.SchemaTypes;
import com.github.aureliano.evtbridge.core.schedule.SchedulerTypes;

public class YamlSchemaBuilderTest {

	@Test
	public void testBuildRoot() {
		String yaml = new YamlSchemaBuilder().build(SchemaTypes.ROOT);

		assertFalse(yaml.contains("http://json-schema.org/draft-04/schema#"));
		assertTrue(yaml.contains("Events collector configuration schema."));
		assertTrue(yaml.contains("object"));
		
		assertTrue(yaml.contains("properties:"));
		
		assertTrue(yaml.contains("  collectorId:"));
		assertTrue(yaml.contains("  metadata:"));
		assertTrue(yaml.contains("  eventsCollectorListeners:"));
		assertTrue(yaml.contains("  scheduler:"));
		assertTrue(yaml.contains("  multiThreadingEnabled:"));
		assertTrue(yaml.contains("  persistExecutionLog:"));
		assertTrue(yaml.contains("  outputConfigs:"));
		assertTrue(yaml.contains("  inputConfigs:"));
	}

	@Test
	public void testBuildScheduler() {
		String yaml = new YamlSchemaBuilder().build(SchemaTypes.SCHEDULER);

		assertTrue(yaml.contains("Scheduling execution configuration."));
		assertTrue(yaml.contains("object"));
		
		assertTrue(yaml.contains("properties:"));
		
		assertTrue(yaml.contains("  type:"));
		assertTrue(yaml.contains("    anyOf:"));
		assertTrue(yaml.contains("execute_once_at_specific_time"));
		assertTrue(yaml.contains("execute_periodically_at_specific_time"));
		assertTrue(yaml.contains("execute_periodically"));
	}

	@Test
	public void testExecutePeriodicallyScheduler() {
		String yaml = new YamlSchemaBuilder().build(SchedulerTypes.EXECUTE_PERIODICALLY);

		assertTrue(yaml.contains("Schedule a task to execute periodically"));
		assertTrue(yaml.contains("object"));
		
		assertTrue(yaml.contains("properties:"));
		
		assertTrue(yaml.contains("  delay"));
		assertTrue(yaml.contains("  period"));
		assertTrue(yaml.contains("  timeUnit"));
	}

	@Test
	public void testBuildInput() {
		String yaml = new YamlSchemaBuilder().build(SchemaTypes.INPUT);

		assertTrue(yaml.contains("Input configuration."));
		assertTrue(yaml.contains("object"));
		
		assertTrue(yaml.contains("properties:"));
		
		assertTrue(yaml.contains("  type:"));
		assertTrue(yaml.contains("    anyOf:"));
		assertTrue(yaml.contains("external_command"));
		assertTrue(yaml.contains("file"));
		assertTrue(yaml.contains("file_tailer"));
		assertTrue(yaml.contains("standard"));
		assertTrue(yaml.contains("url"));
		assertTrue(yaml.contains("jdbc"));
	}

	@Test
	public void testBuildOutput() {
		String yaml = new YamlSchemaBuilder().build(SchemaTypes.OUTPUT);

		assertTrue(yaml.contains("Output configuration."));
		assertTrue(yaml.contains("object"));
		
		assertTrue(yaml.contains("properties:"));
		
		assertTrue(yaml.contains("  type:"));
		assertTrue(yaml.contains("    anyOf:"));
		assertTrue(yaml.contains("file"));
		assertTrue(yaml.contains("elastic_search"));
		assertTrue(yaml.contains("standard"));
		assertTrue(yaml.contains("jdbc"));
	}
}