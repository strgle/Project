<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>样品检测进度查询</title>
 <link rel="stylesheet" href="${pageContext.request.contextPath}/skins/font-awesome/css/font-awesome.min.css">
 <link rel="stylesheet" href="${pageContext.request.contextPath}/skins/bootstrap/css/bootstrap.min.css">
 <script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/jquery.min.js"></script>
 <script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/laydate/laydate.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/layer/layer.js"></script>
 <style type="text/css">
	html,body{
		padding:5px;
		background-color: #F5F5F5;
	}
	th{
		white-space: nowrap;
	}
	th,td{
		text-align: center;
	}
	
		
 </style>
</head>
<body>
<div class="panel panel-default" style="margin-bottom: 0px;">
	<div class="panel-body">
	<form class="form-inline" action="">
		<div class="form-group">
			<label class="control-label" style="font-weight: normal;">任务创建时间：</label>
			<input class="form-control" name="startTime" placeholder="开始时间" value="${params.startTime}" type="text" id="startTime" autocomplete="off" onclick="laydate()"/>
		</div>
		<div class="form-group">
			<label class="control-label" style="font-weight: normal;margin-left: 5px;">至：</label>
			<input class="form-control" name="endTime" placeholder="默认当前时间" value="${params.endTime}" id="endTime" type="text" autocomplete="off" onclick="laydate()"/>
		</div>
		<div class="form-group">
			<label class="control-label" style="font-weight: normal;margin-left: 20px;">检测状态：</label>
			<select name="status" class="form-control">
				<option value="ALL">全部</option>
				<option value="0">进行中</option>
				<option value="1">完成</option>
			</select>
		</div>
		<div class="form-group">
			<label class="control-label" style="font-weight: normal;margin-left: 20px;">${title}：</label>
			<select name="matCode" class="form-control">
				<option value="ALL">全部</option>
				<c:forEach items="${matData}" var="mat">
					<c:choose>
						<c:when test="${mat['id']==params.matCode }">
							<option value="${mat['id'] }" selected="selected">${mat['title'] }</option>
						</c:when>
						<c:otherwise>
							<option value="${mat['id'] }">${mat['title'] }</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		</div>
		<div class="form-group">
			<button type="submit" class="btn btn-success" style="margin-left: 30px;">查询</button>
		</div>
	</form>
	</div>
</div>
<div class="panel panel-default">
	<div class="panel-heading">
		<div class="panel-title">结果列表</div>
	</div>
	<table class="table table-bordered">
		<thead><tr>
			<th>序号</th>
			<th>样品编号</th>
			<th>样品名称</th>
			<th>车间</th>
			<th>装置</th>
			<th>采样点</th>
			<th>任务类型</th>
			<th>创建时间</th>
			<th>样品备注</th>
			<th>状态</th>			
			<th>链接</th>
		</tr></thead>
		<tbody>
			<c:forEach items="${data}" var="d" varStatus="status">
				<tr>
					<td>${status.count}</td>
					<td>${d.ordno}</td>
					<td>${d.matname}</td>
					<td>${d.areaName}</td>
					<td>${d.plant}</td>
					<td>${d.pointdesc}</td>
					<td>${d.tasktype}</td>
					<td>${d.logdate}</td>
					<td>${d.batchname}</td>
					<td>
						<c:choose>
							<c:when test="${d.dispsts=='Planned'}">
								<span class="label label-default"  style="margin:0px;">未提交</span>
							</c:when>
							<c:when test="${d.dispsts=='Approving'}">
								<span class="label label-warning"  style="margin:0px;">加样审批</span>
							</c:when>
							<c:when test="${d.dispsts=='Started'}">
								<span class="label label-info"  style="margin:0px;">进行中</span>
							</c:when>
							<c:when test="${d.dispsts=='Released'}">
								<span class="label label-success"  style="margin:0px;">结果发布</span>
							</c:when>
							<c:when test="${d.dispsts=='Cancelled'}">
								<span class="label label-danger"  style="margin:0px;">样品终止</span>
							</c:when>
						</c:choose>
					</td>
					<td>
						<lable class="btn btn-link" onclick="showProcess('${d.ordno}');" style="padding:0px 5px;">流程</lable>|<lable class="btn btn-link" onclick="showDetail('${d.ordno}');" style="padding:0px 5px;">详情</lable>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
</body>
<script type="text/javascript">

	function showProcess(ordno){
		layer.open({
			  type: 2 //Page层类型
			  ,scrollbar:false
			  ,area: ['70%', '90%']
			  ,title: '检测进度查看('+ordno+')'
			  ,content: '${pageContext.request.contextPath}/lims/tasks/ordSchedule/detail?ordNo='+ordno
		});    
	}
	
	function showDetail(ordno){
		layer.open({
			  type: 2 //Page层类型
			  ,scrollbar:false
			  ,area: ['70%', '90%']
			  ,title: '检测结果'
			  ,content: '${pageContext.request.contextPath}/lims/dataSearch/daily/ordDetail?ordNo='+ordno
		});    
	}

</script>
</html>
