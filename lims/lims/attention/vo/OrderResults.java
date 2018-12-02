package lims.attention.vo;

import java.util.HashMap;
import java.util.Map;

public class OrderResults {

	private String ordNo;
	  private String sampdate;
	  private String batchNo;
	  private String description;
	  private Map<String, String> dataMap = new HashMap<String, String>();
	  private Map<String, String> statusMap = new HashMap<String, String>();
	  
	  public String getOrdNo()
	  {
	    return this.ordNo;
	  }
	  
	  public void setOrdNo(String ordNo)
	  {
	    this.ordNo = ordNo;
	  }
	  
	  public String getSampdate()
	  {
	    return this.sampdate;
	  }
	  
	  public void setSampdate(String sampdate)
	  {
	    this.sampdate = sampdate;
	  }
	  
	  public String getBatchNo()
	  {
	    return this.batchNo;
	  }
	  
	  public void setBatchNo(String batchNo)
	  {
	    this.batchNo = batchNo;
	  }
	  
	  public Map<String, String> getDataMap()
	  {
	    return this.dataMap;
	  }
	  
	  public void setDataMap(Map<String, String> dataMap)
	  {
	    this.dataMap = dataMap;
	  }
	  
	  public Map<String, String> getStatusMap()
	  {
	    return this.statusMap;
	  }
	  
	  public void setStatusMap(Map<String, String> statusMap)
	  {
	    this.statusMap = statusMap;
	  }
	  
	  public String getDescription()
	  {
	    return this.description;
	  }
	  
	  public void setDescription(String description)
	  {
	    this.description = description;
	  }
}
