package com.github.aureliano.defero.reader;

import com.github.aureliano.defero.config.input.IConfigInput;
import com.github.aureliano.defero.config.input.InputFileConfig;
import com.github.aureliano.defero.config.input.StandardInputConfig;
import com.github.aureliano.defero.exception.DeferoException;

public final class DataReaderFactory {

	private DataReaderFactory() {
		super();
	}
	
	public static IDataReader createDataReader(IConfigInput inputConfig) {
		if (inputConfig instanceof InputFileConfig) {
			boolean tailer = ((InputFileConfig) inputConfig).isTailFile();
			return ((tailer) ? new FileTailerDataReader() : new FileDataReader()).withInputConfiguration(inputConfig);
		} else if (inputConfig instanceof StandardInputConfig) {
			return new StandardDataReader().withInputConfiguration(inputConfig);
		} else {
			String clazz = (inputConfig == null) ? "null" : inputConfig.getClass().getName();
			throw new DeferoException("Unsupported data reader for input config " + clazz);
		}
	}
}