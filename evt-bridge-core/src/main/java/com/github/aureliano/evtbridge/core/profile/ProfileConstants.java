package com.github.aureliano.evtbridge.core.profile;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Locale;

public final class ProfileConstants {

	private ProfileConstants() {}
	
	public static final double MEM_FACTOR = 1024.0 * 1024.0; // MiB
	public static final DecimalFormat DECIMAL_FORMAT;
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	static {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ENGLISH);
		symbols.setDecimalSeparator('.');
		symbols.setGroupingSeparator(',');
		
		DECIMAL_FORMAT = new DecimalFormat("#0.00", symbols);
	}
}