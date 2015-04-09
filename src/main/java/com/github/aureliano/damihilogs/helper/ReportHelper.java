package com.github.aureliano.damihilogs.helper;

import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.report.model.CollectorModel;
import com.github.aureliano.damihilogs.report.model.ExceptionModel;

public final class ReportHelper {

	private static final String EXECUTION_LOG_PARTIAL_PREFIX_REGEX = "DEBUG\\s\\[[^\\]]+\\]\\s\\([^\\)]+\\)\\s-\\sExecution\\sLog\\spartial:";
	private static final String EXECUTION_LOG_PARTIAL_REGEX = EXECUTION_LOG_PARTIAL_PREFIX_REGEX + "([\\W\\w\\d](?!TRACE|DEBUG|INFO|WARN|ERROR|FATAL))+";
	private static final Pattern EXECUTION_LOG_PARTIAL = Pattern.compile("(" + EXECUTION_LOG_PARTIAL_REGEX + ")");
	private static final Pattern FILE_SEED = Pattern.compile("(\\d+)\\.log$");
	
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
				Properties properties = LoggerHelper.getLastExecutionLog(n);
				Boolean statusOk = getStatus(properties.keySet());
								
				models.add(new CollectorModel()
					.withId(n)
					.withStatus(statusOk)
					.withTimeElapsed(properties.getProperty("profile.time.elapsed")));
			}
		}
		
		
		return models;
	}
	
	public static List<CollectorModel> getCollectorExecutions(final String collectorId) {
		String[] names = new File(LoggerHelper.LOG_ECHO_DIR_PATH).list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.startsWith(collectorId);
			}
		});
		
		if (names == null) {
			names = new String[0];
		}
		
		Arrays.sort(names, Collections.reverseOrder());
		List<CollectorModel> models = new ArrayList<CollectorModel>();
		
		for (String name : names) {
			String content = FileHelper.readFile(LoggerHelper.LOG_ECHO_DIR_PATH + File.separator + name);
			Matcher matcher = EXECUTION_LOG_PARTIAL.matcher(content);
			
			String buildId = convertExecutionFileNameToDate(name);
			CollectorModel model = new CollectorModel()
				.withId(buildId)
				.withOutputLog(content.replaceAll(EXECUTION_LOG_PARTIAL_REGEX, ""));
			
			models.add(model);
			model.withStatus(false);
			
			while (matcher.find()) {
				String hash = matcher.group().replaceAll(EXECUTION_LOG_PARTIAL_PREFIX_REGEX, "").trim();
				Map<String, String> map = DataHelper.jsonStringToObject(hash, Map.class);				
				Boolean statusOk = getStatus(map.keySet());
				
				model
					.withTimeElapsed(map.get("profile.time.elapsed"))
					.withFreeMemory(map.get("profile.memory.free"))
					.withMaxMemory(map.get("profile.memory.max"))
					.withProcessorAvailable(map.get("profile.processor.available"))
					.withTotalMemory(map.get("profile.memomry.total"))
					.withStatus(statusOk);
				
				ExceptionModel ex = findException(map);
				if (ex != null) {
					model.addException(findException(map));
				}
			}
		}
		
		return models;
	}
	
	public static String loadHtmlTemplate(String resource) {
		return FileHelper.readResource(resource);
	}
	
	private static ExceptionModel findException(Map<String, String> map) {
		ExceptionModel exception = new ExceptionModel();
		
		for (String key : map.keySet()) {
			if (key.endsWith(".exception")) {
				String seed = key.replaceFirst("input.config.", "").replaceFirst(".exception", "");
				exception.withMessage(map.get(key)).withSeed(seed);
			} else if (key.endsWith(".stackTrace")) {
				exception.withStackTrace(map.get(key).replaceFirst("^\\[", "").replaceFirst("\\]$", "").replaceAll(",\\s", "\n"));
			}
		}
		
		if (exception.getSeed() == null) {
			return null;
		}
		
		return exception;
	}
	
	private static String convertExecutionFileNameToDate(String fileName) {
		Matcher matcher = FILE_SEED.matcher(fileName);
		if (!matcher.find()) {
			throw new DaMihiLogsException("Invalid execution log file name. Expected to match " + FILE_SEED.pattern() + " onto " + fileName);
		}
		
		return DATE_FORMATTER.format(new Date(Long.parseLong(matcher.group(1))));
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