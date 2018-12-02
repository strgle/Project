package com.statistics.jbxj.quality.vo;

import pers.czf.dbManager.annotation.Table;

@Table("JBXJ_Quality_keypoint")
public class ProcessKeyPoint {
	private String id;
	private String parentId;
	private String matcode;
	private Integer testcode;
	private String analyte;
	private String sinonym;
	private Integer sort;
	private String pointId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
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
	public String getPointId() {
		return pointId;
	}
	public void setPointId(String pointId) {
		this.pointId = pointId;
	}
}
