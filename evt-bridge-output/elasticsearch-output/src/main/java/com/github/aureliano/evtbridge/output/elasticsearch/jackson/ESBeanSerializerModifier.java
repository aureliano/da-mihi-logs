package com.github.aureliano.evtbridge.output.elasticsearch.jackson;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;

/**
 * Override to use custom serializer for null value
 */
public class ESBeanSerializerModifier extends BeanSerializerModifier {
	
	@Override
	public JsonSerializer<?> modifySerializer(SerializationConfig config, BeanDescription beanDesc, JsonSerializer<?> serializer) {
		if (serializer instanceof BeanSerializerBase) {
			return new ESBeanSerializer((BeanSerializerBase) serializer);
		}
		
		return serializer;
	}
}