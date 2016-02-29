package com.github.aureliano.evtbridge.core;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class SchemaTypesTest {

	@Test
	public void testAvailableSchemaTypes() {
		List<SchemaTypes> types = Arrays.asList(
			SchemaTypes.ROOT, SchemaTypes.INPUT,
			SchemaTypes.OUTPUT, SchemaTypes.SCHEDULER
		);
		
		for (SchemaTypes type : SchemaTypes.values()) {
			assertTrue(types.contains(type));
		}
	}
}