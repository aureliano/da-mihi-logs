package com.github.aureliano.evtbridge.core.listener;

import com.github.aureliano.evtbridge.core.event.AfterInputEvent;
import com.github.aureliano.evtbridge.core.event.BeforeInputEvent;

public interface ExecutionListener {

	public abstract void beforeExecution(BeforeInputEvent evt);
	
	public abstract void afterExecution(AfterInputEvent evt);
}