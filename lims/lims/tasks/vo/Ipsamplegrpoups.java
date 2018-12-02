package lims.tasks.vo;

import java.util.ArrayList;
import java.util.List;

public class Ipsamplegrpoups {

	private String Prodgroup;
	
	private List<Ipsamplegroupdetails> list = new ArrayList<Ipsamplegroupdetails>();
	public String getProdgroup() {
		return Prodgroup;
	}
	public void setProdgroup(String prodgroup) {
		Prodgroup = prodgroup;
	}
	public List<Ipsamplegroupdetails> getList() {
		return list;
	}
	public void setList(List<Ipsamplegroupdetails> list) {
		this.list = list;
	}
	
}
