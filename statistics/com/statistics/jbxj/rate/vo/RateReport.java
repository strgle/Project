package com.statistics.jbxj.rate.vo;

import java.util.ArrayList;
import java.util.List;

import pers.czf.dbManager.annotation.Table;

@Table("JBXJ_RATE_report")
public class RateReport {
	private String id;
	private String reportType;
	private String startTime;
	private String endTime;
	private String matcode;
	private String matName;
	private String matType;
	private String tjDate;
	private int totalNum;
	private int done;	//优等数
	private int oosB;	//合格数
	private int oosA;	//不合格数
	private RateDetail rollBack;	//回切数
	private RateDetail rollBackRate;	//回切合格率
	private String status;
	private int year;
	private int month;
	private String operFalg;
	private List<RateDetail> rateDetail = new ArrayList<RateDetail>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
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
	public String getMatcode() {
		return matcode;
	}
	public void setMatcode(String matcode) {
		this.matcode = matcode;
	}
	public String getMatType() {
		return matType;
	}
	public void setMatType(String matType) {
		this.matType = matType;
	}
	public String getTjDate() {
		return tjDate;
	}
	public void setTjDate(String tjDate) {
		this.tjDate = tjDate;
	}
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getDone() {
		return done;
	}
	public void setDone(int done) {
		this.done = done;
	}
	public int getOosB() {
		return oosB;
	}
	public void setOosB(int oosB) {
		this.oosB = oosB;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getOosA() {
		return oosA;
	}
	public void setOosA(int oosA) {
		this.oosA = oosA;
	}
	
	public RateDetail getRollBack() {
		return rollBack;
	}
	public void setRollBack(RateDetail rollBack) {
		this.rollBack = rollBack;
	}
	public RateDetail getRollBackRate() {
		return rollBackRate;
	}
	public void setRollBackRate(RateDetail rollBackRate) {
		this.rollBackRate = rollBackRate;
	}
	public String getOperFalg() {
		return operFalg;
	}
	public void setOperFalg(String operFalg) {
		this.operFalg = operFalg;
	}
	public String getMatName() {
		return matName;
	}
	public void setMatName(String matName) {
		this.matName = matName;
	}
	public List<RateDetail> getRateDetail() {
		return rateDetail;
	}
	public void setRateDetail(List<RateDetail> rateDetail) {
		this.rateDetail = rateDetail;
	}
	
}
