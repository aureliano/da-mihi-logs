package com.github.aureliano.evtbridge.annotation.validation.apply;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import com.github.aureliano.evtbridge.annotation.validation.Constraint;
import com.github.aureliano.evtbridge.common.helper.ReflectionHelper;

public final class ObjectValidator implements IValidator {

	private static ObjectValidator instance;
	
	private ObjectValidator() {
		super();
	}
	
	public static ObjectValidator instance() {
		if (instance == null) {
			instance = new ObjectValidator();
		}
		
		return instance;
	}

	public Set<ConstraintViolation> validate(Object object) {
		Method[] methods = object.getClass().getMethods();
		Set<ConstraintViolation> violations = new HashSet<ConstraintViolation>();
		
		for (Method method : methods) {
			Annotation[] annotations = method.getAnnotations();
			
			for (Annotation annotation : annotations) {
				Constraint constraintAnnotation = annotation.annotationType().getAnnotation(Constraint.class);
				if (constraintAnnotation == null) {
					continue;
				}
				
				IValidator validator = (IValidator) ReflectionHelper.newInstance(constraintAnnotation.validatedBy());
				Set<ConstraintViolation> res = validator.validate(object, method, annotation);
				
				this.addAll(violations, res);
			}
		}
		
		return violations;
	}
	
	@Override
	public Set<ConstraintViolation> validate(Object object, Method method, Annotation annotation) {
		throw new UnsupportedOperationException("Method not implemented.");
	}
	
	private void addAll(Set<ConstraintViolation> violations, Set<ConstraintViolation> res) {
		if ((res != null) && (!res.isEmpty())) {
			for (ConstraintViolation violation : res) {
				violations.add(violation);
			}
		}
	}
}