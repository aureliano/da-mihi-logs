package com.github.aureliano.damihilogs.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.aureliano.damihilogs.config.EventCollectorConfiguration;
import com.github.aureliano.damihilogs.config.IConfiguration;
import com.github.aureliano.damihilogs.config.input.ExternalCommandInput;
import com.github.aureliano.damihilogs.config.input.IConfigInput;
import com.github.aureliano.damihilogs.config.input.FileInputConfig;
import com.github.aureliano.damihilogs.config.input.StandardInputConfig;
import com.github.aureliano.damihilogs.config.input.UrlInputConfig;
import com.github.aureliano.damihilogs.config.output.ElasticSearchOutputConfig;
import com.github.aureliano.damihilogs.config.output.FileOutputConfig;
import com.github.aureliano.damihilogs.config.output.IConfigOutput;
import com.github.aureliano.damihilogs.config.output.StandardOutputConfig;
import com.github.aureliano.damihilogs.converter.ConfigurationConverter;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;

public final class ConfigHelper {
	
	private static final List<String> AVAILABLE_EXECUTOR_NAMES = new ArrayList<String>();
	private static final List<String> UNAVAILABLE_EXECUTOR_NAMES = new ArrayList<String>();
	
	static {
		populateExecutorNamesMap();
	}

	private ConfigHelper() {
		super();
	}
	
	public static EventCollectorConfiguration loadConfiguration(String path) {
		String text = FileHelper.readFile(path);
		Map<String, Object> data = DataHelper.jsonStringToObject(text, Map.class);
		
		return new ConfigurationConverter().convert(data);
	}

	public static void copyMetadata(IConfiguration from, IConfiguration to) {
		Set<Object> keys = from.getMetadata().keySet();
		for (Object key : keys) {
			to.putMetadata(key.toString(), from.getMetadata(key.toString()));
		}
	}
	
	public static void inputConfigValidation(IConfigInput config) {
		if (config == null) {
			throw new DaMihiLogsException("Input configuration must be provided.");
		} else if (StringHelper.isEmpty(config.getConfigurationId())) {
			throw new DaMihiLogsException("Input configuration ID must be provided.");
		}
		
		if (config instanceof FileInputConfig) {
			inputFileConfigValidation((FileInputConfig) config);
		} else if (config instanceof StandardInputConfig) {
			standardInputConfigValidation((StandardInputConfig) config);
		} else if (config instanceof UrlInputConfig) {
			urlInputConfigValidation((UrlInputConfig) config);
		} else if (config instanceof ExternalCommandInput) {
			externalCommandConfigValidation((ExternalCommandInput) config);
		} else {
			throw new DaMihiLogsException("Validation not implemented for " + config.getClass().getName() + " type");
		}
	}

	protected static void urlInputConfigValidation(UrlInputConfig config) {
		if (config.getConnectionSchema() == null) {
			throw new DaMihiLogsException("Connection schema not provided.");
		} else if (StringHelper.isEmpty(config.getHost())) {
			throw new DaMihiLogsException("Host not provided.");
		} else if (config.getOutputFile() == null) {
			throw new DaMihiLogsException("Output file not provided.");
		} else if (config.getOutputFile().isDirectory()) {
			throw new DaMihiLogsException("Output file '" + config.getOutputFile().getPath() + "' is a directory.");
		}
	}
	
	protected static void externalCommandConfigValidation(ExternalCommandInput config) {
		if (StringHelper.isEmpty(config.getCommand())) {
			throw new DaMihiLogsException("Command not provided.");
		}
	}

	public static void outputConfigValidation(IConfigOutput config) {
		if (config == null) {
			throw new DaMihiLogsException("Output configuration must be provided.");
		}
		
		if (config instanceof StandardOutputConfig) {
			standardOutputConfigValidation((StandardOutputConfig) config);
		} else if (config instanceof FileOutputConfig) {
			fileOutputConfigValidation((FileOutputConfig) config);
		} else if (config instanceof ElasticSearchOutputConfig) {
			elasticSearchConfigValidation((ElasticSearchOutputConfig) config);
		} else {
			throw new DaMihiLogsException("Validation not implemented for " + config.getClass().getName() + " type");
		}
	}

	protected static void elasticSearchConfigValidation(ElasticSearchOutputConfig config) {
		elasticSearchConfigIndexValidation(config);
		elasticSearchConfigMappingTypeValidation(config);
	}

	protected static void standardInputConfigValidation(StandardInputConfig config) {
		// Do nothing. Uses of standard system input.
	}

	protected static void standardOutputConfigValidation(StandardOutputConfig config) {
		// Do nothing. Uses of standard system output.
	}
	
	protected static void fileOutputConfigValidation(FileOutputConfig config) {
		outputFileConfigFileValidation(config);
	}

	protected static void inputFileConfigValidation(FileInputConfig config) {
		inputFileConfigFileValidation(config);
		inputFileConfigStartPositionValidation(config);
	}
	
	protected static void inputFileConfigFileValidation(FileInputConfig config) {
		if (config.getFile() == null) {
			throw new DaMihiLogsException("Input file not provided.");
		}
	}
	
	protected static void outputFileConfigFileValidation(FileOutputConfig config) {
		if (config.getFile() == null) {
			throw new DaMihiLogsException("Output file not provided.");
		}
	}
	
	protected static void inputFileConfigStartPositionValidation(FileInputConfig config) {
		if ((config.getStartPosition() == null) || (config.getStartPosition() < 0)) {
			throw new DaMihiLogsException("Start position must be greater or equal to zero (>= 0).");
		}
	}
	
	protected static void elasticSearchConfigIndexValidation(ElasticSearchOutputConfig config) {
		if (config.getIndex() == null) {
			throw new DaMihiLogsException("Index name not provided.");
		} else if ("".equals(config.getIndex())) {
			throw new DaMihiLogsException("Empty index name.");
		}
	}
	
	protected static void elasticSearchConfigMappingTypeValidation(ElasticSearchOutputConfig config) {
		if (config.getMappingType() == null) {
			throw new DaMihiLogsException("Mapping type not provided.");
		} else if ("".equals(config.getMappingType())) {
			throw new DaMihiLogsException("Empty mapping type.");
		}
	}
	
	public synchronized static String newUniqueConfigurationName() {
		for (String executorName : AVAILABLE_EXECUTOR_NAMES) {
			if (!UNAVAILABLE_EXECUTOR_NAMES.contains(executorName)) {
				AVAILABLE_EXECUTOR_NAMES.remove(executorName);
				UNAVAILABLE_EXECUTOR_NAMES.add(executorName);
				
				return executorName;
			}
		}
		
		throw new DaMihiLogsException("Could not create a new unique configuration name. Exceeded!");
	}
	
	private static void populateExecutorNamesMap() {
		String[] names = FileHelper.readResource("configuration-names").split("\n");
		
		for (String name : names) {
			AVAILABLE_EXECUTOR_NAMES.add(name);
		}
		
		Collections.shuffle(AVAILABLE_EXECUTOR_NAMES);
	}
}