package lims.dataSearch.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Orders {
	private String ordNo;
	private String plant;
	private String pointdesc;
	private String sampDate;
	private String batchName;
	private String grade;
	private String type;
	private String taskType;
	private String suppCode;
	private String matName;
	private String status;
	private List<Map<String,Object>> analytes = new ArrayList<Map<String,Object>>();
	public String getOrdNo() {
		return ordNo;
	}
	public void setOrdNo(String ordNo) {
		this.ordNo = ordNo;
	}
	public String getPlant() {
		return plant;
	}
	public void setPlant(String plant) {
		this.plant = plant;
	}
	public String getPointdesc() {
		return pointdesc;
	}
	public void setPointdesc(String pointdesc) {
		this.pointdesc = pointdesc;
	}
	public String getSampDate() {
		return sampDate;
	}
	public void setSampDate(String sampDate) {
		this.sampDate = sampDate;
	}
	public List<Map<String, Object>> getAnalytes() {
		return analytes;
	}
	public void setAnalytes(List<Map<String, Object>> analytes) {
		this.analytes = analytes;
	}
	public String getBatchName() {
		return batchName;
	}
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	public String getGrade() {
		return grade;
	}
	
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public String getSuppCode() {
		return suppCode;
	}
	public void setSuppCode(String suppCode) {
		this.suppCode = suppCode;
	}
	public String getMatName() {
		return matName;
	}
	public void setMatName(String matName) {
		this.matName = matName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
