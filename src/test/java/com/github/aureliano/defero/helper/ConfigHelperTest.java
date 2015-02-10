package com.github.aureliano.defero.helper;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;

import com.github.aureliano.defero.config.input.InputFileConfig;
import com.github.aureliano.defero.config.output.FileOutputConfig;
import com.github.aureliano.defero.exception.DeferoException;

public class ConfigHelperTest {

	@Test
	public void testInputConfigValidation() {
		try {
			ConfigHelper.inputConfigValidation(null);
		} catch (DeferoException ex) {
			Assert.assertEquals("Input configuration must be provided.", ex.getMessage());
		}
	}
	
	@Test
	public void testOutputConfigValidation() {
		try {
			ConfigHelper.outputConfigValidation(null);
		} catch (DeferoException ex) {
			Assert.assertEquals("Output configuration must be provided.", ex.getMessage());
		}
	}
	
	@Test
	public void testInputFileConfigValidationFile() {
		try {
			ConfigHelper.inputFileConfigValidation(new InputFileConfig());
		} catch (DeferoException ex) {
			Assert.assertEquals("Input file not provided.", ex.getMessage());
		}
		
		try {
			ConfigHelper.inputFileConfigValidation(new InputFileConfig().withFile(new File("/non/existent/file")));
		} catch (DeferoException ex) {
			Assert.assertEquals("Input file '/non/existent/file' does not exist.", ex.getMessage());
		}
		
		try {
			ConfigHelper.inputFileConfigValidation(new InputFileConfig().withFile(new File("src/test/resources")));
		} catch (DeferoException ex) {
			Assert.assertEquals("Input resource 'src/test/resources' is not a file.", ex.getMessage());
		}
		
		ConfigHelper.inputFileConfigValidation(new InputFileConfig().withFile(new File("src/test/resources/empty-file.log")));
		ConfigHelper.inputFileConfigValidation(new InputFileConfig().withFile("src/test/resources/empty-file.log"));
	}
	
	@Test
	public void testInputFileConfigValidationStartPosition() {
		try {
			ConfigHelper.inputFileConfigValidation(new InputFileConfig().withFile("src/test/resources/empty-file.log").withStartPosition(-1));
		} catch (DeferoException ex) {
			Assert.assertEquals("Start position must be greater or equal to zero (>= 0).", ex.getMessage());
		}
		
		ConfigHelper.inputFileConfigValidation(new InputFileConfig().withFile("src/test/resources/empty-file.log").withStartPosition(0));
	}
	
	@Test
	public void testOutputFileConfigValidationFile() {
		try {
			ConfigHelper.outputConfigValidation(new FileOutputConfig());
		} catch (DeferoException ex) {
			Assert.assertEquals("Output file not provided.", ex.getMessage());
		}
		
		try {
			ConfigHelper.outputConfigValidation(new FileOutputConfig().withFile(new File("/non/existent/file")));
		} catch (DeferoException ex) {
			Assert.assertEquals("Output file '/non/existent/file' does not exist.", ex.getMessage());
		}
		
		try {
			ConfigHelper.outputConfigValidation(new FileOutputConfig().withFile(new File("src/test/resources")));
		} catch (DeferoException ex) {
			Assert.assertEquals("Output resource 'src/test/resources' is not a file.", ex.getMessage());
		}
		
		ConfigHelper.outputConfigValidation(new FileOutputConfig().withFile(new File("src/test/resources/empty-file.log")));
		ConfigHelper.outputConfigValidation(new FileOutputConfig().withFile("src/test/resources/empty-file.log"));
	}
}