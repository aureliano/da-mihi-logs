package com.github.aureliano.evtbridge.core.doc;

import java.util.Map;

import com.github.aureliano.evtbridge.core.SchemaTypes;

public interface ISchemaBuilder {

	public abstract Map<String, ?> build(SchemaTypes schemaType);
}