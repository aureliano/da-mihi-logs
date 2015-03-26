package com.github.aureliano.damihilogs.config.input;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ExternalCommandInput implements IConfigInput {

	private String id;
	private String command;
	private List<String> parameters;
	private Boolean useLastExecutionRecords;
	private Properties metadata;
	
	public ExternalCommandInput() {
		this.useLastExecutionRecords = false;
		this.parameters = new ArrayList<String>();
		this.metadata = new Properties();
	}
	
	@Override
	public Boolean isUseLastExecutionRecords() {
		return this.useLastExecutionRecords;
	}

	@Override
	public ExternalCommandInput withUseLastExecutionRecords(Boolean useLastExecutionRecords) {
		this.useLastExecutionRecords = useLastExecutionRecords;
		return this;
	}

	@Override
	public ExternalCommandInput clone() {
		return new ExternalCommandInput()
			.withConfigurationId(this.id)
			.withCommand(this.command)
			.withParameters(this.parameters)
			.withMetadata(this.metadata);
	}

	@Override
	public String getConfigurationId() {
		return this.id;
	}

	@Override
	public ExternalCommandInput withConfigurationId(String id) {
		this.id = id;
		return this;
	}
	
	public String getCommand() {
		return command;
	}
	
	public ExternalCommandInput withCommand(String command) {
		this.command = command;
		return this;
	}
	
	public List<String> getParameters() {
		return parameters;
	}
	
	public ExternalCommandInput withParameters(List<String> parameters) {
		this.parameters = parameters;
		return this;
	}
	
	public ExternalCommandInput addParameter(String parameter) {
		this.parameters.add(parameter);
		return this;
	}
	
	@Override
	public ExternalCommandInput putMetadata(String key, String value) {
		this.metadata.put(key, value);
		return this;
	}
	
	@Override
	public String getMetadata(String key) {
		return this.metadata.getProperty(key);
	}
	
	protected ExternalCommandInput withMetadata(Properties properties) {
		this.metadata = properties;
		return this;
	}

	@Override
	public Properties getMetadata() {
		return this.metadata;
	}
}