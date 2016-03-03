package com.github.aureliano.evtbridge.app.command;

import org.reflections.Reflections;

import com.github.aureliano.evtbridge.app.ErrorCode;
import com.github.aureliano.evtbridge.common.helper.StringHelper;
import com.github.aureliano.evtbridge.core.filter.IEventFielter;

public class FilterCommand implements ICommand {

	public FilterCommand() {}

	@Override
	public ErrorCode execute() {
		String message = new StringBuilder()
				.append("Filter classes:")
				.append("\n -")
				.append(this.matchers())
				.append("\n\n")
				.append("Only core filters are listed above. ")
				.append("If you want to use your own filter you can put the whole class name of it in the output configuration file.")
				.toString();
			
			System.out.println(message);
			return null;
	}

	@Override
	public String id() {
		return Commands.PARSER.getId();
	}
	
	private String matchers() {
		Reflections reflections = new Reflections("com.github.aureliano.evtbridge.core.filter");
		Object[] classes = reflections.getSubTypesOf(IEventFielter.class).toArray();
		
		return StringHelper.join(classes, "\n - ").replaceAll("\\s?class\\s", " ");
	}
}