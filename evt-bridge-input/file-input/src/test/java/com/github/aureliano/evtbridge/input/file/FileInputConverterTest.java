package com.github.aureliano.evtbridge.input.file;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.evtbridge.core.config.InputConfigTypes;
import com.github.aureliano.evtbridge.core.matcher.SingleLineMatcher;

public class FileInputConverterTest {

	@Test
	public void testConvert() {
		Map<String, Object> data = new HashMap<String, Object>();
		Properties metadata = new Properties();
		Map<String, Object> decompress = new HashMap<String, Object>();
		
		metadata.setProperty("test", "test");
		metadata.setProperty("goal", "CAM");
		
		data.put("id", "test-123");
		data.put("matcher", SingleLineMatcher.class.getName());
		data.put("exceptionHandlers", Arrays.asList(InputFileExceptionHandler.class.getName(), InputFileExceptionHandler.class.getName()));
		data.put("dataReadingListeners", Arrays.asList(InputFileDataReadingListener.class.getName()));
		data.put("executionListeners", Arrays.asList(InputFileExecutionListener.class.getName()));
		data.put("metadata", metadata);
		
		data.put("file", "src/test/resources");
		data.put("startPosition", 15);
		data.put("encoding", "ISO-8859-1");
		data.put("tailFile", true);
		data.put("tailDelay", 50);
		data.put("tailInterval", 100);
		
		decompress.put("type", "zip");
		decompress.put("inputFilePath", "path/to/zip");
		decompress.put("outputFilePath", "path/to/extraction");
		data.put("decompressFile", decompress);
		
		data.put("type", "file");
		
		FileInputConfig conf = new FileInputConverter().convert(data);
		Assert.assertTrue(conf.getMatcher() instanceof SingleLineMatcher);
		Assert.assertEquals(2, conf.getExceptionHandlers().size());
		Assert.assertEquals(1, conf.getDataReadingListeners().size());
		Assert.assertEquals(1, conf.getExecutionListeners().size());
		Assert.assertEquals("test", conf.getMetadata("test"));
		Assert.assertEquals("CAM", conf.getMetadata("goal"));
		
		Assert.assertEquals("test-123", conf.getConfigurationId());
		Assert.assertEquals("src/test/resources", conf.getFile().getPath());
		Assert.assertEquals(new Integer(15), conf.getStartPosition());
		Assert.assertEquals("ISO-8859-1", conf.getEncoding());
	}
	
	@Test
	public void testId() {
		Assert.assertEquals(InputConfigTypes.FILE_INPUT.name(), new FileInputConverter().id());
	}
}