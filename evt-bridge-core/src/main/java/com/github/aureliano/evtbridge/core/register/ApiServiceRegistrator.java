package com.github.aureliano.evtbridge.core.register;

import java.util.HashMap;
import java.util.Map;

import com.github.aureliano.evtbridge.common.exception.EventBridgeException;
import com.github.aureliano.evtbridge.common.helper.ReflectionHelper;
import com.github.aureliano.evtbridge.common.helper.StringHelper;
import com.github.aureliano.evtbridge.core.agent.IAgent;
import com.github.aureliano.evtbridge.core.config.IConfiguration;
import com.github.aureliano.evtbridge.core.config.IConfigurationConverter;

public final class ApiServiceRegistrator {

	private static ApiServiceRegistrator instance;
	
	private Map<String, ServiceRegistration> registrations;
	
	private ApiServiceRegistrator() {
		this.registrations = new HashMap<String, ServiceRegistration>();
	}
	
	public void registrate(Class<? extends IConfiguration> configuration, Class<? extends IAgent> executor) {
		this.registrate(configuration, executor, null);
	}
	
	public void registrate(Class<? extends IConfiguration> configuration, Class<? extends IAgent> executor, Class<? extends IConfigurationConverter<?>> converter) {
		this.registrate(this.createService(configuration, executor, converter));
	}
	
	public void registrate(ServiceRegistration registration) {
		if (StringHelper.isEmpty(registration.getId())) {
			if (registration.getConfiguration() != null) {
				IConfiguration configuration = (IConfiguration) ReflectionHelper.newInstance(registration.getConfiguration());
				registration.withId(configuration.id());
			} else {
				throw new EventBridgeException("Service registration expected to get a valid id but got an empty value.");
			}
		}
		
		this.registrations.put(registration.getId(), registration);
	}
	
	public IAgent createAgent(IConfiguration configuration) {
		ServiceRegistration registration = this.registrations.get(configuration.id());
		
		if ((registration == null) || (registration.getAgent() == null)) {
			throw new EventBridgeException("There isn't an executor (reader/writer) registered with ID " + configuration.id());
		}
		
		IAgent executor = (IAgent) ReflectionHelper.newInstance(registration.getAgent());
		return executor.withConfiguration(configuration);
	}
	
	public IConfigurationConverter<?> createConverter(String id) {
		ServiceRegistration registration = this.registrations.get(id);
		if (registration != null) {
			return (IConfigurationConverter<?>) ReflectionHelper.newInstance(registration.getConverter());
		}
		
		registration = this.registrations.get(id.toUpperCase());
		if (registration != null) {
			return (IConfigurationConverter<?>) ReflectionHelper.newInstance(registration.getConverter());
		}
		
		registration = this.registrations.get(id.toLowerCase());
		if (registration != null) {
			return (IConfigurationConverter<?>) ReflectionHelper.newInstance(registration.getConverter());
		}
		
		return null;
	}
	
	public Class<? extends IConfiguration> getConfiguration(String id) {
		ServiceRegistration registration = this.registrations.get(id.toUpperCase());
		if (registration == null) {
			return null;
		}
		
		return registration.getConfiguration();
	}
	
	public static ApiServiceRegistrator instance() {
		if (instance == null) {
			instance = new ApiServiceRegistrator();
		}
		
		return instance;
	}
	
	private ServiceRegistration createService(Class<? extends IConfiguration> configuration,
			Class<? extends IAgent> executor, Class<? extends IConfigurationConverter<?>> converter) {
		
		return new ServiceRegistration()
			.withConfiguration(configuration)
			.withAgent(executor)
			.withConverter(converter);
	}
}