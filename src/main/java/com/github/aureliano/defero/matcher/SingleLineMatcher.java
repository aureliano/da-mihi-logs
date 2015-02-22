package com.github.aureliano.defero.matcher;

import java.util.regex.Pattern;

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
		
		if ((this.prefixRegex == null) || (this.prefixRegex.equals(""))) {
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