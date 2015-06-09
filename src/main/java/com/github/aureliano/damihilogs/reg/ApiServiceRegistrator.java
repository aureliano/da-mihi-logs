package com.github.aureliano.damihilogs.reg;

import java.util.HashMap;
import java.util.Map;

import com.github.aureliano.damihilogs.IExecutor;
import com.github.aureliano.damihilogs.config.IConfiguration;
import com.github.aureliano.damihilogs.config.input.ExternalCommandInput;
import com.github.aureliano.damihilogs.config.input.FileInputConfig;
import com.github.aureliano.damihilogs.config.input.FileTailerInputConfig;
import com.github.aureliano.damihilogs.config.input.StandardInputConfig;
import com.github.aureliano.damihilogs.config.input.UrlInputConfig;
import com.github.aureliano.damihilogs.config.output.ElasticSearchOutputConfig;
import com.github.aureliano.damihilogs.config.output.FileOutputConfig;
import com.github.aureliano.damihilogs.config.output.StandardOutputConfig;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.helper.ReflectionHelper;
import com.github.aureliano.damihilogs.helper.StringHelper;
import com.github.aureliano.damihilogs.reader.ExternalCommandDataReader;
import com.github.aureliano.damihilogs.reader.FileDataReader;
import com.github.aureliano.damihilogs.reader.FileTailerDataReader;
import com.github.aureliano.damihilogs.reader.StandardDataReader;
import com.github.aureliano.damihilogs.reader.UrlDataReader;
import com.github.aureliano.damihilogs.writer.ElasticSearchDataWriter;
import com.github.aureliano.damihilogs.writer.FileDataWriter;
import com.github.aureliano.damihilogs.writer.StandardDataWriter;

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
		
		if (registration.getExecutor() == null) {
			throw new DaMihiLogsException("There isn't an executor (reader/writer) registered with ID " + configuration.id());
		}
		
		try {
			IExecutor executor = (IExecutor) registration.getExecutor().newInstance();
			return executor.withConfiguration(configuration);
		} catch (Exception ex) {
			throw new DaMihiLogsException(ex);
		}
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
		this.registrate(this.createService(ExternalCommandInput.class, ExternalCommandDataReader.class));
		this.registrate(this.createService(StandardOutputConfig.class, StandardDataWriter.class));
		this.registrate(this.createService(FileOutputConfig.class, FileDataWriter.class));
		this.registrate(this.createService(ElasticSearchOutputConfig.class, ElasticSearchDataWriter.class));
	}
	
	private ServiceRegistration createService(Class<? extends IConfiguration> configuration, Class<? extends IExecutor> executor) {
		return new ServiceRegistration()
			.withConfiguration(configuration)
			.withExecutor(executor);
	}
}