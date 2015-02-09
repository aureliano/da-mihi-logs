package com.github.aureliano.defero.profile;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Properties;

public class Profiler {

	private static final double MEM_FACTOR = 1024.0 * 1024.0; // MB
	private static final DecimalFormat DECIMAL_FORMAT;
	
	static {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ENGLISH);
		symbols.setDecimalSeparator('.');
		symbols.setGroupingSeparator(',');
		
		DECIMAL_FORMAT = new DecimalFormat("##0.00", symbols);
	}
	
	private long time;
	private int availableProcessors;
	private double freeMemory;
	private double maxMemory;
	private double totalMemory;
	
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
	}
	
	public Profiler stop() {
		Profiler profiler = new Profiler();
		
		profiler.time = System.currentTimeMillis();
		profiler.availableProcessors = this.availableProcessors;
		profiler.freeMemory = Runtime.getRuntime().freeMemory();
		profiler.maxMemory = this.maxMemory;
		profiler.totalMemory = this.totalMemory;
		
		return profiler;
	}
	
	public static Profiler diff(Profiler p1, Profiler p2) {
		Profiler profiler = new Profiler();
		
		profiler.time = p2.time - p1.time;
		profiler.availableProcessors = p1.availableProcessors;
		profiler.freeMemory = p1.freeMemory - p2.freeMemory;
		profiler.maxMemory = p1.maxMemory;
		profiler.totalMemory = p1.totalMemory;
		
		return profiler;
	}
	
	public static Properties parse(Profiler profiler) {
		Properties properties = new Properties();
		
		properties.put("profile.time.elapsed", formatTime(profiler.time));
		properties.put("profile.processor.available", String.valueOf(profiler.availableProcessors));
		properties.put("profile.memory.free", DECIMAL_FORMAT.format(profiler.freeMemory / MEM_FACTOR) + " MB");
		properties.put("profile.memory.max", DECIMAL_FORMAT.format(profiler.maxMemory / MEM_FACTOR) + " MB");
		properties.put("profile.memomry.total", DECIMAL_FORMAT.format(profiler.totalMemory / MEM_FACTOR) + " MB");
		
		return properties;
	}
	
	private static String formatTime(long t) {
		if (t < 1000) {
			return t + " ms";
		} else if (t > 1000 && t < 60000) {
			return t / 1000 + " sec";
		} else {
			return t / 1000 / 60 + " min";
		}
	}

	public long getTime() {
		return time;
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
}