<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
 	text-align: center;
	vertical-align: middle;
	font-size: 12px;
 }
 
</style>
</head>
<body>
<form action="${pageContext.request.contextPath }/lims/basic/zlhk/savePlants" method="post">
<input type="hidden" name="area" value="${area }"/>
<table style="min-width: 300px;">
	<thead>
		<tr>
			<th>序号</th>
			<th>装置名称</th>
			<th>质量目标</th>
			<th>是否考核</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${plants}" var="plant" varStatus="status">
			<tr>
			<td>${status.count }
				<input type="hidden" name="plants[${status.index }].areaName" value="${plant.areaName}"/>
			</td>
			<td>${plant.plant }
				<input type="hidden" name="plants[${status.index }].plant" value="${plant.plant }">
			</td>
			<td><input type="text" value="${plant.basicRate }" name="plants[${status.index }].basicRate"/> </td>
			<td>
				<c:choose>
					<c:when test="${plant.isCheck==1 }">
						<input type="checkbox" value="1" checked="checked" name="plants[${status.index }].isCheck">
					</c:when>
                  
					<c:otherwise>
						<input type="checkbox" value="1"  name="plants[${status.index }].isCheck">
					</c:otherwise>
           
				</c:choose>
			</td>
			</tr>
		</c:forEach>	
		<!--<fmt:formatNumber type="percent" maxFractionDigits="2" value="${fxxm2.hglMap[rq]}" ></fmt:formatNumber>-->
	</tbody>
	<tfoot>
		<tr>
			<td colspan="4" > <input type="submit" value="提交"/></td>
		</tr>
	</tfoot>
</table>
</form>
</body>
</html>