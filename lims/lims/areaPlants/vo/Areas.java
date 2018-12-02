package lims.areaPlants.vo;

import java.util.ArrayList;
import java.util.List;

public class Areas {
	private String id;
	private String title;
	private String value;
	private String type;
	private List<Plants> children = new ArrayList<Plants>();
	
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
	public List<Plants> getChildren() {
		return children;
	}
	public void setChildren(List<Plants> children) {
		this.children = children;
	}
	
}
