package com.statistics.jbxj.daily.vo;

import java.util.ArrayList;
import java.util.List;

import pers.czf.dbManager.annotation.Table;

@Table("JBXJ_Daily")
public class DailyVo {
	private String ordno;
	private String tjDate;
	private String matcode;
	private String abline;
	private String batchno;
	private String brand;
	private String comments;
	private String startDate;
	private String endDate;
	private String sampDate;
	private int sort;
	private List<DailyDetailVo> list = new ArrayList<DailyDetailVo>();
	
	public String getOrdno() {
		return ordno;
	}
	public void setOrdno(String ordno) {
		this.ordno = ordno;
	}
	public String getTjDate() {
		return tjDate;
	}
	public void setTjDate(String tjDate) {
		this.tjDate = tjDate;
	}
	public String getMatcode() {
		return matcode;
	}
	public void setMatcode(String matcode) {
		this.matcode = matcode;
	}
	public String getAbline() {
		return abline;
	}
	public void setAbline(String abline) {
		this.abline = abline;
	}
	public String getBatchno() {
		return batchno;
	}
	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	
	public List<DailyDetailVo> getList() {
		return list;
	}
	public void setList(List<DailyDetailVo> list) {
		this.list = list;
	}
	public String getSampDate() {
		return sampDate;
	}
	public void setSampDate(String sampDate) {
		this.sampDate = sampDate;
	}
	
}
