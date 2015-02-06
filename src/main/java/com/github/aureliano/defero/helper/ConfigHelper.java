package com.github.aureliano.defero.helper;

import com.github.aureliano.defero.config.input.IConfigInput;
import com.github.aureliano.defero.config.input.InputFileConfig;
import com.github.aureliano.defero.exception.DeferoException;

public final class ConfigHelper {

	private ConfigHelper() {
		super();
	}
	
	public static void inputConfigValidation(IConfigInput config) {
		if (config == null) {
			throw new DeferoException("Input configuration must be provided.");
		}
		
		if (config instanceof InputFileConfig) {
			inputFileConfigValidation((InputFileConfig) config);
		} else {
			throw new DeferoException("Validation not implemented for " + config.getClass().getName() + " type");
		}
	}
	
	protected static void inputFileConfigValidation(InputFileConfig config) {
		inputFileConfigFileValidation(config);
		inputFileConfigStartPositionValidation(config);
	}
	
	protected static void inputFileConfigFileValidation(InputFileConfig config) {
		if (config.getFile() == null) {
			throw new DeferoException("Input file not provided.");
		} else if (!config.getFile().exists()) {
			throw new DeferoException("Input file '" + config.getFile().getPath() + "' does not exist.");
		} else if (!config.getFile().isFile()) {
			throw new DeferoException("Input resource '" + config.getFile().getPath() + "' is not a file.");
		}
	}
	
	protected static void inputFileConfigStartPositionValidation(InputFileConfig config) {
		if (config.getStartPosition() < 0) {
			throw new DeferoException("Start position must be greater or equal to zero (>= 0).");
		}
	}
}