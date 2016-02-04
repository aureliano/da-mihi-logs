package com.github.aureliano.almamater.annotation.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.aureliano.almamater.annotation.validation.apply.NotNullValidator;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Constraint(validatedBy = NotNullValidator.class)
public @interface NotNull {
	
	public abstract String message() default "Expected to find a not null value for field #{0}.";
}