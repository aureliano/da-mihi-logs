package com.github.aureliano.damihilogs;

import junit.framework.Assert;

import org.junit.Test;

import com.github.aureliano.damihilogs.config.input.ExternalCommandInput;
import com.github.aureliano.damihilogs.config.input.FileInputConfig;
import com.github.aureliano.damihilogs.config.input.FileTailerInputConfig;
import com.github.aureliano.damihilogs.config.input.StandardInputConfig;
import com.github.aureliano.damihilogs.config.input.UrlInputConfig;
import com.github.aureliano.damihilogs.config.output.ElasticSearchOutputConfig;
import com.github.aureliano.damihilogs.config.output.FileOutputConfig;
import com.github.aureliano.damihilogs.config.output.StandardOutputConfig;
import com.github.aureliano.damihilogs.reader.ExternalCommandDataReader;
import com.github.aureliano.damihilogs.reader.FileDataReader;
import com.github.aureliano.damihilogs.reader.FileTailerDataReader;
import com.github.aureliano.damihilogs.reader.StandardDataReader;
import com.github.aureliano.damihilogs.reader.UrlDataReader;
import com.github.aureliano.damihilogs.writer.ElasticSearchDataWriter;
import com.github.aureliano.damihilogs.writer.FileDataWriter;
import com.github.aureliano.damihilogs.writer.StandardDataWriter;

public class ExecutorRegistrationServiceTest {

	@Test
	public void testDefaultExecutorRegistrations() {
		ExecutorRegistrationService service = ExecutorRegistrationService.instance();
		Assert.assertTrue(service.createExecutor(new FileInputConfig()) instanceof FileDataReader);
		Assert.assertTrue(service.createExecutor(new FileTailerInputConfig()) instanceof FileTailerDataReader);
		Assert.assertTrue(service.createExecutor(new StandardInputConfig()) instanceof StandardDataReader);
		Assert.assertTrue(service.createExecutor(new UrlInputConfig()) instanceof UrlDataReader);
		Assert.assertTrue(service.createExecutor(new ExternalCommandInput()) instanceof ExternalCommandDataReader);
		Assert.assertTrue(service.createExecutor(new StandardOutputConfig()) instanceof StandardDataWriter);
		Assert.assertTrue(service.createExecutor(new FileOutputConfig()) instanceof FileDataWriter);
		Assert.assertTrue(service.createExecutor(new ElasticSearchOutputConfig()) instanceof ElasticSearchDataWriter);
	}

	@Test
	public void testCustomExecutorRegistrations() {
		ExecutorRegistrationService service = ExecutorRegistrationService.instance();
		service.addExecutor(CustomInputConfig.class, CustomDataReader.class);
		Assert.assertTrue(service.createExecutor(new CustomInputConfig()) instanceof CustomDataReader);
	}
}