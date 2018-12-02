package com.statistics.jbxj.rate.vo;

import java.util.List;

public class ReportModel {
	private String matName;
	private RateReport product;
	private List<MonitorDetail> monitorDetails;
	
	public String getMatName() {
		return matName;
	}
	public void setMatName(String matName) {
		this.matName = matName;
	}
	
	public RateReport getProduct() {
		return product;
	}
	public void setProduct(RateReport product) {
		this.product = product;
	}
	public List<MonitorDetail> getMonitorDetails() {
		return monitorDetails;
	}
	public void setMonitorDetails(List<MonitorDetail> monitorDetails) {
		this.monitorDetails = monitorDetails;
	}
}
