package com.github.aureliano.damihilogs.formatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.aureliano.damihilogs.data.ObjectMapperSingleton;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;

public class JsonFormatter implements IOutputFormatter {
	
	public JsonFormatter() {
		super();
	}

	@Override
	public String format(Object data) {
		if (data == null) {
			return "";
		}
		
		try {
			return ObjectMapperSingleton.instance().getObjectMapper().writeValueAsString(data);
		} catch (JsonProcessingException ex) {
			throw new DaMihiLogsException(ex);
		}
	}
}