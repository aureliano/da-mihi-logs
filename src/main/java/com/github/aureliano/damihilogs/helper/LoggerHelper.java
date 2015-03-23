package com.github.aureliano.damihilogs.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.github.aureliano.damihilogs.exception.DaMihiLogsException;

public final class LoggerHelper {
	
	private static final Logger logger = Logger.getLogger(LoggerHelper.class);
	private static final String LOG_DIR_PATH = "log";

	private LoggerHelper() {
		super();
	}
	
	public static Properties getLastExecutionLog(final String collectorId) {
		File file = getLastExecutionFileLog(collectorId);
		if (file == null) {
			logger.warn("There is not last execution log for collector with id " + collectorId);
			return null;
		}
		
		Properties properties = new Properties();
		
		try {
			properties.load(new FileInputStream(file));
		} catch (IOException ex) {
			throw new DaMihiLogsException(ex);
		}
		
		return properties;
	}
	
	private static File getLastExecutionFileLog(final String collectorId) {
		String[] files = new File(LOG_DIR_PATH).list(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.matches(collectorId + "_\\d{4}-\\d{2}-\\d{2}\\.log");
			}
		});
		
		Arrays.sort(files);
		
		return (files.length > 0) ? new File(LOG_DIR_PATH + File.separator + files[0]) : null;
	}
	
	public static File saveExecutionLog(String collectorId, Properties p, boolean ordered) {
		Properties properties;
		if (ordered) {
			properties = new Properties() {
			    
				private static final long serialVersionUID = 1L;

				@Override
				public synchronized Enumeration<Object> keys() {
					return Collections.enumeration(new TreeSet<Object>(super.keySet()));
				}
			};
		} else {
			properties = new Properties();
		}
		
		for (Object key : p.keySet()) {
			properties.put(key, p.get(key));
		}
		
		return saveExecutionLog(collectorId, properties);
	}
	
	public static File saveExecutionLog(String collectorId, Properties properties) {
		File dir = new File(LOG_DIR_PATH);
		try {
			if (!dir.exists()) {
				if (!dir.mkdir()) {
					throw new DaMihiLogsException("Could not create log directory.");
				}
			}
			
			if ((collectorId == null) || (collectorId.equals(""))) {
				collectorId = "execution";
			}
			
			String fileName = collectorId + "_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".log";
			File output = new File(dir.getPath() + File.separator + fileName);
			logger.debug(properties.toString());
			properties.store(new FileOutputStream(output), "Last execution information.");
			
			return output;
		} catch (IOException ex) {
			logger.error("Could not save execution log.", ex);
			throw new DaMihiLogsException(ex);
		}
	}
}