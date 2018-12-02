<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
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
	padding:2px 2px 2px 2px;
    font-size: 12px;
 }
 
</style>

</head>
<body>

<table id="table11">
	<thead>
		<tr>
			<th colspan="${fn:length(dateList)+3}" style="font-size: 24px;background-color: rgb(0,250,0)">
				月度车间-装置合格率
			</th>
		</tr>
		<tr>
			<th colspan="${fn:length(dateList)+3}" style="text-align: left;">
				<form action="${pageContext.request.contextPath }/lims/zltj/kh/zzhglNew" method="post">
<input type="hidden" name="deptCode" value="${deptCode }"/>
       考核日期：<input type="text" id="startDate" name="startDate" readonly="readonly" value="${startDate }"> &nbsp;至 &nbsp;&nbsp;
       <input type="text" id="endDate" name="endDate" readonly="readonly" value="${endDate }">
       <input type="submit" value="查询"/>  &nbsp;&nbsp;&nbsp;<input type="button" value="导出至excel" onclick="expExcel()"/>
       <input type="button" value="异常分析项汇总" onclick="ycfx()"/>
</form>  
			</th>
		</tr>
			<tr>
				<th>车间名称</th>
				<th>装置名称</th>
				<c:forEach items="${dateList }" var="d">
					<th>${d }</th>
				</c:forEach>
				<th>月累计</th>
			</tr>
		</thead>
		<tbody>
			
		<c:forEach var="cj" items="${rdata }" begin="0">
			<tr>
				<td rowspan="${cj.rowNum }" style="background-color: rgb(255,204,153);">${cj.name }</td>
				<td nowrap="nowrap" ondblclick="showAnalyte('${cj.name }','${cj.data[0].name }')">${cj.data[0].name }</td>
				<c:forEach items="${dateList }" var="rq">
					<td><fmt:formatNumber type="percent" maxFractionDigits="2" value="${cj.data[0].hglMap[rq]}" ></fmt:formatNumber></td>
				</c:forEach>
				<td><fmt:formatNumber type="percent" maxFractionDigits="2" value="${cj.data[0].doneNum*1.0/cj.data[0].totalNum }" ></fmt:formatNumber></td>
				
			</tr>
			<c:forEach items="${cj.data}" begin="1" var="zz">
			<tr>
				<td nowrap="nowrap" ondblclick="showAnalyte('${cj.name }','${zz.name }')">${zz.name }</td>
				<c:forEach items="${dateList }" var="rq">
					<td><fmt:formatNumber type="percent" maxFractionDigits="2" value="${zz.hglMap[rq] }" ></fmt:formatNumber></td>
				</c:forEach>
				<td><fmt:formatNumber type="percent" maxFractionDigits="2" value="${zz.doneNum*1.0/zz.totalNum }" ></fmt:formatNumber></td>
			</tr>
			</c:forEach>
		</c:forEach>
		<tr>
				<td colspan="2">总合格率</td>
				
			  	<c:forEach items="${dateList}" var="rq">
			  		<td>
			  			<fmt:formatNumber type="percent" maxFractionDigits="2" value="${rqhgl[rq] }" ></fmt:formatNumber>
			  		</td>
			  	</c:forEach>
			  	
			  	<td>
					<fmt:formatNumber type="percent" maxFractionDigits="2" value="${zhgl}" ></fmt:formatNumber>
			  	</td>
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
	 function ycfx(){
			var startDate  = $("#startDate").val();
			var endDate = $("#endDate").val();
			var url ="${pageContext.request.contextPath }/lims/zltj/kh/ycfx?startDate="+startDate+"&endDate="+endDate;
			layer.open({
				type:2,
				title:"趋势图",
				area:["100%","100%"],
				content:url,
				maxmin: true
			});
			//window.open("${pageContext.request.contextPath }/lims/zltj/kh/ycfx?startDate="+startDate+"&endDate="+endDate);
		}
	 function showAnalyte(area,plant){
			var areaUri = encodeURIComponent(area);
			var plantUri = encodeURIComponent(plant);
			var startDate  = $("#startDate").val();
			var endDate = $("#endDate").val();
			//showModalDialog("${pageContext.request.contextPath }/lims/zltj/kh/zzYCFX?startDate="+startDate+"&endDate="+endDate+"&area="+areaUri+"&plant="+plantUri,"","dialogWidth:600px;dialogHeight:400px;resizable:yes;status:no;help:no;");
			var url= "${pageContext.request.contextPath }/lims/zltj/kh/zzYCFX?startDate="+startDate+"&endDate="+endDate+"&area="+areaUri+"&plant="+plantUri;
			layer.open({
				type:2,
				title:"不合格项目",
				area:["100%","100%"],
				content:url,
				maxmin: true
			});
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
		 var content ="<table>"+ $("#expTable").html()+"</table>";
		 
		 $("#expContent").val(content);
		 
	   //提交表单，实现导出
	   $eleForm.submit();
	}
		
		function Cleanup() {  
		   
		   CollectGarbage();  
		 }  

</script>
</html>