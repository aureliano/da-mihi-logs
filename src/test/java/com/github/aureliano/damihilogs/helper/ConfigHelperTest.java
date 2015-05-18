package com.github.aureliano.damihilogs.helper;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.junit.Test;

import com.github.aureliano.damihilogs.clean.FileCleaner;
import com.github.aureliano.damihilogs.clean.ICleaner;
import com.github.aureliano.damihilogs.clean.LogCleaner;
import com.github.aureliano.damihilogs.config.EventCollectorConfiguration;
import com.github.aureliano.damihilogs.config.input.ConnectionSchema;
import com.github.aureliano.damihilogs.config.input.ExternalCommandInput;
import com.github.aureliano.damihilogs.config.input.IConfigInput;
import com.github.aureliano.damihilogs.config.input.InputFileConfig;
import com.github.aureliano.damihilogs.config.input.StandardInputConfig;
import com.github.aureliano.damihilogs.config.input.UrlInputConfig;
import com.github.aureliano.damihilogs.config.output.ElasticSearchOutputConfig;
import com.github.aureliano.damihilogs.config.output.FileOutputConfig;
import com.github.aureliano.damihilogs.config.output.IConfigOutput;
import com.github.aureliano.damihilogs.config.output.StandardOutputConfig;
import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.filter.DefaultEmptyFilter;
import com.github.aureliano.damihilogs.formatter.PlainTextFormatter;
import com.github.aureliano.damihilogs.inout.SupportedCompressionType;
import com.github.aureliano.damihilogs.matcher.SingleLineMatcher;
import com.github.aureliano.damihilogs.parser.PlainTextParser;
import com.github.aureliano.damihilogs.report.HtmlReporter;
import com.github.aureliano.damihilogs.report.ReportLanguage;
import com.github.aureliano.damihilogs.schedule.ExecutePeriodicallySchedule;

public class ConfigHelperTest {
	
	@Test
	public void testLoadConfiguration() {
		EventCollectorConfiguration configuration = ConfigHelper.loadConfiguration("src/test/resources/configuration.json");
		this._testMainConfiguration(configuration);
		this._testSchedulerConfiguration(configuration);
		this._testReporterConfiguration(configuration);
		this._testCleanerConfiguration(configuration);
		this._testEventsCollectorListenersConfiguration(configuration);
		this._testInputs(configuration);
		this._testOutputs(configuration);
	}

	@Test
	public void testCopyMetadata() {
		EventCollectorConfiguration c = new EventCollectorConfiguration();
		c.putMetadata("fruit", "orange");
		c.putMetadata("collor", "blue");
		
		StandardInputConfig s = new StandardInputConfig();
		s.putMetadata("fruit", "coconut");
		s.putMetadata("idiom", "Portuguese");
		
		ConfigHelper.copyMetadata(c, s);
		
		Assert.assertEquals("orange", s.getMetadata("fruit"));
		Assert.assertEquals("blue", s.getMetadata("collor"));
		Assert.assertEquals("Portuguese", s.getMetadata("idiom"));
	}

	@Test
	public void testInputConfigValidation() {
		try {
			ConfigHelper.inputConfigValidation(null);
			Assert.fail("Expected to got an exception");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Input configuration must be provided.", ex.getMessage());
		}
	}
	
	@Test
	public void testOutputConfigValidation() {
		try {
			ConfigHelper.outputConfigValidation(null);
			Assert.fail("Expected to got an exception");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Output configuration must be provided.", ex.getMessage());
		}
	}
	
	@Test
	public void testInputFileConfigValidationFile() {
		try {
			ConfigHelper.inputFileConfigValidation(new InputFileConfig());
			Assert.fail("Expected to got an exception");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Input file not provided.", ex.getMessage());
		}
		
		ConfigHelper.inputFileConfigValidation(new InputFileConfig().withFile(new File("src/test/resources/empty-file.log")).withStartPosition(0));
		ConfigHelper.inputFileConfigValidation(new InputFileConfig().withFile("src/test/resources/empty-file.log").withStartPosition(25));
	}
	
	@Test
	public void testInputFileConfigValidationStartPosition() {
		try {
			ConfigHelper.inputFileConfigValidation(new InputFileConfig().withFile("src/test/resources/empty-file.log").withStartPosition(-1));
			Assert.fail("Expected to got an exception");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Start position must be greater or equal to zero (>= 0).", ex.getMessage());
		}
		
		ConfigHelper.inputFileConfigValidation(new InputFileConfig().withFile("src/test/resources/empty-file.log").withStartPosition(0));
	}
	
	@Test
	public void testOutputFileConfigValidationFile() {
		try {
			ConfigHelper.outputConfigValidation(new FileOutputConfig());
			Assert.fail("Expected to got an exception");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Output file not provided.", ex.getMessage());
		}
		
		ConfigHelper.outputConfigValidation(new FileOutputConfig().withFile(new File("src/test/resources/empty-file.log")));
		ConfigHelper.outputConfigValidation(new FileOutputConfig().withFile("src/test/resources/empty-file.log"));
	}
	
	@Test
	public void testUrlInputConfigValidation() {
		try {
			ConfigHelper.urlInputConfigValidation(new UrlInputConfig().withConnectionSchema(null));
			Assert.fail("Expected to got an exception");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Connection schema not provided.", ex.getMessage());
		}
		
		try {
			ConfigHelper.urlInputConfigValidation(new UrlInputConfig().withConnectionSchema(ConnectionSchema.HTTP));
			Assert.fail("Expected to got an exception");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Host not provided.", ex.getMessage());
		}
		
		try {
			ConfigHelper.urlInputConfigValidation(new UrlInputConfig()
				.withConnectionSchema(ConnectionSchema.HTTP).withHost("localhost"));
			Assert.fail("Expected to got an exception");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Output file not provided.", ex.getMessage());
		}
		
		try {
			ConfigHelper.urlInputConfigValidation(new UrlInputConfig()
				.withConnectionSchema(ConnectionSchema.HTTP).withHost("localhost")
				.withOutputFile("src/test/resources"));
			Assert.fail("Expected to got an exception");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Output file 'src/test/resources' is a directory.", ex.getMessage());
		}
		
		ConfigHelper.urlInputConfigValidation(new UrlInputConfig()
			.withConnectionSchema(ConnectionSchema.HTTP).withHost("localhost")
			.withOutputFile("src/test/resources/empty-file.log"));
	}
	
	@Test
	public void testExternalCommandConfigValidation() {
		try {
			ConfigHelper.externalCommandConfigValidation(new ExternalCommandInput());
			Assert.fail("Expected to got an exception");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Command not provided.", ex.getMessage());
		}
		
		try {
			ConfigHelper.externalCommandConfigValidation(new ExternalCommandInput().withCommand(""));
			Assert.fail("Expected to got an exception");
		} catch (DaMihiLogsException ex) {
			Assert.assertEquals("Command not provided.", ex.getMessage());
		}
		
		ConfigHelper.externalCommandConfigValidation(new ExternalCommandInput().withCommand("ls -la"));
	}
	
	@Test
	public void testNewUniqueConfigurationName() {
		Set<String> names = new HashSet<String>();
		for (byte i = 0; i < 85; i++) {
			Assert.assertTrue(names.add(ConfigHelper.newUniqueConfigurationName()));
		}
		
		Assert.assertTrue(names.size() == 85);
	}
	
	private void _testMainConfiguration(EventCollectorConfiguration configuration) {
		Assert.assertEquals("app-log-collector", configuration.getCollectorId());
		Assert.assertTrue(configuration.isPersistExecutionLog());
		Assert.assertTrue(configuration.isMultiThreadingEnabled());
		Assert.assertEquals("something", configuration.getMetadata("test"));
	}
	
	private void _testSchedulerConfiguration(EventCollectorConfiguration configuration) {
		ExecutePeriodicallySchedule scheduler = (ExecutePeriodicallySchedule) configuration.getScheduler();
		Assert.assertEquals(new Long(1), scheduler.getDelay());
		Assert.assertEquals(new Long(10), scheduler.getPeriod());
		Assert.assertEquals(TimeUnit.MINUTES, scheduler.getTimeUnit());
	}
	
	private void _testReporterConfiguration(EventCollectorConfiguration configuration) {
		Assert.assertEquals(1, configuration.getReporters().size());
		HtmlReporter reporter = (HtmlReporter) configuration.getReporters().get(0);
		Assert.assertEquals(ReportLanguage.PORTUGUESE, reporter.getLanguage());
		Assert.assertEquals("Da mihi logs", reporter.getPageTitle());
		Assert.assertEquals("Test configuration", reporter.getDescription());
		Assert.assertEquals("target/some/dir", reporter.getOutputDir().getPath());
	}
	
	private void _testCleanerConfiguration(EventCollectorConfiguration configuration) {
		byte fileCleaners = 0;
		byte logCleaners = 0;
		
		for (ICleaner cleaner : configuration.getCleaners()) {
			if (cleaner instanceof FileCleaner) {
				fileCleaners++;
			} else if (cleaner instanceof LogCleaner) {
				logCleaners++;
			} else {
				Assert.fail("Unexpected cleaner type: " + cleaner.getClass());
			}
		}
		
		Assert.assertEquals(1, fileCleaners);
		Assert.assertEquals(1, logCleaners);
		Assert.assertEquals((fileCleaners + logCleaners), configuration.getCleaners().size());
	}
	
	private void _testEventsCollectorListenersConfiguration(EventCollectorConfiguration configuration) {
		Assert.assertEquals(1, configuration.getEventsCollectorListeners().size());
	}
	
	private void _testInputs(EventCollectorConfiguration configuration) {
		List<IConfigInput> inputs = configuration.getInputConfigs();
		for (IConfigInput input : inputs) {
			if (input instanceof ExternalCommandInput) {
				this._testInputExternalCommand((ExternalCommandInput) input);
			} else if (input instanceof InputFileConfig) {
				this._testInputFile((InputFileConfig) input);
			} else if (input instanceof StandardInputConfig) {
				this._testInputStandard((StandardInputConfig) input);
			} else if (input instanceof UrlInputConfig) {
				this._testInputUrl((UrlInputConfig) input);
			}
		}
	}
	
	private void _testInputExternalCommand(ExternalCommandInput input) {
		Assert.assertNull(input.getMatcher());
		Assert.assertFalse(input.isUseLastExecutionRecords());
		Assert.assertTrue(input.getExceptionHandlers().isEmpty());
		Assert.assertTrue(input.getDataReadingListeners().isEmpty());
		Assert.assertTrue(input.getExecutionListeners().isEmpty());
		Assert.assertEquals("server-host", input.getMetadata("server"));
		
		Assert.assertEquals("ls", input.getCommand());
		Assert.assertEquals(1, input.getParameters().size());
		Assert.assertEquals("-la", input.getParameters().get(0));
	}
	
	private void _testInputFile(InputFileConfig input) {
		Assert.assertNull(input.getMatcher());
		Assert.assertFalse(input.isUseLastExecutionRecords());
		Assert.assertTrue(input.getExceptionHandlers().isEmpty());
		Assert.assertTrue(input.getDataReadingListeners().isEmpty());
		Assert.assertTrue(input.getExecutionListeners().isEmpty());
		Assert.assertEquals("banânia", input.getMetadata("server"));
		
		Assert.assertEquals("target/test/file", input.getFile().getPath());
		Assert.assertEquals(new Integer(1984), input.getStartPosition());
		Assert.assertEquals("ISO-8859-1", input.getEncoding());
		Assert.assertTrue(input.isTailFile());
		Assert.assertEquals(new Long(5000), input.getTailDelay());
		Assert.assertEquals(new Long(120000000), input.getTailInterval());
	}
	
	private void _testInputStandard(StandardInputConfig input) {
		Assert.assertTrue(input.getMatcher() instanceof SingleLineMatcher);
		Assert.assertTrue(input.isUseLastExecutionRecords());
		Assert.assertTrue(input.getExceptionHandlers().size() == 1);
		Assert.assertTrue(input.getDataReadingListeners().size() == 1);
		Assert.assertTrue(input.getExecutionListeners().size() == 1);
		Assert.assertEquals("bananistão", input.getMetadata("server"));
	}
	
	private void _testInputUrl(UrlInputConfig input) {
		Assert.assertNull(input.getMatcher());
		Assert.assertFalse(input.isUseLastExecutionRecords());
		Assert.assertTrue(input.getExceptionHandlers().isEmpty());
		Assert.assertTrue(input.getDataReadingListeners().isEmpty());
		Assert.assertTrue(input.getExecutionListeners().isEmpty());
		Assert.assertEquals("banânia", input.getMetadata("country"));
		Assert.assertEquals("babalorixá", input.getMetadata("leader"));
		
		Assert.assertEquals(ConnectionSchema.HTTPS, input.getConnectionSchema());
		Assert.assertEquals("localhost", input.getHost());
		Assert.assertEquals(8081, input.getPort());
		Assert.assertEquals("system", input.getPath());
		Assert.assertEquals(new Long(5000), input.getReadTimeout());
		Assert.assertEquals(new Integer(0), input.getByteOffSet());
		Assert.assertEquals("src/test/resources/compressed.gz", input.getOutputFile().getPath());
		Assert.assertEquals("user_name", input.getUser());
		Assert.assertEquals("user_pass", input.getPassword());
		Assert.assertEquals(SupportedCompressionType.GZIP, input.getDecompressFileConfiguration().getCompressionType());
		Assert.assertEquals("src/test/resources/compressed.gz", input.getDecompressFileConfiguration().getInputFilePath());
		Assert.assertEquals("target/test/output", input.getDecompressFileConfiguration().getOutputFilePath());
	}
	
	private void _testOutputs(EventCollectorConfiguration configuration) {
		List<IConfigOutput> outputs = configuration.getOutputConfigs();
		for (IConfigOutput output : outputs) {
			if (output instanceof ElasticSearchOutputConfig) {
				this._testOutputElasticSearch((ElasticSearchOutputConfig) output);
			} else if (output instanceof FileOutputConfig) {
				this._testOutputFile((FileOutputConfig) output);
			} else if (output instanceof StandardOutputConfig) {
				this._testOutputStandard((StandardOutputConfig) output);
			}
		}
	}

	private void _testOutputElasticSearch(ElasticSearchOutputConfig output) {
		Assert.assertNull(output.getParser());
		Assert.assertNull(output.getFilter());
		Assert.assertNull(output.getOutputFormatter());
		Assert.assertTrue(output.getDataWritingListeners().isEmpty());
		Assert.assertEquals("Jacques de Moley", output.getMetadata("crusader"));
		
		Assert.assertEquals("127.0.0.1", output.getHost());
		Assert.assertEquals(9200, output.getPort());
		Assert.assertTrue(output.isPrintElasticSearchLog());
		Assert.assertEquals("my-index", output.getIndex());
		Assert.assertEquals("new-type", output.getMappingType());
	}

	private void _testOutputFile(FileOutputConfig output) {
		Assert.assertNull(output.getParser());
		Assert.assertNull(output.getFilter());
		Assert.assertNull(output.getOutputFormatter());
		Assert.assertTrue(output.getDataWritingListeners().isEmpty());
		Assert.assertEquals("Hugo de Payens", output.getMetadata("crusader"));
		
		Assert.assertEquals("src/test/resources/server.log", output.getFile().getPath());
		Assert.assertTrue(output.isAppend());
		Assert.assertEquals("ISO-8859-1", output.getEncoding());
	}

	private void _testOutputStandard(StandardOutputConfig output) {
		Assert.assertTrue(output.getParser() instanceof PlainTextParser);
		Assert.assertTrue(output.getFilter() instanceof DefaultEmptyFilter);
		Assert.assertTrue(output.getOutputFormatter() instanceof PlainTextFormatter);
		Assert.assertTrue(output.getDataWritingListeners().size() == 1);
		Assert.assertEquals("Bernardus Guidonis", output.getMetadata("inquisitor"));
	}
}