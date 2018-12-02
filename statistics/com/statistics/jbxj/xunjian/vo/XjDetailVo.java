package com.statistics.jbxj.xunjian.vo;

import pers.czf.dbManager.annotation.Table;

@Table("JBXJ_XJ_DETAIL")
public class XjDetailVo {
	private String reportId;
	private String area;
	private String plant;
	private String pointId;
	private String matcode;
	private String matname;
	private Integer testcode;
	private String analyte;
	private String charlimits;
	private int sort;
	private Integer specno;
	private Integer doneNum;
	private Integer oosbNum;
	private Integer oosaNum;
	private Integer totalNum;
	private String flag;
	
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
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
	public String getAnalyte() {
		return analyte;
	}
	public void setAnalyte(String analyte) {
		this.analyte = analyte;
	}
	public String getCharlimits() {
		return charlimits;
	}
	public void setCharlimits(String charlimits) {
		this.charlimits = charlimits;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public Integer getSpecno() {
		return specno;
	}
	public void setSpecno(Integer specno) {
		this.specno = specno;
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
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
}
