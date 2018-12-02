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
	}
	.table tr th{
		border: 1px solid #000000 !important;
	}
</style>
</head>
<body>
<form action="" method="post">
	<input type="hidden" name="areaName" value="${areaName}"/>
	<input type="hidden" name="plant" value="${plant}"/>
	<input type="hidden" name="oldProdDate" value="${prodDate}" id="oldProdDate"/>
<div class="panel panel-default" style="margin-bottom:0px;">
	<div class="panel-heading">
		<div class="panel-title">
			<labe style="margin-right:20px;padding:5px 10px;font-size:14px;background-color:#5CB85C;color:#FFFFFF;">${areaName}&nbsp;—&nbsp;${plant}</labe>
		</div>
		<div class="panel-tools" style="top: 5px;right: 100px;">
			日期：<input name="prodDate" class="laydate-icon form-input" placeholder="日期" value="${prodDate}" id="prodDate" type="text" autocomplete="off" onclick="laydate({choose:showDate})"/>	
		</div>
	</div>
</div>
</form>
<div class="panel panel-success" style="margin-bottom:0px;">
	<div class="panel-heading" style="border: 1px solid #000;border-bottom: 0px;">
		<div class="panel-title" style="text-align: center;font-weight: bold;font-size: 16px;letter-spacing: 16px;">生产信息</div>
	</div>
	
		<table class="table table-bordered">
			<thead>
				<tr>
					<th style="width: 210px;">项目</th>
					<c:forEach items="${prodTimes}" var="time">
						<th>${time}</th>
					</c:forEach>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${prodCondition}" var="v" varStatus="row">
					<tr>
						<td>${v['prodCondition'] }
							<c:if test="${not empty v['units']}">
								, ${v['units']}
							</c:if>
						</td>
						<c:forEach begin="0" end="${prodColumnNum-1}" var="column" >
							<td>
								${datas[row.index][column] }
							</td>
						</c:forEach>
					</tr>
				</c:forEach>
			</tbody>
		</table>
</div>

<div class="panel panel-success" style="margin-bottom: 0px;margin-top: -2px;">
	<div class="panel-heading" style="border: 1px solid #000;border-bottom: 0px;">
		<div class="panel-title" style="text-align: center;font-weight: bold;font-size: 16px;letter-spacing: 16px;">物料平衡</div>
	</div>
	<table class="table table-bordered">
			<thead>
				<tr>
					<th colspan="2" style="text-align: center;vertical-align: middle;">项目/时间</th>
					<c:forEach items="${inDatas[0].prodTimes}" var="time">
						<th colspan="2" style="text-align: center;">${time}</th>
					</c:forEach>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty inItems}">
					<tr>
						<td rowspan="${fn:length(inItems)+1}">进方</td>
						<td style="text-align: center;">项目</td>
						<c:forEach items="${inDatas[0].prodTimes}">
							<td style="text-align: center;">加工量</td>
							<td style="text-align: center;">收率</td>
						</c:forEach>
					</tr>
				</c:if>
				<c:forEach items="${inItems}" var="v" varStatus="row">
					<tr>
						<td>${v.prodCondition }&nbsp;${v.untis }</td>
						<c:forEach items="${inDatas[0].prodTimes }" varStatus="status">
							<td>${inDatas[0].itemValues[status.index][v.prodCondition] }</td>
							<td>${inDatas[0].slValues[status.index][v.prodCondition] }</td>
						</c:forEach>
					</tr>
				</c:forEach>
				<c:if test="${not empty outItems}">
					<tr>
						<td rowspan="${fn:length(outItems)+1}">出方</td>
						<td style="text-align: center;">项目</td>
						<c:forEach items="${inDatas[0].prodTimes}">
							<td style="text-align: center;">加工量</td>
							<td style="text-align: center;">收率</td>
						</c:forEach>
					</tr>
				</c:if>
				<c:forEach items="${outItems}" var="v" varStatus="row">
					<tr>
						<td>${v.prodCondition}&nbsp;${v.untis}</td>
						<c:forEach items="${inDatas[0].prodTimes }" varStatus="status">
							<td>${outDatas[0].itemValues[status.index][v.prodCondition] }</td>
							<td>${outDatas[0].slValues[status.index][v.prodCondition] }</td>
						</c:forEach>
					</tr>
				</c:forEach>
			</tbody>
		</table>
</div>


</body>
<script type="text/javascript">
	function showDate(datas){
		var curDate = $("#oldProdDate").val();
		if(curDate!=datas){
			var url="${pageContext.request.contextPath}/lims/dataLedger/area/detail"
			$("form").attr("action",url);
			$("form").submit();
		}
	}
</script>
</html>