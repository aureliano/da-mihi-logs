package com.github.aureliano.evtbridge.core.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.aureliano.evtbridge.annotation.validation.NotNull;

public class ConstraintViolationTest {

	@Test
	public void testEquals() {
		ConstraintViolation c1 = new ConstraintViolation();
		assertFalse(c1.equals(null));
		
		ConstraintViolation c2 = new ConstraintViolation();
		assertEquals(c1, c2);
		
		c1.withMessage("A test message.");
		assertFalse(c1.equals(c2));
		
		c2.withMessage("A test message.");
		assertTrue(c1.equals(c2));
		
		c1.withValidator(NotNull.class);
		assertFalse(c1.equals(c2));
		
		c2.withValidator(Override.class);
		assertFalse(c1.equals(c2));
		
		c2.withValidator(NotNull.class);
		assertTrue(c1.equals(c2));
	}
}