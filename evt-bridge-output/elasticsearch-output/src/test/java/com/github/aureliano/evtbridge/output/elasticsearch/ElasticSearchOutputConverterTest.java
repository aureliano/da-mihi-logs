package com.github.aureliano.evtbridge.output.elasticsearch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;

import com.github.aureliano.evtbridge.core.config.OutputConfigTypes;
import com.github.aureliano.evtbridge.core.filter.EmptyFilter;
import com.github.aureliano.evtbridge.core.formatter.PlainTextFormatter;
import com.github.aureliano.evtbridge.core.parser.PlainTextParser;

public class ElasticSearchOutputConverterTest {

	@Test
	public void testConvert() {
		Map<String, Object> data = new HashMap<String, Object>();
		Properties metadata = new Properties();
		
		metadata.setProperty("test", "test");
		metadata.setProperty("goal", "CAM");
		
		data.put("dataWritingListeners", Arrays.asList(ESOutputDataWritingListener.class.getName()));
		data.put("metadata", metadata);
		
		data.put("parser", PlainTextParser.class.getName());
		data.put("filter", EmptyFilter.class.getName());
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