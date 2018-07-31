package com.arun.project.report.processor;

import com.arun.project.report.builder.Report;
import com.arun.project.report.builder.ReportBuilder;
import com.arun.project.report.builder.ReportProperties;

public class ReportServer {
	private ReportBuilder reportBuilder;
	private ReportProperties reportProp;
	
	public ReportServer(ReportBuilder reportBuilder, ReportProperties prop)
	{
		this.reportBuilder = reportBuilder;
		this.reportProp = prop;
	}
	
	public Report GenerateReport() {
		this.reportBuilder.setProperties(reportProp);
		this.reportBuilder.initialize();
		this.reportBuilder.execute();
		this.reportBuilder.generateReport();
		return this.reportBuilder.getStatus();
		
	}
}
