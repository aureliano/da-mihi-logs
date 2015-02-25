package com.github.aureliano.defero.listener;

import com.github.aureliano.defero.event.AfterReadingEvent;
import com.github.aureliano.defero.event.BeforeReadingEvent;
import com.github.aureliano.defero.event.StepParseEvent;

public interface DataReadingListener {

	public abstract void beforeDataReading(BeforeReadingEvent event);
	
	public abstract void stepLineParse(StepParseEvent event);
	
	public abstract void afterDataReading(AfterReadingEvent event);
}