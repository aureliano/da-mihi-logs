package com.github.aureliano.damihilogs.helper;

import java.util.Set;

import com.github.aureliano.damihilogs.config.IConfiguration;
import com.github.aureliano.damihilogs.config.input.ExternalCommandInput;
import com.github.aureliano.damihilogs.config.input.IConfigInput;
import com.github.aureliano.damihilogs.config.input.InputFileConfig;
import com.github.aureliano.damihilogs.config.input.StandardInputConfig;
import com.github.aureliano.damihilogs.config.input.UrlInputConfig;
import com.github.aureliano.damihilogs.config.output.ElasticSearchOutputConfig;
import com.github.aureliano.damihilogs.config.output.FileOutputConfig;
import com.github.aureliano.damihilogs.config.output.IConfigOutput;
import com.github.aureliano.damihilogs.config.output.StandardOutputConfig;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;

public final class ConfigHelper {

	private ConfigHelper() {
		super();
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
		}
		
		if (config instanceof InputFileConfig) {
			inputFileConfigValidation((InputFileConfig) config);
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
		} else if ((config.getHost() == null) || (config.getHost().equals(""))) {
			throw new DaMihiLogsException("Host not provided.");
		} else if (config.getOutputFile() == null) {
			throw new DaMihiLogsException("Output file not provided.");
		} else if (config.getOutputFile().isDirectory()) {
			throw new DaMihiLogsException("Output file '" + config.getOutputFile().getPath() + "' is a directory.");
		}
	}
	
	protected static void externalCommandConfigValidation(ExternalCommandInput config) {
		if ((config.getCommand() == null) || (config.getCommand().equals(""))) {
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

	protected static void inputFileConfigValidation(InputFileConfig config) {
		inputFileConfigFileValidation(config);
		inputFileConfigStartPositionValidation(config);
	}
	
	protected static void inputFileConfigFileValidation(InputFileConfig config) {
		if (config.getFile() == null) {
			throw new DaMihiLogsException("Input file not provided.");
		} else if (!config.getFile().exists()) {
			throw new DaMihiLogsException("Input file '" + config.getFile().getPath() + "' does not exist.");
		} else if (!config.getFile().isFile()) {
			throw new DaMihiLogsException("Input resource '" + config.getFile().getPath() + "' is not a file.");
		}
	}
	
	protected static void outputFileConfigFileValidation(FileOutputConfig config) {
		if (config.getFile() == null) {
			throw new DaMihiLogsException("Output file not provided.");
		}
	}
	
	protected static void inputFileConfigStartPositionValidation(InputFileConfig config) {
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
}