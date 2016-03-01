package com.github.aureliano.evtbridge.core.doc;

import com.github.aureliano.evtbridge.core.SchemaTypes;

public interface ISchemaBuilder<T> {

	public abstract T build(SchemaTypes schemaType);
}