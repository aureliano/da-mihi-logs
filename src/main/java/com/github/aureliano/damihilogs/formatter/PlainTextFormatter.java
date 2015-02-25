package com.github.aureliano.defero.formatter;

public class PlainTextFormatter implements IOutputFormatter {

	public PlainTextFormatter() {
		super();
	}

	@Override
	public String format(Object data) {
		return (data == null) ? "" : data.toString();
	}
}