package com.github.aureliano.damihilogs.converter.input;

import java.io.File;
import java.util.Map;

import com.github.aureliano.damihilogs.config.input.FileInputConfig;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.helper.DataHelper;
import com.github.aureliano.damihilogs.helper.StringHelper;
import com.github.aureliano.damihilogs.inout.CompressMetadata;
import com.github.aureliano.damihilogs.inout.SupportedCompressionType;

public class FileInputConverter extends AbstractInputConverter<FileInputConfig> {

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
			if (!value.matches("\\d+")) {
				throw new DaMihiLogsException("Property startPosition was expected to match \\d+ pattern in input file configuration.");
			}
			conf.withStartPosition(Integer.parseInt(value));
		}
		
		value = StringHelper.parse(data.get("encoding"));
		if (!StringHelper.isEmpty(value)) {
			conf.withEncoding(value);
		}

		if (data.get("decompressFile") != null) {
			Map<String, Object> map = DataHelper.getAsHash(data, "decompressFile");
			value = StringHelper.parse(map.get("type"));
			conf.withDecompressFileConfiguration(new CompressMetadata()
				.withCompressionType(SupportedCompressionType.valueOf(value.toUpperCase()))
				.withInputFilePath(StringHelper.parse(map.get("inputFilePath")))
				.withOutputFilePath(StringHelper.parse(map.get("outputFilePath"))));
		}
		
		return conf;
	}
}