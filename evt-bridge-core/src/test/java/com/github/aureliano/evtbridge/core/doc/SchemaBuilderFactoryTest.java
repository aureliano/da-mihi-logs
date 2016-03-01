package com.github.aureliano.evtbridge.core.doc;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SchemaBuilderFactoryTest {

	@Test
	public void testCreateBuilder() {
		ISchemaBuilder<?> builder = SchemaBuilderFactory.createBuilder(DocumentationSourceTypes.JSON);
		assertTrue(builder instanceof JsonSchemaBuilder);
		
		builder = SchemaBuilderFactory.createBuilder(DocumentationSourceTypes.YAML);
		assertTrue(builder instanceof YamlSchemaBuilder);
	}
}