package com.github.aureliano.evtbridge.output.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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

public class FileOutputConverterTest {

	@Test
	public void testConvert() {
		Map<String, Object> data = new HashMap<String, Object>();
		Properties metadata = new Properties();
		
		metadata.setProperty("test", "test");
		metadata.setProperty("goal", "CAM");
		
		data.put("dataWritingListeners", Arrays.asList(FileOutputDataWritingListener.class.getName()));
		data.put("metadata", metadata);
		
		data.put("parser", PlainTextParser.class.getName());
		data.put("filter", EmptyFilter.class.getName());
		data.put("formatter", PlainTextFormatter.class.getName());
		
		data.put("file", "src/test/resources");
		data.put("append", true);
		data.put("useBuffer", false);
		data.put("encoding", "ISO-8859-1");
		
		data.put("type", "file");
		
		FileOutputConfig conf = new FileOutputConverter().convert(data);
		assertEquals(1, conf.getDataWritingListeners().size());
		assertEquals("test", conf.getMetadata("test"));
		assertEquals("CAM", conf.getMetadata("goal"));
		
		assertTrue(conf.getParser() instanceof PlainTextParser);
		assertTrue(conf.getFilter() instanceof EmptyFilter);
		assertTrue(conf.getOutputFormatter() instanceof PlainTextFormatter);
		
		assertEquals("src/test/resources", conf.getFile().getPath());
		assertEquals("ISO-8859-1", conf.getEncoding());
		assertTrue(conf.isAppend());
		assertFalse(conf.isUseBuffer());
	}
	
	@Test
	public void testId() {
		assertEquals(OutputConfigTypes.FILE_OUTPUT.name(), new FileOutputConverter().id());
	}
}