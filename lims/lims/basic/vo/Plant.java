package lims.basic.vo;

public class Plant {

	private String areaName;
    private String plant;
    private Float basicRate;
    private String isCheck;
    private String username;
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public Float getBasicRate() {
		return basicRate;
	}
	public void setBasicRate(Float basicRate) {
		this.basicRate = basicRate;
	}
	public String getIsCheck() {
		return isCheck;
	}
	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}
    
}
