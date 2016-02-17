package com.github.aureliano.evtbridge.core.schedule;

public interface IScheduler {

	public abstract String id();
	
	public abstract void schedule(Runnable runnable);
}