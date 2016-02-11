package com.github.aureliano.evtbridge.core.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

public interface IValidator {

	public abstract Set<ConstraintViolation> validate(Object object, Method method, Annotation annotation);
}