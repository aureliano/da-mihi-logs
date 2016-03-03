package com.github.aureliano.evtbridge.output.file;

import java.util.Map;

import com.github.aureliano.evtbridge.common.helper.StringHelper;
import com.github.aureliano.evtbridge.core.config.AbstractConfigOutputConverter;
import com.github.aureliano.evtbridge.core.config.OutputConfigTypes;

public class FileOutputConverter extends AbstractConfigOutputConverter<FileOutputConfig> {

	public FileOutputConverter() {
		super();
	}

	@Override
	public FileOutputConfig convert(Map<String, Object> data) {
		FileOutputConfig conf = new FileOutputConfig();
		
		super.configureObject(conf, data);
		String value = StringHelper.parse(data.get("file"));
		if (!StringHelper.isEmpty(value)) {
			conf.withFile(value);
		}
		
		value = StringHelper.parse(data.get("append"));
		if (!StringHelper.isEmpty(value)) {
			conf.withAppend(Boolean.parseBoolean(value.toLowerCase()));
		}
		
		value = StringHelper.parse(data.get("useBuffer"));
		if (!StringHelper.isEmpty(value)) {
			conf.withUseBuffer(Boolean.parseBoolean(value.toLowerCase()));
		}
		
		value = StringHelper.parse(data.get("encoding"));
		if (!StringHelper.isEmpty(value)) {
			conf.withEncoding(value);
		}
		
		return conf;
	}
	
	@Override
	public String id() {
		return OutputConfigTypes.FILE_OUTPUT.name();
	}
}