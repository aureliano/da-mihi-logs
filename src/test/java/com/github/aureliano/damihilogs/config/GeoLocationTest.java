package com.github.aureliano.damihilogs.config;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.annotation.validation.Decimal;
import com.github.aureliano.damihilogs.annotation.validation.NotNull;
import com.github.aureliano.damihilogs.validation.ConstraintViolation;
import com.github.aureliano.damihilogs.validation.ObjectValidator;

public class GeoLocationTest {
	
	ObjectValidator validator = ObjectValidator.instance();
	
	@Test
	public void testLatitudeValidation() {
		GeoLocation location = this.createValidLocation().withLatitude(null);
		
		Set<ConstraintViolation> violations = this.validator.validate(location);
		Assert.assertTrue(violations.size() == 1);
		Assert.assertEquals(NotNull.class, violations.iterator().next().getValidator());
		
		location.withLatitude(-90.00001);
		violations = this.validator.validate(location);
		Assert.assertTrue(violations.size() == 1);
		ConstraintViolation violation = violations.iterator().next();
		Assert.assertEquals(Decimal.class, violation.getValidator());
		Assert.assertEquals("Expected field latitude to have size between -90.0 and 90.0 but got -90.00001.", violation.getMessage());
		
		location.withLatitude(+90.00001);
		violations = this.validator.validate(location);
		Assert.assertTrue(violations.size() == 1);
		violation = violations.iterator().next();
		Assert.assertEquals(Decimal.class, violation.getValidator());
		Assert.assertEquals("Expected field latitude to have size between -90.0 and 90.0 but got 90.00001.", violation.getMessage());
	}
	
	@Test
	public void testLongitudeValidation() {
		GeoLocation location = this.createValidLocation().withLongitude(null);
		
		Set<ConstraintViolation> violations = this.validator.validate(location);
		Assert.assertTrue(violations.size() == 1);
		Assert.assertEquals(NotNull.class, violations.iterator().next().getValidator());
		
		location.withLongitude(-180.00001);
		violations = this.validator.validate(location);
		Assert.assertTrue(violations.size() == 1);
		ConstraintViolation violation = violations.iterator().next();
		Assert.assertEquals(Decimal.class, violation.getValidator());
		Assert.assertEquals("Expected field longitude to have size between -180.0 and 180.0 but got -180.00001.", violation.getMessage());
		
		location.withLongitude(+180.00001);
		violations = this.validator.validate(location);
		Assert.assertTrue(violations.size() == 1);
		violation = violations.iterator().next();
		Assert.assertEquals(Decimal.class, violation.getValidator());
		Assert.assertEquals("Expected field longitude to have size between -180.0 and 180.0 but got 180.00001.", violation.getMessage());
	}
	
	@Test
	public void testValidation() {
		Set<ConstraintViolation> violations = this.validator.validate(this.createValidLocation());
		Assert.assertTrue(violations.isEmpty());
	}
	
	private GeoLocation createValidLocation() {
		return new GeoLocation().withLatitude(-19.9225).withLongitude(-43.9450);
	}
}