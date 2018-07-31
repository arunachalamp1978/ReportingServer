package org.arun.server.reportserver.model;

public class ClientReport {
	private String clientInformation;
	private String productInformation;
	private Integer totalTransactionAmt;
	private String transDate;
	
	public ClientReport()
	{
		
	}
			
	public String getClientInformation() {
		return clientInformation;
	}
	public void setClientInformation(String clientInformation) {
		this.clientInformation = clientInformation;
	}
	public String getProductInformation() {
		return productInformation;
	}
	public void setProductInformation(String productInformation) {
		this.productInformation = productInformation;
	}
	public Integer getTotalTransactionAmt() {
		return totalTransactionAmt;
	}
	public void setTotalTransactionAmt(Integer totalTransactionAmt) {
		this.totalTransactionAmt = totalTransactionAmt;
	}
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	public ClientReport(String clientInformation, String productInformation, Integer totalTransactionAmt,
			String transDate) {
		this.clientInformation = clientInformation;
		this.productInformation = productInformation;
		this.totalTransactionAmt = totalTransactionAmt;
		this.transDate = transDate;
	}

}

