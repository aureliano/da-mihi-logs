package com.github.aureliano.damihilogs.clean;

public interface ICleaner {
	
	public abstract void clean();
	
	public abstract void validate();
	
	public abstract String id();
}