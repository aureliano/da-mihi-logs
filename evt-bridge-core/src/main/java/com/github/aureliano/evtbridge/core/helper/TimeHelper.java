package com.github.aureliano.evtbridge.core.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.github.aureliano.evtbridge.common.exception.EventBridgeException;

public final class TimeHelper {
	
	private static final SimpleDateFormat DATE_TIME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
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
	
	public static boolean isValidTimeUnit(String timeUnit) {
		for (TimeUnit type : TimeUnit.values()) {
			if (type.name().equalsIgnoreCase(timeUnit)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static Date parseToDateTime(String dateTime) {
		try {
			return DATE_TIME_FORMATTER.parse(dateTime);
		} catch (ParseException ex) {
			throw new EventBridgeException(ex);
		}
	}
	
	public static String formatDateTime(Date dateTime) {
		return DATE_TIME_FORMATTER.format(dateTime);
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