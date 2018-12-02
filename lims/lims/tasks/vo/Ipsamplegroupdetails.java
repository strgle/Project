package lims.tasks.vo;

import java.util.ArrayList;
import java.util.List;

public class Ipsamplegroupdetails {

	private String Samplegroupcode;
	private String Samplegroupname;
	private String Prodgroup;
	private List<SamplePrograms> list = new ArrayList<SamplePrograms>();
	
	public String getProdgroup() {
		return Prodgroup;
	}
	public void setProdgroup(String prodgroup) {
		Prodgroup = prodgroup;
	}
	public String getSamplegroupcode() {
		return Samplegroupcode;
	}
	public void setSamplegroupcode(String samplegroupcode) {
		Samplegroupcode = samplegroupcode;
	}
	public String getSamplegroupname() {
		return Samplegroupname;
	}
	public void setSamplegroupname(String samplegroupname) {
		Samplegroupname = samplegroupname;
	}
	public List<SamplePrograms> getList() {
		return list;
	}
	public void setList(List<SamplePrograms> list) {
		this.list = list;
	}
	
}
