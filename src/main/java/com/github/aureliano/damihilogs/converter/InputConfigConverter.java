package com.github.aureliano.damihilogs.converter;

import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

import com.github.aureliano.damihilogs.config.input.ConnectionSchema;
import com.github.aureliano.damihilogs.config.input.ExternalCommandInput;
import com.github.aureliano.damihilogs.config.input.IConfigInput;
import com.github.aureliano.damihilogs.config.input.InputFileConfig;
import com.github.aureliano.damihilogs.config.input.StandardInputConfig;
import com.github.aureliano.damihilogs.config.input.UrlInputConfig;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.exception.IExceptionHandler;
import com.github.aureliano.damihilogs.helper.DataHelper;
import com.github.aureliano.damihilogs.helper.ReflectionHelper;
import com.github.aureliano.damihilogs.helper.StringHelper;
import com.github.aureliano.damihilogs.inout.CompressMetadata;
import com.github.aureliano.damihilogs.inout.SupportedCompressionType;
import com.github.aureliano.damihilogs.listener.DataReadingListener;
import com.github.aureliano.damihilogs.listener.ExecutionListener;
import com.github.aureliano.damihilogs.matcher.IMatcher;

public class InputConfigConverter implements IConfigurationConverter<IConfigInput> {

	protected static final String[] INPUT_CONFIG_TYPES = new String[] { "externalCommand", "file", "Standard", "url" };
	
	private String id;
	
	public InputConfigConverter() {
		super();
	}

	@Override
	public IConfigInput convert(Map<String, Object> data) {
		if (data.isEmpty()) {
			return null;
		}
		
		this.id = StringHelper.parse(data.keySet().iterator().next());
		Map<String, Object> map = (Map<String, Object>) data.get(this.id);
		String type = StringHelper.parse(DataHelper.getAsHash(data, this.id).get("type"));
		
		if ("file".equals(type)) {
			return this.createFileConfig(DataHelper.getAsHash(map, "properties")).withConfigurationId(this.id);
		} else if ("externalCommand".equals(type)) {
			return this.createExternalCommandConfig(DataHelper.getAsHash(map, "properties")).withConfigurationId(this.id);
		} else if ("standard".equals(type)) {
			return this.createStandardConfig(DataHelper.getAsHash(map, "properties")).withConfigurationId(this.id);
		} else if ("url".equals(type)) {
			return this.createUrlConfig(DataHelper.getAsHash(map, "properties")).withConfigurationId(this.id);
		} else {
			throw new DaMihiLogsException("Input config type '" + type + "' not supported. Expected one of: " + Arrays.toString(INPUT_CONFIG_TYPES));
		}
	}

	private IConfigInput createFileConfig(Map<String, Object> data) {
		InputFileConfig conf = new InputFileConfig();
		
		this.configureObject(conf, data);
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
		
		conf.withEncoding(StringHelper.parse(data.get("encoding")));
		
		value = StringHelper.parse(data.get("tailFile"));
		if (!StringHelper.isEmpty(value)) {
			conf.withTailFile(Boolean.parseBoolean(value.toLowerCase()));
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
	
	private IConfigInput createExternalCommandConfig(Map<String, Object> data) {
		ExternalCommandInput conf = new ExternalCommandInput();
		
		this.configureObject(conf, data);
		conf.withCommand(StringHelper.parse(data.get("command")));
		
		if (data.get("parameters") != null) {
			String[] parameters = (String[]) data.get("parameters");
			for (String parameter : parameters) {
				conf.addParameter(parameter);
			}
		}
		
		return conf;
	}
	
	private IConfigInput createStandardConfig(Map<String, Object> data) {
		StandardInputConfig conf = new StandardInputConfig();
		this.configureObject(conf, data);
		
		return conf;
	}
	
	private IConfigInput createUrlConfig(Map<String, Object> data) {
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
	
	private void configureObject(IConfigInput conf, Map<String, Object> data) {
		String value = StringHelper.parse(data.get("matcher"));
		if (!StringHelper.isEmpty(value)) {
			conf.withMatcher((IMatcher) ReflectionHelper.newInstance(value));
		}
		
		value = StringHelper.parse(data.get("useLastExecutionLog"));
		if (!StringHelper.isEmpty(value)) {
			conf.withUseLastExecutionRecords(Boolean.parseBoolean(value.toLowerCase()));
		}
		
		if (data.get("exceptionHandlers") != null) {
			String[] handlers = (String[]) data.get("exceptionHandlers");
			for (String handler : handlers) {
				conf.addExceptionHandler((IExceptionHandler) ReflectionHelper.newInstance(handler));
			}
		}
		
		if (data.get("dataReadingListeners") != null) {
			String[] listeners = (String[]) data.get("dataReadingListeners");
			for (String listener : listeners) {
				conf.addDataReadingListener((DataReadingListener) ReflectionHelper.newInstance(listener));
			}
		}
		
		if (data.get("executionListeners") != null) {
			String[] listeners = (String[]) data.get("executionListeners");
			for (String listener : listeners) {
				conf.addExecutionListener((ExecutionListener) ReflectionHelper.newInstance(listener));
			}
		}
		
		if (data.get("metadata") != null) {
			Properties properties = DataHelper.mapToProperties((Map<String, Object>) data.get("metadata"));
			for (Object key : properties.keySet()) {
				conf.putMetadata(key.toString(), properties.getProperty(key.toString()));
			}
		}
	}
}