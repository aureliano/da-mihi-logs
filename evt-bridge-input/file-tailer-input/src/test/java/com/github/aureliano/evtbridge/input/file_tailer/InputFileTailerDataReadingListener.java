package com.github.aureliano.evtbridge.input.file_tailer;

import com.github.aureliano.evtbridge.core.event.AfterReadingEvent;
import com.github.aureliano.evtbridge.core.event.BeforeReadingEvent;
import com.github.aureliano.evtbridge.core.event.StepParseEvent;
import com.github.aureliano.evtbridge.core.listener.DataReadingListener;

public class InputFileTailerDataReadingListener implements DataReadingListener {

	@Override
	public void beforeDataReading(BeforeReadingEvent event) { }

	@Override
	public void stepLineParse(StepParseEvent event) { }

	@Override
	public void afterDataReading(AfterReadingEvent event) { }
}