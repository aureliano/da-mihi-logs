package com.github.aureliano.evtbridge.core.helper;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.github.aureliano.evtbridge.core.config.EventCollectorConfiguration;
import com.github.aureliano.evtbridge.core.model.CustomInputConfig;

import junit.framework.Assert;

public class ConfigHelperTest {

	@Test
	public void testCopyMetadata() {
		EventCollectorConfiguration c = new EventCollectorConfiguration();
		c.putMetadata("fruit", "orange");
		c.putMetadata("collor", "blue");
		
		CustomInputConfig s = new CustomInputConfig();
		s.putMetadata("fruit", "coconut");
		s.putMetadata("idiom", "Portuguese");
		
		ConfigHelper.copyMetadata(c, s);
		
		Assert.assertEquals("orange", s.getMetadata("fruit"));
		Assert.assertEquals("blue", s.getMetadata("collor"));
		Assert.assertEquals("Portuguese", s.getMetadata("idiom"));
	}
	
	@Test
	public void testNewUniqueConfigurationName() {
		Set<String> names = new HashSet<String>();
		for (byte i = 0; i < 85; i++) {
			Assert.assertTrue(names.add(ConfigHelper.newUniqueConfigurationName()));
		}
		
		Assert.assertTrue(names.size() == 85);
		ConfigHelper.resetExecutorNamesMap();
	}
}