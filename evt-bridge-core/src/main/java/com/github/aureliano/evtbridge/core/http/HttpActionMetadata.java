package com.github.aureliano.evtbridge.core.http;

public class HttpActionMetadata {

	private String requestMethod;
	private String requestTime;
	private String requestUrl;
	private String requestData;
	private Integer responseStatus;
	private String responseContent;
	
	public HttpActionMetadata() {
		super();
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public HttpActionMetadata withRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
		return this;
	}

	public String getRequestTime() {
		return requestTime;
	}

	public HttpActionMetadata withRequestTime(String requestTime) {
		this.requestTime = requestTime;
		return this;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public HttpActionMetadata withRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
		return this;
	}

	public Integer getResponseStatus() {
		return responseStatus;
	}

	public HttpActionMetadata withResponseStatus(Integer responseStatus) {
		this.responseStatus = responseStatus;
		return this;
	}

	public String getResponseContent() {
		return responseContent;
	}

	public HttpActionMetadata withResponseContent(String responseContent) {
		this.responseContent = responseContent;
		return this;
	}

	public String getRequestData() {
		return requestData;
	}

	public HttpActionMetadata withRequestData(String requestData) {
		this.requestData = requestData;
		return this;
	}
}