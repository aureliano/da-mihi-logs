package com.github.aureliano.damihilogs.config.input;

import java.util.ArrayList;
import java.util.List;

public class ExternalCommandInput implements IConfigInput {

	private String id;
	private String command;
	private List<String> parameters;
	private boolean useLastExecutionRecords;
	
	public ExternalCommandInput() {
		this.parameters = new ArrayList<String>();
	}
	
	@Override
	public boolean isUseLastExecutionRecords() {
		return this.useLastExecutionRecords;
	}

	@Override
	public ExternalCommandInput withUseLastExecutionRecords(boolean useLastExecutionRecords) {
		this.useLastExecutionRecords = useLastExecutionRecords;
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ExternalCommandInput clone() {
		return new ExternalCommandInput()
			.withConfigurationId(this.id)
			.withCommand(this.command)
			.withParameters(this.parameters);
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
}