package com.github.aureliano.damihilogs.converter.input;

import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.github.aureliano.damihilogs.config.input.FileTailerInputConfig;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.helper.StringHelper;
import com.github.aureliano.damihilogs.helper.TimeHelper;

public class FileTailerInputConverter extends AbstractInputConverter<FileTailerInputConfig> {

	public FileTailerInputConverter() {
		super();
	}

	@Override
	public FileTailerInputConfig convert(Map<String, Object> data) {
		FileTailerInputConfig conf = new FileTailerInputConfig();
		
		super.configureObject(conf, data);
		String value = StringHelper.parse(data.get("file"));
		if (!StringHelper.isEmpty(value)) {
			conf.withFile(new File(value));
		}
		
		value = StringHelper.parse(data.get("encoding"));
		if (!StringHelper.isEmpty(value)) {
			conf.withEncoding(value);
		}
		
		value = StringHelper.parse(data.get("tailDelay"));
		if (!StringHelper.isEmpty(value)) {
			if (!value.matches("\\d+")) {
				throw new DaMihiLogsException("Property tailDelay was expected to match \\d+ pattern in input file configuration.");
			}
			conf.withTailDelay(Long.parseLong(value));
		}
		
		value = StringHelper.parse(data.get("tailInterval"));
		if (!StringHelper.isEmpty(value)) {
			if (!value.matches("\\d+")) {
				throw new DaMihiLogsException("Property tailInterval was expected to match \\d+ pattern in input file configuration.");
			}
			conf.withTailInterval(Long.parseLong(value));
		}
		
		value = StringHelper.parse(data.get("timeUnit"));
		if (!StringHelper.isEmpty(value)) {
			if (!TimeHelper.isValidTimeUnit(value)) {
				throw new DaMihiLogsException("Property timeUnit was expected to be one of: " + Arrays.toString(TimeUnit.values()) + " but got " + value);
			}
			conf.withTimeUnit(TimeUnit.valueOf(value.toUpperCase()));
		}
		
		return conf;
	}
}