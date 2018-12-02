package lims.tasks.vo;

public class ParamsOrdVo {
	private String startTime;
	private String endTime;
	private String status;
	private String matCode;
	private String suppcode;
	private String description;
	
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMatCode() {
		return matCode;
	}
	public void setMatCode(String matCode) {
		this.matCode = matCode;
	}
	public String getSuppcode() {
		return suppcode;
	}
	public void setSuppcode(String suppcode) {
		this.suppcode = suppcode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
