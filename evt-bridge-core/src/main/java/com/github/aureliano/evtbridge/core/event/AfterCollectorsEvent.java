package com.github.aureliano.evtbridge.core.event;

import java.util.Properties;

import com.github.aureliano.evtbridge.core.config.EventCollectorConfiguration;

public class AfterCollectorsEvent {

	private final String collectorId;
	private final EventCollectorConfiguration configuration;
	private final Properties executionLog;
	
	public AfterCollectorsEvent(String collectorId, EventCollectorConfiguration configuration, Properties executionLog) {
		this.collectorId = collectorId;
		this.configuration = configuration;
		this.executionLog = executionLog;
	}
	
	public String getCollectorId() {
		return collectorId;
	}
	
	public EventCollectorConfiguration getConfiguration() {
		return configuration;
	}
	
	public Properties getExecutionLog() {
		return executionLog;
	}
}