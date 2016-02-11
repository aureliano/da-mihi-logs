package com.github.aureliano.evtbridge.core.listener;

import com.github.aureliano.evtbridge.core.event.AfterCollectorsEvent;
import com.github.aureliano.evtbridge.core.event.BeforeCollectorsEvent;

public interface EventsCollectorListener {

	public abstract void beforeExecution(BeforeCollectorsEvent evt);
	
	public abstract void afterExecution(AfterCollectorsEvent evt);
}