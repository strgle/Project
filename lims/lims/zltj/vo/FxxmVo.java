/**
 * 
 */
package lims.zltj.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分析项目
 * @author Administrator
 *
 */
public class FxxmVo {
	private String matCode;
	  private String name;
	  private String sinonym;
	  private String zb;
	  private String testcode;
	  private int totalDone;
	  private int totalOos;
	  private int total;
	  private String specno;
	  private String groupPoint;
	  private int groupNum;
	  private int groupOos;
	  private int groupDone;
	  
	  public String getSpecno()
	  {
	    return this.specno;
	  }
	  
	  public void setSpecno(String specno)
	  {
	    this.specno = specno;
	  }
	  
	  public String getGroupPoint()
	  {
	    return this.groupPoint;
	  }
	  
	  public void setGroupPoint(String groupPoint)
	  {
	    this.groupPoint = groupPoint;
	  }
	  
	  public int getGroupNum()
	  {
	    return this.groupNum;
	  }
	  
	  public void setGroupNum(int groupNum)
	  {
	    this.groupNum = groupNum;
	  }
	  
	  public int getGroupOos()
	  {
	    return this.groupOos;
	  }
	  
	  public void setGroupOos(int groupOos)
	  {
	    this.groupOos = groupOos;
	  }
	  
	  public int getGroupDone()
	  {
	    return this.groupDone;
	  }
	  
	  public void setGroupDone(int groupDone)
	  {
	    this.groupDone = groupDone;
	  }
	  
	  public void setMatCode(String matCode)
	  {
	    this.matCode = matCode;
	  }
	  
	  public void setName(String name)
	  {
	    this.name = name;
	  }
	  
	  public void setRqList(List<String> rqList)
	  {
	    this.rqList = rqList;
	  }
	  
	  public void setDoneMap(Map<String, Integer> doneMap)
	  {
	    this.doneMap = doneMap;
	  }
	  
	  public void setOosMap(Map<String, Integer> oosMap)
	  {
	    this.oosMap = oosMap;
	  }
	  
	  public void setHglMap(Map<String, Float> hglMap)
	  {
	    this.hglMap = hglMap;
	  }
	  
	  public int getTotal()
	  {
	    return this.total;
	  }
	  
	  public void setTotal(int total)
	  {
	    this.total = total;
	  }
	  
	  private List<String> rqList = new ArrayList<String>();
	  private Map<String, Integer> doneMap = new HashMap<String, Integer>();
	  private Map<String, Integer> oosMap = new HashMap<String, Integer>();
	  private Map<String, Integer> chyMap = new HashMap<String, Integer>();
	  private Map<String, Float> hglMap = new HashMap<String, Float>();
	  
	  public FxxmVo(String matCode, String name)
	  {
	    this.matCode = matCode;
	    this.name = name;
	  }
	  
	  public String getName()
	  {
	    return this.name;
	  }
	  
	  public String getMatCode()
	  {
	    return this.matCode;
	  }
	  
	  public String getZb()
	  {
	    return this.zb;
	  }
	  
	  public void setZb(String zb)
	  {
	    this.zb = zb;
	  }
	  
	  public void add(String rq, String status, String tasktype)
	  {
	    if (!this.rqList.contains(rq)) {
	      this.rqList.add(rq);
	    }
	    if (("Done".equals(status)) || ("OOS-B".equals(status)))
	    {
	      if (this.doneMap.containsKey(rq)) {
	        this.doneMap.put(rq, Integer.valueOf(((Integer)this.doneMap.get(rq)).intValue() + 1));
	      } else {
	        this.doneMap.put(rq, Integer.valueOf(1));
	      }
	      this.totalDone += 1;
	      this.totalOos += 1;
	    }
	    else
	    {
	      if (this.oosMap.containsKey(rq)) {
	        this.oosMap.put(rq, Integer.valueOf(((Integer)this.oosMap.get(rq)).intValue() + 1));
	      } else {
	        this.oosMap.put(rq, Integer.valueOf(1));
	      }
	      this.totalOos += 1;
	    }
	  }
	  
	  public void add(String rq, String status, String tasktype, String limitType)
	  {
	    if (!this.rqList.contains(rq)) {
	      this.rqList.add(rq);
	    }
	    if ("gradeA".equals(limitType))
	    {
	      if (("Done".equals(status)) || ("OOS-B".equals(status)))
	      {
	        if (this.doneMap.containsKey(rq)) {
	          this.doneMap.put(rq, Integer.valueOf(((Integer)this.doneMap.get(rq)).intValue() + 1));
	        } else {
	          this.doneMap.put(rq, Integer.valueOf(1));
	        }
	        this.totalDone += 1;
	        this.totalOos += 1;
	      }
	      else
	      {
	        if (this.oosMap.containsKey(rq)) {
	          this.oosMap.put(rq, Integer.valueOf(((Integer)this.oosMap.get(rq)).intValue() + 1));
	        } else {
	          this.oosMap.put(rq, Integer.valueOf(1));
	        }
	        this.totalOos += 1;
	      }
	    }
	    else if ("Done".equals(status))
	    {
	      if (this.doneMap.containsKey(rq)) {
	        this.doneMap.put(rq, Integer.valueOf(((Integer)this.doneMap.get(rq)).intValue() + 1));
	      } else {
	        this.doneMap.put(rq, Integer.valueOf(1));
	      }
	      this.totalDone += 1;
	      this.totalOos += 1;
	    }
	    else
	    {
	      if (this.oosMap.containsKey(rq)) {
	        this.oosMap.put(rq, Integer.valueOf(((Integer)this.oosMap.get(rq)).intValue() + 1));
	      } else {
	        this.oosMap.put(rq, Integer.valueOf(1));
	      }
	      this.totalOos += 1;
	    }
	  }
	  
	  public Map<String, Integer> getDoneMap()
	  {
	    return this.doneMap;
	  }
	  
	  public Map<String, Integer> getOosMap()
	  {
	    return this.oosMap;
	  }
	  
	  public void addHgl(String rq, Float hgl)
	  {
	    this.hglMap.put(rq, hgl);
	  }
	  
	  public void jsHgl(String rq)
	  {
	    if (!this.doneMap.containsKey(rq)) {
	      this.doneMap.put(rq, Integer.valueOf(0));
	    }
	    if (!this.oosMap.containsKey(rq)) {
	      this.oosMap.put(rq, Integer.valueOf(0));
	    }
	    int done = ((Integer)this.doneMap.get(rq)).intValue();
	    int oos = ((Integer)this.oosMap.get(rq)).intValue();
	    int total = done + oos;
	    this.oosMap.put(rq, Integer.valueOf(total));
	    if (done + oos > 0)
	    {
	      Float hgl = Float.valueOf(done * 1.0F / total);
	      this.hglMap.put(rq, hgl);
	    }
	  }
	  
	  public List<String> getRqList()
	  {
	    return this.rqList;
	  }
	  
	  public Map<String, Float> getHglMap()
	  {
	    return this.hglMap;
	  }
	  
	  public String getTestcode()
	  {
	    return this.testcode;
	  }
	  
	  public void setTestcode(String testcode)
	  {
	    this.testcode = testcode;
	  }
	  
	  public int getTotalDone()
	  {
	    return this.totalDone;
	  }
	  
	  public int getTotalOos()
	  {
	    return this.totalOos;
	  }
	  
	  public void setTotalDone(int totalDone)
	  {
	    this.totalDone = totalDone;
	  }
	  
	  public void setTotalOos(int totalOos)
	  {
	    this.totalOos = totalOos;
	  }
	  
	  public String getSinonym()
	  {
	    return this.sinonym;
	  }
	  
	  public void setSinonym(String sinonym)
	  {
	    this.sinonym = sinonym;
	  }
	  
	  public Map<String, Integer> getChyMap()
	  {
	    return this.chyMap;
	  }
	  
	  public void setChyMap(Map<String, Integer> chyMap)
	  {
	    this.chyMap = chyMap;
	  }
}
