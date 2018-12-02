package com.statistics.jbxj.oos.vo;

public class ProductOOS {
	private String matcode;
	private Integer testcode;
	private String analyte;
	private String sinonym;
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
	public String getSinonym() {
		return sinonym;
	}
	public void setSinonym(String sinonym) {
		this.sinonym = sinonym;
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

}
