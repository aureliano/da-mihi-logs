package com.github.aureliano.damihilogs.report.model;

import java.util.ArrayList;
import java.util.List;

public class CollectorModel {

	private String id;
	private Boolean status;
	private String timeElapsed;
	private String freeMemory;
	private String processorAvailable;
	private String maxMemory;
	private String totalMemory;
	private List<ExceptionModel> exceptions;
	private String outputLog;
	private String textStatus;
	
	public CollectorModel() {
		this.exceptions = new ArrayList<ExceptionModel>();
	}

	public String getFreeMemory() {
		return freeMemory;
	}

	public CollectorModel withFreeMemory(String freeMemory) {
		this.freeMemory = freeMemory;
		return this;
	}

	public String getProcessorAvailable() {
		return processorAvailable;
	}

	public CollectorModel withProcessorAvailable(String processorAvailable) {
		this.processorAvailable = processorAvailable;
		return this;
	}

	public String getMaxMemory() {
		return maxMemory;
	}

	public CollectorModel withMaxMemory(String maxMemory) {
		this.maxMemory = maxMemory;
		return this;
	}

	public String getTotalMemory() {
		return totalMemory;
	}

	public CollectorModel withTotalMemory(String totalMemory) {
		this.totalMemory = totalMemory;
		return this;
	}

	public String getId() {
		return id;
	}
	
	public String getExecutionId() {
		if (this.id == null) {
			return null;
		}
		
		return this.id.replaceAll("[-:\\s.]+", "_");
	}

	public CollectorModel withId(String id) {
		this.id = id;
		return this;
	}

	public Boolean getStatus() {
		return status;
	}

	public CollectorModel withStatus(Boolean status) {
		this.status = status;
		return this;
	}

	public String getTimeElapsed() {
		return timeElapsed;
	}

	public CollectorModel withTimeElapsed(String timeElapsed) {
		if (timeElapsed == null) {
			timeElapsed = "0 sec";
		}
		
		this.timeElapsed = timeElapsed;
		return this;
	}

	public List<ExceptionModel> getExceptions() {
		return exceptions;
	}

	public CollectorModel withExceptions(List<ExceptionModel> exceptions) {
		this.exceptions = exceptions;
		return this;
	}
	
	public CollectorModel addException(ExceptionModel ex) {
		this.exceptions.add(ex);
		return this;
	}
	
	public String getOutputLog() {
		return outputLog;
	}
	
	public CollectorModel withOutputLog(String outputLog) {
		this.outputLog = outputLog;
		return this;
	}

	public String getTextStatus() {
		return textStatus;
	}

	public CollectorModel withTextStatus(String textStatus) {
		this.textStatus = textStatus;
		return this;
	}
}