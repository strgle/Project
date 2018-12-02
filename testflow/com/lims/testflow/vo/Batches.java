package com.lims.testflow.vo;

/**
 * 下样单位信息
 * @author Administrator
 *
 */
public class Batches {
	private String id;			//主键或者任务编号
	private String batchName;	//任务主题
	
	private String areaName;	//车间或者部门
	private String plant;		//装置，针对车间、装置下样
	
	private String createTime;	//创建时间、提交时间
	private String createBy;	//创建人、提交人
	
	private String status;		//状态
	private String comments;	//备注
	
	private String taskType;	//任务类型（常规检查、计划频次、抽样、加样、开停车）
	private String matType;		//样品类型（原辅料、过程样、成品、环保样、水质）
	private String taskSource;	//任务来源

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getMatType() {
		return matType;
	}

	public void setMatType(String matType) {
		this.matType = matType;
	}

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getTaskSource() {
		return taskSource;
	}

	public void setTaskSource(String taskSource) {
		this.taskSource = taskSource;
	}
	
}
