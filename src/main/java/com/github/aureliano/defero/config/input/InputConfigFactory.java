package com.github.aureliano.defero.config.input;

import com.github.aureliano.defero.exception.DeferoException;

public final class InputConfigFactory {

	private InputConfigFactory() {
		super();
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T createInputConfig(Class<T> type) {
		if (InputFileConfig.class.equals(type)) {
			return (T) new InputFileConfig();
		} else if (StandardInputConfig.class.equals(type)) {
			return (T) new StandardInputConfig();
		} else {
			throw new DeferoException("No such input config for type " + type);
		}
	}
}