package lims.tasks.vo;


public class SamplePrograms {

	private String spcode;
	private String progname;
	private String Samplegroupcode;
	private String Samplegroupname;
	private String Prodgroup;
	
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
	public String getSpcode() {
		return spcode;
	}
	public void setSpcode(String spcode) {
		this.spcode = spcode;
	}
	public String getProgname() {
		return progname;
	}
	public void setProgname(String progname) {
		this.progname = progname;
	}
	
}
