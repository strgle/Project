<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/header/meta.jsp"%>
<title>质量统计</title>
<%@include file="/common/header/jquery.jsp"%>
<%@include file="/common/header/laydate.jsp"%>
<%@include file="/common/header/layer.jsp"%>
<%@include file="/common/header/bootstrap.jsp"%>
<%@include file="/common/header/default.jsp"%>
<style type="text/css">
	html,body{
		background-color: #fff;
		padding: 0px;
		margin: 0px;
		width: 100%;
		height: 100%;
		overflow: hidden;
	}
	
	.table thead tr th{ 
		min-width:80px;
	}
	
</style>
</head>
<body>
<div class="search">
<!-- 查询条件部分 -->
<div class="condition">
	<form action="${pageContext.request.contextPath}/statistics/jbxj/oos/process/${matType}" method="post" id="form">
	<table>
		<tr>
			<td>时间：</td>
			<td><input type="text" id="month" name="month" style="width: 100px;" readonly="readonly" class="dateTime" value="${month }"/>
			</td>
			<td style="padding-left: 20px;"><button class="btn btn-primary btn-sm" type="submit">统计</button></td>
			<td style="padding-left: 20px;"><button class="btn btn-primary btn-sm" type="button" onclick="plt()">帕累托分析</button></td>
			<td style="padding-left: 20px;"><button class="btn btn-success btn-sm" type="button" onclick="expExcel()">导出excel</button></td>
		</tr>
	</table>
	</form>
</div>

<!-- 结果展示部分 -->
<div class="result" style="overflow: auto;">
	  <table class="table" id="expTable">
		<thead>
			<tr>
				<th style="width:60px;min-width:60px;">项目</th>
				<c:forEach items="${days }" var="day">
				<th>${day}</th>
				</c:forEach>
				<th>合计</th>
				<th>占比</th>
			</tr>
		</thead>
	 
		<tbody>
			<c:forEach items="${analytes }" var="product">
			<tr>
				<td>${product.matname}</td>
				<c:forEach begin="0" end="${fn:length(days)-1}" var="index">
				<td>${product.dayOosNum[index]}</td>
				</c:forEach>
				<td data-analyte="${product.matname}" data-value="${product.oosNum}" >${product.oosNum}</td>
				<td>
					<c:choose>
						<c:when test="${totalOosNum==0 }">0</c:when>
						<c:otherwise>
						<fmt:formatNumber type="PERCENT" value="${ product.oosNum/totalOosNum}" maxFractionDigits="2" ></fmt:formatNumber>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			</c:forEach>
			<tr>
				<td>合计</td>
				<c:forEach items="${days }" var="day">
				<td>&nbsp;</td>
				</c:forEach>
				<td data-value="${totalOosNum}">${totalOosNum }</td>
				<td>
					<c:choose>
						<c:when test="${totalOosNum==0 }">0</c:when>
						<c:otherwise>
						<fmt:formatNumber type="PERCENT" value="1" maxFractionDigits="2" ></fmt:formatNumber>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</tbody>
	</table>
</div>
</div>
</body>
<script type="text/javascript">
	laydate.render({
	   elem: '#month',
	   type: 'month',
	   btns: ['confirm'],
	   done: function(value){
		   $("#month").val(value);
		   $("#form").submit();
	   }
	});
	
	$(function(){
		//列计算
		var columnNum = $(".table thead").find("tr").find("th").length; //列数
		
		var rowNum = $(".table tbody").find("tr").length;
		
		for(var i=1;i<columnNum-2;i++){
			var columnTotal = 0;
			for(var j=0;j<rowNum-1;j++){
				var tdValue = $(".table tbody tr:eq("+j+") td:eq("+i+")").text();
				if(tdValue!=""){
					columnTotal = columnTotal+parseInt(tdValue,10);
				}
			}
			
			$(".table tbody tr:eq("+(rowNum-1)+") td:eq("+i+")").text(columnTotal);
		}
	});
	
	
	function pltdata(){
		var arr = [];
		//取值
		var columnNum = $(".table thead").find("tr").find("th").length; //列数
		var columnNum = columnNum-2;
		var rowNum = $(".table tbody").find("tr").length;
		
		for(var j=0;j<rowNum-1;j++){
			var td = $(".table tbody tr:eq("+j+") td:eq("+columnNum+")");
			var tdValue = $(td).data("value");
			if(tdValue>0){
				var tdjson = {};
				tdjson.name=$(td).data("analyte");
				tdjson.value=tdValue;
				arr.push(tdjson);
			}
		}
		
		arr.sort(compare());
		
		//获取总数
		rowNum = rowNum-1;
		var totalNum  = $(".table tbody tr:eq("+rowNum+") td:eq("+columnNum+")").data("value");
		var xTitle= [];
		var serail1 = [];
		var serail2 = [];
		var leijiNum = 0;
		var maxNum = 100;
		for(j = 0,len=arr.length; j< len; j++) {
			if(j==0){
				maxNum = (arr[j].value/10+1)*10;
			}
			xTitle.push(arr[j].name);
			serail1.push(arr[j].value);
			leijiNum = leijiNum+parseInt(arr[j].value,10);
			serail2.push(leijiNum*1.0/parseInt(totalNum,10)*100);
		}
	
		var title = { };
		title.xTitle=xTitle;
		title.serail1=serail1;
		title.serail2=serail2;
		title.serail1MaxNum=maxNum;
		return title;
	}
	
	//排序
	function compare(){
	    return function(a,b){
	        var value1 = a.value;
	        var value2 = b.value;
	        return value2 - value1;
	    }
	}
	
	function receiveReturn(){
		return pltdata();
	}
	
	function plt(){
		layer.open({
			content:"${pageContext.request.contextPath}/jsp/statistics/jbxj/oos/chart.jsp",
			type:2,
			area:['700px','480px']
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
</script>
</html>