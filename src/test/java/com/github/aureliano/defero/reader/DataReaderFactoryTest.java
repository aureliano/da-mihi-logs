package com.github.aureliano.defero.reader;

import junit.framework.Assert;

import org.junit.Test;

import com.github.aureliano.defero.config.input.IConfigInput;
import com.github.aureliano.defero.config.input.InputFileConfig;
import com.github.aureliano.defero.config.input.StandardInputConfig;
import com.github.aureliano.defero.config.input.UrlInputConfig;
import com.github.aureliano.defero.exception.DeferoException;

public class DataReaderFactoryTest {

	@Test(expected = DeferoException.class)
	public void testCreateDataReaderNull() {
		DataReaderFactory.createDataReader(null);
	}
	
	@Test(expected = DeferoException.class)
	public void testCreateDataReaderUnsupported() {
		DataReaderFactory.createDataReader(new IConfigInput() {
			public String inputType() {
				return null;
			}
		});
	}
	
	@Test
	public void testCreateDataReader() {
		Assert.assertTrue(DataReaderFactory.createDataReader(new InputFileConfig()) instanceof FileDataReader);
		Assert.assertTrue(DataReaderFactory.createDataReader(new StandardInputConfig()) instanceof StandardDataReader);
		Assert.assertTrue(DataReaderFactory.createDataReader(new UrlInputConfig()) instanceof UrlDataReader);
	}
}