package com.github.aureliano.damihilogs.report;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.github.aureliano.damihilogs.exception.DaMihiLogsException;
import com.github.aureliano.damihilogs.helper.ReportHelper;
import com.github.aureliano.damihilogs.report.model.CollectorModel;
import com.x5.template.Chunk;
import com.x5.template.Theme;

public class HtmlReporter implements ILoggerReporter {

	private ReportLanguage language;
	private String pageTitle;
	private String description;
	private File outputDir;
	
	private static final Logger logger = Logger.getLogger(HtmlReporter.class);
	
	public HtmlReporter() {
		this.language = ReportLanguage.ENGLISH;
		this.pageTitle = this.language.getLabel("index.title");
		this.description = this.language.getLabel("index.description");
	}

	@Override
	public void buildReport() {
		this.validateOutputDir();
		
		if (!this.outputDir.exists()) {
			if (!this.outputDir.mkdirs()) {
				throw new DaMihiLogsException("Could not create all chain of directories.");
			}
		}
		
		this.generateHtmlIndexPage();
		logger.info("Report generated into " + this.outputDir.getPath());
	}
	
	protected void validateOutputDir() {
		if (this.outputDir == null) {
			throw new DaMihiLogsException("Report output dir not provided.");
		} else if (this.outputDir.isFile()) {
			throw new DaMihiLogsException(outputDir.getPath() + " must be a directory (existing or not).");
		}
	}
	
	private void generateHtmlIndexPage() {
		String htmlTemplate = ReportHelper.loadHtmlTemplate("report/index-template.html");
		
		Chunk html = new Theme().makeChunk();
		html.append(htmlTemplate);
		
		List<CollectorModel> collectorExecutions = ReportHelper.getCollectorExecutions();
		for (CollectorModel c : collectorExecutions) {
			c.withTextStatus(c.getStatus() ? this.language.getLabel("status.ok") : this.language.getLabel("status.bad"));
			
			this.generateHtmlCollectorPage(c);
		}
		
		html.set("pageTitle", this.pageTitle);
		html.set("description", this.description);
		html.set("collectors", collectorExecutions);
		
		html.set("statusLabel", this.language.getLabel("collector.status"));
		html.set("collectorLabel", this.language.getLabel("collector.id"));
		html.set("timeElapsedLabel", this.language.getLabel("collector.time.elapsed"));
		html.set("collectorsSubtitle", this.language.getLabel("index.collector.subtitle"));
		html.set("emptyCollectors", this.language.getLabel("index.collectors.empty"));
		
		try {
			File file = new File(this.outputDir.getPath() + File.separator + "index.html");
			logger.debug(" >>> Saving index page.");
			html.render(new PrintStream(file));
		} catch (IOException ex) {
			throw new DaMihiLogsException(ex);
		}
	}

	private void generateHtmlCollectorPage(CollectorModel model) {
		String collectorTemplate = ReportHelper.loadHtmlTemplate("report/collector-template.html");
		
		Chunk html = new Theme().makeChunk();
		html.append(collectorTemplate);
		
		List<CollectorModel> collectors = new ArrayList<CollectorModel>();
		CollectorModel collectorExecution = ReportHelper.getLastCollectorExecution(model.getId());
		collectorExecution.withTextStatus(collectorExecution.getStatus() ? this.language.getLabel("status.ok") : this.language.getLabel("status.bad"));
		collectors.add(collectorExecution);
		
		this.generateHtmlExecutionPage(collectorExecution, model.getId());
		
		for (CollectorModel c : ReportHelper.getOldCollectorExecutions(this.outputDir, model.getId())) {
			if (collectorExecution.equals(c)) {
				continue;
			}
			
			c.withTextStatus(c.getStatus() ? this.language.getLabel("status.ok") : this.language.getLabel("status.bad"));
			collectors.add(c);
		}
		
		Collections.sort(collectors);
		
		html.set("pageTitle", this.pageTitle + " - " + model.getId());
		html.set("pageSubTitle", this.language.getLabel("collector.id") + " " + model.getId());
		html.set("collectors", collectors);
		html.set("description", this.language.getLabel("collector.description"));
		html.set("id", model.getId());
		
		html.set("statusLabel", this.language.getLabel("collector.status"));
		html.set("collectorLabel", this.language.getLabel("collector.id"));
		html.set("timeElapsedLabel", this.language.getLabel("collector.time.elapsed"));
		html.set("executionsSubtitle", this.language.getLabel("collector.execution.subtitle"));
		html.set("emptyCollectors", this.language.getLabel("collectors.empty"));
		
		try {
			File file = new File(this.outputDir.getPath() + File.separator + model.getId() + ".html");
			logger.debug("Saving collector page " + model.getId() + ".html");
			html.render(new PrintStream(file));
		} catch (IOException ex) {
			throw new DaMihiLogsException(ex);
		}
	}

	private void generateHtmlExecutionPage(CollectorModel model, String collectorId) {
		String executionTemplate = ReportHelper.loadHtmlTemplate("report/execution-template.html");
		
		Chunk html = new Theme().makeChunk();
		html.append(executionTemplate);
		
		html.set("pageTitle", this.pageTitle + " - " + model.getId());
		html.set("pageSubTitle", this.language.getLabel("collector.execution.id") + " " + model.getId());
		html.set("description", this.language.getLabel("collector.execution.description"));
		html.set("executionsSubtitle", this.language.getLabel("collector.execution.detail"));
		html.set("profileTitle", this.language.getLabel("collector.execution.profile"));
		html.set("exceptionsTitle", this.language.getLabel("collector.execution.exceptions"));
		html.set("exceptionsEmpty", this.language.getLabel("collector.execution.exceptions.empty"));
		
		html.set("freeMemoryLabel", this.language.getLabel("collector.execution.free.memory"));
		html.set("maxMemoryLabel", this.language.getLabel("collector.execution.max.memory"));
		html.set("usedMemoryLabel", this.language.getLabel("collector.execution.used.memory"));
		html.set("totalMemoryLabel", this.language.getLabel("collector.execution.total.memory"));
		html.set("processorsAvailableLabel", this.language.getLabel("collector.execution.processors.available"));
		html.set("timeInitLabel", this.language.getLabel("collector.time.init"));
		html.set("timeEndLabel", this.language.getLabel("collector.time.end"));
		html.set("timeElapsedLabel", this.language.getLabel("collector.time.elapsed"));
		html.set("consoleOutputTitle", this.language.getLabel("collector.execution.output.log"));
		
		html.set("collector", model);
		html.set("exceptions", model.getExceptions());
		
		try {
			File file = new File(this.outputDir.getPath() + File.separator + collectorId + "_" + model.getExecutionId() + ".html");
			logger.debug("Saving execution page " + model.getExecutionId() + ".html");
			html.render(new PrintStream(file));
		} catch (IOException ex) {
			throw new DaMihiLogsException(ex);
		}
	}

	@Override
	public HtmlReporter withLanguage(ReportLanguage language) {
		this.language = language;
		return this;
	}

	@Override
	public ReportLanguage getLanguage() {
		return this.language;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public HtmlReporter withPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public HtmlReporter withDescription(String description) {
		this.description = description;
		return this;
	}

	@Override
	public HtmlReporter withOutputDir(File outputDir) {
		this.outputDir = outputDir;
		return this;
	}

	@Override
	public File getOutputDir() {
		return this.outputDir;
	}
}