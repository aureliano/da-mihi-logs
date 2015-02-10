package com.github.aureliano.defero;

import org.junit.Test;

import com.github.aureliano.defero.config.EventCollectorConfiguration;
import com.github.aureliano.defero.config.input.InputConfigFactory;
import com.github.aureliano.defero.config.input.InputFileConfig;
import com.github.aureliano.defero.config.output.OutputConfigFactory;
import com.github.aureliano.defero.config.output.StandardOutputConfig;
import com.github.aureliano.defero.event.AfterReadingEvent;
import com.github.aureliano.defero.event.AfterWritingEvent;
import com.github.aureliano.defero.event.BeforeReadingEvent;
import com.github.aureliano.defero.event.BeforeWritingEvent;
import com.github.aureliano.defero.event.StepParseEvent;
import com.github.aureliano.defero.filter.IEventFielter;
import com.github.aureliano.defero.listener.DataReadingListener;
import com.github.aureliano.defero.listener.DataWritingListener;
import com.github.aureliano.defero.parser.PlainTextParser;

public class AppEventsCollectorTest {

	@Test
	public void testExecute() {		
		new AppEventsCollector()
			.withConfiguration(new EventCollectorConfiguration()
				.withInputConfig(InputConfigFactory.createInputConfig(InputFileConfig.class)
					.withFile("src/test/resources/datalog.log")
					.withStartPosition(20))
				.withOutputConfig(OutputConfigFactory.createOutputConfig(StandardOutputConfig.class))
				.withParser(new PlainTextParser())
				//.addDataReadingListeners(this.getDataReadingListener())
				//.addDataWritingListeners(this.getDataWriteListener())
				.withFilter(new IEventFielter() {
					
					@Override
					public boolean accept(Object data) {
						return false;
					}
				}))
			.execute();
	}
	
	private DataReadingListener getDataReadingListener() {
		return new DataReadingListener() {
			
			@Override
			public void stepLineParse(StepParseEvent event) {
				System.out.println(" >>> STEP PARSE");
				System.out.println(event.getParseAttempts());
				System.out.println(event.getLine());
				System.out.println(event.getCurrentData());
			}
			
			@Override
			public void beforeDataReading(BeforeReadingEvent event) {
				System.out.println(" >>> BEFORE READING");
				System.out.println(event.getInputConfiguration().inputType());
				System.out.println(event.getLineCounter());
				System.out.println(event.getMaxParseAttempts());
			}
			
			@Override
			public void afterDataReading(AfterReadingEvent event) {
				System.out.println(" >>> AFTER READING");
				System.out.println(event.getLineCounter());
				System.out.println(event.getData());
			}
		};
	}
	
	private DataWritingListener getDataWriteListener() {
		return new DataWritingListener() {
			
			@Override
			public void beforeDataWriting(BeforeWritingEvent event) {
				System.out.println(" >>> BEFORE WRITING");
				System.out.println(event.getOutputConfiguration().outputType());
				System.out.println(event.getData());
			}
			
			@Override
			public void afterDataWriting(AfterWritingEvent event) {
				System.out.println(" >>> AFTER WRITING");
				System.out.println(event.getOutputConfiguration().outputType());
				System.out.println(event.getData());
			}
		};
	}
}