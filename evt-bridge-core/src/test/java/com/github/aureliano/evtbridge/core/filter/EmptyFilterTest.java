package com.github.aureliano.evtbridge.core.filter;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EmptyFilterTest {

	@Test
	public void testAccept() {
		IEventFielter filter = new EmptyFilter();
		
		assertTrue(filter.accept(null));
		assertTrue(filter.accept("whatever"));
		assertTrue(filter.accept(38));
	}
}