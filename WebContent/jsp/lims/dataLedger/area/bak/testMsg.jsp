<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>检测台账</title>
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
<div class="panel panel-success" style="margin-bottom: 0px;">
	<div class="panel-heading" style="border: 1px solid #000;">
		<div class="panel-title" style="text-align: center;font-weight: bold;font-size: 16px;letter-spacing: 5px;">样品检测台账</div>
	</div>
	<div class="panel-body" style="padding: 0px;">
		<c:forEach items="${data}" var="d">
		<div class="panel panel-default" style="margin:-1px -1px -1px;padding: 0px;">
			<div class="panel-heading" style="border: 1px solid #000;border-bottom: 0px;background-color: #F0F7FB!important;">
				<div class="panel-title" style="font-size: 14px;letter-spacing: 2px;font-weight: bold;">${d[0] }</div>
			</div>
			<table class="table table-bordered" style="margin: 0px;">
				<tr>
					<td style="width:210px;">项目/时间</td>
					<c:forEach items="${d[2][0]}" var="dd" varStatus="status">
						<td style="text-align: center;width:150px;">${dd}</td>
					</c:forEach>
					<c:forEach begin="${fn:length(d[2][0])+1}" end="3">
							<td style="text-align: center;width:150px;">&nbsp;</td>
					</c:forEach>
				</tr>
				<c:forEach items="${d[1]}" begin="1" var="d1" varStatus="st1">
					<tr>
						<td >${d1}</td>
						<c:forEach items="${d[2][st1.count]}" var="d2">
							<td style="text-align: center;">${d2['finalnum']}</td>
						</c:forEach>
						<c:forEach begin="${fn:length(d[2][st1.count])+1}" end="3">
							<td style="text-align: center;">&nbsp;</td>
						</c:forEach>
					</tr>
				</c:forEach>
			</table>
		</div>
		</c:forEach>
	</div>
</div>

</body>
<script type="text/javascript">
	function showDate(datas){
		var curDate = $("#oldProdDate").val();
		if(curDate!=datas){
			var url="${pageContext.request.contextPath}/lims/dataLedger/area/testDetail"
			$("form").attr("action",url);
			$("form").submit();
		}
	}
</script>
</html>