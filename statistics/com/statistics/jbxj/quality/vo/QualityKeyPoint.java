package com.statistics.jbxj.quality.vo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class QualityKeyPoint {
	private String area;
	private String startDate;
	private String endDate;
	private Map<String,List<QualityVo>> map = new LinkedHashMap<String, List<QualityVo>>();
	
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
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
	public Map<String, List<QualityVo>> getMap() {
		return map;
	}
	public void setMap(Map<String, List<QualityVo>> map) {
		this.map = map;
	}
	
	
}
