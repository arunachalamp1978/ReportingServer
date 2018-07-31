package org.arun.server.reportserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arun.project.report.builder.CSVBuilder;
import com.arun.project.report.builder.Report;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Paths;

import com.arun.project.report.builder.ReportBuilder;
import com.arun.project.report.builder.ReportEnum;
import com.arun.project.report.builder.ReportProperties;
import com.arun.project.report.processor.ReportServer;

public class TestReportServer {
	 ReportEnum reportStat;
	 ReportProperties rptProp = new ReportProperties();
	final static Logger log = LoggerFactory.getLogger(TestReportServer.class); 
	
	@BeforeEach
	void setup() {
	    log.info("@BeforeAll - executes once before all test methods in this class");
	}
	
	@Test
	@DisplayName("Should be FAILURE")
	public void testReportServerforFailure() {
		rptProp.setConfigFile(Paths.get("FixedLength.pzmap.xml").toString());
		rptProp.setInputFile("input.txt");
		rptProp.setOutputFile("output.csv");
		rptProp.setDatFilter("20190909");
		ReportBuilder rptBuilder = new CSVBuilder();
		  ReportServer rptServer = new ReportServer(rptBuilder, rptProp);
		  Report rpt =rptServer.GenerateReport();
		  assertTrue(rpt.getRptStatus().equals(ReportEnum.FAILED_NO_RECORD_FOUND));
		  
	}
	
	@Test
	@DisplayName("Should be SUCCESS")
	public void testReportServerforSuccess() {
		rptProp.setConfigFile(Paths.get("FixedLength.pzmap.xml").toString());
		rptProp.setInputFile("input.txt");
		rptProp.setOutputFile("output.csv");
		rptProp.setDatFilter("20100820");
		ReportBuilder rptBuilder = new CSVBuilder();
		  ReportServer rptServer = new ReportServer(rptBuilder, rptProp);
		  Report rpt =rptServer.GenerateReport();
		  assertTrue(rpt.getRptStatus().equals(ReportEnum.SUCCESS));
		  
	}


	
}
