<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/header/meta.jsp"%>
<%@include file="/common/header/jquery.jsp"%>
<%@include file="/common/header/laydate.jsp"%>
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
		background-color: rgb(255,204,153);
	}
	
	.tdxmat{
		background-color: rgb(255,204,153);
	}
	
 table td{
 	border:1px solid #000;
 	text-align: center;
	vertical-align: middle;
	padding: 5px 0 5px 0;
	background-color: rgb(240,236,224);
    font-size: 12px;
 }
 .search{
 	background: rgb(217,232,251);
 	margin: 0px;
 }
 form{
 	margin:0px;
 	padding:0px;
 }
</style>
</head>
<body>
<div style="margin: 0px;padding: 0px;">
<table id="dataTable">
	<thead>
		<tr>
			<th colspan="7" style="text-align: left;">
			<form action="${pageContext.request.contextPath }/lims/zltj/kh/detailData" method="post">
	<label>开始时间：</label><input type="text" id="startTime" name="startTime" readonly="readonly" value="${startTime }">
	<label>结束时间：</label><input type="text" id="endTime" name="endTime" readonly="readonly" value="${endTime }">
	<input type="submit" value="查询" style="width: 120px;"/>
	</form>
			</th>
		</tr>
		
		<tr>
			<th colspan="7" style="text-align: center;">
				${dept }
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				${startTime } &nbsp;&nbsp;至  &nbsp;&nbsp;${endTime }
			</th>
		</tr>
			<tr>
				<th>车间</th>
				<th>时间</th>
				<th>样品</th>
				<th>分析项目</th>
				<th>指标</th>
				<th>备注</th>
				<th>采样点</th>
			</tr> 
		</thead>
		<tbody>
			
			<c:forEach var="item" items="${areaOOS}">
				<tr>
                   
					<td rowspan="${fn:length(item.value)}">${item.key} </td>
					<td>${item.value[0].sampdate}</td>
					<td>${item.value[0].matname}</td>
					<td>${item.value[0].sinonym}: &nbsp;&nbsp;${item.value[0].analyteresult} ${item.value[0].units}</td>
					<td>${item.value[0].limit}</td>
					<td>&nbsp;</td>
					<td>${item.value[0].description}</td> 
        
				</tr>   
				 <c:forEach items="${item.value}" begin="1" var="ord">
				 	<tr>
          
				 		<td>${ord.sampdate}</td>
						<td>${ord.matname}</td>
						<td>${ord.sinonym}: &nbsp;&nbsp;${ord.analyteresult} ${ord.units}</td>
						<td>${ord.limit}</td>
						<td>&nbsp;</td>
						<td>${ord.description}</td>
				 	</tr>
				 </c:forEach>
			</c:forEach>  
		</tbody>
</table>

</div>

<script type="text/javascript">
 
  //执行一个laydate实例
    laydate.render({
      elem: '#startTime' //指定元素
      ,theme: 'molv'
      ,type: 'datetime'
    });
    laydate.render({
      elem: '#endTime' //指定元素
      ,theme: 'molv'
      ,type: 'datetime'
    }); 
  $("input[readOnly]").keydown(function(e) {
         e.preventDefault();
 });

</script>
</body>
</html>
