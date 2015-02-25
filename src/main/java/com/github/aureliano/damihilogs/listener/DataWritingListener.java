package com.github.aureliano.damihilogs.listener;

import com.github.aureliano.damihilogs.event.AfterWritingEvent;
import com.github.aureliano.damihilogs.event.BeforeWritingEvent;

public interface DataWritingListener {

	public abstract void beforeDataWriting(BeforeWritingEvent event);
	
	public abstract void afterDataWriting(AfterWritingEvent event);
}