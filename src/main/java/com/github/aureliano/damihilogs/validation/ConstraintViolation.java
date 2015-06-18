package com.github.aureliano.damihilogs.validation;

import java.lang.annotation.Annotation;

public class ConstraintViolation {

	private Class<? extends Annotation> validator;
	private String message;
	
	public ConstraintViolation() {
		super();
	}

	public Class<? extends Annotation> getValidator() {
		return validator;
	}

	public ConstraintViolation withValidator(Class<? extends Annotation> validator) {
		this.validator = validator;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public ConstraintViolation withMessage(String message) {
		this.message = message;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.message == null) ? 0 :this. message.hashCode());
		result = prime * result + ((this.validator == null) ? 0 : this.validator.getCanonicalName().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConstraintViolation other = (ConstraintViolation) obj;
		if (this.message == null) {
			if (other.message != null)
				return false;
		} else if (!this.message.equals(other.message))
			return false;
		if (this.validator != null && other.validator != null) {
			return this.validator.getCanonicalName().equals(other.validator.getCanonicalName());
		} else if (this.validator == null && other.validator != null) {
			return false;
		} else if (this.validator != null && other.validator == null) {
			return false;
		}
		
		return true;
	}
}