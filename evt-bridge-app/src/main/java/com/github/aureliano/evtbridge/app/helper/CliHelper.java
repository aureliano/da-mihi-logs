package com.github.aureliano.evtbridge.app.helper;

import java.util.ArrayList;
import java.util.List;

import com.github.aureliano.evtbridge.common.helper.StringHelper;
import com.github.aureliano.evtbridge.converter.ConfigurationSourceType;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

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
	
	public static OptionSet buildOptionSet(String[] args) {
		OptionParser parser = new OptionParser();
		
		parser.accepts("help", "Show this message");
		parser.accepts("version", "Show project version");
		
		return parser.parse(args);
	}
}