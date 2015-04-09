package com.github.aureliano.damihilogs.report;

import java.util.HashMap;
import java.util.Map;

public enum ReportLanguage {

	ENGLISH("en"),
	PORTUGUESE("pt");
	
	private String abbreviation;
	private Map<String, String> labels;
	
	private ReportLanguage(String abbreviation) {
		this.abbreviation = abbreviation;
		this.prepareLabels();
	}
	
	private void prepareLabels() {
		this.labels = new HashMap<String,String>();
		if ("en".equals(this.abbreviation)) {
			this.prepareEnglishLabels();
		} else if ("pt".equals(this.abbreviation)) {
			this.preparePortugueseLabels();
		}
	}
	
	private void prepareEnglishLabels() {
		this.labels.put("index.collector.subtitle", "Collectors execution");
		this.labels.put("index.title", "Execution Log Collectors Report");
		this.labels.put("index.description", "Management of log events collection.");
		this.labels.put("index.collectors.empty", "There's not any collector execution to show.");
		
		this.labels.put("collector.description", "Configuration description of executor.");
		this.labels.put("collector.execution.subtitle", "Execution history");
		this.labels.put("collectors.empty", "There's not any collector execution to show.");
		this.labels.put("collector.execution.description", "Execution description of executor.");
		this.labels.put("collector.execution.detail", "Execution details");
		this.labels.put("collector.execution.profile", "Profile");
		this.labels.put("collector.execution.exceptions", "Exceptions");
		this.labels.put("collector.execution.exceptions.empty", "There's not any exception to show.");
		this.labels.put("collector.execution.free.memory", "Free memory");
		this.labels.put("collector.execution.max.memory", "Max memory");
		this.labels.put("collector.execution.total.memory", "Total memory");
		this.labels.put("collector.execution.processors.available", "Processors available");
		this.labels.put("collector.execution.time.elapsed", "Time elapsed");
		this.labels.put("collector.execution.output.log", "Output log");
		
		this.labels.put("status.ok", "Success");
		this.labels.put("status.bad", "Failed");
		
		this.labels.put("collector.id", "Collector");
		this.labels.put("collector.status", "Status");
		this.labels.put("collector.time.elapsed", "Time elapsed");
		this.labels.put("collector.execution.id", "Execution");
	}
	
	private void preparePortugueseLabels() {
		this.labels.put("index.collector.subtitle", "Execu\u00e7\u00e3o dos coletores");
		this.labels.put("index.title", "Relat\u00f3rio de Execu\u00e7\u00e3o dos Coletores de Log");
		this.labels.put("index.description", "Ger\u00eancia de coleta de log de eventos.");
		this.labels.put("index.collectors.empty", "N\u00e3o h\u00e1 execu\u00e7\u00f5es de coletores a exibir.");
		
		this.labels.put("collector.description", "Descri\u00e7\u00e3o da configura\u00e7\u00e3o de executor.");
		this.labels.put("collector.execution.subtitle", "Hist\u00f3rico de execu\u00e7\u00e3o");
		this.labels.put("collectors.empty", "N\u00e3o h\u00e1 execu\u00e7\u00f5es de coletores a exibir.");
		this.labels.put("collector.execution.description", "Descri\u00e7\u00e3o da execu\u00e7\u00e3o do processo.");
		this.labels.put("collector.execution.detail", "Detalhes da execu\u00e7\u00e3o");
		this.labels.put("collector.execution.profile", "Desempenho");
		this.labels.put("collector.execution.exceptions", "Exce\u00e7\u00f5es");
		this.labels.put("collector.execution.exceptions.empty", "N\u00e3o h\u00e1 exce\u00e7\u00f5es a exibir.");
		this.labels.put("collector.execution.free.memory", "Mem\u00f3ria livre");
		this.labels.put("collector.execution.max.memory", "Mem\u00f3ria m\u00e1xima");
		this.labels.put("collector.execution.total.memory", "Total de mem\u00f3ria");
		this.labels.put("collector.execution.processors.available", "Processadores dispon\u00edveis");
		this.labels.put("collector.execution.time.elapsed", "Tempo gasto");
		this.labels.put("collector.execution.output.log", "Sa\u00edda de log");
		
		this.labels.put("status.ok", "Sucesso");
		this.labels.put("status.bad", "Falhou");
		
		this.labels.put("collector.id", "Coletor");
		this.labels.put("collector.status", "Status");
		this.labels.put("collector.time.elapsed", "Tempo gasto");
		this.labels.put("collector.execution.id", "Executor");
	}
	
	public String getLabel(String key) {
		return this.labels.get(key);
	}
	
	public String getAbbreviation() {
		return this.abbreviation;
	}
}