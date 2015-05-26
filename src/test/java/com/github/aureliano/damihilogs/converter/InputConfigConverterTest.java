package com.github.aureliano.damihilogs.converter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.config.input.ConnectionSchema;
import com.github.aureliano.damihilogs.config.input.ExternalCommandInput;
import com.github.aureliano.damihilogs.config.input.FileInputConfig;
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
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("type", "invalid");
		
		try {
			this.converter.convert(data);
			Assert.fail("An exception was expected.");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Input config type 'invalid' not supported. Expected one of: " + Arrays.toString(InputConfigConverter.INPUT_CONFIG_TYPES), ex.getMessage());
		}
	}
	
	@Test
	public void testCreateInputFile() {
		Map<String, Object> data = new HashMap<String, Object>();
		Properties metadata = new Properties();
		Map<String, Object> decompress = new HashMap<String, Object>();
		
		metadata.setProperty("test", "test");
		metadata.setProperty("goal", "CAM");
		
		data.put("id", "test-123");
		data.put("matcher", SingleLineMatcher.class.getName());
		data.put("exceptionHandlers", Arrays.asList(DefaultExceptionHandler.class.getName(), DefaultExceptionHandler.class.getName()));
		data.put("dataReadingListeners", Arrays.asList(DefaultDataReadingListener.class.getName()));
		data.put("executionListeners", Arrays.asList(DefaultExecutionListener.class.getName()));
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
		
		FileInputConfig conf = (FileInputConfig) this.converter.convert(data);
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
		Assert.assertTrue(conf.isTailFile());
		Assert.assertEquals(new Long(50), conf.getTailDelay());
		Assert.assertEquals(new Long(100), conf.getTailInterval());
		Assert.assertEquals(SupportedCompressionType.ZIP, conf.getDecompressFileConfiguration().getCompressionType());
		Assert.assertEquals("path/to/zip", conf.getDecompressFileConfiguration().getInputFilePath());
		Assert.assertEquals("path/to/extraction", conf.getDecompressFileConfiguration().getOutputFilePath());
	}
	
	@Test
	public void testCreateExternalCommand() {
		Map<String, Object> data = new HashMap<String, Object>();

		data.put("id", "test-123");
		data.put("command", "ls");
		data.put("parameters", Arrays.asList("-la"));
		
		data.put("type", "externalCommand");
		
		ExternalCommandInput conf = (ExternalCommandInput) this.converter.convert(data);
		Assert.assertEquals("test-123", conf.getConfigurationId());
		Assert.assertEquals("ls", conf.getCommand());
		Assert.assertEquals(Arrays.asList("-la"), conf.getParameters());
	}
	
	@Test
	public void testCreateUrl() {
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
		
		data.put("type", "url");
		
		UrlInputConfig conf = (UrlInputConfig) this.converter.convert(data);
		
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
}