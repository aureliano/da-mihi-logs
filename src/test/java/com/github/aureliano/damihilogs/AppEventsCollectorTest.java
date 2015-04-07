package com.github.aureliano.damihilogs;

import java.util.Map;

import org.junit.Test;

import com.github.aureliano.damihilogs.config.EventCollectorConfiguration;
import com.github.aureliano.damihilogs.config.input.IConfigInput;
import com.github.aureliano.damihilogs.config.input.InputFileConfig;
import com.github.aureliano.damihilogs.config.output.StandardOutputConfig;
import com.github.aureliano.damihilogs.event.AfterReadingEvent;
import com.github.aureliano.damihilogs.event.AfterWritingEvent;
import com.github.aureliano.damihilogs.event.BeforeReadingEvent;
import com.github.aureliano.damihilogs.event.BeforeWritingEvent;
import com.github.aureliano.damihilogs.event.StepParseEvent;
import com.github.aureliano.damihilogs.exception.IExceptionHandler;
import com.github.aureliano.damihilogs.filter.IEventFielter;
import com.github.aureliano.damihilogs.formatter.JsonFormatter;
import com.github.aureliano.damihilogs.listener.DataReadingListener;
import com.github.aureliano.damihilogs.listener.DataWritingListener;
import com.github.aureliano.damihilogs.parser.JsonEventParser;

public class AppEventsCollectorTest {

	@Test
	public void testExecute() {		
		new AppEventsCollector()
			.withConfiguration(new EventCollectorConfiguration()
				.addInputConfig(new InputFileConfig()
					.withFile("src/test/resources/datalog.log")
					.withStartPosition(10))
				.addOutputConfig(new StandardOutputConfig()
					.withParser(new JsonEventParser())
					.withFilter(new IEventFielter() {
					
						@Override
						public boolean accept(Object data) {
							@SuppressWarnings("unchecked")
							Map<String, Object> map = (Map<String, Object>) data;
							return map.get("tipoLog").equals("MemoriaServidor");
						}
					}))				
				.addDataReadingListeners(this.getDataReadingListener())
				.addDataWritingListeners(this.getDataWriteListener())
				.withOutputFormatter(new JsonFormatter())
				)
			.execute();
	}
	
	@Test
	public void testExecuteSerialWithErrorHandling() {
		new AppEventsCollector()
			.withConfiguration(new EventCollectorConfiguration()
				.withMultiThreadingEnabled(false)
				.addInputConfig(new InputFileConfig()
					.withConfigurationId("Batman")
					.withFile("src/test/resources/server.log")
					.withStartPosition(0)
					.addExceptionHandler(new IExceptionHandler() {
						
						public void captureException(Runnable runnable, IConfigInput inputConfig) {
							System.out.println("  >>> Batman has captured an exception");
							System.out.println("  <<< Got it?");
						}
					}))
				.addInputConfig(new InputFileConfig()
					.withConfigurationId("Spiderman")
					.withFile("src/test/resources/server.log")
					.withStartPosition(0))
				.addOutputConfig(new StandardOutputConfig()
					.withParser(new JsonEventParser()))
			).withCollectorId("super-hero").execute();
	}
	
	@Test
	public void testExecuteParalelWithErrorHandling() {
		new AppEventsCollector()
			.withConfiguration(new EventCollectorConfiguration()
				.withMultiThreadingEnabled(true)
				.addInputConfig(new InputFileConfig()
					.withConfigurationId("Hulk")
					.withFile("src/test/resources/server.log")
					.withStartPosition(0)
					.addExceptionHandler(new IExceptionHandler() {
						
						public void captureException(Runnable runnable, IConfigInput inputConfig) {
							System.out.println("  >>> Amazing Hulk has captured an exception");
							System.out.println("  <<< Of course he got it!");
						}
					}))
				.addInputConfig(new InputFileConfig()
					.withConfigurationId("Iron-Man")
					.withFile("src/test/resources/server.log")
					.withStartPosition(0)
					.addExceptionHandler(new IExceptionHandler() {
						
						public void captureException(Runnable runnable, IConfigInput inputConfig) {
							System.out.println("  >>> Iron-Man has captured an exception");
							System.out.println("  <<< Gotcha!");
						}
					}))
				.addOutputConfig(new StandardOutputConfig()
					.withParser(new JsonEventParser()))
			).withCollectorId("super-hero").execute();
	}
	
	private DataReadingListener getDataReadingListener() {
		return new DataReadingListener() {
			
			@Override
			public void stepLineParse(StepParseEvent event) { }
			
			@Override
			public void beforeDataReading(BeforeReadingEvent event) { }
			
			@Override
			public void afterDataReading(AfterReadingEvent event) { }
		};
	}
	
	private DataWritingListener getDataWriteListener() {
		return new DataWritingListener() {
			
			@Override
			public void beforeDataWriting(BeforeWritingEvent event) { }
			
			@Override
			public void afterDataWriting(AfterWritingEvent event) { }
		};
	}
}