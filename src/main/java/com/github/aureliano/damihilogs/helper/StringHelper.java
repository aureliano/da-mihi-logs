package com.github.aureliano.damihilogs.helper;

public final class StringHelper {

	public static final String EMPTY = "";
	
	private StringHelper() {
		super();
	}
	
	public static boolean isEmpty(String text) {
		return ((text == null) || (text.equals(EMPTY)));
	}
	
	public static String toString(Object object) {
		return ((object == null) ? "null" : object.toString());
	}
	
	public static String parse(Object object) {
		return ((object == null) ? null : object.toString());
	}
	
	public static String capitalize(String text) {
		return Character.toUpperCase(text.charAt(0)) + text.substring(1).toLowerCase();
	}
}