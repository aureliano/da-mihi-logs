package com.github.aureliano.evtbridge.core.matcher;

import java.util.regex.Pattern;

import com.github.aureliano.evtbridge.common.exception.EventBridgeException;
import com.github.aureliano.evtbridge.common.helper.StringHelper;

public class MultiLineMatcher implements IMatcher {

	private String prefixRegex;
	private String midleRegex;
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
	
	public MultiLineMatcher(String prefixRegex, String midleRegex, int maxMatchAttempts) {
		this.prefixRegex = prefixRegex;
		this.midleRegex = midleRegex;
		
		if (this.midleRegex == null) {
			this.midleRegex = "";
		}
		
		if (StringHelper.isEmpty(this.prefixRegex)) {
			throw new EventBridgeException("Regular expression must be passed to " + this.getClass().getSimpleName());
		}
		
		this.suffixRegex = String.format("([\\W\\w\\d](?!%s))+", this.prefixRegex);
		this.pattern = Pattern.compile(this.prefixRegex + this.midleRegex);
		this.matchAttemptsCounter = 0;
		this.maxMatchAttempts = maxMatchAttempts;
	}

	@Override
	public boolean partialMatch(String text) {
		if (this.matchAttemptsCounter >= this.maxMatchAttempts) {
			throw new EventBridgeException("Max parse attempts overflow (" + this.matchAttemptsCounter + " >= " + this.maxMatchAttempts + ").");
		}
		
		this.matchAttemptsCounter++;
		return this.pattern.matcher(text).find();
	}

	@Override
	public boolean matches(String text) {
		boolean matches = text.matches(this.prefixRegex + this.midleRegex + this.suffixRegex);
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