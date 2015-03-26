package com.github.aureliano.damihilogs.schedule;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.github.aureliano.damihilogs.exception.DaMihiLogsException;

public class ExecutePeriodicallyAtSpecificTimeSchedule extends EventCollectionSchedule {

	private Integer hour;
	private Integer minute;
	private Integer second;
	
	private static final Integer DEFAULT_SECOND = 0;
	
	public ExecutePeriodicallyAtSpecificTimeSchedule() {
		super();
	}
	
	@Override
	public void prepareSchedulingForExecution(Runnable runnable) {
		this.validation();
		
		if (this.second == null) {
			this.second = DEFAULT_SECOND;
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, this.hour);
		calendar.set(Calendar.MINUTE, this.minute);
		calendar.set(Calendar.SECOND, this.second);
		
		if (calendar.getTime().before(new Date())) {
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		long delay = (calendar.getTimeInMillis() - System.currentTimeMillis()) / 1000;
		long oneDay = 24 * 60 * 60;
		super.scheduledExecutorService.scheduleAtFixedRate(runnable, delay, oneDay, TimeUnit.SECONDS);
	}
	
	protected void validation() {
		if ((this.hour == null) || (this.hour < 0) || (this.hour > 23)) {
			throw new DaMihiLogsException("Invalid hour for scheduling. Got " + this.hour + " but expected >= 0 and <= 23");
		} else if ((this.minute == null) || (this.minute < 0) || (this.minute > 59)) {
			throw new DaMihiLogsException("Invalid minute for scheduling. Got " + this.minute + " but expected >= 0 and <= 59");
		} else if ((this.second == null) || (this.second < 0) || (this.second > 59)) {
			throw new DaMihiLogsException("Invalid second for scheduling. Got " + this.second + " but expected >= 0 and <= 59");
		}
	}

	public Integer getHour() {
		return hour;
	}

	public ExecutePeriodicallyAtSpecificTimeSchedule withHour(Integer hour) {
		this.hour = hour;
		return this;
	}

	public Integer getMinute() {
		return minute;
	}

	public ExecutePeriodicallyAtSpecificTimeSchedule withMinute(Integer minute) {
		this.minute = minute;
		return this;
	}

	public Integer getSecond() {
		return second;
	}

	public ExecutePeriodicallyAtSpecificTimeSchedule withSecond(Integer second) {
		this.second = second;
		return this;
	}
}