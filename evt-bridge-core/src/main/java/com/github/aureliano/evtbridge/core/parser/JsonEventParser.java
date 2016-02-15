package com.github.aureliano.evtbridge.core.parser;

import java.util.Map;

import com.github.aureliano.evtbridge.common.exception.EventBridgeException;
import com.github.aureliano.evtbridge.core.data.ObjectMapperSingleton;

public class JsonEventParser implements IParser<Map<String, ?>> {
	
	public JsonEventParser() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, ?> parse(String text) {
		try {
			return ObjectMapperSingleton.instance().getObjectMapper().readValue(text, Map.class);
		} catch (Exception ex) {
			throw new EventBridgeException(ex);
		}
	}
}