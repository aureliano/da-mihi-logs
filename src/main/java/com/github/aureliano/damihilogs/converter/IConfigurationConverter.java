package com.github.aureliano.damihilogs.converter;

import java.util.Map;

public interface IConfigurationConverter<T> {

	public abstract T convert(Map<String, Object> data);
}