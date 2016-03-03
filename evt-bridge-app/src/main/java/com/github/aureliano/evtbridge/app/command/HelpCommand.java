package com.github.aureliano.evtbridge.app.command;

import com.github.aureliano.evtbridge.app.ErrorCode;
import com.github.aureliano.evtbridge.common.helper.StringHelper;
import com.github.aureliano.evtbridge.core.EventBridgeMetadata;
import com.github.aureliano.evtbridge.core.doc.DocumentationSourceTypes;
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
		String help = FileHelper.readResource("help/app.help");
		System.out.println(this.replaceProperties(help));
		
		return null;
	}
	
	private ErrorCode commandHelp() {
		if (Commands.SCHEMATA.name().equalsIgnoreCase(this.command)) {
			this.printSchemataHelp();
		} else if (Commands.SCHEMA.name().equalsIgnoreCase(this.command)) {
			this.printSchemaHelp();
		} else if (Commands.MATCHER.name().equalsIgnoreCase(this.command)) {
			this.printMatcherHelp();
		} else if (Commands.PARSER.name().equalsIgnoreCase(this.command)) {
			this.printParserHelp();
		} else if (Commands.FILTER.name().equalsIgnoreCase(this.command)) {
			this.printFilterHelp();
		} else if (Commands.FORMATTER.name().equalsIgnoreCase(this.command)) {
			this.printFormatterHelp();
		}
		
		return null;
	}
	
	private void printSchemataHelp() {
		String help = FileHelper.readResource("help/schemata.help");
		System.out.println(this.replaceProperties(help));
	}
	
	private void printSchemaHelp() {
		String help = FileHelper.readResource("help/schema.help");
		System.out.println(this.replaceProperties(help));
	}
	
	private void printMatcherHelp() {
		String help = FileHelper.readResource("help/matcher.help");
		System.out.println(this.replaceProperties(help));
	}
	
	private void printParserHelp() {
		String help = FileHelper.readResource("help/parser.help");
		System.out.println(this.replaceProperties(help));
	}
	
	private void printFilterHelp() {
		String help = FileHelper.readResource("help/filter.help");
		System.out.println(this.replaceProperties(help));
	}
	
	private void printFormatterHelp() {
		String help = FileHelper.readResource("help/formatter.help");
		System.out.println(this.replaceProperties(help));
	}
	
	private String replaceProperties(String help) {
		EventBridgeMetadata metadata = EventBridgeMetadata.instance();
		return help.replaceAll("\\$\\{app.binary.linux\\}", metadata.getProperty("app.binary.linux"))
			.replaceAll("\\$\\{app.requirement.jvm\\}", metadata.getProperty("app.requirement.jvm"))
			.replaceAll("\\$\\{project.name\\}", metadata.getProperty("project.name"))
			.replaceAll("\\$\\{project.issues\\}", metadata.getProperty("project.issues"))
			.replaceAll("\\$\\{project.wiki\\}", metadata.getProperty("project.wiki"))
			.replaceAll("\\$\\{format.types\\}", this.formatTypes());
	}
	
	private String formatTypes() {
		return StringHelper.join(DocumentationSourceTypes.values(), ", ").toLowerCase();
	}
	
	public HelpCommand withCommand(String command) {
		this.command = command;
		return this;
	}
	
	public String getCommand() {
		return this.command;
	}
}