<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/header/meta.jsp"%>
<title>质量旬报</title>
<%@include file="/common/header/jquery.jsp"%>
<%@include file="/common/header/laydate.jsp"%>
<%@include file="/common/header/bootstrap.jsp"%>
<%@include file="/common/header/default.jsp"%>
<style type="text/css">
	html{
		background-color: #fff;
		padding:0px;
		margin:0px;
	}
	
	.table tr th{ 
		min-width:120px;
		text-align: center !important;
	}
	.table tbody tr td{ 
		text-align: center;
	}
	
.table tbody tr:hover{
	
}
</style>
</head>
<body>
<table class="table">
			<tr>
				<th  style="background-color: #fff;padding: 15px;">
					<form action="${pageContext.request.contextPath}/statistics/jbxj/rate/ten/${matcode}" method="post" id="form">
					<input type="hidden" name="startDate" value="${startDate}"/>
					<input type="hidden" name="endDate" value="${endDate}"/>
					<input type="hidden" name="flag" value="" id="flag">
					</form>
					<label id="weekdate" style="background-color:#009688;padding: 5px 20px;color:#FFF;margin-right:10px;cursor: pointer;" onclick="prev()">减一旬</label>
					<label id="weekdate" style="font-size:18px;background-color:#009688;padding: 5px 20px;color:#FFF;">${startDate} - ${endDate}</label>
					<label id="weekdate" style="background-color:#009688;padding: 5px 20px;color:#FFF;margin-left:10px;cursor: pointer;" onclick="next()">加一旬</label>
					<button class="btn btn-success btn-sm" type="button" onclick="expExcel()">导出excel</button>
				</th>
			</tr>
</table>
<table class="table" id="expTable">
			 
		<c:forEach items="${reportDatas}" var="datas" varStatus="">
			<tr>
				<th colspan="5" style="background-color: #D4BFFF;padding: 10PX;font-size: 15PX;letter-spacing: 2PX;">${datas.matName } - 产品合格率</th>
			</tr>
			<tr>
				<th>等级</th>
				<th>批次</th>
				<th>总批次</th>
				<th>合格率</th>
			</tr>

			<tr>
				<td>优等品</td>
				<td>${datas.product.done }</td>
				<c:choose>
					<c:when test="${datas.product.matcode=='XJ0111' }"><td rowspan="3">${datas.product.totalNum }</td></c:when>
					<c:otherwise><td rowspan="4">${datas.product.totalNum }</td></c:otherwise>
				</c:choose>
				<td>
					<c:if test="${datas.product.totalNum>0}">
						<fmt:formatNumber value="${datas.product.done*1.0/datas.product.totalNum }" type="percent" maxFractionDigits="2" minFractionDigits="2"></fmt:formatNumber>
					</c:if>
					<c:if test="${datas.product.totalNum==0}">
						0.00
					</c:if>
				</td>
			</tr>
			<tr>
				<td>合格品</td>
				<td>${datas.product.oosB }</td>
				<td>
					<c:if test="${datas.product.totalNum>0}">
						<fmt:formatNumber value="${datas.product.oosB*1.0/datas.product.totalNum }" type="percent" maxFractionDigits="2" minFractionDigits="2"></fmt:formatNumber>
					</c:if>
					<c:if test="${datas.product.totalNum==0}">
						0.00
					</c:if>
				</td>
			</tr>
			<tr>
				<td>副牌胶</td>
				<td>${datas.product.oosA }</td>
				<td>
					<c:if test="${datas.product.totalNum>0}">
						<fmt:formatNumber value="${datas.product.oosA*1.0/datas.product.totalNum }" type="percent" maxFractionDigits="2" minFractionDigits="2"></fmt:formatNumber>
					</c:if>
					<c:if test="${datas.product.totalNum==0}">
						0.00
					</c:if>
				</td>
			</tr>
			<c:if test="${datas.product.matcode=='XJ0135'}">
				<tr>
					<td>回切批次</td>
					<td>
						<input style="padding: 5px 8px;border:0px;width:100px;border-bottom: 1px solid #CCC;text-align:center;" type="text" data-id="${datas.product.rollBack.id }" value='<fmt:formatNumber value="${datas.product.rollBack.totalNum}" maxFractionDigits="0"></fmt:formatNumber>' autocomplete="off"/>
					</td>
					<td>N/A</td>
				</tr>
			</c:if>
			<tr>
				<th colspan="5"  style="background-color: #D4BFFF;padding: 10PX;font-size: 15PX;letter-spacing: 2PX;">${datas.matName } - 产品监控点</th>
			</tr>
			<tr>
				<th>监控内容</th>
				<th>检测项</th>
				<th>合格批次</th>
				<th>总批次</th>
				<th>合格率</th>
			</tr>
		
			<c:forEach items="${datas.monitorDetails }" var="detail">
			<tr>
				<td>${detail.sinonym }</td>
				<td>${detail.analyte }</td>	
				<td>${detail.doneNum+detail.oosbNum }</td>
				<td>${detail.totalNum}  </td>
				<td>
					<c:if test="${detail.totalNum >0 }">
						<fmt:formatNumber value="${(detail.doneNum+detail.oosbNum )*1.0/detail.totalNum }" type="percent" maxFractionDigits="2" minFractionDigits="2"></fmt:formatNumber>
					</c:if>
					<c:if test="${detail.totalNum <=0 }">0.00</c:if>
				</td>
			</tr>
			</c:forEach>
	
		</c:forEach>
		<tr>
				<th colspan="5"  style="background-color: #D4BFFF;padding: 10PX;font-size: 15PX;letter-spacing: 2PX;">中控合格率</th>
			</tr>
			<tr>
				<th>名称</th>
				<th>检测项</th>
				<th>合格批次</th>
				<th>总批次</th>
				<th>合格率</th>
			</tr>

			<c:forEach items="${processDatas }" var="detail">
			<tr>
				<td>${detail.matname }
				<br />
	 				(${detail.pointId})</td>
	 			<td>${detail.analyte }</td>		
				<td>${detail.doneNum+detail.oosbNum }</td>
				<td>${detail.totalNum}  </td>
				<td>
					<c:if test="${detail.totalNum >0 }">
						<fmt:formatNumber value="${(detail.doneNum+detail.oosbNum )*1.0/detail.totalNum }" type="percent" maxFractionDigits="2" minFractionDigits="2"></fmt:formatNumber>
					</c:if>
					<c:if test="${detail.totalNum <=0 }">0.00</c:if>
				</td>
			</tr>
			</c:forEach> 
		
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
	
	function prev(){
		$("#flag").val("prev");
		$("#form").submit();
	}
	
	function next(){
		$("#flag").val("next");
		$("#form").submit();
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