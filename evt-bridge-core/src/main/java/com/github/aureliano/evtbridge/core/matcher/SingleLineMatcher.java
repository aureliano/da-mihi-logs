package com.github.aureliano.evtbridge.core.matcher;

import java.util.regex.Pattern;

import com.github.aureliano.evtbridge.common.helper.StringHelper;

public class SingleLineMatcher implements IMatcher {

	private String prefixRegex;
	private String suffixRegex;
	
	private Pattern pattern;
	
	public SingleLineMatcher() {
		this(null);
	}
	
	public SingleLineMatcher(String prefixRegex) {
		this.prefixRegex = prefixRegex;
		this.suffixRegex = ".+$";
		
		if (StringHelper.isEmpty(this.prefixRegex)) {
			this.prefixRegex = "^(.+)";
			this.suffixRegex = "$";
		}
		
		this.pattern = Pattern.compile(this.prefixRegex);
	}

	@Override
	public boolean partialMatch(String text) {
		return this.pattern.matcher(text).find();
	}

	@Override
	public boolean matches(String text) {
		return text.matches(this.prefixRegex + this.suffixRegex);
	}

	@Override
	public String endMatch(String text) {
		return this.matches(text) ? text : null;
	}

	@Override
	public boolean isMultiLine() {
		return false;
	}
}