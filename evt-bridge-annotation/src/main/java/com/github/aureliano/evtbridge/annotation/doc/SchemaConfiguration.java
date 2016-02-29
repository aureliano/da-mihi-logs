package com.github.aureliano.evtbridge.annotation.doc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SchemaConfiguration {

	public abstract String schema() default "http://json-schema.org/draft-04/schema#";
	public abstract String title();
	public abstract String type() default "Events collector configuration schema.";
}