package com.statistics.jbxj.xunjian.vo;

import java.util.ArrayList;
import java.util.List;

public class ProductXjVo {
	private String matCode;
	private String matName;
	private List<XjDetailVo> xjDetail = new ArrayList<XjDetailVo>();
	private XjDetailVo total = new XjDetailVo();
	public String getMatCode() {
		return matCode;
	}
	public void setMatCode(String matCode) {
		this.matCode = matCode;
	}
	public String getMatName() {
		return matName;
	}
	public void setMatName(String matName) {
		this.matName = matName;
	}
	public List<XjDetailVo> getXjDetail() {
		return xjDetail;
	}
	public void setXjDetail(List<XjDetailVo> xjDetail) {
		this.xjDetail = xjDetail;
	}
	public XjDetailVo getTotal() {
		return total;
	}
	public void setTotal(XjDetailVo total) {
		this.total = total;
	}
	
}
