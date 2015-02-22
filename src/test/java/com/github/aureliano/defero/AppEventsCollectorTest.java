package com.github.aureliano.defero;

import java.util.Map;

import org.junit.Test;

import com.github.aureliano.defero.config.EventCollectorConfiguration;
import com.github.aureliano.defero.config.input.InputFileConfig;
import com.github.aureliano.defero.config.output.StandardOutputConfig;
import com.github.aureliano.defero.event.AfterReadingEvent;
import com.github.aureliano.defero.event.AfterWritingEvent;
import com.github.aureliano.defero.event.BeforeReadingEvent;
import com.github.aureliano.defero.event.BeforeWritingEvent;
import com.github.aureliano.defero.event.StepParseEvent;
import com.github.aureliano.defero.filter.IEventFielter;
import com.github.aureliano.defero.formatter.JsonFormatter;
import com.github.aureliano.defero.listener.DataReadingListener;
import com.github.aureliano.defero.listener.DataWritingListener;
import com.github.aureliano.defero.parser.JsonEventParser;

public class AppEventsCollectorTest {

	@Test
	public void testExecute() {		
		new AppEventsCollector()
			.withConfiguration(new EventCollectorConfiguration()
				.withInputConfig(new InputFileConfig()
					.withFile("src/test/resources/datalog.log")
					.withStartPosition(10))
				.withOutputConfig(new StandardOutputConfig())
				.withParser(new JsonEventParser())
				.addDataReadingListeners(this.getDataReadingListener())
				.addDataWritingListeners(this.getDataWriteListener())
				.withOutputFormatter(new JsonFormatter())
				.withFilter(new IEventFielter() {
					
					@Override
					public boolean accept(Object data) {
						Map<String, Object> map = (Map<String, Object>) data;
						return map.get("tipoLog").equals("MemoriaServidor");
					}
				}))
			.execute();
	}
	
	private DataReadingListener getDataReadingListener() {
		return new DataReadingListener() {
			
			@Override
			public void stepLineParse(StepParseEvent event) { }
			
			@Override
			public void beforeDataReading(BeforeReadingEvent event) { }
			
			@Override
			public void afterDataReading(AfterReadingEvent event) {
				Map<String, Object> map = (Map<String, Object>) event.getData();
				map.put("newOne", "Added!");
			}
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