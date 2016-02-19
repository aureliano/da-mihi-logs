package com.github.aureliano.evtbridge.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.github.aureliano.evtbridge.common.helper.ReflectionHelper;
import com.github.aureliano.evtbridge.common.helper.StringHelper;
import com.github.aureliano.evtbridge.core.config.EventCollectorConfiguration;
import com.github.aureliano.evtbridge.core.config.IConfigInput;
import com.github.aureliano.evtbridge.core.config.IConfigOutput;
import com.github.aureliano.evtbridge.core.helper.DataHelper;
import com.github.aureliano.evtbridge.core.listener.EventsCollectorListener;
import com.github.aureliano.evtbridge.core.schedule.IScheduler;

public class ConfigurationConverter implements IConfigurationConverter<EventCollectorConfiguration> {

	public ConfigurationConverter() {
		super();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public EventCollectorConfiguration convert(Map<String, Object> data) {
		EventCollectorConfiguration configuration = new EventCollectorConfiguration()
			.withCollectorId(StringHelper.parse(data.get("id")));
		
		String value = StringHelper.parse(data.get("persistExecutionLog"));
		if (!StringHelper.isEmpty(value)) {
			configuration.withPersistExecutionLog(Boolean.parseBoolean(value));
		}
		
		value = StringHelper.parse(data.get("multiThreadingEnabled"));
		if (!StringHelper.isEmpty(value)) {
			configuration.withMultiThreadingEnabled(Boolean.parseBoolean(value));
		}
		
		if (data.get("metadata") != null) {
			Properties properties = DataHelper.mapToProperties((Map<String, Object>) data.get("metadata"));
			configuration.withMetadata(properties);
		}
		
		this.propagateConversion(configuration, data);
		return configuration;
	}
	
	@Override
	public String id() {
		return ConverterType.EVENT_COLLECTOR.name();
	}
	
	private void propagateConversion(EventCollectorConfiguration configuration, Map<String, Object> data) {
		for (IConfigInput input : this.convertInputs(data)) {
			configuration.addInputConfig(input);
		}
		
		for (IConfigOutput output : this.convertOutputs(data)) {
			configuration.addOutputConfig(output);
		}
		
		configuration
			.withScheduler(this.convertScheduler(data))
			.withEventsCollectorListeners(this.convertEventsCollectorListeners(data));
	}
	
	private IScheduler convertScheduler(Map<String, Object> data) {
		List<?> result = ConversionApplyer.apply(ConverterType.SCHEDULER, data);
		if ((result != null) && (!result.isEmpty())) {
			return (IScheduler) result.get(0);
		} else {
			return null;
		}
	}
	
	private List<EventsCollectorListener> convertEventsCollectorListeners(Map<String, Object> data) {
		@SuppressWarnings("unchecked")
		List<String> list = (List<String>) data.get("eventsCollectorListeners");
		
		if (list == null) {
			return Collections.emptyList();
		}
		
		List<EventsCollectorListener> listeners = new ArrayList<EventsCollectorListener>(list.size());
		for (String className : list) {
			listeners.add((EventsCollectorListener) ReflectionHelper.newInstance(className));
		}
		
		return listeners;
	}
	
	@SuppressWarnings("unchecked")
	private List<IConfigInput> convertInputs(Map<String, Object> data) {
		return (List<IConfigInput>) ConversionApplyer.apply(ConverterType.INPUT, data);
	}
	
	@SuppressWarnings("unchecked")
	private List<IConfigOutput> convertOutputs(Map<String, Object> data) {
		return (List<IConfigOutput>) ConversionApplyer.apply(ConverterType.OUTPUT, data);
	}
}