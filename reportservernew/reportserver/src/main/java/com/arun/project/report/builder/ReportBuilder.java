package com.arun.project.report.builder;

public interface ReportBuilder {
	public void setProperties(ReportProperties rptProp);
	public void initialize();
	public void execute();
	public Report generateReport();
	Report getStatus();
}
