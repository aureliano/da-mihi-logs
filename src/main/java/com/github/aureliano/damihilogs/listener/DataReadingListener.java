package com.github.aureliano.damihilogs.listener;

import com.github.aureliano.damihilogs.event.AfterReadingEvent;
import com.github.aureliano.damihilogs.event.BeforeReadingEvent;
import com.github.aureliano.damihilogs.event.StepParseEvent;

public interface DataReadingListener {

	public abstract void beforeDataReading(BeforeReadingEvent event);
	
	public abstract void stepLineParse(StepParseEvent event);
	
	public abstract void afterDataReading(AfterReadingEvent event);
}