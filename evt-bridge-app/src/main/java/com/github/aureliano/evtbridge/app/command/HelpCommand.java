package com.github.aureliano.evtbridge.app.command;

import com.github.aureliano.evtbridge.app.ErrorCode;
import com.github.aureliano.evtbridge.common.helper.StringHelper;
import com.github.aureliano.evtbridge.core.EventBridgeMetadata;
import com.github.aureliano.evtbridge.core.helper.FileHelper;

public class HelpCommand implements ICommand {

	private String command;
	
	public HelpCommand() {}
	
	public ErrorCode execute() {
		return (StringHelper.isEmpty(this.command)) ? this.generalHelp() : this.commandHelp();
	}
	
	public String id() {
		return Commands.HELP.getId();
	}
	
	private ErrorCode generalHelp() {
		EventBridgeMetadata metadata = EventBridgeMetadata.instance();
		String help = FileHelper.readResource("app-help")
			.replaceAll("\\$\\{app.binary.linux\\}", metadata.getProperty("app.binary.linux"))
			.replaceAll("\\$\\{app.requirement.jvm\\}", metadata.getProperty("app.requirement.jvm"))
			.replaceAll("\\$\\{project.name\\}", metadata.getProperty("project.name"))
			.replaceAll("\\$\\{project.issues\\}", metadata.getProperty("project.issues"))
			.replaceAll("\\$\\{project.wiki\\}", metadata.getProperty("project.wiki"));
		
		System.out.println(help);
		return null;
	}
	
	private ErrorCode commandHelp() {
		return null;
	}
	
	public HelpCommand withCommand(String command) {
		this.command = command;
		return this;
	}
	
	public String getCommand() {
		return this.command;
	}
}