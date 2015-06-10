package com.github.aureliano.damihilogs.converter.schedule;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.schedule.SchedulerTypes;

public class SchedulerFactoryTest {

	@Test
	public void testCreateConverterNonexistent() {
		String type = "test";
		String errorMessage = "Scheduler type '" + type + "' not supported. Expected one of: " + Arrays.toString(SchedulerTypes.values());
		
		try {
			SchedulerConverterFactory.createConverter(type);
			Assert.fail("An exception was expected.");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals(errorMessage, ex.getMessage());
		}
	}

	@Test
	public void testCreateConverter() {
		Assert.assertTrue(SchedulerConverterFactory.createConverter(SchedulerTypes.EXECUTE_ONCE_AT_SPECIFIC_TIME.name()) instanceof ExecuteOnceAtSpecificTimeScheduleConverter);
		Assert.assertTrue(SchedulerConverterFactory.createConverter(SchedulerTypes.EXECUTE_ONCE_AT_SPECIFIC_TIME.name().toLowerCase()) instanceof ExecuteOnceAtSpecificTimeScheduleConverter);
		
		Assert.assertTrue(SchedulerConverterFactory.createConverter(SchedulerTypes.EXECUTE_PERIODICALLY.name()) instanceof ExecutePeriodicallyScheduleConverter);
		Assert.assertTrue(SchedulerConverterFactory.createConverter(SchedulerTypes.EXECUTE_PERIODICALLY.name().toLowerCase()) instanceof ExecutePeriodicallyScheduleConverter);
		
		Assert.assertTrue(SchedulerConverterFactory.createConverter(SchedulerTypes.EXECUTE_PERIODICALLY_AT_SPECIFIC_TIME.name()) instanceof ExecutePeriodicallyAtSpecificTimeScheduleConverter);
		Assert.assertTrue(SchedulerConverterFactory.createConverter(SchedulerTypes.EXECUTE_PERIODICALLY_AT_SPECIFIC_TIME.name().toLowerCase()) instanceof ExecutePeriodicallyAtSpecificTimeScheduleConverter);
	}
}