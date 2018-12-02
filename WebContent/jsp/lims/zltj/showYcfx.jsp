<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/header/meta.jsp"%>
<%@include file="/common/header/jquery.jsp"%>
<%@include file="/common/header/laydate.jsp"%>

<title>不合格分析项</title>
<style type="text/css">
	html,body{
		margin:0px;
		padding:0px;
	}
	table{
		border-collapse: collapse;
		width: 100%;
	}
	th{
		white-space: nowrap;
		border: 1px solid #000;
		text-align: center;
		vertical-align: middle;
		background-color: rgb(204,255,204);
		padding: 10px;
        font-size: 12px;
	}
	
	.tdxm{
		white-space: nowrap;
	}
	
	
 table td{
 	border:1px solid #000;
 	text-align: center;
	vertical-align: middle;
    font-size: 12px;
 }
 
</style>
</head>
<body>
<table>
	<thead>
		<tr>
			<th colspan="6">不合格检测项目原因</th>
		</tr>
		<tr>
			<th>序号</th>
			<th>车间</th>
			<th>装置</th>
			<th>分析项</th>
			<th>时间</th>
			<th>原因</th>
		</tr>
	</thead>

	 <c:forEach items="${ycfx }" var="ycfx" varStatus="status">
	 	<tr>
	 		<td style="text-align: center;">${status.count }</td>
	 		<td><c:out value="${ycfx.real_area }"></c:out></td>
	 		<td><c:out value="${ycfx.real_plant }"></c:out></td>
	 		<td><c:out value="${ycfx.analyte }"></c:out></td>
	 		<td nowrap="nowrap"><c:out value="${ycfx.sampdate }"></c:out></td>
	 		
	 		<td style="text-align: left;"><c:out value="${ycfx.remark }"></c:out></td>
	 	</tr>
	 </c:forEach>
</table>
</body>
</html>