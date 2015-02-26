package com.github.aureliano.damihilogs.parser;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.aureliano.damihilogs.exception.DeferoException;

public class JsonEventParser implements IParser<Map<String, ?>> {

	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	public JsonEventParser() {
		super();
	}

	@Override
	public Map<String, ?> parse(String text) {
		try {
			return objectMapper.readValue(text, Map.class);
		} catch (Exception ex) {
			throw new DeferoException(ex);
		}
	}
}