package com.github.aureliano.almamater.annotation.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.aureliano.almamater.annotation.validation.apply.NotEmptyValidator;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Constraint(validatedBy = NotEmptyValidator.class)
public @interface NotEmpty {

	public abstract String message() default "Expected to find a not empty text for field #{0}.";
}