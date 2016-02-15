package com.github.aureliano.evtbridge.common.helper;

import java.util.List;

public final class StringHelper {

	public static final String EMPTY = "";
	private static final String NUMBER_REGEX = "[+-]?\\d+\\.?\\d*";
	
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
	
	public static String join(Object[] objects) {
		return join(objects, "");
	}
	
	public static String join(Object[] objects, String separator) {
		StringBuilder builder = new StringBuilder();
		int count = 0;
		for (Object object : objects) {
			if (count > 0) {
				builder.append(separator);
			}
			
			builder.append(object);
			count++;
		}
		
		return builder.toString();
	}
	
	public static String join(List<Object> objects, String separator) {
		return join(objects.toArray(), separator);
	}
	
	public static boolean isNumeric(String value) {
		return (value == null) ? false : value.matches(NUMBER_REGEX);
	}
	
	public static String maskPassword(String pass) {
		return (StringHelper.isEmpty(pass)) ? "*****" : pass.replaceAll(".", "*");
	}
}