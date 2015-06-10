package com.github.aureliano.damihilogs.schedule;

import java.util.concurrent.TimeUnit;

import com.github.aureliano.damihilogs.exception.DaMihiLogsException;

public class ExecutePeriodicallySchedule extends EventCollectionSchedule {

	private Long delay;
	private Long period;
	private TimeUnit timeUnit;
	
	public ExecutePeriodicallySchedule() {
		super();
	}
	
	@Override
	public void prepareSchedulingForExecution(Runnable runnable) {
		this.validation();
		super.scheduledExecutorService.scheduleAtFixedRate(runnable, this.delay, this.period, this.timeUnit);
	}

	protected void validation() {
		if ((this.delay == null) || (this.delay < 0)) {
			throw new DaMihiLogsException("Invalid delay for scheduling. Got " + this.delay + " but expected >= 0");
		} else if ((this.period == null) || (this.period < 0)) {
			throw new DaMihiLogsException("Invalid period for scheduling. Got " + this.period + " but expected >= 0");
		} else if (this.timeUnit == null) {
			throw new DaMihiLogsException("Time unit not provided for scheduling.");
		}
	}

	public Long getDelay() {
		return delay;
	}

	public ExecutePeriodicallySchedule withDelay(Long delay) {
		this.delay = delay;
		return this;
	}

	public Long getPeriod() {
		return period;
	}

	public ExecutePeriodicallySchedule withPeriod(Long period) {
		this.period = period;
		return this;
	}

	public TimeUnit getTimeUnit() {
		return timeUnit;
	}

	public ExecutePeriodicallySchedule withTimeUnit(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
		return this;
	}

	@Override
	public String id() {
		return SchedulerTypes.EXECUTE_PERIODICALLY.name();
	}
}