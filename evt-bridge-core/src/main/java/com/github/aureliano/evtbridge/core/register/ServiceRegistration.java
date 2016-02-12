package com.github.aureliano.evtbridge.core.register;

import com.github.aureliano.evtbridge.core.agent.IAgent;
import com.github.aureliano.evtbridge.core.config.IConfiguration;

public class ServiceRegistration {

	private String id;
	private Class<? extends IConfiguration> configuration;
	private Class<? extends IAgent> agent;
	
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

	public Class<? extends IAgent> getAgent() {
		return agent;
	}

	public ServiceRegistration withAgent(Class<? extends IAgent> executor) {
		this.agent = executor;
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