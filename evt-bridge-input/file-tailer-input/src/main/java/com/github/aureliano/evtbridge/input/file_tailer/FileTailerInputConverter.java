package com.github.aureliano.evtbridge.input.file_tailer;

import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.github.aureliano.evtbridge.common.helper.StringHelper;
import com.github.aureliano.evtbridge.core.config.AbstractConfigInputConverter;
import com.github.aureliano.evtbridge.core.config.InputConfigTypes;
import com.github.aureliano.evtbridge.core.helper.TimeHelper;

public class FileTailerInputConverter extends AbstractConfigInputConverter<FileTailerInputConfig> {

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
			if (!StringHelper.isNumeric(value)) {
				throw new FileTailerInputException("Property tailDelay was expected to match \\d+ pattern in input file configuration.");
			}
			conf.withTailDelay(Long.parseLong(value));
		}
		
		value = StringHelper.parse(data.get("tailInterval"));
		if (!StringHelper.isEmpty(value)) {
			if (!StringHelper.isNumeric(value)) {
				throw new FileTailerInputException("Property tailInterval was expected to match \\d+ pattern in input file configuration.");
			}
			conf.withTailInterval(Long.parseLong(value));
		}
		
		value = StringHelper.parse(data.get("timeUnit"));
		if (!StringHelper.isEmpty(value)) {
			if (!TimeHelper.isValidTimeUnit(value)) {
				throw new FileTailerInputException("Property timeUnit was expected to be one of: " + Arrays.toString(TimeUnit.values()) + " but got " + value);
			}
			conf.withTimeUnit(TimeUnit.valueOf(value.toUpperCase()));
		}
		
		return conf;
	}
	
	@Override
	public String id() {
		return InputConfigTypes.FILE_TAILER.name();
	}
}