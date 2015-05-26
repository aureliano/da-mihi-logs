package com.github.aureliano.damihilogs.reader;

import com.github.aureliano.damihilogs.config.input.ExternalCommandInput;
import com.github.aureliano.damihilogs.config.input.IConfigInput;
import com.github.aureliano.damihilogs.config.input.FileInputConfig;
import com.github.aureliano.damihilogs.config.input.StandardInputConfig;
import com.github.aureliano.damihilogs.config.input.UrlInputConfig;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;

public final class DataReaderFactory {

	private DataReaderFactory() {
		super();
	}
	
	public static IDataReader createDataReader(IConfigInput inputConfig) {
		if (inputConfig instanceof FileInputConfig) {
			boolean tailer = ((FileInputConfig) inputConfig).isTailFile();
			return ((tailer) ? new FileTailerDataReader() : new FileDataReader()).withInputConfiguration(inputConfig);
		} else if (inputConfig instanceof StandardInputConfig) {
			return new StandardDataReader().withInputConfiguration(inputConfig);
		} else if (inputConfig instanceof UrlInputConfig) {
			return new UrlDataReader().withInputConfiguration(inputConfig);
		} else if (inputConfig instanceof ExternalCommandInput) {
			return new ExternalCommandDataReader().withInputConfiguration(inputConfig);
		} else {
			String clazz = (inputConfig == null) ? "null" : inputConfig.getClass().getName();
			throw new DaMihiLogsException("Unsupported data reader for input config " + clazz);
		}
	}
}