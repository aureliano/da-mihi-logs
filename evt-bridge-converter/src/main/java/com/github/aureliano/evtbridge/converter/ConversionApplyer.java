package com.github.aureliano.evtbridge.converter;

import java.util.List;
import java.util.Map;

import com.github.aureliano.evtbridge.converter.exception.ConfigurationConverterException;
import com.github.aureliano.evtbridge.converter.schedule.SchedulerConverter;
import com.github.aureliano.evtbridge.core.helper.DataHelper;

public final class ConversionApplyer {

	private ConversionApplyer() {
		super();
	}
	
	public static List<?> apply(ConverterType type, Map<String, Object> data) {
		switch (type) {
			case INPUT : return new InputConverter().convert(data);
			case OUTPUT : return new OutputConverter().convert(data);
			case SCHEDULER : return DataHelper.encapsulateIntoList(new SchedulerConverter().convert(data));
			default : throw new ConfigurationConverterException("Unsupported converter type.");
		}
	}
}