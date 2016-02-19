package com.github.aureliano.evtbridge.core.config;

import java.util.Map;

public interface IConfigurationConverter<T> {

	public abstract T convert(Map<String, Object> data);
	
	public abstract String id();
}