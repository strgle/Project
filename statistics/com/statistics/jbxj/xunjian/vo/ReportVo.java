package com.statistics.jbxj.xunjian.vo;

import pers.czf.dbManager.annotation.Table;

@Table("JBXJ_XJ_REPORT")
public class ReportVo {
	private String id;
	private String matType;
	private String startTime;
	private String endTime;
	private String status;
	private String tjTime;
	private String matcode;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMatType() {
		return matType;
	}
	public void setMatType(String matType) {
		this.matType = matType;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTjTime() {
		return tjTime;
	}
	public void setTjTime(String tjTime) {
		this.tjTime = tjTime;
	}
	public String getMatcode() {
		return matcode;
	}
	public void setMatcode(String matcode) {
		this.matcode = matcode;
	}
	
}
