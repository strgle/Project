/**
 * 
 */
package lims.zltj.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品
 * @author Administrator
 *
 */
public class ChpVo {
	private String code;
	private String name;
	private String group;
	private List<FxxmVo> data = new ArrayList<FxxmVo>();
	
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public void setData(List<FxxmVo> data) {
		this.data = data;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRowNum() {
		return data.size()*3;
	}
	
	public List<FxxmVo> getData() {
		return data;
	}
	
	public void addData(FxxmVo fxxm) {
		data.add(fxxm);
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
