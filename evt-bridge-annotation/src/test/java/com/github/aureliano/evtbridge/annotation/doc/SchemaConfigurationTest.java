package com.github.aureliano.evtbridge.annotation.doc;

import org.junit.Test;

import com.github.aureliano.evtbridge.annotation.helper.AssertionHelper;
import com.github.aureliano.evtbridge.common.doc.SchemaConfiguration;

public class SchemaConfigurationTest {

	@Test
	public void testCheckAttributes() {
		AssertionHelper.checkAttributes(
			com.github.aureliano.evtbridge.annotation.doc.SchemaConfiguration.class,
			SchemaConfiguration.class
		);
	}
}