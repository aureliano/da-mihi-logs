package com.github.aureliano.damihilogs.converter.output;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;

import com.github.aureliano.damihilogs.config.output.ElasticSearchOutputConfig;
import com.github.aureliano.damihilogs.config.output.OutputConfigTypes;
import com.github.aureliano.damihilogs.filter.DefaultEmptyFilter;
import com.github.aureliano.damihilogs.formatter.PlainTextFormatter;
import com.github.aureliano.damihilogs.listener.DefaultDataWritingListener;
import com.github.aureliano.damihilogs.parser.PlainTextParser;
import com.github.aureliano.evtbridge.core.filter.EmptyFilter;

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
		assertEquals(1, conf.getDataWritingListeners().size());
		assertEquals("test", conf.getMetadata("test"));
		assertEquals("CAM", conf.getMetadata("goal"));
		
		assertTrue(conf.getParser() instanceof PlainTextParser);
		assertTrue(conf.getFilter() instanceof EmptyFilter);
		assertTrue(conf.getOutputFormatter() instanceof PlainTextFormatter);
		
		assertEquals("127.0.0.1", conf.getHost());
		assertEquals(9200, conf.getPort());
		assertTrue(conf.isPrintElasticSearchLog());
		assertEquals("my-index", conf.getIndex());
		assertEquals("new-type", conf.getMappingType());
	}
	
	@Test
	public void testId() {
		assertEquals(OutputConfigTypes.ELASTIC_SEARCH.name(), new ElasticSearchOutputConverter().id());
	}
}