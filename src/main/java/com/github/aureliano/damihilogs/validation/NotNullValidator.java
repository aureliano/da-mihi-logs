package com.github.aureliano.damihilogs.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.github.aureliano.damihilogs.annotation.NotNull;
import com.github.aureliano.damihilogs.config.IConfiguration;
import com.github.aureliano.damihilogs.helper.ReflectionHelper;

public class NotNullValidator implements IValidator {

	public NotNullValidator() {
		super();
	}

	@Override
	public ConstraintViolation validate(IConfiguration configuration, Method method, Annotation annotation) {
		String property = ReflectionHelper.getSimpleAccessMethodName(method);
		Object returnedValue = ReflectionHelper.callMethod(configuration, method.getName(), null, null);
		
		if (returnedValue == null) {
			String message = ((NotNull) annotation).message();
			return new ConstraintViolation()
				.withValidator(NotNull.class)
				.withMessage(message.replaceFirst("\\?", property));
		}
		
		return null;
		
		/*for (Method method : methods) {
			Annotation[] annotations = method.getAnnotations();
			for (Annotation annotation : annotations) {
				if (annotation.annotationType().equals(NotNull.class)) {
					Object returnedValue = ReflectionHelper.callMethod(configuration, method.getName(), null, null);
					if (returnedValue == null) {
						String message = ((NotNull) annotation).message();
						validation.add(new ConstraintViolation()
							.withValidator(NotNull.class)
							.withMessage(message.replaceFirst("\\?", ));
					}
				}
			}
		}
		
		return validation;*/
	}
}