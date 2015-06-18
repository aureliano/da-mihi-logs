package com.github.aureliano.damihilogs.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import com.github.aureliano.damihilogs.annotation.validation.Constraint;
import com.github.aureliano.damihilogs.config.IConfiguration;
import com.github.aureliano.damihilogs.helper.ReflectionHelper;

public final class ConfigurationValidation {

	private ConfigurationValidation() {
		super();
	}
	
	public static Set<ConstraintViolation> applyValidation(IConfiguration configuration) {
		Method[] methods = configuration.getClass().getMethods();
		Set<ConstraintViolation> violations = new HashSet<ConstraintViolation>();
		
		for (Method method : methods) {
			Annotation[] annotations = method.getAnnotations();
			
			for (Annotation annotation : annotations) {
				Constraint constraintAnnotation = annotation.annotationType().getAnnotation(Constraint.class);
				if (constraintAnnotation == null) {
					continue;
				}
				
				IValidator validator = (IValidator) ReflectionHelper.newInstance(constraintAnnotation.validatedBy());
				ConstraintViolation violation = validator.validate(configuration, method, annotation);
				
				if (violation != null) {
					violations.add(violation);
				}
			}
		}
		
		return violations;
	}
}