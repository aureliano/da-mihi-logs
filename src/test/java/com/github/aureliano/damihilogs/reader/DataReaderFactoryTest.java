package com.github.aureliano.damihilogs.reader;

import junit.framework.Assert;

import org.junit.Test;

import com.github.aureliano.damihilogs.config.input.ExternalCommandInput;
import com.github.aureliano.damihilogs.config.input.IConfigInput;
import com.github.aureliano.damihilogs.config.input.InputFileConfig;
import com.github.aureliano.damihilogs.config.input.StandardInputConfig;
import com.github.aureliano.damihilogs.config.input.UrlInputConfig;
import com.github.aureliano.damihilogs.exception.DeferoException;

public class DataReaderFactoryTest {

	@Test(expected = DeferoException.class)
	public void testCreateDataReaderNull() {
		DataReaderFactory.createDataReader(null);
	}
	
	@Test(expected = DeferoException.class)
	public void testCreateDataReaderUnsupported() {
		DataReaderFactory.createDataReader(new IConfigInput() {
			public String getConfigurationId() {
				return null;
			}

			public IConfigInput withConfigurationId(String id) {
				return null;
			}
			
			@SuppressWarnings("unchecked")
			public Object clone() {
				return null;
			}

			public boolean isUseLastExecutionRecords() {
				return false;
			}

			public IConfigInput withUseLastExecutionRecords(boolean useLastExecutionRecords) {
				return null;
			}
		});
	}
	
	@Test
	public void testCreateDataReader() {
		Assert.assertTrue(DataReaderFactory.createDataReader(new InputFileConfig()) instanceof FileDataReader);
		Assert.assertTrue(DataReaderFactory.createDataReader(new StandardInputConfig()) instanceof StandardDataReader);
		Assert.assertTrue(DataReaderFactory.createDataReader(new UrlInputConfig()) instanceof UrlDataReader);
		Assert.assertTrue(DataReaderFactory.createDataReader(new ExternalCommandInput()) instanceof ExternalCommandDataReader);
	}
}