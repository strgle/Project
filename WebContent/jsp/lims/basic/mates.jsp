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
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0">

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
<table  style="width:100%;">
	<thead>
		<tr>
			<th>序号</th>
			<th>检测项目</th>
			<th>分析项</th>
			<th style="width:60px;">操作</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${mate.tests}" var="tests" varStatus="status">
	<c:choose>
		<c:when test="${status.index%2==0 }">
			<tr>
		</c:when>
		<c:otherwise>
			<tr style="background-color: rgb(241,247,255);">
		</c:otherwise>
	</c:choose>
	
		<form action="${pageContext.request.contextPath }/lims/basic/zlhk/saveTest" method="post">
			<td style="text-align: center;">${status.count }</td>
			<td>${tests.testNo }(${tests.testCode})
				<input type="hidden" name="areaName" value="${mate.areaName}"/>
				<input type="hidden" name="mateCode" value="${mate.mateCode}"/>
				<input type="hidden" name="testCode" value="${tests.testCode}"/>
			</td>
			
			<td>
			<c:forEach items="${tests.analyteList }" var="anly">
				<c:choose>
					<c:when test="${anly.isCheck==1 }">
						<li><input type="checkbox"  value="${anly.analyte }" checked="checked" name="analyte"><font style="font-weight: bolder;color: red;">${anly.spAnalyte } </font></li>
					</c:when>
					<c:otherwise>
						<li><input type="checkbox" value="${anly.analyte }" name="analyte">${anly.spAnalyte }</li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			</td>
			<td style="text-align: center;" >
              <input type="submit" value="提交" style="background-color: #FFF;border: solid 1px #ff0;font-size: 18px;"/>
              
            </td>
		</form>
	</tr>
	</c:forEach>	
	</tbody>
</table>

</body>
</html>