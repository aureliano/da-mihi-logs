package com.github.aureliano.damihilogs.listener;

import com.github.aureliano.damihilogs.event.AfterInputEvent;
import com.github.aureliano.damihilogs.event.BeforeInputEvent;

public interface InputExecutionListener {

	public abstract void beforeExecution(BeforeInputEvent evt);
	
	public abstract void afterExecution(AfterInputEvent evt);
}