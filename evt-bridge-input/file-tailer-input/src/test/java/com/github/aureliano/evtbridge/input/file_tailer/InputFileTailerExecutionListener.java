package com.github.aureliano.evtbridge.input.file_tailer;

import com.github.aureliano.evtbridge.core.event.AfterInputEvent;
import com.github.aureliano.evtbridge.core.event.BeforeInputEvent;
import com.github.aureliano.evtbridge.core.listener.ExecutionListener;

public class InputFileTailerExecutionListener implements ExecutionListener {

	@Override
	public void beforeExecution(BeforeInputEvent evt) { }

	@Override
	public void afterExecution(AfterInputEvent evt) { }
}