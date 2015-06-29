package com.github.aureliano.damihilogs.reg;

import java.util.HashMap;
import java.util.Map;

import com.github.aureliano.damihilogs.config.IConfiguration;
import com.github.aureliano.damihilogs.config.input.ExternalCommandInputConfig;
import com.github.aureliano.damihilogs.config.input.FileInputConfig;
import com.github.aureliano.damihilogs.config.input.FileTailerInputConfig;
import com.github.aureliano.damihilogs.config.input.JdbcInputConfig;
import com.github.aureliano.damihilogs.config.input.StandardInputConfig;
import com.github.aureliano.damihilogs.config.input.TwitterInputConfig;
import com.github.aureliano.damihilogs.config.input.UrlInputConfig;
import com.github.aureliano.damihilogs.config.output.ElasticSearchOutputConfig;
import com.github.aureliano.damihilogs.config.output.FileOutputConfig;
import com.github.aureliano.damihilogs.config.output.JdbcOutputConfig;
import com.github.aureliano.damihilogs.config.output.StandardOutputConfig;
import com.github.aureliano.damihilogs.config.output.TwitterOutputConfig;
import com.github.aureliano.damihilogs.converter.IConfigurationConverter;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.executor.IExecutor;
import com.github.aureliano.damihilogs.executor.reader.ExternalCommandDataReader;
import com.github.aureliano.damihilogs.executor.reader.FileDataReader;
import com.github.aureliano.damihilogs.executor.reader.FileTailerDataReader;
import com.github.aureliano.damihilogs.executor.reader.JdbcDataReader;
import com.github.aureliano.damihilogs.executor.reader.StandardDataReader;
import com.github.aureliano.damihilogs.executor.reader.TwitterDataReader;
import com.github.aureliano.damihilogs.executor.reader.UrlDataReader;
import com.github.aureliano.damihilogs.executor.writer.ElasticSearchDataWriter;
import com.github.aureliano.damihilogs.executor.writer.FileDataWriter;
import com.github.aureliano.damihilogs.executor.writer.JdbcDataWriter;
import com.github.aureliano.damihilogs.executor.writer.StandardDataWriter;
import com.github.aureliano.damihilogs.executor.writer.TwitterDataWriter;
import com.github.aureliano.damihilogs.helper.ReflectionHelper;
import com.github.aureliano.damihilogs.helper.StringHelper;

public final class ApiServiceRegistrator {

	private static ApiServiceRegistrator instance;
	
	private Map<String, ServiceRegistration> registrations;
	
	private ApiServiceRegistrator() {
		this.registrations = new HashMap<String, ServiceRegistration>();
		this.registrateCoreServices();
	}
	
	public void registrate(Class<? extends IConfiguration> configuration, Class<? extends IExecutor> executor) {
		this.registrate(this.createService(configuration, executor));
	}
	
	public void registrate(Class<? extends IConfiguration> configuration,
			Class<? extends IExecutor> executor, Class<? extends IConfigurationConverter<?>> converter) {
		
		this.registrate(this.createService(configuration, executor, converter));
	}
	
	public void registrate(ServiceRegistration registration) {
		if (StringHelper.isEmpty(registration.getId())) {
			if (registration.getConfiguration() != null) {
				IConfiguration configuration = (IConfiguration) ReflectionHelper.newInstance(registration.getConfiguration());
				registration.withId(configuration.id());
			} else {
				throw new DaMihiLogsException("Service registration expected to get a valid id but got an empty value.");
			}
		}
		
		this.registrations.put(registration.getId(), registration);
	}
	
	public IExecutor createExecutor(IConfiguration configuration) {
		ServiceRegistration registration = this.registrations.get(configuration.id());
		
		if ((registration == null) || (registration.getExecutor() == null)) {
			throw new DaMihiLogsException("There isn't an executor (reader/writer) registered with ID " + configuration.id());
		}
		
		IExecutor executor = (IExecutor) ReflectionHelper.newInstance(registration.getExecutor());
		return executor.withConfiguration(configuration);
	}
	
	public IConfigurationConverter<?> createConverter(String id) {
		ServiceRegistration registration = this.registrations.get(id);
		if (registration != null) {
			return (IConfigurationConverter<?>) ReflectionHelper.newInstance(registration.getConverter());
		}
		
		registration = this.registrations.get(id.toUpperCase());
		if (registration != null) {
			return (IConfigurationConverter<?>) ReflectionHelper.newInstance(registration.getConverter());
		}
		
		registration = this.registrations.get(id.toLowerCase());
		if (registration != null) {
			return (IConfigurationConverter<?>) ReflectionHelper.newInstance(registration.getConverter());
		}
		
		return null;
	}
	
	public static ApiServiceRegistrator instance() {
		if (instance == null) {
			instance = new ApiServiceRegistrator();
		}
		
		return instance;
	}
	
	private void registrateCoreServices() {
		this.registrate(this.createService(FileInputConfig.class, FileDataReader.class));
		this.registrate(this.createService(FileTailerInputConfig.class, FileTailerDataReader.class));
		this.registrate(this.createService(StandardInputConfig.class, StandardDataReader.class));
		this.registrate(this.createService(UrlInputConfig.class, UrlDataReader.class));
		this.registrate(this.createService(ExternalCommandInputConfig.class, ExternalCommandDataReader.class));
		this.registrate(this.createService(JdbcInputConfig.class, JdbcDataReader.class));
		this.registrate(this.createService(TwitterInputConfig.class, TwitterDataReader.class));
		this.registrate(this.createService(StandardOutputConfig.class, StandardDataWriter.class));
		this.registrate(this.createService(FileOutputConfig.class, FileDataWriter.class));
		this.registrate(this.createService(ElasticSearchOutputConfig.class, ElasticSearchDataWriter.class));
		this.registrate(this.createService(JdbcOutputConfig.class, JdbcDataWriter.class));
		this.registrate(this.createService(TwitterOutputConfig.class, TwitterDataWriter.class));
	}
	
	private ServiceRegistration createService(Class<? extends IConfiguration> configuration,
			Class<? extends IExecutor> executor) {
		
		return this.createService(configuration, executor, null);
	}
	
	private ServiceRegistration createService(Class<? extends IConfiguration> configuration,
			Class<? extends IExecutor> executor, Class<? extends IConfigurationConverter<?>> converter) {
		
		return new ServiceRegistration()
			.withConfiguration(configuration)
			.withExecutor(executor)
			.withConverter(converter);
	}
}