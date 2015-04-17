package com.github.aureliano.damihilogs.parser;

import java.util.Map;

import com.github.aureliano.damihilogs.data.ObjectMapperSingleton;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;

public class JsonEventParser implements IParser<Map<String, ?>> {
	
	public JsonEventParser() {
		super();
	}

	@Override
	public Map<String, ?> parse(String text) {
		try {
			return ObjectMapperSingleton.instance().getObjectMapper().readValue(text, Map.class);
		} catch (Exception ex) {
			throw new DaMihiLogsException(ex);
		}
	}
}