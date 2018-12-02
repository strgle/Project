package com.statistics.jbxj.xunjian.vo;

import pers.czf.dbManager.annotation.Table;

@Table("JBXJ_XJ_MAT")
public class XjSettingVo {
	private String id;
	private String matType;
	private String area;
	private String plant;
	private String pointId;
	private String matcode;
	private Integer testcode;
	private String analyte;
	private String sinonym;
	private int sort;
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
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getPlant() {
		return plant;
	}
	public void setPlant(String plant) {
		this.plant = plant;
	}
	public String getPointId() {
		return pointId;
	}
	public void setPointId(String pointId) {
		this.pointId = pointId;
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
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getSinonym() {
		return sinonym;
	}
	public void setSinonym(String sinonym) {
		this.sinonym = sinonym;
	}
	
}
