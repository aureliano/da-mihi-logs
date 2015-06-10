package com.github.aureliano.damihilogs.converter.input;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.config.input.FileTailerInputConfig;
import com.github.aureliano.damihilogs.config.input.InputConfigTypes;
import com.github.aureliano.damihilogs.exception.DefaultExceptionHandler;
import com.github.aureliano.damihilogs.listener.DefaultDataReadingListener;
import com.github.aureliano.damihilogs.listener.DefaultExecutionListener;
import com.github.aureliano.damihilogs.matcher.SingleLineMatcher;

public class FileTailerInputConverterTest {

	@Test
	public void testConvert() {
		Map<String, Object> data = new HashMap<String, Object>();
		Properties metadata = new Properties();
		
		metadata.setProperty("test", "test");
		metadata.setProperty("goal", "CAM");
		
		data.put("id", "test-123");
		data.put("matcher", SingleLineMatcher.class.getName());
		data.put("exceptionHandlers", Arrays.asList(DefaultExceptionHandler.class.getName(), DefaultExceptionHandler.class.getName()));
		data.put("dataReadingListeners", Arrays.asList(DefaultDataReadingListener.class.getName()));
		data.put("executionListeners", Arrays.asList(DefaultExecutionListener.class.getName()));
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
}