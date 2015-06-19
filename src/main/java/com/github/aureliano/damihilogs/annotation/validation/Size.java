package com.github.aureliano.damihilogs.annotation.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.aureliano.damihilogs.validation.SizeValidator;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Constraint(validatedBy = SizeValidator.class)
public @interface Size {

	public abstract String message() default "Expected field ? to have size between ? and ? but got ?.";
	
	public abstract int min();
	
	public abstract int max();
}