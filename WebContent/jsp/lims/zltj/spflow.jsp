<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>审批流程</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/js/common.js"></script>

<style type="text/css">
	html,body{
		padding: 5px;
		background-color: #ECECEC;
	}

	.panel,.panel-body{
		border-radius:0px!important;
		
	}
	.table tr td{
		border: 1px solid #000000 !important;
        font-size: 12px;
	}
	.table tr th{
		border: 1px solid #000000 !important;
        font-size: 12px;
	}
</style>
</head>
<body>

<div class="panel panel-success" style="margin-bottom:0px;">
	<table class="table table-bordered">
		<thead>
			<tr>
				<th>序号</th>
				<th>环节</th>
				<th>操作</th>
				<th>操作人</th>
				<th>操作时间</th>
				<th>审批意见</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${spflow}" var="v" varStatus="row">
			<tr>
				<td>${row.count }</td>
				<td>${v.activeName}</td>
				<td>${v.activeOper}</td>
				<td>${v.fullname}</td>
				<td><fmt:formatDate value="${v.operTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${v.operYj}</td>		
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>



</body>
<script type="text/javascript">
	
	function changePoint(pointId){
		if($("#"+pointId).is(":checked")){
			$("input[name='pointId']").val(pointId);
			$("input[name='isCheck']").val(true);
		}else{
			$("input[name='pointId']").val(pointId);
			$("input[name='isCheck']").val(false);
		};
		
		var parmas = $("form").serialize();
		//获取装置及采样点信息
		$.ajax({type: 'POST',
			async: false,
			dataType:"json",
			data:parmas,
			url:"${pageContext.request.contextPath}/lims/screen/gq/saleSet",
			success: function(data){
				
			}
		});
		
	}
</script>
</html>