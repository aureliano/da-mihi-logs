package com.github.aureliano.evtbridge.input.url;

import java.util.Map;

import com.github.aureliano.evtbridge.common.helper.UrlHelper;

public final class UrlInputHelper {

	private UrlInputHelper() {
		super();
	}
	
	public static String buildUrl(UrlInputConfig config) {
		StringBuilder builder = new StringBuilder();
		
		builder
			.append(config.getConnectionSchema().name().toLowerCase())
			.append("://");
		
		String host = config.getHost();
		if (host.endsWith("/")) {
			host = host.replaceFirst("/$", "");
		}
		
		builder.append(host);
		
		if (config.getPort() >= 0) {
			builder.append(":").append(config.getPort());
		}
		
		String path = config.getPath();
		if (path != null) {
			if (path.startsWith("/")) {
				path = path.replaceFirst("^/", "");
			}
			builder.append("/").append(path);
		}
		
		Map<String, String> parameters = config.getParameters();
		if (!parameters.isEmpty()) {
			builder.append("?");
		}
		
		builder.append(UrlHelper.formatParameters(parameters));
		
		return builder.toString();
	}
}