package com.github.aureliano.defero.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;

import com.github.aureliano.defero.exception.DeferoException;

public final class LoggerHelper {
	
	private static final Logger logger = Logger.getLogger(LoggerHelper.class.getName());

	private LoggerHelper() {
		super();
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
			logger.fine(properties.toString());
			properties.store(new FileOutputStream(output), "Last execution information.");
			
			return output;
		} catch (IOException ex) {
			logger.severe("Could not save execution log. " + ex.getMessage());
			throw new DeferoException(ex);
		}
	}
}