package com.github.aureliano.damihilogs.report;

import java.io.File;

public interface ILoggerReporter {
	
	public abstract void buildReport();

	public abstract ILoggerReporter withOutputDir(File outputDir);
	
	public abstract File getOutputDir();
	
	public abstract ILoggerReporter withLanguage(ReportLanguage language);
	
	public abstract ReportLanguage getLanguage();
	
	public abstract ILoggerReporter withDeleteOldFiles(Boolean deleteOldFiles);
	
	public abstract Boolean isDeleteOldFiles();
}