package com.github.aureliano.damihilogs.listener;

import com.github.aureliano.damihilogs.event.AfterCollectorsEvent;
import com.github.aureliano.damihilogs.event.BeforeCollectorsEvent;

public class DefaultEventsCollectorListener implements EventsCollectorListener {

	@Override
	public void beforeExecution(BeforeCollectorsEvent evt) { }

	@Override
	public void afterExecution(AfterCollectorsEvent evt) { }
}