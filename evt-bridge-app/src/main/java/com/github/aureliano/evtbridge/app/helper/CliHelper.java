package com.github.aureliano.evtbridge.app.helper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.github.aureliano.evtbridge.app.command.Commands;
import com.github.aureliano.evtbridge.app.command.HelpCommand;
import com.github.aureliano.evtbridge.app.command.ICommand;
import com.github.aureliano.evtbridge.app.command.SchemataCommand;
import com.github.aureliano.evtbridge.app.command.VersionCommand;
import com.github.aureliano.evtbridge.common.helper.StringHelper;
import com.github.aureliano.evtbridge.converter.ConfigurationSourceType;
import com.github.aureliano.evtbridge.core.EventBridgeMetadata;

import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

public final class CliHelper {

	private CliHelper() {}
	
	public static String supportedFileTypes() {
		ConfigurationSourceType[] availableTypes = ConfigurationSourceType.values();
		List<String> types = new ArrayList<String>(availableTypes.length);
		
		for (ConfigurationSourceType type : ConfigurationSourceType.values()) {
			types.add(type.name().toLowerCase());
		}
		
		return StringHelper.join(types, ", ");
	}
	
	public static ICommand buildCommand(String[] args) {
		final String unknownCommand = invalidOptionMessage(args);
		
		OptionParser parser = parseOptions();
		OptionSpec<String> nonOptions = parser.nonOptions().ofType(String.class);
		OptionSet options = null;
		
		try {
			options = parser.parse(args);
		} catch (OptionException ex) {
			System.err.println(unknownCommand);
			return null;
		}
		
		List<String> command = nonOptions.values(options);
		ICommand commandExecutor = null;
		
		if (command.isEmpty()) {
			commandExecutor = buildLooseCommands(options);
		} else {
			commandExecutor = buildAppCommand(options, command);
		}
		
		if (commandExecutor == null) {
			System.err.println(unknownCommand);
		}
		
		return commandExecutor;
	}
	
	protected static OptionParser parseOptions() {
		OptionParser parser = new OptionParser();
		
		parser.accepts(Commands.HELP.getId(), "Show this message");
		parser.accepts(Commands.VERSION.getId(), "Show project version");
		parser.accepts(Commands.SCHEMATA.getId(), "List all configuration schema names");
		
		parser.nonOptions().ofType(File.class);
		
		return parser;
	}
	
	protected static ICommand buildAppCommand(OptionSet options, List<String> command) {
		if (Commands.HELP.getId().equals(command.get(0))) {
			return help(command);
		} else if (Commands.VERSION.getId().equals(command.get(0))) {
			return version(command);
		} else if (Commands.SCHEMATA.getId().equals(command.get(0))) {
			return schemata(command);
		}
		
		return null;
	}
	
	private static String invalidOptionMessage(String[] args) {
		return new StringBuilder()
			.append(EventBridgeMetadata.instance().getProperty("app.binary.linux").replaceAll("\\.sh$", ""))
			.append(": invalid option => ")
			.append(StringHelper.join(args, " "))
			.append("\n(-h --help) show valid options")
			.toString();
	}
	
	private static ICommand buildLooseCommands(OptionSet options) {
		if (options.has(Commands.HELP.getId())) {
			return new HelpCommand();
		} else if (options.has(Commands.VERSION.getId())) {
			return new VersionCommand();
		} else if (options.has(Commands.SCHEMATA.getId())) {
			return new SchemataCommand();
		}
		
		return null;
	}
	
	private static ICommand help(List<String> command) {
		if (command.size() > 2) {
			return null;
		} else if (command.size() == 1) {
			return new HelpCommand();
		}
		
		return new HelpCommand().withCommand(command.get(1));
	}
	
	private static ICommand version(List<String> command) {
		if (command.size() > 1) {
			return null;
		}
		
		return new VersionCommand();
	}
	
	private static ICommand schemata(List<String> command) {
		if (command.size() > 1) {
			return null;
		}
		
		return new SchemataCommand();
	}
}