package com.github.aureliano.damihilogs.validation;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.annotation.validation.NotNull;

public class ConstraintViolationTest {

	@Test
	public void testEquals() {
		ConstraintViolation c1 = new ConstraintViolation();
		Assert.assertFalse(c1.equals(null));
		
		ConstraintViolation c2 = new ConstraintViolation();
		Assert.assertEquals(c1, c2);
		
		c1.withMessage("A test message.");
		Assert.assertFalse(c1.equals(c2));
		
		c2.withMessage("A test message.");
		Assert.assertTrue(c1.equals(c2));
		
		c1.withValidator(NotNull.class);
		Assert.assertFalse(c1.equals(c2));
		
		c2.withValidator(Override.class);
		Assert.assertFalse(c1.equals(c2));
		
		c2.withValidator(NotNull.class);
		Assert.assertTrue(c1.equals(c2));
	}
}