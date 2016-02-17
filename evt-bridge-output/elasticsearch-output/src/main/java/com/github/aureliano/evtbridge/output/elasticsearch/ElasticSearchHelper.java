package com.github.aureliano.evtbridge.output.elasticsearch;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.aureliano.evtbridge.common.helper.StringHelper;
import com.github.aureliano.evtbridge.core.data.ObjectMapperSingleton;
import com.github.aureliano.evtbridge.core.helper.FileHelper;
import com.github.aureliano.evtbridge.core.http.HttpActionData;
import com.github.aureliano.evtbridge.core.http.HttpActionMetadata;

public final class ElasticSearchHelper {
	
	private static final int SECOND_IN_MILLIS = 1 * 1000;
	private static final int MINUTE_IN_MILLIS = 60 * SECOND_IN_MILLIS;
	private static final int HOUR_IN_MILLIS = 60 * MINUTE_IN_MILLIS;
	
	private static final Logger logger = Logger.getLogger(ElasticSearchHelper.class);
	private static final Pattern PATTERN_ID = Pattern.compile("\"_id\"\\s*:\\s*\"?[^,]+");
	
	private ElasticSearchHelper() {
		super();
	}
	
	public static HttpActionMetadata doRequest(HttpActionData action) {
		HttpActionMetadata metadata = new HttpActionMetadata();
		
		try {
			long start = System.currentTimeMillis();
			HttpURLConnection conn = (HttpURLConnection) new URL(action.getUrl()).openConnection();
			conn.setRequestMethod(action.getMethod());
			
			for (String key : action.getRequestProperties().keySet()) {
				conn.setRequestProperty(key, action.getRequestProperties().get(key));
			}
			
			if (action.getData() != null) {
				conn.setDoOutput(true);
				OutputStream osw = conn.getOutputStream();
				osw.write(action.getData());
				osw.flush();
				osw.close();
				
				metadata.withRequestData(new String(action.getData()));
			}
			
			conn.connect();
			
			metadata
				.withRequestUrl(action.getUrl())
				.withRequestMethod(action.getMethod())
				.withRequestTime(timeElapsed(start))
				.withResponseContent(FileHelper.readFile(conn.getInputStream()))
				.withResponseStatus(conn.getResponseCode());
			
			conn.disconnect();
			return metadata;
		} catch (IOException ex) {
			logger.warn(new StringBuilder("Response code: ")
				.append(metadata.getResponseStatus())
				.append(" Method: ").append(metadata.getRequestMethod())
				.append(" URL: ").append(metadata.getRequestUrl())
				.append(" Body: ").append(metadata.getRequestData()).toString());
			throw new ElasticSearchOutputException(ex);
		}
	}
	
	public static boolean checkStatusOk(HttpActionData action) {
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(action.getUrl()).openConnection();
			conn.setRequestMethod(action.getMethod());
			
			for (String key : action.getRequestProperties().keySet()) {
				conn.setRequestProperty(key, action.getRequestProperties().get(key));
			}
			
			conn.connect();
			boolean status = (conn.getResponseCode() / 100 == 2);
			conn.disconnect();
			
			return status;
		} catch (IOException ex) {
			throw new ElasticSearchOutputException(ex);
		}
	}

	@SuppressWarnings("unchecked")
	public static String formatMappingType(String indexName, String content) {
		if (StringHelper.isEmpty(content) || content.equals("{}")) {
			return null;
		}
		
		ObjectMapper mapper = ObjectMapperSingleton.instance().getObjectMapper();
		
		try {
			Map<String, ?> map = mapper.readValue(content, Map.class);
			Map<String, ?> m = (Map<String, ?>) map.get(indexName);
			
			return mapper.writeValueAsString(m.get("mappings"));
		} catch (IOException ex) {
			throw new ElasticSearchOutputException(ex);
		}
	}
	
	public static String getIdFromHash(String hash) {
		Matcher m = PATTERN_ID.matcher(hash);
		if (m.find()) {
			return m.group().replaceFirst("\"_id\"\\s*:\\s*", "").replaceAll("\"", "");
		} else {
			return String.valueOf(System.currentTimeMillis()) + String.valueOf(hash.length());
		}
	}
	
	private static String timeElapsed(long start) {
		long diff = System.currentTimeMillis() - start;
		
		if (diff == 0) {
			return "0s";
		} else if (diff < SECOND_IN_MILLIS) {
			return String.valueOf((double) diff / SECOND_IN_MILLIS) + "s";
		} else if ((diff >= SECOND_IN_MILLIS) && (diff < MINUTE_IN_MILLIS)) {
			diff = diff % MINUTE_IN_MILLIS;
			long elapsedSeconds = diff / SECOND_IN_MILLIS;
			
			return elapsedSeconds + "s";
		} else if ((diff >= MINUTE_IN_MILLIS) && (diff < HOUR_IN_MILLIS)) {
			long elapsedMinutes = diff / MINUTE_IN_MILLIS;
			diff = diff % MINUTE_IN_MILLIS;
			long elapsedSeconds = diff / SECOND_IN_MILLIS;
			
			return elapsedMinutes + "m, " + elapsedSeconds + "s";
		} else {
			long elapsedHours = diff / HOUR_IN_MILLIS;
			diff = diff % HOUR_IN_MILLIS;
			
			long elapsedMinutes = diff / MINUTE_IN_MILLIS;
			diff = diff % MINUTE_IN_MILLIS;
			long elapsedSeconds = diff / SECOND_IN_MILLIS;
			
			return elapsedHours + "h, " + elapsedMinutes + "m, " + elapsedSeconds + "s";
		}
	}
}