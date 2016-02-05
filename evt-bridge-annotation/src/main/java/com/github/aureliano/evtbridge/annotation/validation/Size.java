package com.github.aureliano.evtbridge.annotation.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.aureliano.evtbridge.annotation.validation.apply.SizeValidator;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Constraint(validatedBy = SizeValidator.class)
public @interface Size {

	public abstract String message() default "Expected field #{0} to have size between #{1} and #{2} but got #{3}.";
	
	public abstract int min();
	
	public abstract int max();
}