package com.statistics.jbxj.daily.vo;

import java.util.List;
import java.util.Map;

public class DailyHz {
	
	private List<Map<String,Object>> titles;
	
	private List<DailyVo> lineA;
	
	private List<DailyVo> lineB;

	public List<Map<String, Object>> getTitles() {
		return titles;
	}

	public void setTitles(List<Map<String, Object>> titles) {
		this.titles = titles;
	}

	public List<DailyVo> getLineA() {
		return lineA;
	}

	public void setLineA(List<DailyVo> lineA) {
		this.lineA = lineA;
	}

	public List<DailyVo> getLineB() {
		return lineB;
	}

	public void setLineB(List<DailyVo> lineB) {
		this.lineB = lineB;
	}

	
}
