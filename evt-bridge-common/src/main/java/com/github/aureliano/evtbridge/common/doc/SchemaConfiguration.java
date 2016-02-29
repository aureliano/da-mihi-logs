package com.github.aureliano.evtbridge.common.doc;

import java.util.ArrayList;
import java.util.List;

public class SchemaConfiguration {

	public static final String SCHEMA_DRAFT = "http://json-schema.org/draft-04/schema#";
	public static final String SCHEMA_TYPE = "object";
	
	private String schema;
	private String title;
	private String type;
	private List<SchemaProperty> properties;
	
	public SchemaConfiguration() {
		this.properties = new ArrayList<>();
	}

	public String getSchema() {
		return schema;
	}

	public SchemaConfiguration withSchema(String schema) {
		this.schema = schema;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public SchemaConfiguration withTitle(String title) {
		this.title = title;
		return this;
	}

	public String getType() {
		return type;
	}

	public SchemaConfiguration withType(String type) {
		this.type = type;
		return this;
	}

	public List<SchemaProperty> getProperties() {
		return properties;
	}

	public SchemaConfiguration withProperties(List<SchemaProperty> properties) {
		this.properties = properties;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((schema == null) ? 0 : schema.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		SchemaConfiguration other = (SchemaConfiguration) obj;
		if (schema == null) {
			if (other.schema != null)
				return false;
		} else if (!schema.equals(other.schema))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
}