package com.github.aureliano.defero.helper;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;

import com.github.aureliano.defero.config.input.InputFileConfig;
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
	public void testInputConfigValidationFile() {
		try {
			ConfigHelper.inputConfigValidation(new InputFileConfig());
		} catch (DeferoException ex) {
			Assert.assertEquals("Input file not provided.", ex.getMessage());
		}
		
		try {
			ConfigHelper.inputConfigValidation(new InputFileConfig().withFile(new File("/non/existent/file")));
		} catch (DeferoException ex) {
			Assert.assertEquals("Input file '/non/existent/file' does not exist.", ex.getMessage());
		}
		
		try {
			ConfigHelper.inputConfigValidation(new InputFileConfig().withFile(new File("src/test/resources")));
		} catch (DeferoException ex) {
			Assert.assertEquals("Input resource 'src/test/resources' is not a file.", ex.getMessage());
		}
		
		ConfigHelper.inputConfigValidation(new InputFileConfig().withFile(new File("src/test/resources/empty-file.log")));
		ConfigHelper.inputConfigValidation(new InputFileConfig().withFile("src/test/resources/empty-file.log"));
	}
	
	@Test
	public void testInputConfigValidationStartPosition() {
		try {
			ConfigHelper.inputConfigValidation(new InputFileConfig().withFile("src/test/resources/empty-file.log").withStartPosition(-1));
		} catch (DeferoException ex) {
			Assert.assertEquals("Start position must be greater or equal to zero (>= 0).", ex.getMessage());
		}
		
		ConfigHelper.inputConfigValidation(new InputFileConfig().withFile("src/test/resources/empty-file.log").withStartPosition(0));
	}
}