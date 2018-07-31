package com.arun.project.report.builder;

public class ReportProperties {
 private String datFilter;
 
 
 private String inputFile;
 public String getInputFile() {
	return inputFile;
}
public void setInputFile(String inputFile) {
	this.inputFile = inputFile;
}
public String getConfigFile() {
	return configFile;
}
public void setConfigFile(String configFile) {
	this.configFile = configFile;
}
public String getOutputFile() {
	return outputFile;
}
public void setOutputFile(String outputFile) {
	this.outputFile = outputFile;
}
public String getDatFilter() {
	return datFilter;
}
public void setDatFilter(String datFilter) {
	this.datFilter = datFilter;
}
private String configFile;
 private String outputFile;
}
