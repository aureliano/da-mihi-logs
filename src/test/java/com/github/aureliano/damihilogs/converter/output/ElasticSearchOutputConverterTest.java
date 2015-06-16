package com.github.aureliano.damihilogs.converter.output;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.config.output.ElasticSearchOutputConfig;
import com.github.aureliano.damihilogs.config.output.OutputConfigTypes;
import com.github.aureliano.damihilogs.filter.DefaultEmptyFilter;
import com.github.aureliano.damihilogs.formatter.PlainTextFormatter;
import com.github.aureliano.damihilogs.listener.DefaultDataWritingListener;
import com.github.aureliano.damihilogs.parser.PlainTextParser;

public class ElasticSearchOutputConverterTest {

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
		
		data.put("host", "127.0.0.1");
		data.put("port", 9200);
		data.put("printElasticSearchLog", true);
		data.put("index", "my-index");
		data.put("mappingType", "new-type");
		
		data.put("type", OutputConfigTypes.ELASTIC_SEARCH);
		
		ElasticSearchOutputConfig conf = new ElasticSearchOutputConverter().convert(data);
		Assert.assertEquals(1, conf.getDataWritingListeners().size());
		Assert.assertEquals("test", conf.getMetadata("test"));
		Assert.assertEquals("CAM", conf.getMetadata("goal"));
		
		Assert.assertTrue(conf.getParser() instanceof PlainTextParser);
		Assert.assertTrue(conf.getFilter() instanceof DefaultEmptyFilter);
		Assert.assertTrue(conf.getOutputFormatter() instanceof PlainTextFormatter);
		
		Assert.assertEquals("127.0.0.1", conf.getHost());
		Assert.assertEquals(9200, conf.getPort());
		Assert.assertTrue(conf.isPrintElasticSearchLog());
		Assert.assertEquals("my-index", conf.getIndex());
		Assert.assertEquals("new-type", conf.getMappingType());
	}
	
	@Test
	public void testId() {
		Assert.assertEquals(OutputConfigTypes.ELASTIC_SEARCH.name(), new ElasticSearchOutputConverter().id());
	}
}