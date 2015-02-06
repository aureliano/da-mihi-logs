package com.github.aureliano.defero;

import org.junit.Test;

import com.github.aureliano.defero.config.EventCollectorConfiguration;
import com.github.aureliano.defero.config.input.InputConfigFactory;
import com.github.aureliano.defero.config.input.InputFileConfig;
import com.github.aureliano.defero.parser.PlainText;

public class AppEventsCollectorTest {

	@Test
	public void testExecute() {
		new AppEventsCollector()
			.withConfiguration(new EventCollectorConfiguration()
				.withInputConfig(InputConfigFactory.createInputConfig(InputFileConfig.class)
						.withFile("src/test/resources/datalog.log")
						.withStartPosition(20))
				.withParser(new PlainText()))
			.execute();
	}
}