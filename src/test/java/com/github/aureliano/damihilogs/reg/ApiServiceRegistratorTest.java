package com.github.aureliano.damihilogs.reg;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.CustomDataReader;
import com.github.aureliano.damihilogs.CustomInputConfig;
import com.github.aureliano.damihilogs.config.input.ExternalCommandInput;
import com.github.aureliano.damihilogs.config.input.FileInputConfig;
import com.github.aureliano.damihilogs.config.input.FileTailerInputConfig;
import com.github.aureliano.damihilogs.config.input.StandardInputConfig;
import com.github.aureliano.damihilogs.config.input.UrlInputConfig;
import com.github.aureliano.damihilogs.config.output.ElasticSearchOutputConfig;
import com.github.aureliano.damihilogs.config.output.FileOutputConfig;
import com.github.aureliano.damihilogs.config.output.StandardOutputConfig;
import com.github.aureliano.damihilogs.executor.reader.ExternalCommandDataReader;
import com.github.aureliano.damihilogs.executor.reader.FileDataReader;
import com.github.aureliano.damihilogs.executor.reader.FileTailerDataReader;
import com.github.aureliano.damihilogs.executor.reader.StandardDataReader;
import com.github.aureliano.damihilogs.executor.reader.UrlDataReader;
import com.github.aureliano.damihilogs.executor.writer.ElasticSearchDataWriter;
import com.github.aureliano.damihilogs.executor.writer.FileDataWriter;
import com.github.aureliano.damihilogs.executor.writer.StandardDataWriter;

public class ApiServiceRegistratorTest {

	@Test
	public void testCoreExecutorRegistrations() {
		ApiServiceRegistrator service = ApiServiceRegistrator.instance();
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
		ApiServiceRegistrator service = ApiServiceRegistrator.instance();
		service.registrate(
			new ServiceRegistration()
				.withConfiguration(CustomInputConfig.class)
				.withExecutor(CustomDataReader.class));
		Assert.assertTrue(service.createExecutor(new CustomInputConfig()) instanceof CustomDataReader);
	}

	@Test
	public void testCustomExecutorRegistrations2() {
		ApiServiceRegistrator service = ApiServiceRegistrator.instance();
		service.registrate(CustomInputConfig.class, CustomDataReader.class);
		Assert.assertTrue(service.createExecutor(new CustomInputConfig()) instanceof CustomDataReader);
	}
}