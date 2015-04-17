package com.github.aureliano.damihilogs.http;

import java.util.HashMap;
import java.util.Map;

public class HttpActionData {

	private String url;
	private String method;
	private byte[] data;
	private Map<String, String> requestProperties;
	
	public HttpActionData() {
		this.requestProperties = new HashMap<String, String>();
	}

	public String getUrl() {
		return url;
	}

	public HttpActionData withUrl(String url) {
		this.url = url;
		return this;
	}

	public String getMethod() {
		return method;
	}

	public HttpActionData withMethod(String method) {
		this.method = method;
		return this;
	}

	public byte[] getData() {
		return data;
	}

	public HttpActionData withData(byte[] data) {
		this.data = data;
		return this;
	}

	public Map<String, String> getRequestProperties() {
		return requestProperties;
	}

	public HttpActionData withRequestProperties(Map<String, String> requestProperties) {
		this.requestProperties = requestProperties;
		return this;
	}
	
	public HttpActionData putRequestProperty(String key, String value) {
		this.requestProperties.put(key, value);
		return this;
	}
}