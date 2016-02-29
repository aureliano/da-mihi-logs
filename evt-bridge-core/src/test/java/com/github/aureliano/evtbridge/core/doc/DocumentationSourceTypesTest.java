package com.github.aureliano.evtbridge.core.doc;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class DocumentationSourceTypesTest {

	@Test
	public void testAvailableSourceTypes() {
		List<DocumentationSourceTypes> types = Arrays.asList(
			DocumentationSourceTypes.JSON, DocumentationSourceTypes.YAML
		);
		
		for (DocumentationSourceTypes type : DocumentationSourceTypes.values()) {
			assertTrue(types.contains(type));
		}
	}
}