package com.github.aureliano.damihilogs.converter.input;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.config.input.ConnectionSchema;
import com.github.aureliano.damihilogs.config.input.InputConfigTypes;
import com.github.aureliano.damihilogs.config.input.UrlInputConfig;

public class UrlInputConverterTest {

	@Test
	public void testConvert() {
		Map<String, Object> data = new HashMap<String, Object>();
		Properties metadata = new Properties();
		Map<String, Object> decompress = new HashMap<String, Object>();
		
		metadata.setProperty("test", "test");
		metadata.setProperty("goal", "CAM");
		
		decompress.put("type", "zip");
		decompress.put("inputFilePath", "path/to/zip");
		decompress.put("outputFilePath", "path/to/extraction");
		data.put("decompressFile", decompress);

		data.put("id", "test-123");
		data.put("metadata", metadata);
		data.put("connectionSchema", "http");
		data.put("host", "localhost");
		data.put("port", "80");
		data.put("path", "test/ok");
		data.put("readTimeOut", 1000);
		data.put("byteOffSet", 45789);
		data.put("outputFile", "src/test/resources");
		data.put("user", "usr");
		data.put("password", "pwd");
		
		data.put("type", InputConfigTypes.URL);
		
		UrlInputConfig conf = new UrlInputConverter().convert(data);
		
		Assert.assertEquals("test", conf.getMetadata("test"));
		Assert.assertEquals("CAM", conf.getMetadata("goal"));

		Assert.assertEquals("test-123", conf.getConfigurationId());
		Assert.assertEquals(ConnectionSchema.HTTP, conf.getConnectionSchema());
		Assert.assertEquals("localhost", conf.getHost());
		Assert.assertEquals(80, conf.getPort());
		Assert.assertEquals("test/ok", conf.getPath());
		Assert.assertEquals(new Long(1000), conf.getReadTimeout());
		Assert.assertEquals(new Integer(45789), conf.getByteOffSet());
		Assert.assertEquals("src/test/resources", conf.getOutputFile().getPath());
		Assert.assertEquals("usr", conf.getUser());
		Assert.assertEquals("pwd", conf.getPassword());
	}
	
	@Test
	public void testId() {
		Assert.assertEquals(InputConfigTypes.URL.name(), new UrlInputConverter().id());
	}
}