package com.lims.testflow.vo;

public class Orders {
	private String folderNo;
	private String ordNo;
	private boolean isParallel;		//是否平行样
	private int serial;				//序号
	
	public String getFolderNo() {
		return folderNo;
	}
	public void setFolderNo(String folderNo) {
		this.folderNo = folderNo;
	}
	public String getOrdNo() {
		return ordNo;
	}
	public void setOrdNo(String ordNo) {
		this.ordNo = ordNo;
	}
	public boolean isParallel() {
		return isParallel;
	}
	public void setParallel(boolean isParallel) {
		this.isParallel = isParallel;
	}
	public int getSerial() {
		return serial;
	}
	public void setSerial(int serial) {
		this.serial = serial;
	}
	
}
