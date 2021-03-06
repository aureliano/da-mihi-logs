package com.github.aureliano.evtbridge.core.event;

import com.github.aureliano.evtbridge.core.config.EventCollectorConfiguration;

public class BeforeCollectorsEvent {

	private final String collectorId;
	private final EventCollectorConfiguration configuration;
	
	public BeforeCollectorsEvent(String collectorId, EventCollectorConfiguration configuration) {
		this.collectorId = collectorId;
		this.configuration = configuration;
	}
	
	public String getCollectorId() {
		return collectorId;
	}
	
	public EventCollectorConfiguration getConfiguration() {
		return configuration;
	}
}