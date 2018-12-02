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
<%@include file="/common/header/jquery.jsp"%>
<%@include file="/common/header/font-awesome.jsp"%>
<%@include file="/common/header/bootstrap.jsp"%>
<%@include file="/common/header/layui.jsp"%>
<%@include file="/common/header/default.jsp"%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/css/style.css">

<script type="text/javascript" src="${pageContext.request.contextPath}/skins/js/common.js"></script>
<style type="text/css">
	html,body{
		padding: 5px;
		background-color: #F5F5F5;
	}
	.editTd{
		padding: 0px!important;
	}
	input{
		width:100%;
		height: 38px;
		padding-left: 8px;
		border: 0px;
		background-color: #f5ffff;		
	}
	
	input:disabled{
		background-color: #FFF;		
	}
	td{
		overflow:hidden;
		max-width: 200px;
        font-size: 12px;
	}
	
	.table td{
		text-align: center;
        font-size: 12px;
	}
	
	.table th{
		text-align: center;
        font-size: 12px;
	}
	a{
		color: #337AB7;
	}
</style>
</head>
<body>

<div class="panel panel-default" style="margin-bottom: 0px;">

	<div class="panel-body" style="padding:0px;">
		<table class="table table-bordered" style="margin-bottom: 0px;">
			<tr>
				<td>样品编号：</td><td>&nbsp;${ord.ordno }</td>
				<td>样品名称：</td><td>&nbsp;${ord.matname }</td>
			</tr>
			<tr>
				<td>车间/装置：</td><td>&nbsp;${ord.areaName }&nbsp;--&nbsp;${ord.plant}</td>
				<td>采样点：</td><td>&nbsp;${ord.pointdesc}</td>
			</tr>
			<tr>
				<td>采样时间：</td><td>&nbsp;${ord.sampdate}&nbsp;</td>
				<td>检测状态：</td><td>
					<c:choose>
						<c:when test="${ord.status=='Done' }">
							&nbsp;合格
						</c:when>
						<c:when test="${ord.status=='OOS' }">
							&nbsp;超标
						</c:when>
						<c:when test="${ord.status=='Planned' }">
							&nbsp;未提交
						</c:when>
						<c:otherwise>
							&nbsp;进行中
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td>样品备注：</td><td>&nbsp;${ord.batchname }&nbsp;</td>
				<td>检测成本：</td><td>
					<c:if test="${empty price}"><fmt:formatNumber value="0" pattern="￥0.00"/></c:if>
					<c:if test="${not empty price }"><fmt:formatNumber value="${price}" pattern="￥0.00"/></c:if>
				</td>
			</tr>
			<tr>
				<td>车间备注：</td><td colspan="2" class="editTd"><input id="sampdesc" value="${ord.sampdesc }" disabled="disabled"/></</td>
				<td><label class="btn btn-link btn-xs" onclick="cjremark('${ord.ordno}',this)">修改</label></td>
			</tr>
		</table>
	</div>
</div>
<div class="panel panel-default">
	<div class="panel-heading">
		<div class="panel-title">结果列表</div>
		<div class="panel-tools" style="top:8px;">
			<label class="btn btn-xs btn-warning" onclick="expExcel('${ord.ordno}')">导出EXCEL</label>
		</div>
		
	</div>
	<table class="table table-bordered table-hover tablelist">
		<thead>
		<tr>
           <th>序号</th>
           <th>分析项</th>
           <th>检测结果</th>
           <th>控制指标</th>
           <th>内控指标</th>
           <th>内控C指标</th>
           <th>内控D指标</th>   
        </tr>
		</thead>
		<tbody>
		<c:forEach items="${analytes}" var="v" varStatus="statu">
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
                <td><c:if test="${empty v.limitC }">/</c:if>${v.limitC }</td>
                <td><c:if test="${empty v.limitD }">/</c:if>${v.limitD }</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="panel-footer" style="text-align: right;">
			<label>检测结果标记：</label>
			<label style="color:#FF0000;background-color: #F2DEDE;"><i class="fa fa-star"></i>超控制指标</label>
			<label><i class="fa fa-bell" style="color: #FFDB4C;" ></i>超内控指标</label>
	</div>
</div>

</body>
<script type="text/javascript">
 function cjremark(ordno,tar){
	 var txt = $(tar).text();
	 if(txt=="修改"){
		 $("#sampdesc").removeAttr("disabled");
		 $(tar).text("保存");
	 }else{
		 var sampdesc = $("#sampdesc").val();
		 $.ajax({type: 'POST',
				async: false,
				data:"sampdesc="+sampdesc,
				dataType:"json",
				url:"${pageContext.request.contextPath}/api/lims/dataSearch/result/sampdesc/"+ordno,
				success: function(data){
					$("#sampdesc").attr("disabled","disabled"); 
					$(tar).text("修改");
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					alert(textStatus);
				}
			});
	 }
 }
 
 layui.use(['layer'],function(){
	 var layer = layui.layer;
 });
 
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

 function mkh(ordno){
	 var urls = "${pageContext.request.contextPath}/lims/zltj/kh/mkhTj";
	 layer.prompt({title:'免考核原因', formType: 2},function(txt,index){
		 layer.close(index);
		 $.ajax({
			url:urls,
			data:{"ordNo":ordno,"yj":txt},
			traditional:true,
			type: 'POST',
			dataType: "json",
			success:function(data){
				if(data.code=="SUCCESS"){
					layer.msg(data.message,{
							time: 1000
						},function(){
							layer.close(index);
						}
					)			
				}else{
					layer.alert(data.message, {icon: 1});
					layer.close(index);
				}
			}
		 });
	 });
 }
 
 
 function mjy(ordno){
	 var urls = "${pageContext.request.contextPath}/lims/zltj/jy/mjyTj";
	 layer.prompt({title:'免统计原因', formType: 2},function(txt,index){
		 layer.close(index);
		 $.ajax({
			url:urls,
			data:{"ordNo":ordno,"yj":txt},
			traditional:true,
			type: 'POST',
			dataType: "json",
			success:function(data){
				if(data.code=="SUCCESS"){
					layer.msg(data.message,{
							time: 1000
						},function(){
							layer.close(index);
						}
					)			
				}else{
					layer.alert(data.message, {icon: 1});
					layer.close(index);
				}
			}
			 
		 });
	 });
 }
 
 function expExcel(ordno){
	 var urls = "${pageContext.request.contextPath}/lims/dataSearch/daily/expOrdDetail?ordNo="+ordno;
	window.location = urls;
 }
</script>
</html>