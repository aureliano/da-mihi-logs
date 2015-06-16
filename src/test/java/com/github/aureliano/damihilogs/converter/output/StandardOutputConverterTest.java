package com.github.aureliano.damihilogs.converter.output;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.config.output.OutputConfigTypes;
import com.github.aureliano.damihilogs.config.output.StandardOutputConfig;
import com.github.aureliano.damihilogs.filter.DefaultEmptyFilter;
import com.github.aureliano.damihilogs.formatter.PlainTextFormatter;
import com.github.aureliano.damihilogs.listener.DefaultDataWritingListener;
import com.github.aureliano.damihilogs.parser.PlainTextParser;

public class StandardOutputConverterTest {

	@Test
	public void testConvert() {
		Map<String, Object> data = new HashMap<String, Object>();
		Properties metadata = new Properties();
		
		metadata.setProperty("test", "test");
		metadata.setProperty("goal", "CAM");
		
		data.put("dataWritingListeners", Arrays.asList(DefaultDataWritingListener.class.getName()));
		data.put("metadata", metadata);
		
		data.put("parser", PlainTextParser.class.getName());
		data.put("filter", DefaultEmptyFilter.class.getName());
		data.put("formatter", PlainTextFormatter.class.getName());
		
		data.put("type", OutputConfigTypes.STANDARD_OUTPUT);
		
		StandardOutputConfig conf = new StandardOutputConverter().convert(data);
		Assert.assertEquals(1, conf.getDataWritingListeners().size());
		Assert.assertEquals("test", conf.getMetadata("test"));
		Assert.assertEquals("CAM", conf.getMetadata("goal"));
		
		Assert.assertTrue(conf.getParser() instanceof PlainTextParser);
		Assert.assertTrue(conf.getFilter() instanceof DefaultEmptyFilter);
		Assert.assertTrue(conf.getOutputFormatter() instanceof PlainTextFormatter);
	}
	
	@Test
	public void testId() {
		Assert.assertEquals(OutputConfigTypes.STANDARD_OUTPUT.name(), new StandardOutputConverter().id());
	}
}