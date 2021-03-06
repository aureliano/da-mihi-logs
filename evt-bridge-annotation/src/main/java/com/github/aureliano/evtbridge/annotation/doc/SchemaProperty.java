package com.github.aureliano.evtbridge.annotation.doc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SchemaProperty {

	public abstract String property();
	public abstract String[] types();
	public abstract String description();
	public abstract boolean required() default false;
	public abstract String defaultValue() default "";
	public abstract Class<?> reference() default Class.class;
}