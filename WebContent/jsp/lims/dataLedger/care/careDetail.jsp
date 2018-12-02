<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>样品明细</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/js/common.js"></script>
<style type="text/css">
	html,body{
		padding: 5px;
		background-color: #F5F5F5;
		text-align: center;
	}
	td{
		overflow:hidden;
		white-space:nowrap;
		text-overflow:ellipsis;
		-o-text-overflow:ellipsis;
		-moz-text-overflow: ellipsis;
		-webkit-text-overflow: ellipsis;
		max-width: 200px;
	}
	
	a{
		color: #337AB7;
	}
</style>
</head>
<body>
<c:forEach items="${ords}" var="ord">
<div style="padding:20px;background-color: #FFFFFF;width:70%;margin:10px auto 20px;">
<div class="panel panel-default" style="margin-bottom: 0px;">
	<div class="panel-body" style="padding:0px;">
		<table class="table table-bordered" style="margin-bottom: 0px;">
			<tr>
				<td>样品编号：</td><td>&nbsp;${ord[0].ordno }</td>
				<td>样品名称：</td><td>&nbsp;${ord[0].matname }</td>
			</tr>
			<tr>
				<td>车间/装置：</td><td>&nbsp;${ord[0].areaName }&nbsp;--&nbsp;${ord[0].plant}</td>
				<td>采样点：</td><td>&nbsp;${ord[0].pointdesc}</td>
			</tr>
			<tr>
				<td>采样时间：</td><td>&nbsp;${ord[0].sampdate}&nbsp;</td>
				<td>检测状态：</td><td>
					<c:choose>
						<c:when test="${ord[0].status=='Done' }">
							&nbsp;合格
						</c:when>
						<c:when test="${ord[0].status=='OOS' }">
							<c:choose>
								<c:when test="${ord[0].grade=='OOS-A' }">
									&nbsp;超标
								</c:when>
								<c:when test="${ord[0].grade=='OOS-B' }">
									&nbsp;超内控指标
								</c:when>
								<c:otherwise>
									&nbsp;合格
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:when test="${ord[0].status=='Planned' }">
							&nbsp;未提交
						</c:when>
						<c:otherwise>
							&nbsp;进行中
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td>样品备注：</td><td>&nbsp;${ord[0].batchname }&nbsp;</td>
				<td>车间备注：</td><td>&nbsp;${ord[0].sampdesc }&nbsp;</</td>
			</tr>
		</table>
	</div>
</div>
<div class="panel panel-default" style="margin-bottom: 0px;">
	<table class="table table-bordered table-hover">
		<thead>
		<tr><th>序号</th><th>分析项</th><th>检测结果</th><th>控制指标</th><th>内控指标</th></tr>
		</thead>
		<tbody>
		<c:forEach items="${ord}" begin="1" var="v" varStatus="statu">
			<tr>
				<td>${statu.count }</td>
				<td>
					<c:choose>
						<c:when test="${not empty v.url}">
							<a href="javascript:void(0)" onclick="showFile2('${v.url}')">${v.sinonym }</a>
						</c:when>
						<c:otherwise>
							${v.sinonym }
						</c:otherwise>
					</c:choose>
				</td>
				
					<c:choose>
						<c:when test="${v.s=='OOS-A' }">
							<td style="color: #FF0000;" class="danger"><i class="fa fa-star"></i>&nbsp;
								<c:if test="${v.analtype=='AT'}"><a href="javascript:void(0)" onclick="showFile('${ord.ordno}','${v.testcode}','${v.analyte}')"></c:if>
									${v.finalNum}
								<c:if test="${v.analtype=='AT'}"></a></c:if>&nbsp;
								<c:if test="${not empty v.isQzwc}">
									(强制完成：${v.isQzwc})
								</c:if>
								<c:if test="${empty v.isQzwc}">${v.units}</c:if>
							</td>
						</c:when>
						<c:when test="${v.s=='OOS-B' }">
							<td class="warning"><i class="fa fa-bell" style="color:#FFDB4C;"></i>&nbsp;
								<c:if test="${v.analtype=='AT'}"><a href="javascript:void(0)" onclick="showFile('${ord.ordno}','${v.testcode}','${v.analyte}')"></c:if>
									${v.finalNum}
								<c:if test="${v.analtype=='AT'}"></a></c:if>&nbsp;
								<c:if test="${not empty v.isQzwc}">
									(强制完成：${v.isQzwc})
								</c:if>
								<c:if test="${empty v.isQzwc}">${v.units}</c:if>
							</td>
						</c:when>
						<c:when test="${v.s=='Done'}">
							<td>&nbsp;
								<c:if test="${v.analtype=='AT' }"><a href="javascript:void(0)" onclick="showFile('${ord.ordno}','${v.testcode}','${v.analyte}')"></c:if>
									${v.finalNum}
								<c:if test="${v.analtype=='AT' }"></a></c:if>&nbsp;
								<c:if test="${not empty v.isQzwc}">
									(强制完成：${v.isQzwc})
								</c:if>
								<c:if test="${empty v.isQzwc}">${v.units}</c:if>
							</td>
						</c:when>
						<c:otherwise>
							<td>&nbsp;
								<c:if test="${v.analtype=='AT' }"><a href="javascript:void(0)" onclick="showFile('${ord.ordno}','${v.testcode}','${v.analyte}')"></c:if>
									${v.finalNum}
								<c:if test="${v.analtype=='AT' }"></a></c:if>
								<c:if test="${not empty v.isQzwc}">
									(强制完成：${v.isQzwc})
								</c:if>
								<c:if test="${empty v.isQzwc}">${v.units}</c:if>
							</td>
						</c:otherwise>
					</c:choose>
				
				<td><c:if test="${empty v.limitA }">/</c:if>${v.limitA }</td>
				<td><c:if test="${empty v.limitB }">/</c:if>${v.limitB }</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="panel-footer" style="background-color: #FFFFFF;text-align: right;">
			<label>检测结果标记：</label>
			<label style="color:#FF0000;background-color: #F2DEDE;"><i class="fa fa-star"></i>超控制指标</label>
			<label><i class="fa fa-bell" style="color: #FFDB4C;" ></i>超内控指标</label>
	</div>
</div>
</div>
</c:forEach>
</body>
<script type="text/javascript">
 function showFile(ordno,testcode,analyte){
	 //判断下载form是否存在
	 var $elemForm = $("#downLoadForm");
	 if($elemForm.length==0){
		 $eleForm = $("<form method='post' id='downLoadForm'></form>");
		 var ordNoInput = $("<input type='hidden' id='ordNo' name='ordNo' />");
		 var testCodeInput = $("<input type='hidden' id='testCode' name='testCode' />");
		 var analyteInput = $("<input type='hidden' id='analyte' name='analyte' />");
		 $eleForm.append(ordNoInput);
		 $eleForm.append(testCodeInput);
		 $eleForm.append(analyteInput);
	     $eleForm.attr("action","${pageContext.request.contextPath}/lims/common/downLoad");
	     $(document.body).append($eleForm);
	 }
	 
	 $("#ordNo").val(ordno);
	 $("#testCode").val(testcode);
	 $("#analyte").val(analyte);
     //提交表单，实现下载
     $eleForm.submit();
 }
 

 function showFile2(fileurl){
	 //判断下载form是否存在
	 var $elemForm = $("#downLoadForm2");
	 if($elemForm.length==0){
		 $eleForm = $("<form method='post' id='downLoadForm2'></form>");
		 var fileUrlInput = $("<input type='hidden' id='fileurl' name='fileurl' />");
		 $eleForm.append(fileUrlInput);
	     $eleForm.attr("action","${pageContext.request.contextPath}/lims/common/ftpdownLoad");
	     $(document.body).append($eleForm);
	 }
	 
	 $("#fileurl").val(fileurl);
     //提交表单，实现下载
     $eleForm.submit();
 }

 function expExcel(){
	
 }
</script>
</html>