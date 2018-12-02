package lims.areaPlants.vo;

import java.util.ArrayList;
import java.util.List;

public class Plants {
	private String id;
	private String title;
	private String type;
	private String areaName;
	private String value;
	private List<Points> children = new ArrayList<Points>();
	private List<Materials> mats = new ArrayList<Materials>();
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public List<Points> getChildren() {
		return children;
	}
	public void setChildren(List<Points> children) {
		this.children = children;
	}
	public List<Materials> getMats() {
		return mats;
	}
	public void setMats(List<Materials> mats) {
		this.mats = mats;
	}
	
}
