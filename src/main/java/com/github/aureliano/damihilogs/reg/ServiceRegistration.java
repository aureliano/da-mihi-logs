package com.github.aureliano.damihilogs.reg;

import com.github.aureliano.damihilogs.config.IConfiguration;
import com.github.aureliano.damihilogs.converter.IConfigurationConverter;
import com.github.aureliano.damihilogs.executor.IExecutor;

public class ServiceRegistration {

	private String id;
	private Class<? extends IConfiguration> configuration;
	private Class<? extends IExecutor> executor;
	private Class<? extends IConfigurationConverter<?>> converter;
	
	public ServiceRegistration() {
		super();
	}

	public String getId() {
		return id;
	}

	public ServiceRegistration withId(String id) {
		this.id = id;
		return this;
	}

	public Class<? extends IConfiguration> getConfiguration() {
		return configuration;
	}

	public ServiceRegistration withConfiguration(Class<? extends IConfiguration> configuration) {
		this.configuration = configuration;
		return this;
	}

	public Class<? extends IExecutor> getExecutor() {
		return executor;
	}

	public ServiceRegistration withExecutor(Class<? extends IExecutor> executor) {
		this.executor = executor;
		return this;
	}

	public Class<? extends IConfigurationConverter<?>> getConverter() {
		return converter;
	}

	public ServiceRegistration withConverter(Class<? extends IConfigurationConverter<?>> converter) {
		this.converter = converter;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServiceRegistration other = (ServiceRegistration) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}