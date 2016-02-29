package com.github.aureliano.evtbridge.common.doc;

public class SchemaProperty {

	private String property;
	private String type;
	private String description;
	public String defaultValue;
	public Class<?> reference;
	
	public SchemaProperty() {
		this.defaultValue = "";
		this.reference = Class.class;
	}

	public String getProperty() {
		return property;
	}

	public SchemaProperty withProperty(String property) {
		this.property = property;
		return this;
	}

	public String getType() {
		return type;
	}

	public SchemaProperty withType(String type) {
		this.type = type;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public SchemaProperty withDescription(String description) {
		this.description = description;
		return this;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public SchemaProperty withDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
		return this;
	}

	public Class<?> getReference() {
		return reference;
	}

	public SchemaProperty withReference(Class<?> reference) {
		this.reference = reference;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((property == null) ? 0 : property.hashCode());
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
		SchemaProperty other = (SchemaProperty) obj;
		if (property == null) {
			if (other.property != null)
				return false;
		} else if (!property.equals(other.property))
			return false;
		return true;
	}
}