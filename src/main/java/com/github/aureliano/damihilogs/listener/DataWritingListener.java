package com.github.aureliano.defero.listener;

import com.github.aureliano.defero.event.AfterWritingEvent;
import com.github.aureliano.defero.event.BeforeWritingEvent;

public interface DataWritingListener {

	public abstract void beforeDataWriting(BeforeWritingEvent event);
	
	public abstract void afterDataWriting(AfterWritingEvent event);
}