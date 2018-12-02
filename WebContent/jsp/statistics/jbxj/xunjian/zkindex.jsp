<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/header/meta.jsp"%>
<title>每日巡检记录</title>
<%@include file="/common/header/jquery.jsp"%>
<%@include file="/common/header/laydate.jsp"%>
<%@include file="/common/header/default.jsp"%>
<style type="text/css">

	.table tr td{
		text-align: center;
	}
</style>
</head>
<body>
<div class="search">
<!-- 查询条件部分 -->
<div class="condition">
	<!--中控巡检情况记录展示  lqygcy 0920 -->
	<form action="${pageContext.request.contextPath}/statistics/jbxj/xunjian/zk" method="post">
	<table>
		<tr>
			<td>巡检时间：</td>
			<td>
				<input type="text" id="endDate" name="endDate" readonly="readonly" class="dateTime" value="${endDate}" autocomplete="off"/>
				<select name="endTime" class="dateTime" id="endTime">
					<option value="15:00:00">15</option>
				</select>
			</td>
			<td style="padding-left: 20px;"><button class="btn btn-primary btn-sm" type="button" onclick="query()">查询</button></td>
			<td style="padding-left: 20px;"><button class="btn btn-default btn-sm" type="button" onclick="expExcel()">导出excel</button></td>
		</tr>
	</table>
	</form>
</div>

<!-- 结果展示部分 -->
<div class="result">
	 <table class="table" id="expTable">
	 	<tr>
	 		<th colspan="6"  style="background-color: #D4BFFF;padding: 10PX;font-size: 15PX;letter-spacing: 2PX;">中控巡检情况记录</th>
	 	</tr>
	 	<tr>
	 		<th>检测样品</th>
	 		<th>检测项</th>
	 		<th>检测次数</th>
	 		<th>合格次数</th>
	 		<th>合格率</th>
	 		<th>中控指标</th>
	 	</tr>
	 	
	 	<c:forEach items="${process}" var="data" >
	 		<tr>
	 			<td>${data.matname }<br />
	 				(${data.pointId })
	 			</td>
	 			<td>${data.analyte }</td>
	 			<td>${data.totalNum }</td>
	 			<td>${data.doneNum + data.oosbNum }</td>
	 			<td>
	 				<c:if test="${data.totalNum ==0}">/</c:if>
					<c:if test="${data.totalNum >0}">
						<fmt:formatNumber value="${(data.doneNum + data.oosbNum)/data.totalNum }" type="percent" maxFractionDigits="2" minFractionDigits="2"/>
					</c:if>
	 			</td>
	 			<td>${data.charlimits}</td>
	 		</tr>
	 	</c:forEach>
	 	<tr>
	 			<td colspan="2">汇总</td>
	 			
	 			<td>${procTotal.totalNum }</td>
	 			<td>${procTotal.doneNum + procTotal.oosbNum }</td>
	 			<td>
	 				<c:if test="${procTotal.totalNum ==0}">/</c:if>
					<c:if test="${procTotal.totalNum >0}">
						<fmt:formatNumber value="${(procTotal.doneNum + procTotal.oosbNum)/procTotal.totalNum }" type="percent" maxFractionDigits="2" minFractionDigits="2"/>
					</c:if>
	 			</td>
	 			<td>/</td> 
	 		 </tr> 
	 	

	 	
	 	<tr>
	 		<th colspan="6"  style="background-color: #D4BFFF;padding: 10PX;font-size: 15PX;letter-spacing: 2PX;">每日巡检备注</th>
	 	</tr>
	 	<tr>
	 		<td colspan="6">
	 			<c:forEach items="${remarks }" var="remark" varStatus="status">
	 				<textarea rows="3" style="width:100%;"  data-id="${remark.id}" id="remark">${remark.remark}</textarea>
	 			</c:forEach>
			</td>
	 	</tr>
	 </table>  
</div>
</div>
</body>
<script type="text/javascript">
	laydate.render({
		elem: '#endDate'
		,type: 'date'
		,format: 'yyyy-MM-dd'
		,done: function(value, date, endDate){
			query();
		  }
	}); 

	function query(){
		$("form").submit();
	}
	
	$("#remark").blur(function(){
		var value = $(this).val();
		var id = $(this).data("id");
		updateRemark(id,value);
	});
	
	function updateRemark(id,value){
		var node={};
		node.id=id;
		node.value=value;
		var params = $.param(node);
		var urls="/jbxj/statistics/jbxj/xunjian/remark";
		$.ajax({
			url:urls,
			data:params,
			type: 'POST',
			dataType: "json",
			success:function(data){
				if(data.code=="SUCCESS"){
						
				}else{
					
				}
			}
		 });
	}

	$(":text").blur(function(){
		var value = $(this).val();
		var id = $(this).data("id");
		updateValue(id,value);
	});
	
	$(":text").keyup(function(event){
		if(event.keyCode==13){
			var value = $(this).val();
			var id = $(this).data("id");
			updateValue(id,value);
		}
	});
	
	function updateValue(id,value){
		var node={};
		node.id=id;
		node.value=value;
		var params = $.param(node);
		var urls="${pageContext.request.contextPath}/statistics/jbxj/rate/manual";
		$.ajax({
			url:urls,
			data:params,
			type: 'POST',
			dataType: "json",
			success:function(data){
				if(data.code=="SUCCESS"){
						
				}else{
					
				}
			}
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