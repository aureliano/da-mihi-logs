package com.github.aureliano.evtbridge.app.command;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.aureliano.evtbridge.app.ErrorCode;
import com.github.aureliano.evtbridge.common.helper.StringHelper;
import com.github.aureliano.evtbridge.converter.ConfigurationSourceType;
import com.github.aureliano.evtbridge.converter.helper.ConversionHelper;
import com.github.aureliano.evtbridge.core.EventsCollector;
import com.github.aureliano.evtbridge.core.config.EventCollectorConfiguration;

public class RunCommand implements ICommand {

	private String configurationFilePath;
	
	public RunCommand() {}

	@Override
	public ErrorCode execute() {
		ErrorCode errorCode = this.validate();
		if (errorCode == null) {
			ConfigurationSourceType sourceType = this.getConfigurationSourceType();
			EventCollectorConfiguration configuration = ConversionHelper.loadConfiguration(this.configurationFilePath, sourceType);
			
			new EventsCollector()
				.withConfiguration(configuration)
				.execute();;
		}
		
		return errorCode;
	}
	
	private ErrorCode validate() {
		if (StringHelper.isEmpty(this.configurationFilePath)) {
			System.err.println("Invalid configuration file path (-c --configuration) parameter [" + this.configurationFilePath + "]");
			return ErrorCode.RUN_CONFIGURATION_FILE_NOT_PROVIDED;
		}
		
		File file = new File(this.configurationFilePath);
		if (!file.exists()) {
			System.err.println("File '" + this.configurationFilePath + "' does not exist.");
			return ErrorCode.RUN_FILE_DOES_NOT_EXIST;
		} else if (file.isDirectory()) {
			System.err.println("Path '" + this.configurationFilePath + "' represents a directory.");
			return ErrorCode.RUN_FILE_IS_DIRECTORY;
		} else if (!Files.isRegularFile(FileSystems.getDefault().getPath(file.getParent(), file.getName()))) {
			System.err.println("File '" + this.configurationFilePath + "' is not a regular file.");
			return ErrorCode.RUN_FILE_IS_NOT_REGULAR_FILE;
		}
		
		Matcher matcher = Pattern.compile("\\.(json|yaml)$").matcher(this.configurationFilePath);
		if (!matcher.find()) {
			String allowedTypes = StringHelper.join(Arrays.asList(ConfigurationSourceType.values()), ", ");
			System.err.println("File '" + this.configurationFilePath + "' does not have a valid type. Allowed are: " + allowedTypes.toLowerCase());
			return ErrorCode.RUN_INVALID_FILE_TYPE;
		}
		
		return null;
	}
	
	private ConfigurationSourceType getConfigurationSourceType() {
		String extension = this.getFileExtension().toUpperCase();
		return ConfigurationSourceType.valueOf(extension);
	}
	
	private String getFileExtension() {
		return this.configurationFilePath.substring(
			this.configurationFilePath.lastIndexOf(".") + 1, this.configurationFilePath.length()
		);
	}

	@Override
	public String id() {
		return Commands.RUN.getId();
	}
	
	public String getConfigurationFilePath() {
		return configurationFilePath;
	}
	
	public RunCommand withConfigurationFilePath(String configurationFilePath) {
		this.configurationFilePath = configurationFilePath;
		return this;
	}
}