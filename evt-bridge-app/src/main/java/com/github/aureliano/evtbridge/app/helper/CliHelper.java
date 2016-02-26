package com.github.aureliano.evtbridge.app.helper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.github.aureliano.evtbridge.app.command.HelpCommand;
import com.github.aureliano.evtbridge.app.command.ICommand;
import com.github.aureliano.evtbridge.app.command.VersionCommand;
import com.github.aureliano.evtbridge.common.helper.StringHelper;
import com.github.aureliano.evtbridge.converter.ConfigurationSourceType;

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
		final String unknownCommand = "Don't know how to handle the command: " + StringHelper.join(args, " ");
		
		OptionParser parser = parseOptions();
		OptionSpec<String> nonOptions = parser.nonOptions().ofType(String.class);
		OptionSet options = parser.parse(args);
		
		List<String> command = nonOptions.values(options);
		
		if ("help".equals(command.get(0))) {
			return help(unknownCommand, command);
		}
		
		if (options.has("help")) {
			return new HelpCommand();
		} else if (options.has("version")) {
			return new VersionCommand();
		}
		
		System.err.println(unknownCommand);
		return null;
	}
	
	protected static OptionParser parseOptions() {
		OptionParser parser = new OptionParser();
		
		parser.accepts("help", "Show this message");
		parser.accepts("version", "Show project version");
		parser.nonOptions().ofType(File.class);
		
		return parser;
	}
	
	protected static ICommand buildCommand(String[] args, OptionSet options) {
		if (options.has("help")) {
			return new HelpCommand();
		} else if (options.has("version")) {
			return new VersionCommand();
		}
		
		System.err.println("Don't know how to handle the command: " + StringHelper.join(args, " "));
		return null;
	}
	
	private static ICommand help(String unknownCommandMessage, List<String> command) {
		if (command.size() > 2) {
			System.err.println(unknownCommandMessage);
			return null;
		}
		return new HelpCommand().withCommand(command.get(1));
	}
}