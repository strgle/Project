<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/header/meta.jsp"%>
<title>检测数据汇总</title>
<%@include file="/common/header/jquery.jsp"%>
<%@include file="/common/header/default.jsp"%>
<style type="text/css">
	html,body{
		background-color: #fff;
		padding: 0px;
		margin: 0px;
		width: 100%;
		height: 100%;
		overflow: hidden;
	}
	
	.table thead tr th{ 
		min-width:80px;
	}
	
</style>
</head>
<body>
	<div style="position: relative;height: 100%;overflow: auto;" id="tableContaint">
	<table style="position:absolute;top:0px;left: 60px;right: 0px;" class="table" id="hidefix">
		<thead>
			<tr>
				
				<th colspan="${fn:length(matHz.titles)+3}">A线</th>
				<th colspan="${fn:length(matHz.titles)+3}">B线</th>
			</tr>
			<tr>
				
				<th rowspan="2" style="width:120px;min-width:120px;max-width: 120px;">批次</th>
				<c:forEach items="${matHz.titles }" var="title">
				<th>${title.sinonym}</th>
				</c:forEach>
				<th rowspan="2">等级</th>
				<th rowspan="2">备注</th>
				<th rowspan="2" style="width:120px;min-width:120px;max-width: 120px;">批次</th>
				<c:forEach items="${matHz.titles }" var="title">
				<th>${title.sinonym}</th>
				</c:forEach>
				<th rowspan="2">等级</th>
				<th rowspan="2">备注</th>
			</tr>
			<tr>
				<c:forEach items="${matHz.titles }" var="title">
				<th>${title.charlimit}&nbsp;</th>
				</c:forEach>
			
				<c:forEach items="${matHz.titles }" var="title">
				<th>${title.charlimit}</th>
				</c:forEach>
				
			</tr>
		</thead>
	</table>
	
	<table style="position:absolute;top:93px;left: 0px;right: 0px;width: 60px;" class="table" id="hidefixleft">
		<tbody>
			<c:forEach begin="1" end="${fn:length(matHz.lineA )}" varStatus="status">
				<tr>
					<td style="text-align: center;">${status.index}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<table style="position:absolute;top:0px;left: 0px;right:0px;width: 60px;height:93px;" class="table" id="hidefixjc">
		<thead>
			<tr>
				<th style="width:60px;min-width: 60px;">序号</th>
			</tr>
		</thead>
	</table>
	
	<table class="table">
		<thead>
			<tr>
				<th rowspan="3" style="width:60px;min-width: 60px;">序号</th>
				<th colspan="${fn:length(matHz.titles)+3}">A线</th>
				<th colspan="${fn:length(matHz.titles)+3}">B线</th>
			</tr>
			<tr>
				<th rowspan="2" style="width:120px;min-width:120px;max-width: 120px;">批次</th>
				<c:forEach items="${matHz.titles }" var="title">
				<th>${title.sinonym}</th>
				</c:forEach>
				<th rowspan="2">等级</th>
				<th rowspan="2">备注</th>
				<th rowspan="2" style="width:120px;min-width:120px;max-width: 120px;">批次</th>
				<c:forEach items="${matHz.titles }" var="title">
				<th>${title.sinonym}</th>
				</c:forEach>
				<th rowspan="2">等级</th>
				<th rowspan="2">备注</th>
			</tr>
			<tr>
				<c:forEach items="${matHz.titles }" var="title">
				<th>${title.charlimit}&nbsp;</th>
				</c:forEach>
			
				<c:forEach items="${matHz.titles }" var="title">
				<th>${title.charlimit}</th>
				</c:forEach>
				
			</tr>
		</thead>
	 
		<tbody>
			<c:forEach begin="1" end="${fn:length(matHz.lineA )}" varStatus="status">
				<tr>
					<td>${status.index}</td>
					<td>${matHz.lineA[status.index-1].batchno}</td>
					<c:forEach items="${matHz.titles }" var="title">
					<td>${matHz.lineA[status.index-1][fn:toLowerCase(title.rnMap)] }</td>
					</c:forEach>
					<td>${matHz.lineA[status.index-1].brand}</td>
					<td>${matHz.lineA[status.index-1].comments}</td>
					<td>${matHz.lineB[status.index-1].batchno}</td>
					<c:forEach items="${matHz.titles }" var="title">
					<td>${matHz.lineB[status.index-1][fn:toLowerCase(title.rnMap)] }</td>
					</c:forEach>
					<td>${matHz.lineB[status.index-1].brand}</td>
					<td>${matHz.lineB[status.index-1].comments}</td>
				</tr>
			</c:forEach>
		</tbody>
	
	</table>
	</div>
</body>
<script type="text/javascript">
	
	
	$('#tableContaint').on('scroll',function(){
		var top = $('#tableContaint').scrollTop();
		var left = $('#tableContaint').scrollLeft();
		$("#hidefix").css("top",top);
		$("#hidefixleft").css("left",left);
		$("#hidefixjc").css("left",left);
		$("#hidefixjc").css("top",top);
	});
	
	function expExcel(){
		 //判断下载form是否存在
		 var $elemForm = $("#expExcelForm");
		 if($elemForm.length==0){
			 $eleForm = $("<form method='post' id='expExcelForm' enctype='multipart/form-data'></form>");
			 var expContent = $("<input type='hidden' id='expContent' name='expContent' />");
			 $eleForm.append(expContent);
		     $eleForm.attr("action","${pageContext.request.contextPath}/lims/common/expExcel");
		     $(document.body).append($eleForm);
		 }
		 var content ="<table>"+ $("#expTable").html()+"</table>";
		 
		 $("#expContent").val(content);
		 
	   //提交表单，实现导出
	   $eleForm.submit();
	}
</script>
</html>