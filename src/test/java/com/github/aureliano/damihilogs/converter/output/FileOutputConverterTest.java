package com.github.aureliano.damihilogs.converter.output;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.config.output.FileOutputConfig;
import com.github.aureliano.damihilogs.config.output.OutputConfigTypes;
import com.github.aureliano.damihilogs.filter.DefaultEmptyFilter;
import com.github.aureliano.damihilogs.formatter.PlainTextFormatter;
import com.github.aureliano.damihilogs.listener.DefaultDataWritingListener;
import com.github.aureliano.damihilogs.parser.PlainTextParser;

public class FileOutputConverterTest {

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
		
		data.put("file", "src/test/resources");
		data.put("append", true);
		data.put("useBuffer", false);
		data.put("encoding", "ISO-8859-1");
		
		data.put("type", "file");
		
		FileOutputConfig conf = new FileOutputConverter().convert(data);
		Assert.assertEquals(1, conf.getDataWritingListeners().size());
		Assert.assertEquals("test", conf.getMetadata("test"));
		Assert.assertEquals("CAM", conf.getMetadata("goal"));
		
		Assert.assertTrue(conf.getParser() instanceof PlainTextParser);
		Assert.assertTrue(conf.getFilter() instanceof DefaultEmptyFilter);
		Assert.assertTrue(conf.getOutputFormatter() instanceof PlainTextFormatter);
		
		Assert.assertEquals("src/test/resources", conf.getFile().getPath());
		Assert.assertEquals("ISO-8859-1", conf.getEncoding());
		Assert.assertTrue(conf.isAppend());
		Assert.assertFalse(conf.isUseBuffer());
	}
	
	@Test
	public void testId() {
		Assert.assertEquals(OutputConfigTypes.FILE_OUTPUT.name(), new FileOutputConverter().id());
	}
}