package com.github.aureliano.evtbridge.core.schedule;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.github.aureliano.evtbridge.common.exception.EventBridgeException;

public class ExecuteOnceAtSpecificTimeScheduler extends StandardScheduler {

	private Date startupTime;
	
	public ExecuteOnceAtSpecificTimeScheduler() {
		super();
	}
	
	@Override
	public void schedule(Runnable runnable) {
		this.validation();
		
		long delay = this.startupTime.getTime() - System.currentTimeMillis();
		super.scheduledExecutorService.schedule(runnable, delay, TimeUnit.MILLISECONDS);
		super.scheduledExecutorService.shutdown();
	}
	
	protected void validation() {
		if (this.startupTime == null) {
			throw new EventBridgeException("You must provide a startup time for scheduling.");
		} else if (this.startupTime.before(new Date())) {
			throw new EventBridgeException("Startup time for scheduling must be greater than now.");
		}
	}
	
	public ExecuteOnceAtSpecificTimeScheduler withStartupTime(Date startupTime) {
		this.startupTime = startupTime;
		return this;
	}
	
	public Date getStartupTime() {
		return startupTime;
	}

	@Override
	public String id() {
		return SchedulerTypes.EXECUTE_ONCE_AT_SPECIFIC_TIME.name();
	}
}