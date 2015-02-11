package com.github.aureliano.defero.filter;

import junit.framework.Assert;

import org.junit.Test;

public class DefaultEmptyFilterTest {

	@Test
	public void testAccept() {
		IEventFielter filter = new DefaultEmptyFilter();
		
		Assert.assertTrue(filter.accept(null));
		Assert.assertTrue(filter.accept("whatever"));
		Assert.assertTrue(filter.accept(38));
	}
}