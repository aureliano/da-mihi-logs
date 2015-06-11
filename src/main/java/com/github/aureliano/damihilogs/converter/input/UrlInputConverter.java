package com.github.aureliano.damihilogs.converter.input;

import java.util.Map;

import com.github.aureliano.damihilogs.config.input.ConnectionSchema;
import com.github.aureliano.damihilogs.config.input.InputConfigTypes;
import com.github.aureliano.damihilogs.config.input.UrlInputConfig;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.helper.DataHelper;
import com.github.aureliano.damihilogs.helper.StringHelper;
import com.github.aureliano.damihilogs.inout.CompressMetadata;
import com.github.aureliano.damihilogs.inout.SupportedCompressionType;

public class UrlInputConverter extends AbstractInputConverter<UrlInputConfig> {

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
				throw new DaMihiLogsException("Property connectionSchema was expected to match (HTTP|HTTPS) pattern in input url configuration.");
			}
			conf.withConnectionSchema(ConnectionSchema.valueOf(value.toUpperCase()));
		}
		
		value = StringHelper.parse(data.get("port"));
		if (!StringHelper.isEmpty(value)) {
			if (!value.toUpperCase().matches("\\d+")) {
				throw new DaMihiLogsException("Property port was expected to match \\d+ pattern in input url configuration.");
			}
			conf.withPort(Integer.parseInt(value));
		}
		
		value = StringHelper.parse(data.get("readTimeOut"));
		if (!StringHelper.isEmpty(value)) {
			if (!value.toUpperCase().matches("\\d+")) {
				throw new DaMihiLogsException("Property readTimeOut was expected to match \\d+ pattern in input url configuration.");
			}
			conf.withReadTimeout(Long.parseLong(value));
		}
		
		value = StringHelper.parse(data.get("byteOffSet"));
		if (!StringHelper.isEmpty(value)) {
			if (!value.toUpperCase().matches("\\d+")) {
				throw new DaMihiLogsException("Property byteOffSet was expected to match \\d+ pattern in input url configuration.");
			}
			conf.withByteOffSet(Integer.parseInt(value));
		}
		
		value = StringHelper.parse(data.get("outputFile"));
		if (!StringHelper.isEmpty(value)) {
			conf.withOutputFile(value);
		}

		if (data.get("decompressFile") != null) {
			Map<String, Object> map = DataHelper.getAsHash(data, "decompressFile");
			value = StringHelper.parse(map.get("type"));
			conf.withDecompressFileConfiguration(new CompressMetadata()
				.withCompressionType(SupportedCompressionType.valueOf(value.toUpperCase()))
				.withInputFilePath(StringHelper.parse(map.get("inputFilePath")))
				.withOutputFilePath(StringHelper.parse(map.get("outputFilePath"))));
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