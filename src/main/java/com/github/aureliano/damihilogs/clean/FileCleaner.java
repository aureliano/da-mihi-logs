package com.github.aureliano.damihilogs.clean;

import java.io.File;
import java.util.concurrent.TimeUnit;

import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.helper.FileHelper;
import com.github.aureliano.damihilogs.helper.TimeHelper;

public class FileCleaner implements ICleaner {

	private Long seed;
	private String fileNameRegex;
	private File directory;
	
	public FileCleaner(File dir) {
		this.directory = dir;
	}

	@Override
	public void clean() {
		if (this.fileNameRegex == null) {
			this.fileNameRegex = "";
		}
		
		FileHelper.deleteAllFiles(this.directory, (System.currentTimeMillis() - this.seed), this.fileNameRegex);
	}
	
	@Override
	public void validate() {
		if (this.seed == null) {
			throw new DaMihiLogsException("Time seed for clean task not provided.");
		} else if (this.directory == null) {
			throw new DaMihiLogsException("Directory for clean task not provided.");
		} else if (!this.directory.isDirectory()) {
			throw new DaMihiLogsException(
				"Abstract path for clean task does not represent a directory. Dir was expected but got '" + this.directory.getPath() + "'.");
		}
	}

	public FileCleaner removeFilesAfterSeconds(Integer seconds) {
		return this.setTimeSeed(TimeUnit.SECONDS, seconds);
	}

	public FileCleaner removeFilesAfterMinutes(Integer minutes) {
		return this.setTimeSeed(TimeUnit.MINUTES, minutes);
	}

	public FileCleaner removeFilesAfterHours(Integer hours) {
		return this.setTimeSeed(TimeUnit.HOURS, hours);
	}

	public FileCleaner removeFilesAfterDays(Integer days) {
		return this.setTimeSeed(TimeUnit.DAYS, days);
	}
	
	public FileCleaner withFileNameRegex(String regex) {
		this.fileNameRegex = regex;
		return this;
	}
	
	protected Long getSeed() {
		return this.seed;
	}
	
	private FileCleaner setTimeSeed(TimeUnit timeUnit, Number seed) {
		if ((seed == null) || (seed.longValue() < 1)) {
			seed = 1L;
		}
		
		this.seed = TimeHelper.convertToMilliseconds(timeUnit, seed.longValue());
		return this;
	}

	@Override
	public String id() {
		return CleanerTypes.FILE.name();
	}
}