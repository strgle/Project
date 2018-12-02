package lims.zltj.vo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class ChjVo {
	  private String name;
	  private int rowNum;
	  private Map<String, Integer> doneMap = new HashMap<String, Integer>();
	  private Map<String, Integer> totalMap = new HashMap<String, Integer>();
	  private Map<String, Float> hglMap = new HashMap<String, Float>();
	  private int doneNum;
	  private int totalNum;
	  private int checkNum;
	  private List<ZzhiVo> data = new ArrayList<ZzhiVo>();
	  public String getName()
	  {
	    return this.name;
	  }
	  
	  public void setName(String name)
	  {
	    this.name = name;
	  }
	  
	  public int getRowNum()
	  {
	    return this.rowNum;
	  }
	  
	  public void setRowNum(int rowNum)
	  {
	    this.rowNum = rowNum;
	  }
	  
	  public List<ZzhiVo> getData()
	  {
	    return this.data;
	  }
	  
	  public void setData(List<ZzhiVo> data)
	  {
	    this.data = data;
	  }
	  
	  public void addZzh(ZzhiVo zz)
	  {
	    this.data.add(zz);
	    this.rowNum = this.data.size();
	  }
	  
	  public void addNum(String rq, String status, String taskType)
	  {
	    if (("抽样检查".equals(taskType)) && (!"Done".equals(status)))
	    {
	      if (this.totalMap.containsKey(rq)) {
	        this.totalMap.put(rq, Integer.valueOf(((Integer)this.totalMap.get(rq)).intValue() + 3));
	      } else {
	        this.totalMap.put(rq, 1);
	      }
	      this.totalNum += 1;
	      this.checkNum += 1;
	    }
	    else
	    {
	    	if(totalMap.containsKey(rq)){
				totalMap.put(rq, totalMap.get(rq)+1);
			}else{
				totalMap.put(rq,1);
			}
			totalNum = totalNum+1;
	      //this.totalNum += 1;
	    }
	    if ("Done".equals(status))
	    {
	    	if(doneMap.containsKey(rq)){
				doneMap.put(rq, doneMap.get(rq)+1);
			}else{
				doneMap.put(rq, 1);
			}
	      this.doneNum += 1;
	    }
	    if (!this.doneMap.containsKey(rq)) {
	      this.doneMap.put(rq, 0);
	    }
	    hglMap.put(rq, doneMap.get(rq)*1.0f/totalMap.get(rq));
	  }
	  
	  public Map<String, Integer> getDoneMap()
	  {
	    return this.doneMap;
	  }
	  
	  public void setDoneMap(Map<String, Integer> doneMap)
	  {
	    this.doneMap = doneMap;
	  }
	  
	  public Map<String, Integer> getTotalMap()
	  {
	    return this.totalMap;
	  }
	  
	  public void setTotalMap(Map<String, Integer> totalMap)
	  {
	    this.totalMap = totalMap;
	  }
	  
	  public Map<String, Float> getHglMap()
	  {
	    return this.hglMap;
	  }
	  
	  public void setHglMap(Map<String, Float> hglMap)
	  {
	    this.hglMap = hglMap;
	  }
	  
	  public int getDoneNum()
	  {
	    return this.doneNum;
	  }
	  
	  public void setDoneNum(int doneNum)
	  {
	    this.doneNum = doneNum;
	  }
	  
	  public int getTotalNum()
	  {
	    return this.totalNum;
	  }
	  
	  public void setTotalNum(int totalNum)
	  {
	    this.totalNum = totalNum;
	  }
	  
	  public int getCheckNum()
	  {
	    return this.checkNum;
	  }
	  
	  public void setCheckNum(int checkNum)
	  {
	    this.checkNum = checkNum;
	  }
}
