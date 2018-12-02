<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>免考核待审批记录</title>
 <link rel="stylesheet" href="${pageContext.request.contextPath}/skins/font-awesome/css/font-awesome.min.css">
 <link rel="stylesheet" href="${pageContext.request.contextPath}/skins/bootstrap/css/bootstrap.min.css">
 <link rel="stylesheet" href="${pageContext.request.contextPath}/skins/fuelux/css/ace.min.css">
 <script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/layer/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/laydate/laydate.js"></script>
<style type="text/css">
	html,body{
		margin: 0px;
		padding: 5px;
		height: 100%;
		background-color: #F5F5F5;
	}
	th{
		white-space: nowrap;
		background-color: #5CB85C;
        font-size: 12px;
	}
	th,td{
		text-align: center;
		vertical-align: middle;
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
				<option value="">请选择</option>
				<c:forEach items="${areaList }" var="v">
					<c:if test="${v==area }">
						<option value="${v}" selected="selected">${v}</option>
					</c:if>
					<c:if test="${v!=area}">
						<option value="${v}">${v}</option>
					</c:if>
				</c:forEach>
			</select>
		</div>
		<div class="form-group">
			<button type="submit" class="btn btn-success" style="margin-left: 30px;">查询</button>
			<button type="button" class="btn btn-info" style="margin-left: 20px;" onclick="tg()">审批通过</button>
			<button type="button" class="btn btn-danger" style="margin-left: 20px;" onclick="bh()">审批驳回</button>
		</div>
	</form>
	</div>
</div>
<div class="panel panel-default">
	<div class="panel-body" style="padding: 0px;margin: 0px;">
	<table class="table table-bordered table-striped table-condensed" style="margin: 0px;">
		<thead><tr>
			<th>&nbsp;</th>
			<th>序号</th>
			<th>车间</th>
			<th>装置</th>
			<th>样品编号</th>
			<th>样品名称</th>
			<th>采样时间</th>
			<th>申请人</th>
			<th>申请时间</th>
			<th>申请原因</th>
			<th>链接</th>
		</tr></thead>
		<tbody>
			<c:forEach items="${dsplist}" var="d" varStatus="status">
				<tr>
					<td><input type="checkbox" value="${d.ordno}" name="ordno"/></td>
					<td>${status.count}</td>
					<td>${d.areaName}</td>
					<td>${d.plant}</td>
					<td>
						${d.ordno}
					</td>
					<td>${d.matname}</td>
					<td>
						<fmt:formatDate value="${d.sampdate}" type="both" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate>
					</td>
					<td>${d.fullname}</td>
					<td>
						<fmt:formatDate value="${d.tjTime}" type="both" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate>
					</td>
					<td>${d.tjRemark}</td>
					<td>
						<label class="btn btn-link" style="margin: 0px;padding: 0px;" onclick="showDetail('${d.ordno}')">明细</label>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
</div>
	


<script type="text/javascript">

$(window).on('resize', function() {
	$("#treeContent").height($("body").height()-45);
}).resize();

function showDetail(ordno){
	layer.open({
		  type: 2 //Page层类型
		  ,scrollbar:false
		  ,area: ['70%', '90%']
		  ,title: '检测结果'
		  ,content: '${pageContext.request.contextPath}/lims/dataSearch/daily/ordDetail?optHide=true&ordNo='+ordno
	});    
}

function tg(){
	var ordArray = [];
	$("input:checkbox[name='ordno']:checked").each(function() {
		ordArray.push($(this).val());
	});
	
	if(ordArray.length==0){
		layer.msg('请选择审批通过的样品',{time:1000});
		return;
	}else{
		$.ajax({
			url:"${pageContext.request.contextPath}/lims/zltj/kh/mkhtg",
			traditional:true,
			type: 'POST',
			dataType: "json",
			data:{"ords":ordArray},
			success:function(d){
				if(d.code=="SUCCESS"){
					layer.msg('审批通过',{time:1000});
					$("input:checkbox[name='ordno']:checked").each(function() {
						$(this).parent().parent().remove();
					})
				}else{
					layer.alert('审批失败');
				}
			}
		});
	}
}


function bh(){
	var ordArray = [];
	$("input:checkbox[name='ordno']:checked").each(function() {
		ordArray.push($(this).val());
	});
	
	if(ordArray.length==0){
		layer.msg('请选择驳回的样品',{time:1000});
		return;
	}else{
		layer.prompt({title: '驳回原因', formType: 2}, function(txt, index){
			layer.close(index);
			$.ajax({
				url:"${pageContext.request.contextPath}/lims/zltj/kh/mkhbh",
				traditional:true,
				type: 'POST',
				dataType: "json",
				data:{"ords":ordArray,"yj":txt},
				success:function(d){
					if(d.code=="SUCCESS"){
						layer.msg('成功驳回申请',{time:1000});
						$("input:checkbox[name='ordno']:checked").each(function() {
							$(this).parent().parent().remove();
						})
					}else{
						layer.alert('驳回失败');
					}
				}
			});
		});
	}
}
</script>
</body>
</html>