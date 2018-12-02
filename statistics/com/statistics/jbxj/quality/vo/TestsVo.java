package com.statistics.jbxj.quality.vo;

import java.util.ArrayList;
import java.util.List;

public class TestsVo {
	private int testCode;
	private String testno;
	private List<AnalytesVo> analytes = new ArrayList<AnalytesVo>();
	public int getTestCode() {
		return testCode;
	}
	public void setTestCode(int testCode) {
		this.testCode = testCode;
	}
	public String getTestno() {
		return testno;
	}
	public void setTestno(String testno) {
		this.testno = testno;
	}
	public List<AnalytesVo> getAnalytes() {
		return analytes;
	}
	public void setAnalytes(List<AnalytesVo> analytes) {
		this.analytes = analytes;
	}
	
}
