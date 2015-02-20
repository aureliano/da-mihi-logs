package com.github.aureliano.defero.matcher;

public interface IMatcher {

	public abstract boolean partialMatch(String text);
	
	public abstract boolean matches(String text);
	
	public abstract String endMatch(String text);
	
	public abstract boolean isMultiLine();
	
	public static final int DEFAULT_MAX_MATCH_ATTEMPTS = 10000;
}