package com.statistics.jbxj.quality.vo;

import java.util.ArrayList;
import java.util.List;

public class ProductRate {
	private String matcode;
	private String matname;
	private int doneNum;
	private int totalNum;
	private List<TestsVo> tests = new ArrayList<TestsVo>();
	private List<AnalytesVo> analytes = new ArrayList<AnalytesVo>();
	
	public String getMatcode() {
		return matcode;
	}
	public void setMatcode(String matcode) {
		this.matcode = matcode;
	}
	public String getMatname() {
		return matname;
	}
	public void setMatname(String matname) {
		this.matname = matname;
	}
	public List<TestsVo> getTests() {
		return tests;
	}
	public void setTests(List<TestsVo> tests) {
		this.tests = tests;
	}
	public int getDoneNum() {
		return doneNum;
	}
	public void setDoneNum(int doneNum) {
		this.doneNum = doneNum;
	}
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	public List<AnalytesVo> getAnalytes() {
		return analytes;
	}
	public void setAnalytes(List<AnalytesVo> analytes) {
		this.analytes = analytes;
	}
	
}
