package com.github.aureliano.evtbridge.core.listener;

import com.github.aureliano.evtbridge.core.event.AfterReadingEvent;
import com.github.aureliano.evtbridge.core.event.BeforeReadingEvent;
import com.github.aureliano.evtbridge.core.event.StepParseEvent;

public interface DataReadingListener {

	public abstract void beforeDataReading(BeforeReadingEvent event);
	
	public abstract void stepLineParse(StepParseEvent event);
	
	public abstract void afterDataReading(AfterReadingEvent event);
}