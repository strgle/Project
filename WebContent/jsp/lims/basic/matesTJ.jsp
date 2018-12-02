<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">  
<title>Insert title here</title>
<style type="text/css">
	html,body{
		margin:0px;
		padding:0px;
		width: 100%;
		height: 100%;
	}
	table{
		border-collapse: collapse;
	}
	th{
		white-space: nowrap;
		border: 1px solid #000;
		text-align: center;
		vertical-align: middle;
		background-color: rgb(255,204,153);
		padding: 10px;
        font-size: 12px;
	}
	
	.tdxm{
		white-space: nowrap;
	}
	
 table td{
 	border:1px solid #000;
	vertical-align: middle;
    font-size: 12px;
	
 }
 li{
 	float:left;
 	list-style: none;
 	width:120px;
 	margin:5px 10px 5px 0;
 	
 }
 
</style>
</head>
<body>
<form action="${pageContext.request.contextPath }/lims/basic/zlhk/saveMat" method="post">
<table style="width:100%;">
	<thead>
		<tr>
			<th style="width:20%;">车间</th>
			<th style="width:80%;">样品</th>
		</tr>
	</thead>
	<tbody>
	<tr>
		<td>${areaName}
			<input type="hidden" name="areaName" value="${areaName}"/>
			<input type="hidden" name="usrnam" value="${usrnam}"/>
		</td>
		<td>
			<ul>
			<c:forEach items="${mateList}" var="mat">
				<c:choose>
					<c:when test="${mat.ischeck==1 }">
						<li><input type="checkbox" value="${mat.matcode }" checked="checked" name="matcode"><font style="font-weight: bolder;color: red;">${mat.matname } </font></li>
					</c:when>
					<c:otherwise>
						<li><input type="checkbox" value="${mat.matcode }" name=matcode>${mat.matname }</li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			</ul>
		</td>
	</tr>
	<tr>
		<td style="text-align: center;" colspan="2"><input type="submit" value="提交" style="background-color: #FFF;border: solid 1px #ff0;font-size: 18px;"/></td>
	</tr>
	</tbody>
</table>
</form>
</body>
</html>