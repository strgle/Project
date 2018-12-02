<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>样品明细</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/bootstrap/css/bootstrap.min.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/js/common.js"></script>
<style type="text/css">
	html,body{
		text-align: center;
		height: 100%;
		width: 100%;
		overflow: auto;
		padding: 5px;
		background-color: #EcEcEc;
	}
	td{
		overflow:hidden;
		white-space:nowrap;
		max-width: 200px;
	}
	th{
		text-align: center;
	}
</style>
</head>
<body>

<table class="table table-bordered" id="contentTable" style="background-color: #fff;">
	<thead id="fixheader">
		<tr>
			<c:choose>
				<c:when test="${matType=='RAW' }">
					<th rowspan="6" style="vertical-align: middle;width: 80px;">样品名称</th>
					<th rowspan="6" style="vertical-align: middle;width: 160px;">分析项目</th>
				</c:when>
				<c:otherwise>
					<th rowspan="5" style="vertical-align: middle;width: 80px;">样品名称</th>
					<th rowspan="5" style="vertical-align: middle;width: 160px;">分析项目</th>
				</c:otherwise>
			</c:choose>

			<c:forEach items="${ords}" var="ord">
				<th style="width:160px;">${ord[0].sampdate}</th>
			</c:forEach>
			<c:choose>
				<c:when test="${matType=='RAW' }">
					<th rowspan="6" style="vertical-align: middle;width: 60px;">控制指标</th>
					<th rowspan="6" style="vertical-align: middle;width: 60px;">内控指标</th>
				</c:when>
				<c:otherwise>
					<th rowspan="5" style="vertical-align: middle;width: 60px;">控制指标</th>
					<th rowspan="5" style="vertical-align: middle;width: 60px;">内控指标</th>
				</c:otherwise>
			</c:choose>
		</tr>
		<tr>
			<c:forEach items="${ords}" var="ord">
				<th>${ord[0].tasktype}</th>
			</c:forEach>
		</tr>
		<tr>
			<c:forEach items="${ords}" var="ord">
				<th>${ord[0].plant}</th>
			</c:forEach>
		</tr>
		<tr>
			<c:forEach items="${ords}" var="ord">
				<th>${ord[0].pointdesc}</th>
			</c:forEach>
		</tr>
		<tr>
			<c:forEach items="${ords}" var="ord">
				<th>${ord[0].batchname }</th>
			</c:forEach>
		</tr>
		
		<c:if test="${matType=='RAW'}">
			<tr>
				<c:forEach items="${ords}" var="ord">
					<th>&nbsp;${ord[0].suppcode}&nbsp;</th>
				</c:forEach>
			</tr>
		</c:if>
	</thead>
	<tbody>
		<c:forEach items="${analytes }" var="analyte" varStatus="status">
			<tr>
				<c:if test="${status.index==0 }">
					<td rowspan="${fn:length(analytes)}" style="vertical-align: middle;">${title}</td>
				</c:if>
				<td>${analyte.sinonym}
					<c:if test="${not empty analyte.units}">,${analyte.units}</c:if>
				</td>
				<c:forEach items="${ords}" var="ord">
				<td>&nbsp;
					<c:forEach items="${ord}" var="orda" begin="1">
						<c:if test="${analyte.sinonym ==orda.sinonym}">
							<c:choose>
								<c:when test="${orda.s=='OOS-A'}"><label style="background-color: #FF0000;">${orda.finalNum}</label> </c:when>
								<c:when test="${orda.s=='OOS-B'}"><label style="background-color: yellow;">${orda.finalNum}</label></c:when>
								<c:otherwise>${orda.finalNum}</c:otherwise>
							</c:choose>
						</c:if>
					</c:forEach>
					&nbsp;
				</td>
				</c:forEach>
				<td>${limitA[analyte.sinonym] }</td>
				<td>${limitB[analyte.sinonym] }</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
</body>
<script type="text/javascript">
function expExcel(){
	 //判断下载form是否存在
	 var $elemForm = $("#expExcelForm");
	 if($elemForm.length==0){
		 $eleForm = $("<form method='post' id='expExcelForm'  enctype='multipart/form-data'></form>");
		 var expContent = $("<input type='hidden' id='expContent' name='expContent' />");
		
		 $eleForm.append(expContent);
	     $eleForm.attr("action","${pageContext.request.contextPath}/lims/common/expExcel");
	     $(document.body).append($eleForm);
	 }
	 var content ="<table>"+ $("#contentTable").html()+"</table>";
	 
	 $("#expContent").val(content);
	 
  //提交表单，实现导出
  $eleForm.submit();
}
 

 
</script>
</html>