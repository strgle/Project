<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/header/meta.jsp"%>
<title>检测数据汇总</title>
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
<body>
<table class="table" id="expTable">
		<thead>
			<tr>
				<th colspan="4">考核内容及指标</th>
				<th rowspan="2">考核指标</th>
				<th rowspan="2">总取样数</th>
				<th rowspan="2">合格数</th>
				<th rowspan="2">合格率</th>
				<th rowspan="2">备注</th>
			</tr>
			<tr>
				<th>样品分组</th>
				<th>测试</th>
				<th>分析项</th>
				<th>指标</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${datas}" var="mat" varStatus="sta1">
				<c:forEach items="${mat.tests}" var="tests" varStatus="sta2">
					<c:forEach items="${tests.analytes }" var="analytes" varStatus="sta3">
					<tr style="background-color: #FFF;">
						<c:if test="${sta2.index==0}"><td rowspan="${fn:length(mat.analytes)}">${mat.matname}</td></c:if>
						<c:if test="${sta3.index==0}"><td rowspan="${fn:length(tests.analytes)}">${tests.testno}</td></c:if>
						<td>${analytes.sinonym }</td>
						<td>${analytes.charlimits }</td>
						<c:if test="${sta2.index==0}"><td rowspan="${fn:length(mat.analytes)}">执行考核方案</td></c:if>
						<c:if test="${sta2.index==0}"><td rowspan="${fn:length(mat.analytes)}">${mat.totalNum }</td></c:if>
						<c:if test="${sta2.index==0}"><td rowspan="${fn:length(mat.analytes)}">${mat.doneNum }</td></c:if>
						<c:if test="${sta2.index==0}"><td rowspan="${fn:length(mat.analytes)}">
							<c:if test="${mat.totalNum ==0}">/</c:if>
							<c:if test="${mat.totalNum >0}">
								<fmt:formatNumber value="${mat.doneNum /mat.totalNum }" type="percent" maxFractionDigits="2" minFractionDigits="2"/>
							</c:if>
						</td></c:if>
						<c:if test="${sta2.index==0}"><td rowspan="${fn:length(mat.analytes)}" style="white-space:normal;">以最终产品入库报告单统计</td></c:if>
					</tr>
					</c:forEach>
				</c:forEach> 
    		</c:forEach>
		</tbody>
	</table>
</body>
<script type="text/javascript">
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