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
}