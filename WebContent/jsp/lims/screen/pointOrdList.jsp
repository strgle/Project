<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>装置日报信息</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/layui/css/layui.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/bootstrap/css/bootstrap.min.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/layui/layui.js"></script>

 <style type="text/css">
	html,body{
		background-color: #F5F5F5;
		overflow: hidden;
		height: 100%;
	}
	th{
		white-space: nowrap;
	}
	
	td{
		overflow:hidden;
		white-space:nowrap;
		text-overflow:ellipsis;
		-o-text-overflow:ellipsis;
		-moz-text-overflow: ellipsis;
		-webkit-text-overflow: ellipsis;
		max-width: 150px;
	}
	
 </style>
</head>
<body>
<div class="panel panel-default" style="margin-bottom: 0px;">
	<div class="panel-body" style="padding:10px 20px;">
	<form class="form-inline" action="">
		<input type="hidden" name="areaName" value="${params.areaName}"/>
		<input type="hidden" name="plant" value="${params.plant}"/>
		<input type="hidden" name="type" value="${params.type}"/>
		<input type="hidden" name="keyValue" value="${params.keyValue}"/>
		
		<div class="form-group">
			<label style="margin-left: 5px;margin-right:5px;">采样时间</label>
			<input class="form-control" name="startTime" style="width: 150px;" placeholder="开始时间" value="${params.startTime}" type="text" id="startTime" autocomplete="off"/>
		</div>
		<div class="form-group">
			<label style="margin-left: 5px;margin-right:5px;">至</label>
			<input class="form-control" name="endTime" style="width: 150px;" placeholder="默认当前时间" value="${params.endTime}" id="endTime" type="text" autocomplete="off"/>
		</div>
		<div class="form-group">
			<label style="margin-left: 5px;margin-right:5px;"></label>
			<select class="form-control" name="status">
				<option value="">样品状态</option>
				<c:choose>
					<c:when test="${params.status=='Done' }">
						<option value="Done" selected="selected">合格</option>
						<option value="OOS-A">超标</option>
						<option value="OOS-B">超内控</option>
					</c:when>
					<c:when test="${params.status=='OOS-A' }">
						<option value="Done">合格</option>
						<option value="OOS-A" selected="selected">超标</option>
						<option value="OOS-B">超内控</option>
					</c:when>
					<c:when test="${params.status=='OOS-B' }">
						<option value="Done">合格</option>
						<option value="OOS-A">超标</option>
						<option value="OOS-B"  selected="selected">超内控</option>
					</c:when>
					<c:otherwise>
						<option value="Done">合格</option>
						<option value="OOS-A">超标</option>
						<option value="OOS-B">超内控</option>
					</c:otherwise>
				</c:choose>
			</select>
		</div>
		<div class="form-group">
			<label style="margin-left: 5px;margin-right:5px;"></label>
			<select name="taskType" class="form-control">
				<option value="">任务类型</option>
       			<c:choose>
       				<c:when test="${params.taskType=='0'}">
       					<option value="0" selected="selected">常规/计划样</option>
       					<option value="加样">加样</option>
       					<option value="抽样">抽样</option>
       				</c:when>
       				<c:when test="${params.taskType=='加样'}">
       					<option value="0">常规/计划样</option>
       					<option value="加样" selected="selected">加样</option>
       					<option value="抽样">抽样</option>
       				</c:when>
       				<c:when test="${params.taskType=='抽样'}">
       					<option value="0">常规/计划样</option>
       					<option value="加样">加样</option>
       					<option value="抽样" selected="selected">抽样</option>
       				</c:when>
       				<c:otherwise>
       					<option value="0">常规/计划样</option>
       					<option value="加样">加样</option>
       					<option value="抽样">抽样</option>
       				</c:otherwise>
       			</c:choose>
       		</select>
		</div>
		<button type="submit" class="btn btn-success" style="margin-left: 20px;">查询</button>
	</form>
	</div>
</div>
<div class="panel panel-default" style="margin-bottom:0px;padding: 0px;">
	<div class="panel-heading">
		结果列表
	</div>
	<div class="panel-body" style="padding:0px;overflow-y:auto;margin: 0px;" id="dataContent">
	<div id="panelContent" style="width:100%;">
	<table class="table table-bordered table-condensed">
		<thead><tr class="active">
			<th style="text-align: center;">序号</th>
			<th style="text-align: center;">装置</th>
			<th style="text-align: center;">采样点</th>
			<th style="text-align: center;">样品名称</th>
			<th style="text-align: center;">状态</th>
			<th style="text-align: center;">采样时间</th>
			<th style="text-align: center;">发布时间</th>
			<th style="text-align: center;">样品编号</th>
			<th style="text-align: center;">任务类型</th>
			<th style="text-align: center;">样品备注</th>
			<th style="text-align: center;">链接</th>
		</tr></thead>
		<tbody id="dailyContent">
			<c:forEach items="${orders}" var="ord" varStatus="status">
				<tr>
					<td style="text-align: center;">${status.count }</td>
					<td>${ord.plant}</td>
					<td>${ord.pointdesc}</td>
					<td>${ord.matname}</td>
					<c:choose>
						<c:when test="${ord.grade =='OOS-A'}">
							<td style="white-space: nowrap;color:#D9534F;cursor:Pointer;" onclick="showDetail('${ord.ordno}')"><i class="fa fa-star"></i>&nbsp;超标</td>
						</c:when>
						<c:when test="${ord.grade =='OOS-B'}">
							<td style="white-space: nowrap;color:#FFDB4C;cursor:Pointer;" onclick="showDetail('${ord.ordno}')"><i class="fa fa-bell" style="color: #FFDB4C;"></i>&nbsp;超标</td>
						</c:when>
						<c:when test="${ord.grade =='Done'}">
							<td style="white-space: nowrap;cursor:Pointer;" onclick="showDetail('${ord.ordno}')"><i class="fa fa-circle" style="color: #5CB85C;"></i>&nbsp;合格</td>
						</c:when>
						<c:otherwise>
							<td style="white-space: nowrap;cursor:Pointer;" onclick="showDetail('${ord.ordno}')">&nbsp;进行中</td>
						</c:otherwise>
					</c:choose>
					<td>${ord.sampdate}</td>
					<td>${ord.apprdate}</td>
					<td>${ord.ordno}</td>
					<td>${ord.tasktype}</td>
					<td>${ord.batchname}</td>
					<td><lable class="btn btn-link" style="padding:0px 2px;" onclick="showDetail('${ord.ordno}')">明细</lable></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	</div>
</div>


</body>
</html>
<script type="text/javascript">
layui.use(['layer','laydate',"form"],function(){
	layer = layui.layer,
	laydate = layui.laydate,
	form = layui.form();
	
	var start = {
			  format: 'YYYY-MM-DD hh:mm',
			  min: '2017-01-01 00:00', 
			  istime: true,
			  max: laydate.now()
			};
	var end = {
			  format: 'YYYY-MM-DD hh:mm',
			  istime: true,
			  min: '2017-01-01 00:00',
			  max: laydate.now()
			};

	$("#startTime").on("click",function(){
		start.elem = this;
	    laydate(start);
	})
	
	$("#endTime").on("click",function(){
		end.elem = this;
	    laydate(end);
	}) 
});


function showDetail(ordno){

	layer.open({
		  type: 2 //Page层类型
		  ,area: ['70%', '90%']
		  ,title: '检测结果'
		  ,content: '${pageContext.request.contextPath}/lims/dataSearch/daily/ordDetail?ordNo='+ordno
	});  
}
	
$(window).on('resize', function() {
	var height = $("body").height()-100;
	$("#dataContent").height(height);
}).resize();

</script>