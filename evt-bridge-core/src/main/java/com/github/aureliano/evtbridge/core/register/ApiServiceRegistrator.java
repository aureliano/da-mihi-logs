package com.github.aureliano.evtbridge.core.register;

import java.util.HashMap;
import java.util.Map;

import com.github.aureliano.evtbridge.common.helper.StringHelper;
import com.github.aureliano.evtbridge.core.agent.IAgent;
import com.github.aureliano.evtbridge.core.config.IConfiguration;
import com.github.aureliano.evtbridge.core.exception.EventBridgeException;
import com.github.aureliano.evtbridge.core.helper.ReflectionHelper;

public final class ApiServiceRegistrator {

	private static ApiServiceRegistrator instance;
	
	private Map<String, ServiceRegistration> registrations;
	
	private ApiServiceRegistrator() {
		this.registrations = new HashMap<String, ServiceRegistration>();
	}
	
	public void registrate(Class<? extends IConfiguration> configuration, Class<? extends IAgent> executor) {
		this.registrate(this.createService(configuration, executor));
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
	
	public IAgent createExecutor(IConfiguration configuration) {
		ServiceRegistration registration = this.registrations.get(configuration.id());
		
		if ((registration == null) || (registration.getExecutor() == null)) {
			throw new EventBridgeException("There isn't an executor (reader/writer) registered with ID " + configuration.id());
		}
		
		IAgent executor = (IAgent) ReflectionHelper.newInstance(registration.getExecutor());
		return executor.withConfiguration(configuration);
	}
	
	public static ApiServiceRegistrator instance() {
		if (instance == null) {
			instance = new ApiServiceRegistrator();
		}
		
		return instance;
	}
	
	private ServiceRegistration createService(Class<? extends IConfiguration> configuration,
			Class<? extends IAgent> executor) {
		
		return new ServiceRegistration()
			.withConfiguration(configuration)
			.withExecutor(executor);
	}
}