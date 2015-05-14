package com.github.aureliano.damihilogs.matcher;

import java.util.regex.Pattern;

import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.helper.StringHelper;

public class MultiLineMatcher implements IMatcher {

	private String prefixRegex;
	private String partialRegex;
	private String suffixRegex;
	
	private int maxMatchAttempts;
	private int matchAttemptsCounter;
	
	private Pattern pattern;
	private String oldEntry;
	
	public MultiLineMatcher(String prefixRegex) {
		this(prefixRegex, null);
	}
	
	public MultiLineMatcher(String prefixRegex, String partialRegex) {
		this(prefixRegex, partialRegex, DEFAULT_MAX_MATCH_ATTEMPTS);
	}
	
	public MultiLineMatcher(String prefixRegex, String partialRegex, int maxMatchAttempts) {
		this.prefixRegex = prefixRegex;
		this.partialRegex = partialRegex;
		
		if (this.partialRegex == null) {
			this.partialRegex = "";
		}
		
		if (StringHelper.isEmpty(this.prefixRegex)) {
			throw new DaMihiLogsException("Regular expression must be passed to " + this.getClass().getSimpleName());
		}
		
		this.suffixRegex = String.format("([\\W\\w\\d](?!%s))+", this.prefixRegex);
		this.pattern = Pattern.compile(this.prefixRegex + this.partialRegex);
		this.matchAttemptsCounter = 0;
		this.maxMatchAttempts = maxMatchAttempts;
	}

	@Override
	public boolean partialMatch(String text) {
		if (this.matchAttemptsCounter >= this.maxMatchAttempts) {
			throw new DaMihiLogsException("Max parse attempts overflow (" + this.matchAttemptsCounter + " >= " + this.maxMatchAttempts + ").");
		}
		
		this.matchAttemptsCounter++;
		return this.pattern.matcher(text).find();
	}

	@Override
	public boolean matches(String text) {
		boolean matches = text.matches(this.prefixRegex + this.partialRegex + this.suffixRegex);
		if (!matches) {
			this.matchAttemptsCounter = 0;
		}
		
		return matches;
	}

	@Override
	public String endMatch(String text) {
		boolean partialMatch = this.partialMatch(text);
		boolean match = this.matches(text);
		
		if (partialMatch && !match) {
			this.matchAttemptsCounter = 0;
			return oldEntry;
		} else {
			this.oldEntry = text;
			return null;
		}
	}

	@Override
	public boolean isMultiLine() {
		return true;
	}
}