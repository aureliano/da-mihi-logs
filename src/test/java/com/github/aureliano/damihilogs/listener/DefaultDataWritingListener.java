package com.github.aureliano.damihilogs.listener;

import com.github.aureliano.damihilogs.event.AfterWritingEvent;
import com.github.aureliano.damihilogs.event.BeforeWritingEvent;

public class DefaultDataWritingListener implements DataWritingListener {

	@Override
	public void beforeDataWriting(BeforeWritingEvent event) { }

	@Override
	public void afterDataWriting(AfterWritingEvent event) { }
}