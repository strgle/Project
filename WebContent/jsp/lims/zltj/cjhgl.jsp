<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/header/meta.jsp"%>
<%@include file="/common/header/jquery.jsp"%>
<%@include file="/common/header/laydate.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/font-awesome/css/font-awesome.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/skins/links/bootstrap-3.3.7/css/bootstrap.min.css">
 <link rel="stylesheet" href="${pageContext.request.contextPath}/skins/fuelux/css/ace.min.css">

<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/bootstrap-3.3.7/js/bootstrap.min.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/layer/layer.js"></script> 
 
<title>Insert title here</title>
<style type="text/css">
	html,body{
		margin:0px;
		padding:0px;
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

<table id="expTable">
	<thead>
		<tr>
			<th colspan="${fn:length(dateList)+3}" style="font-size: 24px;background-color: rgb(0,250,0)">
				月度车间合格率
			</th>
		</tr>
		<tr>
		<th colspan="${fn:length(dateList)+3}" style="text-align: left;">
	   <form action="${pageContext.request.contextPath }/lims/zltj/kh/cjhglNew" method="post">
       <input type="hidden" name="deptCode" value="${deptCode }"/>
                 考核日期：<input type="text" id="startDate" name="startDate" readonly="readonly" value="${startDate }"> &nbsp;至 &nbsp;&nbsp;
       <input type="text" id="endDate" name="endDate" readonly="readonly" value="${endDate }">
       <input type="submit" value="查询"/> <input type="button" value="导出至excel" onclick="expExcel()"/>
         <input type="button" value="异常分析项汇总" onclick="ycfx()"/>
       </form>  
			</th>
		</tr>
			<tr>
				<th>车间名称</th>
				<th>项目</th>
				<th>月累计</th>
				<c:forEach items="${dateList }" var="d">
					<th>${d}</th>
				</c:forEach>
			</tr>
		</thead>
		<tbody>
			
		<c:forEach var="cj" items="${rdata }" begin="0">
			<tr>
				<td rowspan="3">${cj.name }</td>
				<td>合格数</td>
				<td>${cj.doneNum }</td>
				<c:forEach items="${dateList }" var="rq">
					<td><c:out value="${cj.doneMap[rq]}" /></td>
				</c:forEach>
			</tr>
			<tr>
				<td>总点数</td>
				<td>${cj.totalNum }
					<c:if test="${cj.checkNum>0 }">(${cj.checkNum})</c:if>
				</td>
				<c:forEach items="${dateList }" var="rq">
					<td><c:out value="${cj.totalMap[rq]}" /></td>
				</c:forEach>
			</tr>
			<tr>
				<td>合格率</td>
				<td><fmt:formatNumber type="percent" maxFractionDigits="2" value="${cj.doneNum*1.0/cj.totalNum }" ></fmt:formatNumber></td>
				<c:forEach items="${dateList }" var="rq">
					<td>
						<fmt:formatNumber type="percent" maxFractionDigits="2" value="${cj.hglMap[rq] }" ></fmt:formatNumber>
					</td>
				</c:forEach>
			</tr>
			</c:forEach>
			<tr>
				<td colspan="2">总合格率</td>
				<td>
    
					<fmt:formatNumber type="percent" maxFractionDigits="2" value="${zhgl}" ></fmt:formatNumber>
			  	</td>
			  	<c:forEach items="${dateList}" var="rq">
			  		<td>
			  			<fmt:formatNumber type="percent" maxFractionDigits="2" value="${rqhgl[rq] }" ></fmt:formatNumber>
			  		</td>
			  	</c:forEach>
			</tr>
		</tbody>
</table>
</body>
<script type="text/javascript">
    //执行一个laydate实例
    laydate.render({
      elem: '#startDate' //指定元素
      ,theme: 'molv'
    });
    laydate.render({
      elem: '#endDate' //指定元素
      ,theme: 'molv'
    }); 
   
	 $("input[readOnly]").keydown(function(e) {
         e.preventDefault();
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
	function Cleanup() {  
		   
		 CollectGarbage();  
	}  
	
	function ycfx(){
		var startDate  = $("#startDate").val();
		var endDate = $("#endDate").val();
		
		var url ="${pageContext.request.contextPath }/lims/zltj/kh/ycfx?startDate="+startDate+"&endDate="+endDate;
		layer.open({
			type:2,
			title:"趋势图",
			scrollbar:false,
			area:["100%","100%"],
			content:url,
			maxmin: true
		});
		
		//window.open("${pageContext.request.contextPath }/lims/zltj/kh/ycfx&startDate="+startDate+"&endDate="+endDate);
	}
</script>
</html>