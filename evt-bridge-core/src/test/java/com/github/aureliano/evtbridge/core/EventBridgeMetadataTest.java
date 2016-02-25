package com.github.aureliano.evtbridge.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class EventBridgeMetadataTest {

	@Test
	public void testInstance() {
		assertNotNull(EventBridgeMetadata.instance());
	}
	
	@Test
	public void testGetProperty() {
		EventBridgeMetadata metadata = EventBridgeMetadata.instance();

		assertEquals("Event Bridge", metadata.getProperty("project.name"));
		assertEquals("https://github.com/aureliano/evt-bridge", metadata.getProperty("project.scm"));
		assertEquals("https://github.com/aureliano/evt-bridge/issues", metadata.getProperty("project.issues"));
		assertEquals("https://github.com/aureliano/evt-bridge/wiki", metadata.getProperty("project.wiki"));
		
		assertEquals("1.7", metadata.getProperty("app.requirement.jvm"));
		assertEquals("0.1.0", metadata.getProperty("app.version"));
	}
}