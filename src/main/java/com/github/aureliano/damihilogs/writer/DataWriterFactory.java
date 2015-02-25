package com.github.aureliano.defero.writer;

import com.github.aureliano.defero.config.output.ElasticSearchOutputConfig;
import com.github.aureliano.defero.config.output.FileOutputConfig;
import com.github.aureliano.defero.config.output.IConfigOutput;
import com.github.aureliano.defero.config.output.StandardOutputConfig;
import com.github.aureliano.defero.exception.DeferoException;

public final class DataWriterFactory {

	private DataWriterFactory() {
		super();
	}
	
	public static IDataWriter createDataWriter(IConfigOutput outputConfig) {
		if (outputConfig instanceof StandardOutputConfig) {
			return new StandardDataWriter().withOutputConfiguration(outputConfig);
		} else if (outputConfig instanceof FileOutputConfig) {
			return new FileDataWriter().withOutputConfiguration(outputConfig);
		} else if (outputConfig instanceof ElasticSearchOutputConfig) {
			return new ElasticSearchDataWriter().withOutputConfiguration(outputConfig);
		} else {
			String clazz = (outputConfig == null) ? "null" : outputConfig.getClass().getName();
			throw new DeferoException("Unsupported data writer for output config " + clazz);
		}
	}
}