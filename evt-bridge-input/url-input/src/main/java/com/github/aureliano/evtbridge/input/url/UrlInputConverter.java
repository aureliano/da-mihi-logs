package com.github.aureliano.evtbridge.input.url;

import java.util.Map;

import com.github.aureliano.evtbridge.common.helper.StringHelper;
import com.github.aureliano.evtbridge.core.config.AbstractConfigInputConverter;
import com.github.aureliano.evtbridge.core.config.ConnectionSchema;
import com.github.aureliano.evtbridge.core.config.InputConfigTypes;

public class UrlInputConverter extends AbstractConfigInputConverter<UrlInputConfig> {

	public UrlInputConverter() {
		super();
	}

	@Override
	public UrlInputConfig convert(Map<String, Object> data) {
		UrlInputConfig conf = new UrlInputConfig();
		
		this.configureObject(conf, data);
		String value = StringHelper.parse(data.get("connectionSchema"));
		if (!StringHelper.isEmpty(value)) {
			if (!value.toUpperCase().matches("(HTTP|HTTPS)")) {
				throw new UrlInputException("Property connectionSchema was expected to match (HTTP|HTTPS) pattern in input url configuration.");
			}
			conf.withConnectionSchema(ConnectionSchema.valueOf(value.toUpperCase()));
		}
		
		value = StringHelper.parse(data.get("port"));
		if (!StringHelper.isEmpty(value)) {
			if (!StringHelper.isNumeric(value)) {
				throw new UrlInputException("Property port was expected to match \\d+ pattern in input url configuration.");
			}
			conf.withPort(Integer.parseInt(value));
		}
		
		value = StringHelper.parse(data.get("readTimeOut"));
		if (!StringHelper.isEmpty(value)) {
			if (!StringHelper.isNumeric(value)) {
				throw new UrlInputException("Property readTimeOut was expected to match \\d+ pattern in input url configuration.");
			}
			conf.withReadTimeout(Long.parseLong(value));
		}
		
		value = StringHelper.parse(data.get("byteOffSet"));
		if (!StringHelper.isEmpty(value)) {
			if (!StringHelper.isNumeric(value)) {
				throw new UrlInputException("Property byteOffSet was expected to match \\d+ pattern in input url configuration.");
			}
			conf.withByteOffSet(Integer.parseInt(value));
		}
		
		value = StringHelper.parse(data.get("outputFile"));
		if (!StringHelper.isEmpty(value)) {
			conf.withOutputFile(value);
		}
		
		conf
			.withUser(StringHelper.parse(data.get("user")))
			.withPassword(StringHelper.parse(data.get("password")))
			.withPath(StringHelper.parse(data.get("path")))
			.withHost(StringHelper.parse(data.get("host")));
		
		return conf;
	}
	
	@Override
	public String id() {
		return InputConfigTypes.URL.name();
	}
}