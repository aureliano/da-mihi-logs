package com.github.aureliano.damihilogs.helper;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

import com.github.aureliano.damihilogs.config.EventCollectorConfiguration;
import com.github.aureliano.damihilogs.config.input.ConnectionSchema;
import com.github.aureliano.damihilogs.config.input.ExternalCommandInput;
import com.github.aureliano.damihilogs.config.input.InputFileConfig;
import com.github.aureliano.damihilogs.config.input.StandardInputConfig;
import com.github.aureliano.damihilogs.config.input.UrlInputConfig;
import com.github.aureliano.damihilogs.config.output.FileOutputConfig;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;

public class ConfigHelperTest {
	
	@Test
	public void testCopyMetadata() {
		EventCollectorConfiguration c = new EventCollectorConfiguration();
		c.putMetadata("fruit", "orange");
		c.putMetadata("collor", "blue");
		
		StandardInputConfig s = new StandardInputConfig();
		s.putMetadata("fruit", "coconut");
		s.putMetadata("idiom", "Portuguese");
		
		ConfigHelper.copyMetadata(c, s);
		
		Assert.assertEquals("orange", s.getMetadata("fruit"));
		Assert.assertEquals("blue", s.getMetadata("collor"));
		Assert.assertEquals("Portuguese", s.getMetadata("idiom"));
	}

	@Test
	public void testInputConfigValidation() {
		try {
			ConfigHelper.inputConfigValidation(null);
			Assert.fail("Expected to got an exception");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Input configuration must be provided.", ex.getMessage());
		}
	}
	
	@Test
	public void testOutputConfigValidation() {
		try {
			ConfigHelper.outputConfigValidation(null);
			Assert.fail("Expected to got an exception");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Output configuration must be provided.", ex.getMessage());
		}
	}
	
	@Test
	public void testInputFileConfigValidationFile() {
		try {
			ConfigHelper.inputFileConfigValidation(new InputFileConfig());
			Assert.fail("Expected to got an exception");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Input file not provided.", ex.getMessage());
		}
		
		ConfigHelper.inputFileConfigValidation(new InputFileConfig().withFile(new File("src/test/resources/empty-file.log")).withStartPosition(0));
		ConfigHelper.inputFileConfigValidation(new InputFileConfig().withFile("src/test/resources/empty-file.log").withStartPosition(25));
	}
	
	@Test
	public void testInputFileConfigValidationStartPosition() {
		try {
			ConfigHelper.inputFileConfigValidation(new InputFileConfig().withFile("src/test/resources/empty-file.log").withStartPosition(-1));
			Assert.fail("Expected to got an exception");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Start position must be greater or equal to zero (>= 0).", ex.getMessage());
		}
		
		ConfigHelper.inputFileConfigValidation(new InputFileConfig().withFile("src/test/resources/empty-file.log").withStartPosition(0));
	}
	
	@Test
	public void testOutputFileConfigValidationFile() {
		try {
			ConfigHelper.outputConfigValidation(new FileOutputConfig());
			Assert.fail("Expected to got an exception");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Output file not provided.", ex.getMessage());
		}
		
		ConfigHelper.outputConfigValidation(new FileOutputConfig().withFile(new File("src/test/resources/empty-file.log")));
		ConfigHelper.outputConfigValidation(new FileOutputConfig().withFile("src/test/resources/empty-file.log"));
	}
	
	@Test
	public void testUrlInputConfigValidation() {
		try {
			ConfigHelper.urlInputConfigValidation(new UrlInputConfig().withConnectionSchema(null));
			Assert.fail("Expected to got an exception");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Connection schema not provided.", ex.getMessage());
		}
		
		try {
			ConfigHelper.urlInputConfigValidation(new UrlInputConfig().withConnectionSchema(ConnectionSchema.HTTP));
			Assert.fail("Expected to got an exception");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Host not provided.", ex.getMessage());
		}
		
		try {
			ConfigHelper.urlInputConfigValidation(new UrlInputConfig()
				.withConnectionSchema(ConnectionSchema.HTTP).withHost("localhost"));
			Assert.fail("Expected to got an exception");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Output file not provided.", ex.getMessage());
		}
		
		try {
			ConfigHelper.urlInputConfigValidation(new UrlInputConfig()
				.withConnectionSchema(ConnectionSchema.HTTP).withHost("localhost")
				.withOutputFile("src/test/resources"));
			Assert.fail("Expected to got an exception");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Output file 'src/test/resources' is a directory.", ex.getMessage());
		}
		
		ConfigHelper.urlInputConfigValidation(new UrlInputConfig()
			.withConnectionSchema(ConnectionSchema.HTTP).withHost("localhost")
			.withOutputFile("src/test/resources/empty-file.log"));
	}
	
	@Test
	public void testExternalCommandConfigValidation() {
		try {
			ConfigHelper.externalCommandConfigValidation(new ExternalCommandInput());
			Assert.fail("Expected to got an exception");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Command not provided.", ex.getMessage());
		}
		
		try {
			ConfigHelper.externalCommandConfigValidation(new ExternalCommandInput().withCommand(""));
			Assert.fail("Expected to got an exception");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Command not provided.", ex.getMessage());
		}
		
		ConfigHelper.externalCommandConfigValidation(new ExternalCommandInput().withCommand("ls -la"));
	}
	
	@Test
	public void testNewUniqueConfigurationName() {
		Set<String> names = new HashSet<String>();
		for (byte i = 0; i < 85; i++) {
			Assert.assertTrue(names.add(ConfigHelper.newUniqueConfigurationName()));
		}
		
		Assert.assertTrue(names.size() == 85);
	}
}