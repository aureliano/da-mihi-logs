package com.github.aureliano.evtbridge.core.formatter;

import com.github.aureliano.evtbridge.common.helper.StringHelper;

public class PlainTextFormatter implements IOutputFormatter {

	public PlainTextFormatter() {
		super();
	}

	@Override
	public String format(Object data) {
		return StringHelper.toString(data);
	}
}