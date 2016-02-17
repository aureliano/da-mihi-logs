package com.github.aureliano.evtbridge.output.elasticsearch.databind;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import com.github.aureliano.evtbridge.output.elasticsearch.annotation.IndexableProperty;

/**
 * Custom Data Serialize use Joda to parse date
 */
public class DateSerializer extends StdScalarSerializer<Date> implements ContextualSerializer {

	private String formatString;
	
	public DateSerializer() {
		super(Date.class);
	}
	
	@Override
	public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
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
	public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
		if (value == null) {
			jgen.writeNull();
			return;
		}
		
		if (formatString != null && !formatString.isEmpty()) {
			//jgen.writeString(Joda.forPattern(formatString).printer().print(value));
			throw new UnsupportedOperationException("Date/Time custom serialization not implemented, only default.");
		} else {
			provider.defaultSerializeDateValue(value, jgen);
		}
	}
}