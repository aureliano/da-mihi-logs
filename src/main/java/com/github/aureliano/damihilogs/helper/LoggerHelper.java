package com.github.aureliano.damihilogs.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.github.aureliano.damihilogs.exception.DaMihiLogsException;

public final class LoggerHelper {
	
	private static final Logger logger = Logger.getLogger(LoggerHelper.class);
	private static final String LOG_FILE_NAME = "events-collector.log";
	
	public static final String LOG_DIR_PATH = "log";
	public static final String DEFAULT_COLLECTOR_ID_NAME = "execution";
	public static final String LOG_DATA_DIR_PATH = LOG_DIR_PATH + File.separator + "data";

	private LoggerHelper() {
		super();
	}
	
	public static PrintStream createLoggingProxy(final PrintStream printStream) {
		return createLoggingProxy(printStream, null);
	}
	
	public static PrintStream createLoggingProxy(final PrintStream printStream, final Logger _logger) {
		return new PrintStream(printStream) {
			public void print(final String text) {
				printStream.print(text);
				this._printLog(text, _logger);
			}
			
			public void println(final String text) {
				printStream.println(text);
				this._printLog(text, _logger);
			}
			
			private void _printLog(final String text, final Logger _logger) {
				if (_logger == null) {
					Logger.getRootLogger().debug(text);
				} else {
					_logger.debug(text);
				}
			}
		};
	}
	
	public static File getCurrentLogFile() {
		return new File(LOG_DIR_PATH + File.separator + LOG_FILE_NAME);
	}
	
	public static Properties getLastExecutionLog(final String collectorId, boolean todayReference) {
		File file = getLastExecutionFileLog(collectorId, todayReference);
		
		Properties properties = new Properties();
		if (file == null) {
			logger.warn("There is not last execution log for collector with id " + collectorId);
			return properties;
		}
		
		try {
			properties.load(new FileInputStream(file));
		} catch (IOException ex) {
			throw new DaMihiLogsException(ex);
		}
		
		return properties;
	}
	
	private static File getLastExecutionFileLog(final String collectorId, boolean todayReference) {
		String[] files = new File(LOG_DATA_DIR_PATH).list(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.matches(collectorId + "_\\d{4}-\\d{2}-\\d{2}\\.log");
			}
		});
		
		if ((files == null) || (files.length == 0)) {
			return null;
		}
		Arrays.sort(files);
		
		String fileName = files[files.length - 1];
		if (todayReference) {
			return (FileHelper.getLastExecutionLogDataFileName(collectorId).equals(fileName)) ? new File(FileHelper.buildPath(LOG_DATA_DIR_PATH, fileName)) : null;
		} else {
			return (fileName != null) ? new File(FileHelper.buildPath(LOG_DATA_DIR_PATH, fileName)) : null;
		}
	}
	
	public static File saveExecutionLogData(String collectorId, Properties p, boolean ordered) {
		if (ordered) {
			Properties properties = DataHelper.sortProperties(p);
			return saveExecutionLogData(collectorId, properties);
		}
		
		return saveExecutionLogData(collectorId, p);
	}
	
	public static File saveExecutionLogData(String collectorId, Properties properties) {
		File dir = new File(LOG_DATA_DIR_PATH);
		try {
			if (!dir.exists()) {
				if (!dir.mkdirs()) {
					throw new DaMihiLogsException("Could not create log directory.");
				}
			}
			
			if ((collectorId == null) || (collectorId.equals(""))) {
				collectorId = DEFAULT_COLLECTOR_ID_NAME;
			}
			
			String fileName = FileHelper.getLastExecutionLogDataFileName(collectorId);
			File output = new File(dir.getPath() + File.separator + fileName);
			properties.store(new FileOutputStream(output), "Last execution information.");
			logger.debug("Execution Log partial: " + DataHelper.propertiesToJson(properties));
			
			return output;
		} catch (IOException ex) {
			logger.error("Could not save execution log.", ex);
			throw new DaMihiLogsException(ex);
		}
	}
}