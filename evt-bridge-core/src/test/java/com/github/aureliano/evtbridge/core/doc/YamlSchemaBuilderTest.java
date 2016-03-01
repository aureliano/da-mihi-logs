package com.github.aureliano.evtbridge.core.doc;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.aureliano.evtbridge.core.SchemaTypes;

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
}