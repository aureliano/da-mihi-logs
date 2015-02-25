package com.github.aureliano.defero.config.input;

import java.util.ArrayList;
import java.util.List;

public class ExternalCommandInput implements IConfigInput {

	private String id;
	private String command;
	private List<String> parameters;
	
	public ExternalCommandInput() {
		this.parameters = new ArrayList<String>();
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