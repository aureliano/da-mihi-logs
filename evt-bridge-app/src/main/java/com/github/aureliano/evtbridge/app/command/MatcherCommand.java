package com.github.aureliano.evtbridge.app.command;

import org.reflections.Reflections;

import com.github.aureliano.evtbridge.app.ErrorCode;
import com.github.aureliano.evtbridge.common.helper.StringHelper;
import com.github.aureliano.evtbridge.core.matcher.IMatcher;

public class MatcherCommand implements ICommand {

	public MatcherCommand() {}

	@Override
	public ErrorCode execute() {
		String message = new StringBuilder()
				.append("Matcher classes:")
				.append("\n -")
				.append(this.matchers())
				.append("\n\n")
				.append("Only core matchers are listed above. ")
				.append("If you want to use your own matcher you can put the whole class name of it in the input configuration file.")
				.toString();
			
			System.out.println(message);
			return null;
	}

	@Override
	public String id() {
		return Commands.MATCHER.getId();
	}
	
	private String matchers() {
		Reflections reflections = new Reflections("com.github.aureliano.evtbridge.core.matcher");
		Object[] classes = reflections.getSubTypesOf(IMatcher.class).toArray();
		
		return StringHelper.join(classes, "\n - ").replaceAll("\\s?class\\s", " ");
	}
}