package com.github.aureliano.evtbridge.core.schedule;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public abstract class StandardScheduler {

	protected ScheduledExecutorService scheduledExecutorService;
	
	protected StandardScheduler() {
		this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
	}
	
	public abstract void prepareSchedulingForExecution(Runnable runnable);
	
	public abstract String id();
}