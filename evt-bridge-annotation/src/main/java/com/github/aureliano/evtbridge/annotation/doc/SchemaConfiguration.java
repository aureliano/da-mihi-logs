package com.github.aureliano.evtbridge.annotation.doc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SchemaConfiguration {

	public abstract String schema() default com.github.aureliano.evtbridge.common.doc.SchemaConfiguration.SCHEMA_DRAFT;
	public abstract String title();
	public abstract String type() default com.github.aureliano.evtbridge.common.doc.SchemaConfiguration.SCHEMA_TYPE;
}