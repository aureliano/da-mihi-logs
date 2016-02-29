package com.github.aureliano.evtbridge.common.doc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SchemaConfigurationTest {

	@Test
	public void testHashCode() {
		SchemaConfiguration c1 = new SchemaConfiguration().withSchema("schema");
		SchemaConfiguration c2 = new SchemaConfiguration().withSchema("schema");
		assertEquals(c1.hashCode(), c2.hashCode());

		c1.withTitle("title");
		c2.withTitle("title");
		assertEquals(c1.hashCode(), c2.hashCode());
		
		c1.withType("type");
		c2.withType("type");
		assertEquals(c1.hashCode(), c2.hashCode());
		
		c1.withType("type_A");
		assertFalse(c1.hashCode() == c2.hashCode());
	}
	
	@Test
	public void testEquals() {
		SchemaConfiguration c1 = new SchemaConfiguration();
		SchemaConfiguration c2 = new SchemaConfiguration();
		assertTrue(c1.equals(c2));
		
		c1.withSchema("schema");
		assertFalse(c1.equals(c2));
		
		c2.withSchema("schema");
		assertTrue(c1.equals(c2));
		
		c1.withTitle("title");
		assertFalse(c1.equals(c2));
		
		c2.withTitle("title");
		assertTrue(c1.equals(c2));
		
		c1.withType("type");
		assertFalse(c1.equals(c2));
		
		c2.withType("type");
		assertTrue(c1.equals(c2));
	}
}