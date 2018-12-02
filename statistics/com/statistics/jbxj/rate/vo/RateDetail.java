package com.statistics.jbxj.rate.vo;

import pers.czf.dbManager.annotation.Table;

@Table("JBXJ_RATE_Detail")
public class RateDetail {
	private String id;
	private String reportId;
	private int month;
	private String brand;
	private String grade;
	private Float totalNum;
	private String dataSource;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public Float getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Float totalNum) {
		this.totalNum = totalNum;
	}
	public int getMonth() {
		return month;
	}
	
	public void setMonth(int month) {
		this.month = month;
	}
	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	
}
