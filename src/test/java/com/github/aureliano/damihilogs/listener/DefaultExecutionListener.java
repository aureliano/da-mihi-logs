package com.github.aureliano.damihilogs.listener;

import com.github.aureliano.damihilogs.event.AfterInputEvent;
import com.github.aureliano.damihilogs.event.BeforeInputEvent;

public class DefaultExecutionListener implements ExecutionListener {

	@Override
	public void beforeExecution(BeforeInputEvent evt) { }

	@Override
	public void afterExecution(AfterInputEvent evt) { }
}