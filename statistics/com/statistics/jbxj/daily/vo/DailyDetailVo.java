package com.statistics.jbxj.daily.vo;

import pers.czf.dbManager.annotation.Table;

@Table("JBXJ_Daily_Detail")
public class DailyDetailVo {
	private String ordno;
	private String matcode;
	private String abline;
	private String batchno;
	private int testCode;
	private String analyte;
	private String finalNum;
	private String status;
	private String charlimits;
	private String startDate;
	private String brand;
	public String getOrdno() {
		return ordno;
	}
	public void setOrdno(String ordno) {
		this.ordno = ordno;
	}

	public String getMatcode() {
		return matcode;
	}
	public void setMatcode(String matcode) {
		this.matcode = matcode;
	}
	public String getAbline() {
		return abline;
	}
	public void setAbline(String abline) {
		this.abline = abline;
	}
	public String getBatchno() {
		return batchno;
	}
	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}
	public int getTestCode() {
		return testCode;
	}
	public void setTestCode(int testCode) {
		this.testCode = testCode;
	}
	public String getAnalyte() {
		return analyte;
	}
	public void setAnalyte(String analyte) {
		this.analyte = analyte;
	}
	public String getFinalNum() {
		return finalNum;
	}
	public void setFinalNum(String finalNum) {
		this.finalNum = finalNum;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getCharlimits() {
		return charlimits;
	}
	public void setCharlimits(String charlimits) {
		this.charlimits = charlimits;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
}
