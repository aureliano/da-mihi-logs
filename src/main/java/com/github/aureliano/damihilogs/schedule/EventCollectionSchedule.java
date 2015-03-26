package com.github.aureliano.damihilogs.schedule;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public abstract class EventCollectionSchedule {

	protected ScheduledExecutorService scheduledExecutorService;
	
	protected EventCollectionSchedule() {
		this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
	}
	
	public abstract void prepareSchedulingForExecution(Runnable runnable);
}