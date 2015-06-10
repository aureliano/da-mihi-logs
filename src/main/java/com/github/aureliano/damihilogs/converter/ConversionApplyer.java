package com.github.aureliano.damihilogs.converter;

import java.util.List;
import java.util.Map;

import com.github.aureliano.damihilogs.converter.clean.CleanerConverter;
import com.github.aureliano.damihilogs.converter.schedule.SchedulerConverter;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.helper.DataHelper;

public final class ConversionApplyer {

	private ConversionApplyer() {
		super();
	}
	
	public static List<?> apply(ConverterType type, Map<String, Object> data) {
		switch (type) {
			case CLEANER : return new CleanerConverter().convert(data);
			case SCHEDULER : return DataHelper.encapsulateIntoList(new SchedulerConverter().convert(data));
			default : throw new DaMihiLogsException("Unsupported converter type.");
		}
	}
}