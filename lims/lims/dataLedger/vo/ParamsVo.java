package lims.dataLedger.vo;

public class ParamsVo {
	private String startTime;
	private String endTime;
	private String areaName;
	private String plant;
	private String matCode;
	private String samplePointId;
	
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
	public String getMatCode() {
		return matCode;
	}
	public void setMatCode(String matCode) {
		this.matCode = matCode;
	}
	public String getSamplePointId() {
		return samplePointId;
	}
	public void setSamplePointId(String samplePointId) {
		this.samplePointId = samplePointId;
	}
	
}
