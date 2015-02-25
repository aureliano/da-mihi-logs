package com.github.aureliano.damihilogs.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.github.aureliano.damihilogs.exception.DeferoException;

public final class LoggerHelper {
	
	private static final Logger logger = Logger.getLogger(LoggerHelper.class);

	private LoggerHelper() {
		super();
	}
	
	public static File saveExecutionLog(Properties p, boolean ordered) {
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
		
		return saveExecutionLog(properties);
	}
	
	public static File saveExecutionLog(Properties properties) {
		File dir = new File("log");
		try {
			if (!dir.exists()) {
				if (!dir.mkdir()) {
					throw new DeferoException("Could not create log directory.");
				}
			}
			
			String fileName = "execution_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".log";
			File output = new File(dir.getPath() + File.separator + fileName);
			logger.debug(properties.toString());
			properties.store(new FileOutputStream(output), "Last execution information.");
			
			return output;
		} catch (IOException ex) {
			logger.error("Could not save execution log.", ex);
			throw new DeferoException(ex);
		}
	}
}