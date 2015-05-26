package com.github.aureliano.damihilogs;

import java.util.Map;

import org.junit.Test;

import com.github.aureliano.damihilogs.config.EventCollectorConfiguration;
import com.github.aureliano.damihilogs.config.input.FileInputConfig;
import com.github.aureliano.damihilogs.config.output.StandardOutputConfig;
import com.github.aureliano.damihilogs.event.AfterCollectorsEvent;
import com.github.aureliano.damihilogs.event.AfterInputEvent;
import com.github.aureliano.damihilogs.event.AfterReadingEvent;
import com.github.aureliano.damihilogs.event.AfterWritingEvent;
import com.github.aureliano.damihilogs.event.BeforeCollectorsEvent;
import com.github.aureliano.damihilogs.event.BeforeInputEvent;
import com.github.aureliano.damihilogs.event.BeforeReadingEvent;
import com.github.aureliano.damihilogs.event.BeforeWritingEvent;
import com.github.aureliano.damihilogs.event.StepParseEvent;
import com.github.aureliano.damihilogs.filter.IEventFielter;
import com.github.aureliano.damihilogs.formatter.JsonFormatter;
import com.github.aureliano.damihilogs.listener.DataReadingListener;
import com.github.aureliano.damihilogs.listener.DataWritingListener;
import com.github.aureliano.damihilogs.listener.EventsCollectorListener;
import com.github.aureliano.damihilogs.listener.ExecutionListener;
import com.github.aureliano.damihilogs.parser.JsonEventParser;

public class AppEventsCollectorTest {

	@Test
	public void testExecute() {		
		new AppEventsCollector()
			.withConfiguration(new EventCollectorConfiguration()
				.addInputConfig(new FileInputConfig()
					.withFile("src/test/resources/datalog.log")
					.withStartPosition(10)
					.addDataReadingListener(this.getDataReadingListener())
					.addExecutionListener(this.getExecutionListener()))
				.addOutputConfig(new StandardOutputConfig()
					.withParser(new JsonEventParser())
					.withFilter(new IEventFielter() {
					
						@Override
						public boolean accept(Object data) {
							@SuppressWarnings("unchecked")
							Map<String, Object> map = (Map<String, Object>) data;
							return map.get("tipoLog").equals("MemoriaServidor");
						}
					})
					.withOutputFormatter(new JsonFormatter())
					.addDataWritingListener(this.getDataWriteListener()))
				.addEventsCollectorListeners(this.getEventsCollectorListener()))
			.execute();
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
	
	private ExecutionListener getExecutionListener() {
		return new ExecutionListener() {
			
			@Override
			public void beforeExecution(BeforeInputEvent evt) { }
			
			@Override
			public void afterExecution(AfterInputEvent evt) { }
		};
	}
	
	private EventsCollectorListener getEventsCollectorListener() {
		return new EventsCollectorListener() {
			
			@Override
			public void beforeExecution(BeforeCollectorsEvent evt) { }
			
			@Override
			public void afterExecution(AfterCollectorsEvent evt) { }
		};
	}
}