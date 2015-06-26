package com.github.aureliano.damihilogs.config;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.annotation.validation.NotNull;
import com.github.aureliano.damihilogs.validation.ConstraintViolation;
import com.github.aureliano.damihilogs.validation.ObjectValidator;

public class BoundingBoxLocationTest {

	ObjectValidator validator = ObjectValidator.instance();
	
	@Test
	public void testValidation() {
		Set<ConstraintViolation> violations = this.validator.validate(this.createValidLocation());
		Assert.assertTrue(violations.isEmpty());
	}
	
	@Test
	public void testFromLocationValidation() {
		BoundingBoxLocation location = this.createValidLocation().withFromLocation(null);
		
		Set<ConstraintViolation> violations = this.validator.validate(location);
		Assert.assertTrue(violations.size() == 1);
		Assert.assertEquals(NotNull.class, violations.iterator().next().getValidator());
	}
	
	@Test
	public void testToLocationValidation() {
		BoundingBoxLocation location = this.createValidLocation().withToLocation(null);
		
		Set<ConstraintViolation> violations = this.validator.validate(location);
		Assert.assertTrue(violations.size() == 1);
		Assert.assertEquals(NotNull.class, violations.iterator().next().getValidator());
	}
	
	private BoundingBoxLocation createValidLocation() {
		return new BoundingBoxLocation()
			.withFromLocation(new GeoLocation().withLatitude(5.0).withLongitude(6.3))
			.withToLocation(new GeoLocation().withLatitude(5.1).withLongitude(6.45217));
	}
}