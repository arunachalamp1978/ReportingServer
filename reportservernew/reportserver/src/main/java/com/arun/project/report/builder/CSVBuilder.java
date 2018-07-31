package com.arun.project.report.builder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.arun.server.reportserver.model.ClientReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arun.project.report.server.BootStrap;
import com.arun.project.util.StringUtil;
import com.opencsv.CSVWriter;

import net.sf.flatpack.DataSet;
import net.sf.flatpack.DefaultParserFactory;
import net.sf.flatpack.Parser;

public class CSVBuilder implements ReportBuilder {


	private Report report;
	public ReportEnum reportStat;
	
	final String TRANSACTION_DATE="TransactionDate";
	final String CLIENT_INFORMATION="ClientInformation";
	final String PRODUCT_INFROMATION="ProductInformation";
	final String TOTAL_AMOUNT = "TotalAmount";
			
			
	final Logger log = LoggerFactory.getLogger(CSVBuilder.class);
	private ReportProperties rptProplocal;
	List<ClientReport> lstClientRpt;
	
	public CSVBuilder()
	{
		this.report = new Report();
		
		
	}
	
	@Override
	public void setProperties(ReportProperties rptProp) {
		// TODO Auto-generated method stub
		rptProplocal = rptProp;
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
		lstClientRpt = new ArrayList<ClientReport>();
		
		 Parser pzparsers = null;
		try {
			pzparsers = DefaultParserFactory.getInstance().newFixedLengthParser(new FileReader(rptProplocal.getConfigFile()), new FileReader(rptProplocal.getInputFile()));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        pzparsers.setIgnoreExtraColumns(true);
	        final DataSet ds = pzparsers.parse();
	        
	        while (ds.next()) {
	        	
	        	ClientReport cb = new ClientReport();
	        	cb.setClientInformation(ds.getString("CLIENTTYPE") + ds.getString("CLIENTNUMBER") + ds.getString("ACCOUNTNUMBER")+
	        			ds.getString("SUBACCOUNTNUMBER"));
	        	cb.setProductInformation(ds.getString("EXCHANGE_CODE") + ds.getString("PRODUCT_GROUP_CODE") + ds.getString("SYMBOL")+
	        			ds.getString("EXPIRATION_DATE"));
	        	cb.setTotalTransactionAmt(ds.getInt("QUANTITY_LONG") - ds.getInt("QUANTITY_SHORT"));
	        	cb.setTransDate(ds.getString("TRANSACTION_DATE"));
	        	lstClientRpt.add(cb);	        	

	        }  

	        log.info("Initialized data Successfully");

	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		try {
			twoLevelGrouping(rptProplocal.getDatFilter());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.debug("" + e.getStackTrace());
		}
		 
	}
	
	private void twoLevelGrouping(String dat) throws IOException {
	     
	     Map<String, Map<List<String>,Integer>> summary = lstClientRpt.stream()
	    		 	.filter(l -> 
	    		 		StringUtil.isNullOrBlank(dat) || dat.equals(l.getTransDate() // Apply date filter when passes
	    		 	 ))
		    	    .collect(Collectors.groupingBy(ClientReport::getTransDate, Collectors.groupingBy(
		    	    				x -> new ArrayList<String>(
		    	    							Arrays.asList(x.getTransDate(),
		    	    											x.getClientInformation(),
		    	    											x.getProductInformation()
		    	    							)),
		    	    			Collectors.summingInt(ClientReport::getTotalTransactionAmt))));
	     
	     if (summary != null && summary.isEmpty())
	     {
	    	 log.info("" + ReportEnum.FAILED_NO_RECORD_FOUND);
	    	 report.setRptStatus(ReportEnum.FAILED_NO_RECORD_FOUND);
	     }
	     else 
	     {
	    	 processRecords(summary);
	    	 log.info(ReportEnum.SUCCESS + "");
	    	 report.setRptStatus(ReportEnum.SUCCESS);
	     }
	     
	     

	}
	
	private void processRecords(Map<String, Map<List<String>,Integer>> summary) throws IOException
	{
		
		
		ArrayList<String[]> str = new ArrayList<>();
		str.add( new String[] {TRANSACTION_DATE,CLIENT_INFORMATION,PRODUCT_INFROMATION,TOTAL_AMOUNT});
		
		 for (Entry<String, Map<List<String>, Integer>> entry : summary.entrySet()){
			 
	            Map<List<String>,Integer> ent = entry.getValue();
	            
	            List<String> transaction= null;
				 for (Entry<List<String>, Integer> entry1 : ent.entrySet()){
					 
					 transaction = entry1.getKey();
					 transaction.add(Integer.toString(entry1.getValue()));
					 
					 String[] s = transaction.toArray(new String[transaction.size()]);
					 str.add(s);
				 }
				 
	        }
		 writeHashMapToCsv(str);
	}
	
	private void writeHashMapToCsv(ArrayList<String[]> records) throws IOException {
		
		try (
				 Writer writer = Files.newBufferedWriter(Paths.get(rptProplocal.getOutputFile()));
				 CSVWriter csvWriter = new CSVWriter(writer,
		                    CSVWriter.DEFAULT_SEPARATOR,
		                    CSVWriter.NO_QUOTE_CHARACTER,
		                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
		                    CSVWriter.DEFAULT_LINE_END);
					) {
			csvWriter.writeAll(records);
			log.debug("CSV Written Successfully");
		}
		
	}



	@Override
	public Report getStatus() {
		// TODO Auto-generated method stub
		return report;
	}

	@Override
	public Report generateReport() {
		// Left blank for future enhancements
		return null;
	}

}
