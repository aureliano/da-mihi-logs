package com.github.aureliano.damihilogs.converter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.config.input.ConnectionSchema;
import com.github.aureliano.damihilogs.config.input.ExternalCommandInput;
import com.github.aureliano.damihilogs.config.input.InputFileConfig;
import com.github.aureliano.damihilogs.config.input.UrlInputConfig;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.exception.DefaultExceptionHandler;
import com.github.aureliano.damihilogs.inout.SupportedCompressionType;
import com.github.aureliano.damihilogs.listener.DefaultDataReadingListener;
import com.github.aureliano.damihilogs.listener.DefaultExecutionListener;
import com.github.aureliano.damihilogs.matcher.SingleLineMatcher;

public class InputConfigConverterTest {

	private InputConfigConverter converter;
	
	public InputConfigConverterTest() {
		this.converter = new InputConfigConverter();
	}
	
	@Test
	public void testCreateInputError() {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("type", "invalid");
		map.put("input", data);
		
		try {
			this.converter.convert(map);
			Assert.fail("An exception was expected.");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Input config type 'invalid' not supported. Expected one of: " + Arrays.toString(InputConfigConverter.INPUT_CONFIG_TYPES), ex.getMessage());
		}
	}
	
	@Test
	public void testCreateInputFile() {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> properties = new HashMap<String, Object>();
		Properties metadata = new Properties();
		Map<String, Object> decompress = new HashMap<String, Object>();
		
		metadata.setProperty("test", "test");
		metadata.setProperty("goal", "CAM");
		
		properties.put("matcher", SingleLineMatcher.class.getName());
		properties.put("exceptionHandlers", Arrays.asList(DefaultExceptionHandler.class.getName(), DefaultExceptionHandler.class.getName()));
		properties.put("dataReadingListeners", Arrays.asList(DefaultDataReadingListener.class.getName()));
		properties.put("executionListeners", Arrays.asList(DefaultExecutionListener.class.getName()));
		properties.put("metadata", metadata);
		
		properties.put("file", "src/test/resources");
		properties.put("startPosition", 15);
		properties.put("encoding", "ISO-8859-1");
		properties.put("tailFile", true);
		properties.put("tailDelay", 50);
		properties.put("tailInterval", 100);
		
		decompress.put("type", "zip");
		decompress.put("inputFilePath", "path/to/zip");
		decompress.put("outputFilePath", "path/to/extraction");
		properties.put("decompressFile", decompress);
		
		data.put("type", "file");
		data.put("properties", properties);
		map.put("input", data);
		
		InputFileConfig conf = (InputFileConfig) this.converter.convert(map);
		Assert.assertTrue(conf.getMatcher() instanceof SingleLineMatcher);
		Assert.assertEquals(2, conf.getExceptionHandlers().size());
		Assert.assertEquals(1, conf.getDataReadingListeners().size());
		Assert.assertEquals(1, conf.getExecutionListeners().size());
		Assert.assertEquals("test", conf.getMetadata("test"));
		Assert.assertEquals("CAM", conf.getMetadata("goal"));
		
		Assert.assertEquals("src/test/resources", conf.getFile().getPath());
		Assert.assertEquals(new Integer(15), conf.getStartPosition());
		Assert.assertEquals("ISO-8859-1", conf.getEncoding());
		Assert.assertTrue(conf.isTailFile());
		Assert.assertEquals(new Long(50), conf.getTailDelay());
		Assert.assertEquals(new Long(100), conf.getTailInterval());
		Assert.assertEquals(SupportedCompressionType.ZIP, conf.getDecompressFileConfiguration().getCompressionType());
		Assert.assertEquals("path/to/zip", conf.getDecompressFileConfiguration().getInputFilePath());
		Assert.assertEquals("path/to/extraction", conf.getDecompressFileConfiguration().getOutputFilePath());
	}
	
	@Test
	public void testCreateExternalCommand() {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> properties = new HashMap<String, Object>();
		
		properties.put("command", "ls");
		properties.put("parameters", Arrays.asList("-la"));
		
		data.put("type", "externalCommand");
		data.put("properties", properties);
		map.put("input", data);
		
		ExternalCommandInput conf = (ExternalCommandInput) this.converter.convert(map);
		Assert.assertEquals("ls", conf.getCommand());
		Assert.assertEquals(Arrays.asList("-la"), conf.getParameters());
	}
	
	@Test
	public void testCreateUrl() {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> properties = new HashMap<String, Object>();
		Properties metadata = new Properties();
		Map<String, Object> decompress = new HashMap<String, Object>();
		
		metadata.setProperty("test", "test");
		metadata.setProperty("goal", "CAM");
		
		decompress.put("type", "zip");
		decompress.put("inputFilePath", "path/to/zip");
		decompress.put("outputFilePath", "path/to/extraction");
		properties.put("decompressFile", decompress);
		
		properties.put("metadata", metadata);
		properties.put("connectionSchema", "http");
		properties.put("host", "localhost");
		properties.put("port", "80");
		properties.put("path", "test/ok");
		properties.put("readTimeOut", 1000);
		properties.put("byteOffSet", 45789);
		properties.put("outputFile", "src/test/resources");
		properties.put("user", "usr");
		properties.put("password", "pwd");
		
		data.put("type", "url");
		data.put("properties", properties);
		map.put("input", data);
		
		UrlInputConfig conf = (UrlInputConfig) this.converter.convert(map);
		
		Assert.assertEquals("test", conf.getMetadata("test"));
		Assert.assertEquals("CAM", conf.getMetadata("goal"));
		
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
}