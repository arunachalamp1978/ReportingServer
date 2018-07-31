package com.arun.project.report.server;

import java.io.IOException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arun.project.report.builder.CSVBuilder;
import com.arun.project.report.builder.Report;
import com.arun.project.report.builder.ReportBuilder;
import com.arun.project.report.builder.ReportProperties;
import com.arun.project.report.processor.ReportServer;

public class BootStrap {
	final static Logger log = LoggerFactory.getLogger(BootStrap.class);
	  public static void main( String[] args ) throws IOException
	    {
		  try
		  {
			  log.info("Arguments : " + Arrays.toString(args));
			  ReportProperties rptProp = new ReportProperties();
			  rptProp.setConfigFile("FixedLength.pzmap.xml");
			  
			  if (args.length < 2) {
		            System.out.println("Invalid arguments : Please provide <INPUT_FILE>, <OUTPUT_FILE>, <DATE_FILTER> (YYYYMMDD: optional)");
		        } 
		         else {
		        	 if (args.length >= 2)
			            {
				            rptProp.setInputFile(args[0]);
				            rptProp.setOutputFile(args[1]);
				            
				            if (args.length == 3)
				            {
				            	rptProp.setDatFilter(args[2]);
				            }
			            }
		 		 
		 		  ReportBuilder rptBuilder = new CSVBuilder();
		 		  ReportServer rptServer = new ReportServer(rptBuilder, rptProp);
		 		  Report rpt =rptServer.GenerateReport();
		 		  
		 		  log.debug("Report Status : : " + rpt.getRptStatus() );
			      System.out.println( "Report Status : " + rpt.getRptStatus());
		           
		        }
		  }
		  catch(Exception ex)
		  {
			  log.info(ex + "");
		  }
		 
	    }
}


