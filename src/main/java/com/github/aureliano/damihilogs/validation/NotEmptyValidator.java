package com.github.aureliano.damihilogs.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.github.aureliano.damihilogs.annotation.validation.NotEmpty;
import com.github.aureliano.damihilogs.config.IConfiguration;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.helper.ReflectionHelper;
import com.github.aureliano.damihilogs.helper.StringHelper;

public class NotEmptyValidator implements IValidator {

	public NotEmptyValidator() {
		super();
	}

	@Override
	public ConstraintViolation validate(IConfiguration configuration, Method method, Annotation annotation) {
		String property = ReflectionHelper.getSimpleAccessMethodName(method);
		Object returnedValue = ReflectionHelper.callMethod(configuration, method.getName(), null, null);
		
		if ((returnedValue != null) && !(returnedValue instanceof String)) {
			throw new DaMihiLogsException("Expected to find a String but got " + returnedValue.getClass().getName() +
					". You might want to use NotNull annotation instead of NotEmpty.");
		}
		
		if (StringHelper.isEmpty(StringHelper.parse(returnedValue))) {
			String message = ((NotEmpty) annotation).message();
			return new ConstraintViolation()
				.withValidator(NotEmpty.class)
				.withMessage(message.replaceFirst("#\\{0\\}", property));
		}
		
		return null;
	}
}