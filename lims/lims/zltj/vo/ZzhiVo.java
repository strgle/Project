package lims.zltj.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 装置
 * @author Administrator
 */
public class ZzhiVo {
	private String areaName;
	
	private String name;
	private int rowNum;
	
	private Map<String,Integer> doneMap = new HashMap<String, Integer>();
	private Map<String,Integer> totalMap = new HashMap<String, Integer>();
	private Map<String,Float> hglMap = new HashMap<String,Float>();
	
	private List<Map<String,Object>> ycfx = new ArrayList<Map<String, Object>>();
	
	private Float zlmb;
	private int samplingCheckNum;
	private Float executivePower;
	
	/**
	 * 总取样次数
	 */
	private int totalNum;
	
	/**
	 * 合格次数
	 */
	private int doneNum;
	
	/**
	 * 合格率
	 */
	private Float qualifiedRate;
	
	private List<ChpVo> data = new ArrayList<ChpVo>();
	
	
	public Float getZlmb() {
		return zlmb;
	}

	public void setZlmb(Float zlmb) {
		this.zlmb = zlmb;
	}

	public int getSamplingCheckNum() {
		return samplingCheckNum;
	}

	public void setSamplingCheckNum(int samplingCheckNum) {
		this.samplingCheckNum = samplingCheckNum;
	}

	public Float getExecutivePower() {
		return executivePower;
	}

	public void setExecutivePower(Float executivePower) {
		this.executivePower = executivePower;
	}

	public ZzhiVo(String name){
		this.name=name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRowNum() {
		return rowNum;
	}
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	public List<ChpVo> getData() {
		return data;
	}
	public void setData(List<ChpVo> data) {
		this.data = data;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int getDoneNum() {
		return doneNum;
	}

	public void setDoneNum(int doneNum) {
		this.doneNum = doneNum;
	}

	public Float getQualifiedRate() {
		return qualifiedRate;
	}

	public void setQualifiedRate(Float qualifiedRate) {
		this.qualifiedRate = qualifiedRate;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	
	public void addNum(String status,String taskType){
		if ("抽样检查".equals(taskType))
	    {
	      if ("Done".equals(status))
	      {
	        this.doneNum += 1;
	        this.totalNum += 1;
	      }
	      else
	      {
	        this.totalNum += 3;
	        this.samplingCheckNum += 1;
	      }
	    }
	    else
	    {
	      if ("Done".equals(status)) {
	        this.doneNum += 1;
	      }
	      this.totalNum += 1;
	    }
	    this.qualifiedRate = Float.valueOf(this.doneNum * 1.0F / this.totalNum * 100.0F);
	    if ((this.zlmb != null) && (this.zlmb.floatValue() != 0.0F)) {
	      this.executivePower = Float.valueOf(this.qualifiedRate.floatValue() / this.zlmb.floatValue());
	    }
	}
	
	public void addRqNum(String status,String rq,String taskType){

		if("抽样检查".equals(taskType)){
			if("Done".equals(status)){
				this.doneNum = this.doneNum+1;
				this.totalNum=this.totalNum+1;
				if(!this.doneMap.containsKey(rq)){
					this.doneMap.put(rq, 1);
				}else{
					this.doneMap.put(rq,this.doneMap.get(rq)+1);
				}
				
				if(this.totalMap.containsKey(rq)){
					this.totalMap.put(rq, this.totalMap.get(rq)+1);
				}else{
					this.totalMap.put(rq,1);
				}
			}else{
				this.totalNum=this.totalNum+3;
				this.samplingCheckNum = this.samplingCheckNum+1;
				if(this.totalMap.containsKey(rq)){
					this.totalMap.put(rq, this.totalMap.get(rq)+3);
				}else{
					this.totalMap.put(rq,3);
				}
			}
		}else{
			if("Done".equals(status)){
				this.doneNum = this.doneNum+1;
				if(!this.doneMap.containsKey(rq)){
					this.doneMap.put(rq, 1);
				}else{
					this.doneMap.put(rq,this.doneMap.get(rq)+1);
				}
			}
			if(this.totalMap.containsKey(rq)){
				this.totalMap.put(rq, this.totalMap.get(rq)+1);
			}else{
				this.totalMap.put(rq,1);
			}
			this.totalNum=this.totalNum+1;
		}
		if(!this.doneMap.containsKey(rq)){
			this.doneMap.put(rq, 0);
		}
		this.hglMap.put(rq, this.doneMap.get(rq)*1.0f/this.totalMap.get(rq));
	}

	public Map<String, Integer> getDoneMap() {
		return doneMap;
	}

	public void setDoneMap(Map<String, Integer> doneMap) {
		this.doneMap = doneMap;
	}

	public Map<String, Integer> getTotalMap() {
		return totalMap;
	}

	public void setTotalMap(Map<String, Integer> totalMap) {
		this.totalMap = totalMap;
	}

	public Map<String, Float> getHglMap() {
		return hglMap;
	}

	public void setHglMap(Map<String, Float> hglMap) {
		this.hglMap = hglMap;
	}

	public List<Map<String, Object>> getYcfx() {
		return ycfx;
	}

	public void setYcfx(List<Map<String, Object>> ycfx) {
		this.ycfx = ycfx;
	}
}
