package com.github.aureliano.evtbridge.output.elasticsearch.databind;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * Deserializer used to convert JSON object to json string directly
 * used for JSON type {@link com.github.aureliano.damihilogs.annotation.elasticsearch.kzwang.osem.annotations.TypeEnum#JSON}
 */
public class RawJsonDeSerializer extends JsonDeserializer<String> {

	@Override
	public String deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
		return jp.getCodec().readTree(jp).toString();
	}
}