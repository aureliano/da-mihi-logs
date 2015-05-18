package com.github.aureliano.damihilogs.converter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.config.output.ElasticSearchOutputConfig;
import com.github.aureliano.damihilogs.config.output.FileOutputConfig;
import com.github.aureliano.damihilogs.config.output.StandardOutputConfig;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.filter.DefaultEmptyFilter;
import com.github.aureliano.damihilogs.formatter.PlainTextFormatter;
import com.github.aureliano.damihilogs.listener.DefaultDataWritingListener;
import com.github.aureliano.damihilogs.parser.PlainTextParser;

public class OutputConfigConverterTest {

	private OutputConfigConverter converter;
	
	public OutputConfigConverterTest() {
		this.converter = new OutputConfigConverter();
	}
	
	@Test
	public void testCreateInputError() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", "invalid");
		
		try {
			this.converter.convert(map);
			Assert.fail("An exception was expected.");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Output config type 'invalid' not supported. Expected one of: " + Arrays.toString(OutputConfigConverter.OUTPUT_CONFIG_TYPES), ex.getMessage());
		}
	}
	
	@Test
	public void testCreateOutputFile() {
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> properties = new HashMap<String, Object>();
		Properties metadata = new Properties();
		
		metadata.setProperty("test", "test");
		metadata.setProperty("goal", "CAM");
		
		properties.put("dataWritingListeners", new String[] { DefaultDataWritingListener.class.getName() });
		properties.put("metadata", metadata);
		
		properties.put("parser", PlainTextParser.class.getName());
		properties.put("filter", DefaultEmptyFilter.class.getName());
		properties.put("formatter", PlainTextFormatter.class.getName());
		
		properties.put("file", "src/test/resources");
		properties.put("append", true);
		properties.put("encoding", "ISO-8859-1");
		
		data.put("type", "file");
		data.put("properties", properties);
		
		FileOutputConfig conf = (FileOutputConfig) this.converter.convert(data);
		Assert.assertEquals(1, conf.getDataWritingListeners().size());
		Assert.assertEquals("test", conf.getMetadata("test"));
		Assert.assertEquals("CAM", conf.getMetadata("goal"));
		
		Assert.assertTrue(conf.getParser() instanceof PlainTextParser);
		Assert.assertTrue(conf.getFilter() instanceof DefaultEmptyFilter);
		Assert.assertTrue(conf.getOutputFormatter() instanceof PlainTextFormatter);
		
		Assert.assertEquals("src/test/resources", conf.getFile().getPath());
		Assert.assertEquals("ISO-8859-1", conf.getEncoding());
		Assert.assertTrue(conf.isAppend());
	}
	
	@Test
	public void testCreateOutputES() {
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> properties = new HashMap<String, Object>();
		Properties metadata = new Properties();
		
		metadata.setProperty("test", "test");
		metadata.setProperty("goal", "CAM");
		
		properties.put("dataWritingListeners", new String[] { DefaultDataWritingListener.class.getName() });
		properties.put("metadata", metadata);
		
		properties.put("parser", PlainTextParser.class.getName());
		properties.put("filter", DefaultEmptyFilter.class.getName());
		properties.put("formatter", PlainTextFormatter.class.getName());
		
		properties.put("host", "127.0.0.1");
		properties.put("port", 9200);
		properties.put("printElasticSearchLog", true);
		properties.put("index", "my-index");
		properties.put("type", "new-type");
		
		data.put("type", "elasticSearch");
		data.put("properties", properties);
		
		ElasticSearchOutputConfig conf = (ElasticSearchOutputConfig) this.converter.convert(data);
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
	public void testCreateOutputStandard() {
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> properties = new HashMap<String, Object>();
		Properties metadata = new Properties();
		
		metadata.setProperty("test", "test");
		metadata.setProperty("goal", "CAM");
		
		properties.put("dataWritingListeners", new String[] { DefaultDataWritingListener.class.getName() });
		properties.put("metadata", metadata);
		
		properties.put("parser", PlainTextParser.class.getName());
		properties.put("filter", DefaultEmptyFilter.class.getName());
		properties.put("formatter", PlainTextFormatter.class.getName());
		
		data.put("type", "standard");
		data.put("properties", properties);
		
		StandardOutputConfig conf = (StandardOutputConfig) this.converter.convert(data);
		Assert.assertEquals(1, conf.getDataWritingListeners().size());
		Assert.assertEquals("test", conf.getMetadata("test"));
		Assert.assertEquals("CAM", conf.getMetadata("goal"));
		
		Assert.assertTrue(conf.getParser() instanceof PlainTextParser);
		Assert.assertTrue(conf.getFilter() instanceof DefaultEmptyFilter);
		Assert.assertTrue(conf.getOutputFormatter() instanceof PlainTextFormatter);
	}
}