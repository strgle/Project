package com.statistics.jbxj.rate.vo;

import pers.czf.dbManager.annotation.Table;

@Table("JBXJ_MONITOR_DETAIL")
public class MonitorDetail {
	private String id;
	private String reportId;
	private String pointId;
	private String matcode;
	private String matname;
	private Integer testcode;
	private String analyte;
	private String sinonym;
	private Integer doneNum;
	private Integer oosbNum;
	private Integer oosaNum;
	private Integer totalNum;
	private Integer sort;
	private String area;
	
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
	public String getMatcode() {
		return matcode;
	}
	public void setMatcode(String matcode) {
		this.matcode = matcode;
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

	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public void setTestcode(int testcode) {
		this.testcode = testcode;
	}
	public String getMatname() {
		return matname;
	}
	public void setMatname(String matname) {
		this.matname = matname;
	}
	public Integer getTestcode() {
		return testcode;
	}
	public void setTestcode(Integer testcode) {
		this.testcode = testcode;
	}
	public Integer getDoneNum() {
		if(doneNum==null) {
			return 0;
		}
		return doneNum;
	}
	public void setDoneNum(Integer doneNum) {
		this.doneNum = doneNum;
	}
	public Integer getOosbNum() {
		if(oosbNum==null) {
			return 0;
		}
		return oosbNum;
	}
	public void setOosbNum(Integer oosbNum) {
		this.oosbNum = oosbNum;
	}
	public Integer getOosaNum() {
		if(oosaNum==null) {
			return 0;
		}
		return oosaNum;
	}
	public void setOosaNum(Integer oosaNum) {
		this.oosaNum = oosaNum;
	}
	public Integer getTotalNum() {
		if(totalNum==null) {
			return 0;
		}
		return totalNum;
	}
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getPointId() {
		return pointId;
	}
	public void setPointId(String pointId) {
		this.pointId = pointId;
	}
	
}
