package com.github.aureliano.defero.helper;

import com.github.aureliano.defero.config.InputConfig;
import com.github.aureliano.defero.exception.DeferoException;

public final class ConfigHelper {

	private ConfigHelper() {
		super();
	}
	
	public static void inputConfigValidation(InputConfig config) {
		if (config == null) {
			throw new DeferoException("Input configuration must be provided.");
		}
		
		inputConfigFileValidation(config);
		inputConfigStartPositionValidation(config);
	}
	
	protected static void inputConfigFileValidation(InputConfig config) {
		if (config.getFile() == null) {
			throw new DeferoException("Input file not provided.");
		} else if (!config.getFile().exists()) {
			throw new DeferoException("Input file '" + config.getFile().getPath() + "' does not exist.");
		} else if (!config.getFile().isFile()) {
			throw new DeferoException("Input resource '" + config.getFile().getPath() + "' is not a file.");
		}
	}
	
	protected static void inputConfigStartPositionValidation(InputConfig config) {
		if (config.getStartPosition() < 0) {
			throw new DeferoException("Start position must be greater or equal to zero (>= 0).");
		}
	}
}