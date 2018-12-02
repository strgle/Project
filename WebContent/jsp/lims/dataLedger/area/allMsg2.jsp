<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>调度台账</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/laydate/laydate.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/js/common.js"></script>

<style type="text/css">
	html,body{
		padding: 5px;
		background-color: #ECECEC;
	}

	.panel-default>.panel-heading{
		background-color: #FFFFFF;
	}
	
	.form-input {
	  width:140px;
	  padding-left:12px;
	  font-size: 14px;
	  line-height: 1.42857143;
	  color: #555;
	  background-color: #fff;
	}
	.panel,.panel-heading,.panel-title,.panel-body{
		border-radius:0px!important;
		
	}
	.table tr td{
		border: 1px solid #000000 !important;
		text-align: center;
	}
	.table tr th{
		border: 1px solid #000000 !important;
		text-align: center;
		vertical-align: middle;
		min-width:80px;
		padding: 0px;
	}
</style>
</head>
<body>
<form action="" method="post">
	<input type="hidden" name="areaName" value="${areaName}"/>
	<input type="hidden" name="plant" value="${plant}"/>
	
<div class="panel panel-default" style="margin-bottom:0px;">
	<div class="panel-heading">
		<div class="panel-title">
			<labe style="margin-right:20px;padding:5px 10px;font-size:14px;background-color:#5CB85C;color:#FFFFFF;">${areaName}&nbsp;—&nbsp;${plant}</labe>
		</div>
		<div class="panel-tools" style="top: 10px;right: 50px;">
			日期：<input name="startDate" class="laydate-icon form-input" placeholder="日期" value="${startDate}" id="startDate" type="text" autocomplete="off" onclick="laydate()"/>	
			&nbsp;至&nbsp;<input name="endDate" class="laydate-icon form-input" placeholder="日期" value="${endDate}" id="endDate" type="text" autocomplete="off" onclick="laydate()"/>	
			<label class="btn btn-success btn-xs" onclick="tj()">查询</label>
			<button type="button" class="btn btn-success btn-xs" style="margin-left:10px;" onclick="expExcel()">导出EXCEL</button>
		</div>
	</div>
</div>
</form>
<div style="overflow: auto;position:absolute;bottom: 0px;top:55px;left:8px;right:8px;margin: 0px;background-color: #FFF;">
	<table class="table table-bordered" id="contentTable">
		<thead>
			<tr>
				<th>日期</th>
				<th>时间</th>
				<th colspan="${fn:length(items) }">操作参数</th>
				<th colspan="${fn:length(inItems) }">供方</th>
				<th colspan="${fn:length(outItems) }">出方产量</th>
				<th colspan="${fn:length(outItems)+1 }">出方收率</th>
			</tr>
			<tr>
				<th>&nbsp;</th>
				<th>&nbsp;</th>
				<c:forEach items="${items}" var="item">
					<th>${item.prodCondition}&nbsp;${item.units}</th>
				</c:forEach>
				<c:forEach items="${inItems}" var="item">
					<th>${item.prodCondition}&nbsp;${item.units}</th>
				</c:forEach>
				<c:forEach items="${outItems}" var="item">
					<th>${item.prodCondition}&nbsp;${item.units}</th>
				</c:forEach>
				<c:forEach items="${outItems}" var="item">
					<th>${item.prodCondition}&nbsp;${item.units}</th>
				</c:forEach>
				<th>总收率</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${datas}" var="data" varStatus="status">
				<c:forEach items="${data.itemValues}" varStatus="row" var="iv">
					<tr>
					<c:if test="${row.index==0 }">
						<td rowspan="${fn:length(data.prodTimes)}">${data.prodDate}</td>
					</c:if>
					<td>${data.prodTimes[row.index]}</td>
					<c:forEach items="${items }" var="v">
						<td>${iv[v.prodCondition]}</td>
					</c:forEach>
					
					<c:forEach items="${inItems }" var="v">
						<td>${inDatas[status.index].itemValues[row.index][v.prodCondition] }</td>
					</c:forEach>
					
					<c:forEach items="${outItems }" var="v">
						<td>${outDatas[status.index].itemValues[row.index][v.prodCondition] }</td>
					</c:forEach>
					<c:forEach items="${outItems }" var="v">
						<td>${outDatas[status.index].slValues[row.index][v.prodCondition] }</td>
					</c:forEach>
					<td>${outDatas[status.index].slValues[row.index]['_总收率_']}</td>
					</tr>
				</c:forEach>
			</c:forEach>
		</tbody>
	</table>
</div>
</body>
<script type="text/javascript">
	function tj(){
		var url="${pageContext.request.contextPath}/lims/dataLedger/area/detail2"
		$("form").attr("action",url);
		$("form").submit();
	}
	
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
		 var content ="<table>"+ $("#contentTable").html()+"</table>";
		 
		 $("#expContent").val(content);
		 
	   //提交表单，实现导出
	   $eleForm.submit();
	}
</script>
</html>