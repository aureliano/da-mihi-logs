package com.github.aureliano.damihilogs.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.github.aureliano.damihilogs.config.IConfiguration;

public interface IValidator {

	public abstract ConstraintViolation validate(IConfiguration configuration, Method method, Annotation annotation);
}