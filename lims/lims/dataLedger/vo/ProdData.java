package lims.dataLedger.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProdData {
	//车间、装置
	private String areaName;
	private String plant;
	
	//日期
	private String prodDate;
	
	//方案
	private List<String> prodTimes=new ArrayList<String>();
	
	//具体值
	private List<Map<String,Object>> itemValues = new ArrayList<Map<String,Object>>();
	
	//具体值
	private List<Map<String,Object>> slValues = new ArrayList<Map<String,Object>>();
	
	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	public String getProdDate() {
		return prodDate;
	}

	public void setProdDate(String prodDate) {
		this.prodDate = prodDate;
	}

	public List<String> getProdTimes() {
		return prodTimes;
	}

	public void setProdTimes(List<String> prodTimes) {
		this.prodTimes = prodTimes;
	}

	public List<Map<String, Object>> getItemValues() {
		return itemValues;
	}

	public void setItemValues(List<Map<String, Object>> itemValues) {
		this.itemValues = itemValues;
	}

	public List<Map<String, Object>> getSlValues() {
		return slValues;
	}

	public void setSlValues(List<Map<String, Object>> slValues) {
		this.slValues = slValues;
	}
	
}
