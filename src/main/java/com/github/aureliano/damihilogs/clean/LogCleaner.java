package com.github.aureliano.damihilogs.clean;

import java.io.File;

import com.github.aureliano.damihilogs.helper.LoggerHelper;

public class LogCleaner implements ICleaner {

	private FileCleaner dataCleaner;
	private FileCleaner echoCleaner;
	
	public LogCleaner() {
		this.dataCleaner = new FileCleaner(new File(LoggerHelper.LOG_DATA_DIR_PATH));
		this.echoCleaner = new FileCleaner(new File(LoggerHelper.LOG_DIR_PATH));
	}
	
	public LogCleaner removeLogDataFilesAfterSeconds(Integer seconds) {
		this.dataCleaner.removeFilesAfterSeconds(seconds);
		return this;
	}
	
	public LogCleaner removeLogDataFilesAfterMinutes(Integer minutes) {
		this.dataCleaner.removeFilesAfterMinutes(minutes);
		return this;
	}
	
	public LogCleaner removeLogDataFilesAfterHours(Integer hours) {
		this.dataCleaner.removeFilesAfterHours(hours);
		return this;
	}
	
	public LogCleaner removeLogDataFilesAfterDays(Integer days) {
		this.dataCleaner.removeFilesAfterDays(days);
		return this;
	}
	
	public LogCleaner removeLogEchoFilesAfterSeconds(Integer seconds) {
		this.echoCleaner.removeFilesAfterSeconds(seconds);
		return this;
	}
	
	public LogCleaner removeLogEchoFilesAfterMinutes(Integer minutes) {
		this.echoCleaner.removeFilesAfterMinutes(minutes);
		return this;
	}
	
	public LogCleaner removeLogEchoFilesAfterHours(Integer hours) {
		this.echoCleaner.removeFilesAfterHours(hours);
		return this;
	}
	
	public LogCleaner removeLogEchoFilesAfterDays(Integer days) {
		this.echoCleaner.removeFilesAfterDays(days);
		return this;
	}

	@Override
	public void clean() {
		this.dataCleaner.clean();
		this.echoCleaner.clean();
	}
}