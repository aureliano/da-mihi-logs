package com.github.aureliano.evtbridge.core.schedule;

import java.util.concurrent.TimeUnit;

import com.github.aureliano.evtbridge.annotation.doc.SchemaConfiguration;
import com.github.aureliano.evtbridge.annotation.doc.SchemaProperty;
import com.github.aureliano.evtbridge.common.exception.EventBridgeException;

@SchemaConfiguration(
	schema = "http://json-schema.org/draft-04/schema#",
	title = "Schedule a task to execute periodically",
	type = "object"
)
public class ExecutePeriodicallyScheduler extends StandardScheduler {

	private Long delay;
	private Long period;
	private TimeUnit timeUnit;
	
	public ExecutePeriodicallyScheduler() {
		super();
	}
	
	@Override
	public void schedule(Runnable runnable) {
		this.validation();
		super.scheduledExecutorService.scheduleAtFixedRate(runnable, this.delay, this.period, this.timeUnit);
	}

	protected void validation() {
		if ((this.delay == null) || (this.delay < 0)) {
			throw new EventBridgeException("Invalid delay for scheduling. Got " + this.delay + " but expected >= 0");
		} else if ((this.period == null) || (this.period < 0)) {
			throw new EventBridgeException("Invalid period for scheduling. Got " + this.period + " but expected >= 0");
		} else if (this.timeUnit == null) {
			throw new EventBridgeException("Time unit not provided for scheduling.");
		}
	}

	@SchemaProperty(
		property = "delay",
		types = "integer",
		description = "The time to delay at first execution.",
		required = true
	)
	public Long getDelay() {
		return delay;
	}

	public ExecutePeriodicallyScheduler withDelay(Long delay) {
		this.delay = delay;
		return this;
	}

	@SchemaProperty(
		property = "period",
		types = "integer",
		description = "The period between successive executions.",
		required = true
	)
	public Long getPeriod() {
		return period;
	}

	public ExecutePeriodicallyScheduler withPeriod(Long period) {
		this.period = period;
		return this;
	}

	@SchemaProperty(
		property = "timeUnit",
		types = { "days", "hours", "minutes", "seconds", "milliseconds", "microseconds", "nanoseconds" },
		description = "The time unit of the delay and period parameters.",
		required = true
	)
	public TimeUnit getTimeUnit() {
		return timeUnit;
	}

	public ExecutePeriodicallyScheduler withTimeUnit(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
		return this;
	}

	@Override
	public String id() {
		return SchedulerTypes.EXECUTE_PERIODICALLY.name();
	}
}