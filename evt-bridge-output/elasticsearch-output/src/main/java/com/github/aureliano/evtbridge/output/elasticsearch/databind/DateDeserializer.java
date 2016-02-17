package com.github.aureliano.evtbridge.output.elasticsearch.databind;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.github.aureliano.evtbridge.output.elasticsearch.annotation.IndexableProperty;

/**
 * Custom Data Deserializer use Joda to parse date
 */
public class DateDeserializer extends StdScalarDeserializer<Object> implements ContextualDeserializer {

	private static final long serialVersionUID = -8389539662267200123L;
	
	private String formatString;
	
	public DateDeserializer() {
		super(Object.class);
	}
	
	@Override
	public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
		if (property != null) {
			Annotated annotated = property.getMember();
			if (annotated instanceof AnnotatedField || annotated instanceof AnnotatedMethod) {
				IndexableProperty indexableProperty = annotated.getAnnotation(IndexableProperty.class);
				if (indexableProperty != null && !indexableProperty.format().isEmpty()) {
					formatString = indexableProperty.format();
				}
			}
		}

		return this;
	}
	
	@Override
	public Object deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		if (formatString != null && !formatString.isEmpty() && jp.getCurrentToken() == JsonToken.VALUE_STRING) {
			String str = jp.getText().trim();
			if (str.length() == 0) {
				return null;
			}
			
			//return Joda.forPattern(formatString).parser().parseDateTime(str).toDate();
			throw new UnsupportedOperationException("Date/Time custom deserialization not implemented, only default.");
		}

		return super._parseDate(jp, ctxt);
	}
}