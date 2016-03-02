package com.github.aureliano.evtbridge.core.schedule;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.github.aureliano.evtbridge.annotation.doc.SchemaConfiguration;
import com.github.aureliano.evtbridge.annotation.doc.SchemaProperty;
import com.github.aureliano.evtbridge.common.exception.EventBridgeException;

@SchemaConfiguration(
	schema = "http://json-schema.org/draft-04/schema#",
	title = "Schedule a task to a exute at specified date time.",
	type = "object"
)
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

	@SchemaProperty(
		property = "startupTime",
		types = "string",
		description = "A string describing a date time following the pattern yyyy-MM-dd HH:mm:ss",
		required = true
	)
	public Date getStartupTime() {
		return startupTime;
	}

	@Override
	public String id() {
		return SchedulerTypes.EXECUTE_ONCE_AT_SPECIFIC_TIME.name();
	}
}