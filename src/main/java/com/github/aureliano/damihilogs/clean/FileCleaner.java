package com.github.aureliano.damihilogs.clean;

import java.io.File;

import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.helper.FileHelper;

public class FileCleaner implements ICleaner {

	private Long seed;
	private String fileNameRegex;
	private File directory;
	
	public FileCleaner(File dir) {
		this.directory = dir;
	}

	@Override
	public void clean() {
		this.validation();
		if (this.fileNameRegex == null) {
			this.fileNameRegex = "";
		}
		
		FileHelper.deleteAllFiles(this.directory, (System.currentTimeMillis() - this.seed), this.fileNameRegex);
	}
	
	protected void validation() {
		if (this.seed == null) {
			throw new DaMihiLogsException("Time seed for clean task not provided.");
		} else if (this.directory == null) {
			throw new DaMihiLogsException("Directory for clean task not provided.");
		} else if (!this.directory.isDirectory()) {
			throw new DaMihiLogsException(
				"Abstract path for clean task does not represent a directory. Dir was expected but got '" + this.directory.getPath() + "'.");
		}
	}

	public ICleaner removeFilesAfterSeconds(Integer seconds) {
		if ((seconds == null) || (seconds < 1)) {
			seconds = 1;
		}
		
		this.seed = seconds.longValue() * 1000;
		return this;
	}

	public ICleaner removeFilesAfterMinutes(Integer minutes) {
		if ((minutes == null) || (minutes < 1)) {
			minutes = 1;
		}
		
		return this.removeFilesAfterSeconds(minutes * 60);
	}

	public ICleaner removeFilesAfterHours(Integer hours) {
		if ((hours == null) || (hours < 1)) {
			hours = 1;
		}
		
		return this.removeFilesAfterMinutes(hours * 60);
	}

	public ICleaner removeFilesAfterDays(Integer days) {
		if ((days == null) || (days < 1)) {
			days = 1;
		}
		
		return this.removeFilesAfterHours(days * 24);
	}
	
	public ICleaner withFileNameRegex(String regex) {
		this.fileNameRegex = regex;
		return this;
	}
	
	protected Long getSeed() {
		return this.seed;
	}
}