<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0"> 
<%@include file="/common/header/meta.jsp"%>
<title>产品合格率统计</title>
<%@include file="/common/header/jquery.jsp"%>
<%@include file="/common/header/default.jsp"%>
<style type="text/css">
	html{
		background-color: #fff;
		padding:0px;
		margin:0px;
	}
	
	.table thead tr th{ 
		min-width:120px;
	}
	.table tbody tr td{ 
		text-align: center;
	}
</style>
</head>
<body onload="testl()">
<table class="table" id="expTable">
		<thead>
			
			<tr>
				<th>月份</th>
				<c:forEach items="${titles}" var="title">
					<th>${title.brand}
						<c:if test="${fn:contains(title.grade,'RATE')}">(%)</c:if>
					</th>
				</c:forEach>
			</tr>
		</thead>
		<tbody>
		
			<c:forEach items="${monthsData}" var="monthData" varStatus="status">
				<tr>
				<td>${status.count }</td>
				<c:forEach items="${titles}" var="title" varStatus="status2">
					<c:if test="${title.dataSource=='0' }">
					<td>
					</c:if>
					<c:if test="${title.dataSource=='1' }">
					<td style="padding: 2px 0px;">
					</c:if>
						<c:forEach items="${monthData }" var="detail">
							<c:if test="${title.brand == detail.brand && title.dataSource=='0'}">
								<c:choose>
									<c:when test="${fn:contains(detail.grade,'RATE')}">
										<fmt:formatNumber value="${detail.totalNum}" maxFractionDigits="2"></fmt:formatNumber>
									</c:when>
									<c:otherwise><fmt:formatNumber value="${detail.totalNum}" maxFractionDigits="0"></fmt:formatNumber></c:otherwise>
								</c:choose>
							</c:if>
							
							<c:if test="${title.brand == detail.brand && title.dataSource=='1'}">
								<c:choose>
									<c:when test="${fn:contains(detail.grade,'RATE')}">
										<input style="padding: 5px 8px;border:0px;width:100px;border-bottom: 1px solid #CCC;text-align:center;" type="text" data-id="${detail.id }" value='<fmt:formatNumber value="${detail.totalNum}" maxFractionDigits="2"></fmt:formatNumber>' autocomplete="off"/>
									</c:when>
									<c:otherwise>
										<input style="padding: 5px 8px;border:0px;width:100px;border-bottom: 1px solid #CCC;text-align:center;" type="text" data-id="${detail.id }" value='<fmt:formatNumber value="${detail.totalNum}" maxFractionDigits="0"></fmt:formatNumber>' autocomplete="off"/>
									</c:otherwise>
								</c:choose>
							</c:if>
						</c:forEach>
					</td>
				</c:forEach>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
<script type="text/javascript">
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