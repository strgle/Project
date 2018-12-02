package lims.basic.vo;
import java.util.ArrayList;
import java.util.List;
public class MateVo {

	  private String areaName;
	  private String mateCode;
	  private String mateName;
	  private int isCheck;
	  private List<TestVo> tests = new ArrayList();
	  public String getMateCode()
	  {
	    return this.mateCode;
	  }
	  
	  public void setMateCode(String mateCode)
	  {
	    this.mateCode = mateCode;
	  }
	  
	  public String getMateName()
	  {
	    return this.mateName;
	  }
	  
	  public void setMateName(String mateName)
	  {
	    this.mateName = mateName;
	  }
	  
	  public List<TestVo> getTests()
	  {
	    return this.tests;
	  }
	  
	  public void setTests(List<TestVo> tests)
	  {
	    this.tests = tests;
	  }
	  
	  public String getAreaName()
	  {
	    return this.areaName;
	  }
	  
	  public void setAreaName(String areaName)
	  {
	    this.areaName = areaName;
	  }
	  
	  public int getIsCheck()
	  {
	    return this.isCheck;
	  }
	  
	  public void setIsCheck(int isCheck)
	  {
	    this.isCheck = isCheck;
	  }
}
