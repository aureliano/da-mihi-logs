package com.github.aureliano.almamater.core.profile;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class JvmProfile implements IProfile {

	private long time;
	private long timeDiff;
	private int availableProcessors;
	private double freeMemory;
	private double maxMemory;
	private double totalMemory;
	private double usedMemory;
	
	public JvmProfile() {
		super();
	}
	
	@Override
	public void start() {
		this.time = System.currentTimeMillis();
		
		Runtime runtime = Runtime.getRuntime();
		this.availableProcessors = runtime.availableProcessors();
		this.freeMemory = runtime.freeMemory();
		this.maxMemory = runtime.maxMemory();
		this.totalMemory = runtime.totalMemory();
		this.usedMemory = (runtime.totalMemory() - runtime.freeMemory());
	}
	
	@Override
	public IProfile stop() {
		JvmProfile profile = new JvmProfile();
		
		profile.time = System.currentTimeMillis();
		profile.availableProcessors = this.availableProcessors;
		profile.freeMemory = Runtime.getRuntime().freeMemory();
		profile.maxMemory = this.maxMemory;
		profile.totalMemory = this.totalMemory;
		profile.usedMemory = this.totalMemory - profile.freeMemory;
		
		return profile;
	}
	
	@Override
	public Properties parse() {
		Properties properties = new Properties();
		
		properties.put("profile.time.elapsed", formatTime(this.timeDiff));
		properties.put("profile.time.init", String.valueOf(this.time));
		properties.put("profile.time.init.date", ProfileConstants.DATE_FORMAT.format(new Date(this.time)));
		properties.put("profile.time.end.date", ProfileConstants.DATE_FORMAT.format(new Date()));
		properties.put("profile.processor.available", String.valueOf(this.availableProcessors));
		properties.put("profile.jvm.memory.free", ProfileConstants.DECIMAL_FORMAT.format(this.freeMemory / ProfileConstants.MEM_FACTOR) + " MiB");
		properties.put("profile.jvm.memory.max", ProfileConstants.DECIMAL_FORMAT.format(this.maxMemory / ProfileConstants.MEM_FACTOR) + " MiB");
		properties.put("profile.jvm.memomry.total", ProfileConstants.DECIMAL_FORMAT.format(this.totalMemory / ProfileConstants.MEM_FACTOR) + " MiB");
		properties.put("profile.jvm.memomry.used", ProfileConstants.DECIMAL_FORMAT.format(this.usedMemory / ProfileConstants.MEM_FACTOR) + " MiB");
		
		return properties;
	}
	
	@Override
	public IProfile intersection(IProfile profile) {
		JvmProfile p = (JvmProfile) profile;
		JvmProfile intersection = new JvmProfile();
		
		intersection.time = this.time;
		intersection.timeDiff = p.time - this.time;
		intersection.availableProcessors = this.availableProcessors;
		intersection.freeMemory = p.freeMemory;
		intersection.maxMemory = this.maxMemory;
		intersection.totalMemory = this.totalMemory;
		intersection.usedMemory = p.usedMemory;
		
		return intersection;
	}
	
	private static String formatTime(long millis) {
		int sencondInMillis = 1000;
		
		if (millis < sencondInMillis) {
			return millis + " milliseconds";
		}
		
		return String.format("%d min, %d sec - (%s millis)", 
			TimeUnit.MILLISECONDS.toMinutes(millis),
			TimeUnit.MILLISECONDS.toSeconds(millis) - 
			TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)),
			millis
		);
	}

	public long getTime() {
		return time;
	}

	public long getTimeDiff() {
		return timeDiff;
	}

	public int getAvailableProcessors() {
		return availableProcessors;
	}

	public double getFreeMemory() {
		return freeMemory;
	}

	public double getMaxMemory() {
		return maxMemory;
	}

	public double getTotalMemory() {
		return totalMemory;
	}

	public double getUsedMemory() {
		return usedMemory;
	}
}