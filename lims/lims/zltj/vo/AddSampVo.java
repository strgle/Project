package lims.zltj.vo;
import java.util.ArrayList;
import java.util.List;
public class AddSampVo {

	  private String sampeDate;
	  private String ordNo;
	  private String matname;
	  private List<String> testList = new ArrayList();
	  private float price;
	  private String decription;
	  private String createBy;
	  private String comments;
	  private String remark;
	  private String approveRemark;
	  private String applyUser;
	  private String approve;
	  private String approveDate;
	  private String status;
	  private String plant;
	  
	  public String getPlant() {
		return plant;
	 }

	public void setPlant(String plant) {
		this.plant = plant;
	}

	public void addTest(String testNo, float price)
	  {
	    this.testList.add(testNo);
	    this.price += price;
	  }
	  
	  public String getSampeDate()
	  {
	    return this.sampeDate;
	  }
	  
	  public void setSampeDate(String sampeDate)
	  {
	    this.sampeDate = sampeDate;
	  }
	  
	  public String getOrdNo()
	  {
	    return this.ordNo;
	  }
	  
	  public void setOrdNo(String ordNo)
	  {
	    this.ordNo = ordNo;
	  }
	  
	  public String getMatname()
	  {
	    return this.matname;
	  }
	  
	  public void setMatname(String matname)
	  {
	    this.matname = matname;
	  }
	  
	  public List<String> getTestList()
	  {
	    return this.testList;
	  }
	  
	  public void setTestList(List<String> testList)
	  {
	    this.testList = testList;
	  }
	  
	  public float getPrice()
	  {
	    return this.price;
	  }
	  
	  public void setPrice(float price)
	  {
	    this.price = price;
	  }
	  
	  public String getDecription()
	  {
	    return this.decription;
	  }
	  
	  public void setDecription(String decription)
	  {
	    this.decription = decription;
	  }
	  
	  public String getCreateBy()
	  {
	    return this.createBy;
	  }
	  
	  public void setCreateBy(String createBy)
	  {
	    this.createBy = createBy;
	  }
	  
	  public String getComments()
	  {
	    return this.comments;
	  }
	  
	  public void setComments(String comments)
	  {
	    this.comments = comments;
	  }
	  
	  public String getRemark()
	  {
	    return this.remark;
	  }
	  
	  public void setRemark(String remark)
	  {
	    this.remark = remark;
	  }
	  
	  public String getApproveRemark()
	  {
	    return this.approveRemark;
	  }
	  
	  public void setApproveRemark(String approveRemark)
	  {
	    this.approveRemark = approveRemark;
	  }
	  
	  public String getApplyUser()
	  {
	    return this.applyUser;
	  }
	  
	  public void setApplyUser(String applyUser)
	  {
	    this.applyUser = applyUser;
	  }
	  
	  public String getApprove()
	  {
	    return this.approve;
	  }
	  
	  public void setApprove(String approve)
	  {
	    this.approve = approve;
	  }
	  
	  public String getApproveDate()
	  {
	    return this.approveDate;
	  }
	  
	  public void setApproveDate(String approveDate)
	  {
	    this.approveDate = approveDate;
	  }
	  
	  public String getStatus()
	  {
	    return this.status;
	  }
	  
	  public void setStatus(String status)
	  {
	    this.status = status;
	  }
}
