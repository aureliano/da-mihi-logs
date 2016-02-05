package com.github.aureliano.almamater.annotation.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.aureliano.almamater.annotation.validation.apply.DecimalValidator;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Constraint(validatedBy = DecimalValidator.class)
public @interface Decimal {

public abstract String message() default "Expected field #{0} to have size between #{1} and #{2} but got #{3}.";
	
	public abstract double min();
	
	public abstract double max();
}