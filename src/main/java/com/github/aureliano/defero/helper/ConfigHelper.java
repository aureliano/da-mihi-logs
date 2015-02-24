package com.github.aureliano.defero.helper;

import java.util.Map;

import com.github.aureliano.defero.config.input.IConfigInput;
import com.github.aureliano.defero.config.input.InputFileConfig;
import com.github.aureliano.defero.config.input.StandardInputConfig;
import com.github.aureliano.defero.config.input.UrlInputConfig;
import com.github.aureliano.defero.config.output.ElasticSearchOutputConfig;
import com.github.aureliano.defero.config.output.FileOutputConfig;
import com.github.aureliano.defero.config.output.IConfigOutput;
import com.github.aureliano.defero.config.output.StandardOutputConfig;
import com.github.aureliano.defero.exception.DeferoException;

public final class ConfigHelper {

	private ConfigHelper() {
		super();
	}
	
	public static String buildUrl(UrlInputConfig config) {
		StringBuilder builder = new StringBuilder();
		
		builder
			.append(config.getConnectionSchema().name().toLowerCase())
			.append("://");
		
		String host = config.getHost();
		if (host.endsWith("/")) {
			host = host.replaceFirst("/$", "");
		}
		
		builder.append(host);
		
		if (config.getPort() >= 0) {
			builder.append(":").append(config.getPort());
		}
		
		String path = config.getPath();
		if (path != null) {
			if (path.startsWith("/")) {
				path = path.replaceFirst("^/", "");
			}
			builder.append("/").append(path);
		}
		
		Map<String, String> parameters = config.getParameters();
		if (!parameters.isEmpty()) {
			builder.append("?");
		}
		
		builder.append(UrlEncodeHelper.formatParameters(parameters));
		
		return builder.toString();
	}
	
	public static void inputConfigValidation(IConfigInput config) {
		if (config == null) {
			throw new DeferoException("Input configuration must be provided.");
		}
		
		if (config instanceof InputFileConfig) {
			inputFileConfigValidation((InputFileConfig) config);
		} else if (config instanceof StandardInputConfig) {
			standardInputConfigValidation((StandardInputConfig) config);
		} else if (config instanceof UrlInputConfig) {
			urlInputConfigValidation((UrlInputConfig) config);
		} else {
			throw new DeferoException("Validation not implemented for " + config.getClass().getName() + " type");
		}
	}
	
	private static void urlInputConfigValidation(UrlInputConfig config) {
		if (config.getConnectionSchema() == null) {
			throw new DeferoException("Connection schema not provided.");
		} else if ((config.getHost() == null) || (config.getHost().equals(""))) {
			throw new DeferoException("Host not provided.");
		}
	}

	public static void outputConfigValidation(IConfigOutput config) {
		if (config == null) {
			throw new DeferoException("Output configuration must be provided.");
		}
		
		if (config instanceof StandardOutputConfig) {
			standardOutputConfigValidation((StandardOutputConfig) config);
		} else if (config instanceof FileOutputConfig) {
			fileOutputConfigValidation((FileOutputConfig) config);
		} else if (config instanceof ElasticSearchOutputConfig) {
			elasticSearchConfigValidation((ElasticSearchOutputConfig) config);
		} else {
			throw new DeferoException("Validation not implemented for " + config.getClass().getName() + " type");
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
			throw new DeferoException("Input file not provided.");
		} else if (!config.getFile().exists()) {
			throw new DeferoException("Input file '" + config.getFile().getPath() + "' does not exist.");
		} else if (!config.getFile().isFile()) {
			throw new DeferoException("Input resource '" + config.getFile().getPath() + "' is not a file.");
		}
	}
	
	protected static void outputFileConfigFileValidation(FileOutputConfig config) {
		if (config.getFile() == null) {
			throw new DeferoException("Output file not provided.");
		}
	}
	
	protected static void inputFileConfigStartPositionValidation(InputFileConfig config) {
		if (config.getStartPosition() < 0) {
			throw new DeferoException("Start position must be greater or equal to zero (>= 0).");
		}
	}
	
	protected static void elasticSearchConfigIndexValidation(ElasticSearchOutputConfig config) {
		if (config.getIndex() == null) {
			throw new DeferoException("Index name not provided.");
		} else if ("".equals(config.getIndex())) {
			throw new DeferoException("Empty index name.");
		}
	}
	
	protected static void elasticSearchConfigMappingTypeValidation(ElasticSearchOutputConfig config) {
		if (config.getMappingType() == null) {
			throw new DeferoException("Mapping type not provided.");
		} else if ("".equals(config.getMappingType())) {
			throw new DeferoException("Empty mapping type.");
		}
	}
}