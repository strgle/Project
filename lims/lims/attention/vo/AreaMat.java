package lims.attention.vo;

import java.util.ArrayList;
import java.util.List;

public class AreaMat {

	private String areaName;
	  private List<MatVo> matArray = new ArrayList<MatVo>();
	  
	  public String getAreaName()
	  {
	    return this.areaName;
	  }
	  
	  public void setAreaName(String areaName)
	  {
	    this.areaName = areaName;
	  }
	  
	  public List<MatVo> getMatArray()
	  {
	    return this.matArray;
	  }
	  
	  public void setMatArray(List<MatVo> matArray)
	  {
	    this.matArray = matArray;
	  }
}
