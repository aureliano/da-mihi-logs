package com.github.aureliano.damihilogs.schedule;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.github.aureliano.damihilogs.exception.DaMihiLogsException;

public class ExecuteOnceAtSpecificTimeSchedule extends EventCollectionSchedule {

	private Date startupTime;
	
	public ExecuteOnceAtSpecificTimeSchedule() {
		super();
	}
	
	@Override
	public void prepareSchedulingForExecution(Runnable runnable) {
		this.validation();
		
		long delay = this.startupTime.getTime() - System.currentTimeMillis();
		super.scheduledExecutorService.schedule(runnable, delay, TimeUnit.MILLISECONDS);
		super.scheduledExecutorService.shutdown();
	}
	
	protected void validation() {
		if (this.startupTime == null) {
			throw new DaMihiLogsException("You must provide a startup time for scheduling.");
		} else if (this.startupTime.before(new Date())) {
			throw new DaMihiLogsException("Startup time for scheduling must be greater than now.");
		}
	}
	
	public ExecuteOnceAtSpecificTimeSchedule withStartupTime(Date startupTime) {
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