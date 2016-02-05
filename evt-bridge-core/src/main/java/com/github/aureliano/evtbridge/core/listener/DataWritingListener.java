package com.github.aureliano.evtbridge.core.listener;

import com.github.aureliano.evtbridge.core.event.AfterWritingEvent;
import com.github.aureliano.evtbridge.core.event.BeforeWritingEvent;

public interface DataWritingListener {

	public abstract void beforeDataWriting(BeforeWritingEvent event);
	
	public abstract void afterDataWriting(AfterWritingEvent event);
}