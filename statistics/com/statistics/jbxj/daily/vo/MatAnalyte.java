package com.statistics.jbxj.daily.vo;

import pers.czf.dbManager.annotation.Table;

@Table("JBXJ_DS_ANALYTE")
public class MatAnalyte {
	private String id;
	private String matcode;
	private Integer testcode;
	private String analyte;
	private String sinonym;
	private Integer sort;
	private String status;
	private String charlimits;
	private String isMonitor;
	private String isXj;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMatcode() {
		return matcode;
	}
	public void setMatcode(String matcode) {
		this.matcode = matcode;
	}
	public Integer getTestcode() {
		return testcode;
	}
	public void setTestcode(Integer testcode) {
		this.testcode = testcode;
	}
	public String getAnalyte() {
		return analyte;
	}
	public void setAnalyte(String analyte) {
		this.analyte = analyte;
	}
	public String getSinonym() {
		return sinonym;
	}
	public void setSinonym(String sinonym) {
		this.sinonym = sinonym;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
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
	public String getIsMonitor() {
		return isMonitor;
	}
	public void setIsMonitor(String isMonitor) {
		this.isMonitor = isMonitor;
	}
	public String getIsXj() {
		return isXj;
	}
	public void setIsXj(String isXj) {
		this.isXj = isXj;
	}
	
}
