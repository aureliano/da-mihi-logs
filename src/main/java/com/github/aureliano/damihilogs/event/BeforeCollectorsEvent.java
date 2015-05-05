package com.github.aureliano.damihilogs.event;

import com.github.aureliano.damihilogs.config.EventCollectorConfiguration;

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