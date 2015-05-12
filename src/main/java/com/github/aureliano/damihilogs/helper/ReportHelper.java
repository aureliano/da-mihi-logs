package com.github.aureliano.damihilogs.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.report.model.CollectorModel;
import com.github.aureliano.damihilogs.report.model.ExceptionModel;

public final class ReportHelper {

	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
	
	private ReportHelper() {
		super();
	}
	
	public static List<CollectorModel> getCollectorExecutions() {
		String[] names = new File(LoggerHelper.LOG_DATA_DIR_PATH).list();
		Set<String> uniqueNames = new HashSet<String>();
		List<CollectorModel> models = new ArrayList<CollectorModel>();
		
		for (String name : names) {
			String n = name.replaceAll("_\\d{4}-\\d{2}-\\d{2}.log$", "");
			if (uniqueNames.add(n)) {
				Properties properties = LoggerHelper.getLastExecutionLog(n, false);
				Boolean statusOk = getStatus(properties.keySet());
								
				models.add(new CollectorModel()
					.withId(n)
					.withStatus(statusOk)
					.withTimeElapsed(properties.getProperty("profile.time.elapsed")));
			}
		}
		
		return models;
	}
	
	public static CollectorModel getLastCollectorExecution(final String collectorId) {
		Properties properties = LoggerHelper.getLastExecutionLog(collectorId, false);
		if (properties.isEmpty()) {
			return null;
		}
		String buildId = DATE_FORMATTER.format(new Date(Long.parseLong(properties.getProperty("profile.time.init"))));
		
		String content = FileHelper.readFile(LoggerHelper.getCurrentLogFile());
		int index = content.indexOf(buildId.replaceFirst("\\.\\d+", ""));
		if (index >= 0) {
			content = content.substring(content.indexOf(buildId.replaceFirst("\\.\\d+", "")));
		}
		BufferedReader reader = new BufferedReader(new StringReader(content));
		
		CollectorModel model = new CollectorModel()
			.withId(buildId)
			.withStatus(false);
		
		StringBuilder log = new StringBuilder();
		String line = null;
		
		try {
			while ((line = reader.readLine()) != null) {
				log.append(line).append("\n");
			}
		} catch (IOException ex) {
			throw new DaMihiLogsException(ex);
		}
		
		content = ((log.length() > 0) ? (log.deleteCharAt(log.length() - 1).toString()) : "");
		
		model
			.withOutputLog(content)
			.withStatus(getStatus(properties.keySet()))
			.withTimeElapsed(properties.getProperty("profile.time.elapsed"))
			.withTimeInit(properties.getProperty("profile.time.init.date"))
			.withTimeEnd(properties.getProperty("profile.time.end.date"))
			.withFreeMemory(properties.getProperty("profile.jvm.memory.free"))
			.withMaxMemory(properties.getProperty("profile.jvm.memory.max"))
			.withProcessorAvailable(properties.getProperty("profile.processor.available"))
			.withTotalMemory(properties.getProperty("profile.jvm.memomry.total"))
			.withUsedMemory(properties.getProperty("profile.jvm.memomry.used"));
		
		ExceptionModel ex = findException(properties);
		if (ex != null) {
			model.addException(ex);
		}
		
		return model;
	}
	
	public static String loadHtmlTemplate(String resource) {
		return FileHelper.readResource(resource);
	}
	
	public static List<CollectorModel> getOldCollectorExecutions(File dir, final String collectorId) {
		File[] files = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.matches(collectorId + "_[\\d_]+\\.html");
			}
		});
		
		if (files == null) {
			return new ArrayList<CollectorModel>();
		}
		
		List<CollectorModel> executions = new ArrayList<CollectorModel>();
		Pattern pattern = Pattern.compile("<meta\\sname=\"([^\"]+)\"\\scontent=\"([^\"]+)\"/>");
		for (File file : files) {
			CollectorModel exec = new CollectorModel();
			String content = FileHelper.readFile(file);
			
			Matcher matcher = pattern.matcher(content);
			matcher.find();
			exec.withId(matcher.group(2));
			matcher.find();
			exec.withStatus(Boolean.parseBoolean(matcher.group(2)));
			matcher.find();
			exec.withTimeElapsed(matcher.group(2));
			
			executions.add(exec);
		}

		return executions;
	}
	
	private static ExceptionModel findException(Properties properties) {
		ExceptionModel exception = new ExceptionModel();
		
		for (Object key : properties.keySet()) {
			if (key.toString().endsWith(".exception")) {
				String seed = key.toString().replaceFirst("input.config.", "").replaceFirst(".exception", "");
				exception.withMessage(properties.get(key).toString()).withSeed(seed);
			} else if (key.toString().endsWith(".stackTrace")) {
				exception.withStackTrace(properties.get(key).toString().replaceFirst("^\\[", "").replaceFirst("\\]$", "").replaceAll(",\\s", "\n"));
			}
		}
		
		if (exception.getSeed() == null) {
			return null;
		}
		
		return exception;
	}
	
	private static boolean getStatus(Set<?> keySet) {
		Boolean statusOk = true;
		
		for (Object key : keySet) {
			if (key.toString().endsWith(".exception")) {
				statusOk = false;
				break;
			}
		}
		
		return statusOk;
	}
}