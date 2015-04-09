package com.github.aureliano.damihilogs.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.github.aureliano.damihilogs.exception.DaMihiLogsException;

public final class FileHelper {
	
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	private FileHelper() {
		super();
	}
	
	public static String createLoggerFileName(String dir, String collectorId, Long seed) {
		if (seed == null) {
			seed = System.currentTimeMillis();
		}
		
		return String.format("%s%s%s_%s.log", dir, File.separator, collectorId, seed);
	}
	
	public static String getLastExecutionLogDataFileName(final String collectorId) {
		return collectorId + "_" + DATE_FORMAT.format(new Date()) + ".log";
	}
	
	public static String readFile(String path) {
		return readFile(new File(path));
	}
	
	public static String readFile(File file) {
		try {
			return readFile(new FileInputStream(file));
		} catch (IOException ex) {
			throw new DaMihiLogsException(ex);
		}
	}
	
	public static String readFile(InputStream stream) {
		StringBuilder builder = new StringBuilder();
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			String line = null;
			
			while ((line = reader.readLine()) != null) {
				if (builder.length() > 0) {
					builder.append("\n");
				}
				builder.append(line);
			}
			
			reader.close();
		} catch (IOException ex) {
			throw new DaMihiLogsException(ex);
		}
		
		return builder.toString();
	}
	
	public static String readResource(String resourceName) {
		InputStream stream = ClassLoader.getSystemResourceAsStream(resourceName);
		return FileHelper.readFile(stream);
	}
}