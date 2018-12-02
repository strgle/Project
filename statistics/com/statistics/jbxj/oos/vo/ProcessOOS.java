package com.statistics.jbxj.oos.vo;

public class ProcessOOS {
	private String matcode;
	private Integer testcode;
	private String analyte;
	private String matname;
	private int oosNum = 0;
	private Integer[] dayOosNum = new Integer[31];
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
	public int getOosNum() {
		return oosNum;
	}
	public void setOosNum(int oosNum) {
		this.oosNum = oosNum;
	}
	public Integer[] getDayOosNum() {
		return dayOosNum;
	}
	public void setDayOosNum(Integer[] dayOosNum) {
		this.dayOosNum = dayOosNum;
	}
	public String getMatname() {
		return matname;
	}
	public void setMatname(String matname) {
		this.matname = matname;
	}
}
