package com.github.aureliano.damihilogs.listener;

import com.github.aureliano.damihilogs.event.AfterCollectorsEvent;
import com.github.aureliano.damihilogs.event.BeforeCollectorsEvent;

public interface EventsCollectorListener {

	public abstract void beforeExecution(BeforeCollectorsEvent evt);
	
	public abstract void afterExecution(AfterCollectorsEvent evt);
}