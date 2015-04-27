package com.github.aureliano.damihilogs.profile;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Profiler {

	private static final double MEM_FACTOR = 1024.0 * 1024.0; // MiB
	private static final DecimalFormat DECIMAL_FORMAT;
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	static {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ENGLISH);
		symbols.setDecimalSeparator('.');
		symbols.setGroupingSeparator(',');
		
		DECIMAL_FORMAT = new DecimalFormat("##0.00", symbols);
	}
	
	private long time;
	private long timeDiff;
	private int availableProcessors;
	private double freeMemory;
	private double maxMemory;
	private double totalMemory;
	private double usedMemory;
	
	public Profiler() {
		super();
	}
	
	public void start() {
		this.time = System.currentTimeMillis();
		
		Runtime runtime = Runtime.getRuntime();
		this.availableProcessors = runtime.availableProcessors();
		this.freeMemory = runtime.freeMemory();
		this.maxMemory = runtime.maxMemory();
		this.totalMemory = runtime.totalMemory();
		this.usedMemory = (runtime.totalMemory() - runtime.freeMemory());
	}
	
	public Profiler stop() {
		Profiler profiler = new Profiler();
		
		profiler.time = System.currentTimeMillis();
		profiler.availableProcessors = this.availableProcessors;
		profiler.freeMemory = Runtime.getRuntime().freeMemory();
		profiler.maxMemory = this.maxMemory;
		profiler.totalMemory = this.totalMemory;
		profiler.usedMemory = this.totalMemory - profiler.freeMemory;
		
		return profiler;
	}
	
	public static Profiler diff(Profiler p1, Profiler p2) {
		Profiler profiler = new Profiler();
		
		profiler.time = p1.time;
		profiler.timeDiff = p2.time - p1.time;
		profiler.availableProcessors = p1.availableProcessors;
		profiler.freeMemory = p2.freeMemory;
		profiler.maxMemory = p1.maxMemory;
		profiler.totalMemory = p1.totalMemory;
		profiler.usedMemory = p2.usedMemory;
		
		return profiler;
	}
	
	public static Properties parse(Profiler profiler) {
		Properties properties = new Properties();
		
		properties.put("profile.time.elapsed", formatTime(profiler.timeDiff));
		properties.put("profile.time.init", String.valueOf(profiler.time));
		properties.put("profile.time.init.date", DATE_FORMAT.format(new Date(profiler.time)));
		properties.put("profile.time.end.date", DATE_FORMAT.format(new Date()));
		properties.put("profile.processor.available", String.valueOf(profiler.availableProcessors));
		properties.put("profile.jvm.memory.free", DECIMAL_FORMAT.format(profiler.freeMemory / MEM_FACTOR) + " MiB");
		properties.put("profile.jvm.memory.max", DECIMAL_FORMAT.format(profiler.maxMemory / MEM_FACTOR) + " MiB");
		properties.put("profile.jvm.memomry.total", DECIMAL_FORMAT.format(profiler.totalMemory / MEM_FACTOR) + " MiB");
		properties.put("profile.jvm.memomry.used", DECIMAL_FORMAT.format(profiler.usedMemory / MEM_FACTOR) + " MiB");
		
		return properties;
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