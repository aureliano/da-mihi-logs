package com.github.aureliano.evtbridge.common.doc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SchemaPropertyTest {

	@Test
	public void testHashCode() {
		SchemaProperty p1 = new SchemaProperty().withProperty("test");
		SchemaProperty p2 = new SchemaProperty().withProperty("test");
		assertEquals(p1.hashCode(), p2.hashCode());

		p1 = new SchemaProperty().withProperty("property");
		p2 = new SchemaProperty().withProperty("property");
		assertEquals(p1.hashCode(), p2.hashCode());
	}
	
	@Test
	public void testEquals() {
		SchemaProperty p1 = new SchemaProperty();
		SchemaProperty p2 = new SchemaProperty();
		assertTrue(p1.equals(p2));
		
		p1.withProperty("test");
		assertFalse(p1.equals( p2));
		
		p2.withProperty("test");
		assertTrue(p1.equals( p2));
	}
}