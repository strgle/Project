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
<%@include file="/common/header/layer.jsp"%>
<%@include file="/common/header/layui.jsp"%>
<%@include file="/common/header/default.jsp"%>
<%@include file="/common/header/table.jsp"%>

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

<div class="panel panel-default" >
    <div class="panel-heading">
        
            <button type="button" class="btn btn-success" style="margin-left: 20px;" onclick="expExcel()"/>导出至excel</button>
       
    </div>
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
			<th data-mynum="4">样品名称</th>
			<th data-mynum="5">样品编号</th>
			<th data-mynum="6">任务类型</th>
			<c:forEach items="${titles}" var="t" varStatus="status">
				<th data-mynum="${status.count+6}">${t}</th>
			</c:forEach>
		</tr></thead>
		<tbody>
			<c:forEach  items="${data}" var="v" varStatus="status">
				<tr>
					<td style="text-align: center;" >${status.count}</td>
					<td>${v.pointdesc}</td>
					<td>${v.sampDate}</td>
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
					<td>${v.matName}</td>
					<td>${v.ordNo}</td>
					<td>${v.taskType}</td>
			       <%-- <td>${v}</td> --%>
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
<script type="text/javascript">

function showDetail(ordno,obj){
 $(".selected").removeClass("selected");
 $(obj).parent().addClass("selected");
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
	 var content ="<table>"+ $("#contentTable").html()+"</table>";
	 
	 $("#expContent").val(content);
	 
 //提交表单，实现导出
 $eleForm.submit();
}

$(window).on('resize', function() {
 var height = $("body").height()-110;
 $("#tableContent").height(height);
 new TableSorter("tableContent");
}).resize();
</script>
</body>
</html>
