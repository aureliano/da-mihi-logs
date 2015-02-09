package com.github.aureliano.defero.config.output;

import com.github.aureliano.defero.exception.DeferoException;

public final class OutputConfigFactory {

	private OutputConfigFactory() {
		super();
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T createOutputConfig(Class<T> type) {
		if (StandardOutputConfig.class.equals(type)) {
			return (T) new StandardOutputConfig();
		} else {
			throw new DeferoException("No such input config for type " + type);
		}
	}
}