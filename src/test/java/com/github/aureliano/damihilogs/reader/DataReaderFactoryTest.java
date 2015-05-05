package com.github.aureliano.damihilogs.reader;

import java.util.List;
import java.util.Properties;

import junit.framework.Assert;

import org.junit.Test;

import com.github.aureliano.damihilogs.config.IConfiguration;
import com.github.aureliano.damihilogs.config.input.ExternalCommandInput;
import com.github.aureliano.damihilogs.config.input.IConfigInput;
import com.github.aureliano.damihilogs.config.input.InputFileConfig;
import com.github.aureliano.damihilogs.config.input.StandardInputConfig;
import com.github.aureliano.damihilogs.config.input.UrlInputConfig;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.exception.IExceptionHandler;
import com.github.aureliano.damihilogs.listener.DataReadingListener;
import com.github.aureliano.damihilogs.listener.ExecutionListener;
import com.github.aureliano.damihilogs.matcher.IMatcher;

public class DataReaderFactoryTest {

	@Test(expected = DaMihiLogsException.class)
	public void testCreateDataReaderNull() {
		DataReaderFactory.createDataReader(null);
	}
	
	@Test(expected = DaMihiLogsException.class)
	public void testCreateDataReaderUnsupported() {
		DataReaderFactory.createDataReader(new IConfigInput() {
			public String getConfigurationId() { return null; }

			public IConfigInput withConfigurationId(String id) { return null; }
			
			public IConfiguration clone() { return null; }

			public Boolean isUseLastExecutionRecords() { return false; }

			public IConfigInput withUseLastExecutionRecords(Boolean useLastExecutionRecords) { return null; }

			public IConfiguration putMetadata(String key, String value) { return null; }

			public String getMetadata(String key) { return null; }
			
			public Properties getMetadata() { return null; }

			public IConfigInput addExceptionHandler(IExceptionHandler handler) { return null; }

			public List<IExceptionHandler> getExceptionHandlers() { return null; }

			public IMatcher getMatcher() { return null; }

			public IConfigInput withMatcher(IMatcher matcher) { return null; }

			public List<DataReadingListener> getDataReadingListeners() { return null; }

			public IConfigInput withDataReadingListeners(List<DataReadingListener> dataReadingListeners) { return null; }

			public IConfigInput addDataReadingListener(DataReadingListener listener) { return null; }

			public List<ExecutionListener> getExecutionListeners() { return null; }

			public IConfigInput withExecutionListeners(List<ExecutionListener> inputExecutionListeners) { return null; }

			public IConfigInput addExecutionListener(ExecutionListener listener) { return null; }
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