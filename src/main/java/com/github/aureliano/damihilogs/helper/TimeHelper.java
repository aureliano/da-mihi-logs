package com.github.aureliano.damihilogs.helper;

import java.util.concurrent.TimeUnit;

public final class TimeHelper {
	
	private TimeHelper() {
		super();
	}
	
	public static long convertToMilliseconds(TimeUnit timeUnit, long seed) {
		switch (timeUnit) {
			case MILLISECONDS : return seed;
			case SECONDS : return fromSeconds(seed);
			case MINUTES : return fromMinutes(seed);
			case HOURS : return fromHours(seed);
			case DAYS : return fromDays(seed);
			default : throw new IllegalArgumentException(
				"Unsupported time unit. The smallest time unit supported is " +
					TimeUnit.MILLISECONDS + " but got " + timeUnit);
		}
	}
	
	private static long fromSeconds(long seed) {
		return seed * 1000;
	}
	
	private static long fromMinutes(long seed) {
		return 60 * fromSeconds(seed);
	}
	
	private static long fromHours(long seed) {
		return 60 * fromMinutes(seed);
	}
	
	private static long fromDays(long seed) {
		return 24 * fromHours(seed);
	}
}