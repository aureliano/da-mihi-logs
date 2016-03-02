package com.github.aureliano.evtbridge.core.doc;

import com.github.aureliano.evtbridge.core.SchemaTypes;
import com.github.aureliano.evtbridge.core.schedule.SchedulerTypes;

public interface ISchemaBuilder<T> {

	public abstract T build(SchemaTypes schemaType);
	
	public abstract T build(SchedulerTypes schedulerType);
}