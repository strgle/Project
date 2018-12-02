package lims.basic.vo;

import java.util.ArrayList;
import java.util.List;

public class TestVo {

	  private String matcode;
	  private String testCode;
	  private String testNo;
	  private String testDesc;
	  private String isCheck;
	  private String[] analytes;
	  private List<AnalyteVo> analyteList = new ArrayList();
	  public String getMatcode()
	  {
	    return this.matcode;
	  }
	  
	  public void setMatcode(String matcode)
	  {
	    this.matcode = matcode;
	  }
	  
	  public String getTestCode()
	  {
	    return this.testCode;
	  }
	  
	  public void setTestCode(String testCode)
	  {
	    this.testCode = testCode;
	  }
	  
	  public String getTestNo()
	  {
	    return this.testNo;
	  }
	  
	  public void setTestNo(String testNo)
	  {
	    this.testNo = testNo;
	  }
	  
	  public String getIsCheck()
	  {
	    return this.isCheck;
	  }
	  
	  public void setIsCheck(String isCheck)
	  {
	    this.isCheck = isCheck;
	  }
	  
	  public String getTestDesc()
	  {
	    if ((this.testDesc == null) || (this.testDesc.equals(""))) {
	      this.testDesc = this.testNo;
	    }
	    return this.testDesc;
	  }
	  
	  public void setTestDesc(String testDesc)
	  {
	    this.testDesc = testDesc;
	  }
	  
	  public String[] getAnalytes()
	  {
	    return this.analytes;
	  }
	  
	  public void setAnalytes(String[] analytes)
	  {
	    this.analytes = analytes;
	  }
	  
	  public List<AnalyteVo> getAnalyteList()
	  {
	    return this.analyteList;
	  }
	  
	  public void setAnalyteList(List<AnalyteVo> analyteList)
	  {
	    this.analyteList = analyteList;
	  }
}
