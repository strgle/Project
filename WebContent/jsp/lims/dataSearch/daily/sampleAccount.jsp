<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>装置日报信息</title>
<%@include file="/common/header/jquery.jsp"%>
<%@include file="/common/header/font-awesome.jsp"%>
<%@include file="/common/header/bootstrap.jsp"%>
<%-- <%@include file="/common/header/layui.jsp"%> --%>
<%@include file="/common/header/default.jsp"%>
<%@include file="/common/header/table.jsp"%>
 <script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/layer/layer.js"></script> 
 <!-- 起弹出框作用的js文件 -->
 <script type="text/javascript" src="${pageContext.request.contextPath}/skins/layui/layui.js"></script>
 

 <style type="text/css">
 	html,body{
 		width: 100%;
 		height: 100%;
 	}
 	
 	tr.selected{
		background-color: #f5f5f5;
	}
	
 	.table-bordered th,.table-bordered td{
 		text-align: center;
 		vertical-align: middle!important;
 		min-width: 80px;
        font-size: 12px;
 	}
 	
	.control-label{
		font-weight: normal;
	}
	
	.link-label{
		color: #1E91E2;
		text-decoration: underline;
		cursor: pointer;
	}
	.dropdown-menu li{
		padding:5px 10px;
		white-space: nowrap;
	}
</style>
</head>
<body>

<div class="panel panel-default" style="margin-bottom: 0px;">
	<div class="panel-heading">${params.areaName } -- ${params.plant}-- ${matName } -- 检测台帐</div>
	<div class="panel-body">
	<form class="form-inline" action="">
		<input type="hidden" name="areaName" value="${params.areaName}"/>
		<input type="hidden" name="plant" value="${params.plant}"/>
		<input type="hidden" name="matName" value="${matName}"/>
		<input type="hidden" name="matCode" value="${params.matCode}"/>
		<input type="hidden" name="startDate" value="${params.startDate}"/>
		<input type="hidden" name="endDate" value="${params.endDate}"/>

	
    	<div class="form-group col-xs-12 col-sm-12 col-md-12 col-lg-12">

    		 
    		 <div class="btn-group" style="margin-left:10px;">
			  <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			    选择显示项 <span class="caret"></span>
			  </button>
			  <ul class="dropdown-menu">
			  		<li style="text-align:right;"><button type="button" class="btn btn-default"  aria-haspopup="true" aria-expanded="false" onclick="selectAll()">全选/反选</button></li>
			  		<li><input type="checkbox" value="状态" checked="checked" onchange="showHide(this)" autocomplete="off"/>&nbsp;状态</li>
			  		<li><input type="checkbox" value="样品名称" onchange="showHide(this)" autocomplete="off"/>&nbsp;样品名称</li>
			  		<li><input type="checkbox" value="样品编号" onchange="showHide(this)" autocomplete="off"/>&nbsp;样品编号</li>
			  		<li><input type="checkbox" value="任务类型" onchange="showHide(this)" autocomplete="off"/>&nbsp;任务类型</li>
			  	<c:forEach items="${titles }" var="t">
					<li><input type="checkbox" value="${t}" checked="checked" onchange="showHide(this)" autocomplete="off"/>&nbsp;${t}</li>
				</c:forEach>
			  </ul>
			</div>
			<button type="button" class="btn btn-success" style="margin-left:10px;" onclick="expExcel()">导出EXCEL</button>
  		</div>
  		
	</form>
	</div>
</div>
<div class="panel panel-default" style="margin-bottom: 0px;padding: 0px;">
	<div class="panel-body" style="overflow: auto;padding: 0px;margin: 0px;" id="tableContent">
	<table class="table table-bordered table-hover table-condensed" id="contentTable">
		<col width="60">
		<col width="150">
		<col width="150">
		<col width="200">
		<col width="150">
		<thead>
			<tr class="active">
			<th>序号</th>
			<th>采样点</th>
			<th>采样时间</th>
			<th data-mynum="3">状态</th>
			<th data-mynum="4" style="display: none;">样品名称</th>
			<th data-mynum="5" style="display: none;">样品编号</th>
			<th data-mynum="6" style="display: none;">任务类型</th>
			<c:forEach items="${titles}" var="t" varStatus="status">
				<th data-mynum="${status.count+6}">${t}</th>
			</c:forEach>
		</tr></thead>
		<tbody>
			<c:forEach  items="${data}" var="v" varStatus="status">
				<tr>
					<td style="text-align: center;" >${status.count}</td>
					<td>${v.pointdesc}</td>
					<td nowrap="nowrap">${v.sampDate}</td>
					<td onclick="showDetail('${v.ordNo}',this)" style="cursor: pointer;">
						<c:choose>
							<c:when test="${v.status=='OOS'}">
								<i class="fa fa-star" style="color:#D9534F;"></i>&nbsp;超标
							</c:when>
							<c:when test="${v.status=='Done' }">
								<i class="fa fa-circle" style="color:#5CB85C;"></i>&nbsp;合格
							</c:when>
							<c:otherwise>进行中</c:otherwise>
						</c:choose>
					</td>
					<td style="display: none;">${matName}</td>
					<td style="display: none;">${v.ordNo}</td>
					<td style="display: none;">${v.taskType}</td>
			
					<c:forEach items="${v.analytes}" var="a">
						<td>
							<c:if test="${!empty a.finalnum}">
								<c:if test="${a.analtype=='AT'}"><a href="javascript:void(0)" onclick="showFile('${v.ordNo}','${a.testcode}','${a.analyte}')"></c:if>
									${a.finalnum}
								<c:if test="${a.analtype=='AT'}"></a></c:if>		
							</c:if>
							<c:if test="${empty a.finalnum}">/</c:if>
						</td>
					</c:forEach>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
</div>
</body>
</html>
<script type="text/javascript">
new TableSorter("tableContent");
layui.use(['form','laydate','layer'],function(){
	var form = layui.form;
});

function selectAll(){
	var checks = $(".dropdown-menu :checkbox:checked");
	var all = $(".dropdown-menu :checkbox");
	if(checks.length>0&&checks.length==all.length){
		$.each(all,function(index,obj){				
			$(obj).prop("checked",false);
			showHide(obj);
		});
	}else if(checks.length>0&&checks.length!=all.length){
		$.each(all,function(index,obj){
			 if(!$(obj).is(":checked")){
				 $(obj).prop("checked",true);
				 showHide(obj);
			 };
		});
	}else{		
		$.each(all,function(index,obj){
			$(obj).prop("checked",true);
			 showHide(obj);
		});
	}
	//event.stopPropagation(); // 阻止事件冒泡
}

function showDetail(ordno,obj){
	/* $(".selected").removeClass("selected");
	$(obj).parent().addClass("selected");
	 */
	 layui.use(['layer'],function(){
			var layer = layui.layer;
			layer.open({
				  type: 2 //Page层类型
				  ,scrollbar:false
				  ,area: ['70%', '90%']
				  ,title: '检测结果'
				  ,content: '${pageContext.request.contextPath}/lims/dataSearch/daily/ordDetail?ordNo='+ordno
			});    
	});
	
}

function showHide(thtitle){
	var title = $(thtitle).val();
	var ths = $("thead tr:eq(0) th");
	var num=0;
	for(var i=0;i<ths.length;i++){
		var it = $(ths[i]).text();
		if(title==it){
			num = $(ths[i]).data("mynum");
		}
	}	
	
	if($(thtitle).is(":checked")){
		$('table tr').find('th:eq('+num+')').show();
		$('table tr').find('td:eq('+num+')').show();
	}else{
		$('table tr').find('th:eq('+num+')').hide();
		$('table tr').find('td:eq('+num+')').hide();
	}
}

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

$(window).on('resize', function() {
	var height = $("body").height()-110;
	$("#tableContent").height(height);
}).resize();


</script>