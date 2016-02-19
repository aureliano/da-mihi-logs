package com.github.aureliano.evtbridge.converter;

import java.util.List;
import java.util.Map;

import com.github.aureliano.evtbridge.common.exception.EventBridgeException;

public final class ConversionApplyer {

	private ConversionApplyer() {
		super();
	}
	
	public static List<?> apply(ConverterType type, Map<String, Object> data) {
		throw new EventBridgeException("Method not implemented.");
	}
}