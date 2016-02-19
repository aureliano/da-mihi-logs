package com.github.aureliano.evtbridge.input.file;

import java.io.File;
import java.util.Map;

import com.github.aureliano.evtbridge.common.helper.StringHelper;
import com.github.aureliano.evtbridge.core.config.AbstractConfigInputConverter;
import com.github.aureliano.evtbridge.core.config.InputConfigTypes;

public class FileInputConverter extends AbstractConfigInputConverter<FileInputConfig> {

	public FileInputConverter() {
		super();
	}

	@Override
	public FileInputConfig convert(Map<String, Object> data) {
		FileInputConfig conf = new FileInputConfig();
		
		super.configureObject(conf, data);
		String value = StringHelper.parse(data.get("file"));
		if (!StringHelper.isEmpty(value)) {
			conf.withFile(new File(value));
		}
		
		value = StringHelper.parse(data.get("startPosition"));
		if (!StringHelper.isEmpty(value)) {
			if (!StringHelper.isNumeric(value)) {
				throw new FileInputException("Property startPosition was expected to match \\d+ pattern in input file configuration.");
			}
			conf.withStartPosition(Integer.parseInt(value));
		}
		
		value = StringHelper.parse(data.get("encoding"));
		if (!StringHelper.isEmpty(value)) {
			conf.withEncoding(value);
		}
		
		return conf;
	}
	
	@Override
	public String id() {
		return InputConfigTypes.FILE_INPUT.name();
	}
}