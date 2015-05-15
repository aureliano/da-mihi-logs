package com.github.aureliano.damihilogs.listener;

import com.github.aureliano.damihilogs.event.AfterReadingEvent;
import com.github.aureliano.damihilogs.event.BeforeReadingEvent;
import com.github.aureliano.damihilogs.event.StepParseEvent;

public class DefaultDataReadingListener implements DataReadingListener {

	@Override
	public void beforeDataReading(BeforeReadingEvent event) { }

	@Override
	public void stepLineParse(StepParseEvent event) { }

	@Override
	public void afterDataReading(AfterReadingEvent event) { }
}