package com.github.aureliano.evtbridge.converter;

import java.util.List;
import java.util.Map;

import com.github.aureliano.evtbridge.converter.exception.ConfigurationConverterException;

public final class ConversionApplyer {

	private ConversionApplyer() {
		super();
	}
	
	public static List<?> apply(ConverterType type, Map<String, Object> data) {
		switch (type) {
			case INPUT : return new InputConverter().convert(data);
			case OUTPUT : return new OutputConverter().convert(data);
			default : throw new ConfigurationConverterException("Unsupported converter type.");
		}
	}
}