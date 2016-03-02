package com.github.aureliano.evtbridge.core.doc;

import com.github.aureliano.evtbridge.core.SchemaTypes;
import com.github.aureliano.evtbridge.core.config.InputConfigTypes;
import com.github.aureliano.evtbridge.core.schedule.SchedulerTypes;

public interface ISchemaBuilder<T> {

	public abstract T build(SchemaTypes schemaType);
	
	public abstract T build(SchedulerTypes schedulerType);
	
	public abstract T build(InputConfigTypes inputType);
}