package com.github.aureliano.damihilogs.annotation.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.aureliano.damihilogs.validation.NotNullValidator;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Constraint(validatedBy = NotNullValidator.class)
public @interface NotNull {
	
	public abstract String message() default "Expected to find a not null value for field #{0}.";
}