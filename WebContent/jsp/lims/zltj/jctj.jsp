<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>检测统计</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/skins/font-awesome/css/font-awesome.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/skins/links/bootstrap-3.3.7/css/bootstrap.min.css">
 <link rel="stylesheet" href="${pageContext.request.contextPath}/skins/fuelux/css/ace.min.css">
 <script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/bootstrap-3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/laydate/laydate.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/layer/layer.js"></script>
 
<style type="text/css">
	html,body{
		margin: 0px;
		padding: 5px;
		height: 100%;
		background-color: #F5F5F5;
	}
	th{
		background-color: #5CB85C;
		color: #FFF;
        font-size: 12px;
	}
	th,td{
		white-space: nowrap;
		text-align: center;
        font-size: 12px;
	}
	

</style>
</head>
<body>

<div class="panel panel-default" style="margin-bottom: 0px;">
	<div class="panel-body">
	<form class="form-inline" action="">
		<div class="form-group">
			<label class="control-label" style="font-weight: normal;">车间：</label>
			<select class="form-control" name="area">
				<c:forEach items="${areaList}" var="area">
					<c:choose>
						<c:when test="${area.selected=='true'}">
							<option value="${area.areaName}" selected="selected">${area.areaName }</option>
						</c:when>
						<c:otherwise>
							<option value="${area.areaName}">${area.areaName }</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		</div>
		<div class="form-group">
			<label class="control-label" style="font-weight: normal;">开始时间：</label>
			<input class="form-control" name="startDate" style="width: 150px;" placeholder="开始时间" value="${startDate}" type="text" id="startDate" autocomplete="off" onclick="laydate()" readonly="readonly"/>
		</div>
		<div class="form-group">
			<label class="control-label" style="font-weight: normal;margin-left: 20px;">截至时间：</label>
			<input class="form-control" name="endDate" style="width: 150px;" placeholder="默认当前时间" value="${endDate}" id="endDate" type="text" autocomplete="off" onclick="laydate()" readonly="readonly"/>
		</div>
		<div class="form-group">
			<label class="control-label" style="font-weight: normal;">任务类型：</label>
			<select class="form-control" name="taskType">
				<c:choose>
					<c:when test="${taskType=='0'}">
							<option value="">全部</option>
							<option value="0" selected="selected">计划/常规检验</option>
							<option value="临时加样">临时加样</option>
							<option value="抽样检查">抽样检查</option>
					</c:when>
					<c:when test="${taskType=='临时加样'}">
							<option value="">全部</option>
							<option value="0">计划/常规检验</option>
							<option value="临时加样"  selected="selected">临时加样</option>
							<option value="抽样检查">抽样检查</option>
					</c:when>
					<c:when test="${taskType=='抽样检查'}">
							<option value="">全部</option>
							<option value="0">计划/常规检验</option>
							<option value="临时加样">临时加样</option>
							<option value="抽样检查" selected="selected">抽样检查</option>
					</c:when>
					<c:otherwise>
							<option value="">全部</option>
							<option value="0">计划/常规检验</option>
							<option value="临时加样">临时加样</option>
							<option value="抽样检查">抽样检查</option>
					</c:otherwise>
				</c:choose>
			</select>
		</div>
		<div class="form-group">
			<button type="submit" class="btn btn-success" style="margin-left: 30px;">查询</button>
		</div>
	</form>
	</div>
</div>
<div class="panel panel-default">
	<div class="panel-body" style="padding: 0px;margin: 0px;">
	<table class="table table-bordered table-striped table-condensed" style="margin: 0px;">
		<thead><tr>
			<th>序号</th>
			<th>车间</th>
			<th>装置</th>
			<th>样品数量</th>
			<th>检测成本</th>
		</tr></thead>
		<tbody>
			<c:forEach items="${jytj}" var="d" varStatus="status">
				<tr>
					<td>${status.count}</td>
					<td>${area}</td>
					<td>${d.plant}</td>
					<td>
						<c:if test="${empty d.countNum}">0</c:if>${d.countNum}
					</td>
					<td>
						<c:if test="${empty d.price}"><fmt:formatNumber value="0" pattern="￥0.00"/></c:if>
						<c:if test="${not empty d.price }"><fmt:formatNumber value="${d.price}" pattern="￥0.00"/></c:if>
						
					</td>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<th colspan="3">合计</th>
				<td>${countNum }</td>
				<td><fmt:formatNumber value="${countPrice}" pattern="￥0.00"/> </td>
			</tr>
		</tfoot>
	</table>
	</div>
</div>
	
<script type="text/javascript">

	$(window).on('resize', function() {
		$("#treeContent").height($("body").height()-45);
	}).resize();

	//执行一个laydate实例
	laydate.render({
	  elem: '#startDate' //指定元素
	  ,theme: 'molv'
	});
	laydate.render({
	  elem: '#endDate' //指定元素
	  ,theme: 'molv'
	}); 
</script>
</body>
</html>