package com.github.aureliano.evtbridge.input.file_tailer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.evtbridge.core.config.InputConfigTypes;
import com.github.aureliano.evtbridge.core.matcher.SingleLineMatcher;

public class FileTailerInputConverterTest {

	@Test
	public void testConvert() {
		Map<String, Object> data = new HashMap<String, Object>();
		Properties metadata = new Properties();
		
		metadata.setProperty("test", "test");
		metadata.setProperty("goal", "CAM");
		
		data.put("configurationId", "test-123");
		data.put("matcher", SingleLineMatcher.class.getName());
		data.put("exceptionHandlers", Arrays.asList(InputFileTailerExceptionHandler.class.getName(), InputFileTailerExceptionHandler.class.getName()));
		data.put("dataReadingListeners", Arrays.asList(InputFileTailerDataReadingListener.class.getName()));
		data.put("executionListeners", Arrays.asList(InputFileTailerExecutionListener.class.getName()));
		data.put("metadata", metadata);
		
		data.put("file", "src/test/resources");
		data.put("encoding", "ISO-8859-1");
		data.put("tailDelay", 50);
		data.put("tailInterval", 100);
		
		data.put("type", InputConfigTypes.FILE_TAILER);
		
		FileTailerInputConfig conf = new FileTailerInputConverter().convert(data);
		Assert.assertTrue(conf.getMatcher() instanceof SingleLineMatcher);
		Assert.assertEquals(2, conf.getExceptionHandlers().size());
		Assert.assertEquals(1, conf.getDataReadingListeners().size());
		Assert.assertEquals(1, conf.getExecutionListeners().size());
		Assert.assertEquals("test", conf.getMetadata("test"));
		Assert.assertEquals("CAM", conf.getMetadata("goal"));
		
		Assert.assertEquals("test-123", conf.getConfigurationId());
		Assert.assertEquals("src/test/resources", conf.getFile().getPath());
		Assert.assertEquals("ISO-8859-1", conf.getEncoding());
		Assert.assertEquals(new Long(50), conf.getTailDelay());
		Assert.assertEquals(new Long(100), conf.getTailInterval());
	}
	
	@Test
	public void testId() {
		Assert.assertEquals(InputConfigTypes.FILE_TAILER.name(), new FileTailerInputConverter().id());
	}
}