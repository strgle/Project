<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>样品检测进度查询</title>
<%@include file="/common/header/jquery.jsp"%>
<%@include file="/common/header/font-awesome.jsp"%>
<%@include file="/common/header/bootstrap.jsp"%>
<%@include file="/common/header/default.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/css/laydateneed.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/js/laydate.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/js/laydate.dev.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/layer/layer.css"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/layer/layer.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/layui/css/layui.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/layui/layui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/js/common.js"></script>
 <style type="text/css">
	html,body{
     padding:5px;
     background-color: #F5F5F5;
     height: 100%;
     overflow:hidden;
    }
   
    .panel-default>.panel-heading{
     background-color: #FFFFFF;
    }
    
    .form-input {
      width:140px;
      padding-left:12px;
      font-size: 14px;
      line-height: 1.42857143;
      color: #555;
      background-color: #fff;
    }
    .panel,.panel-heading,.panel-title,.panel-body{
     border-radius:0px!important;
     
    }
    .table tr td{
     /* border: 1px solid #000000 !important; */
     text-align: center;
     vertical-align: middle;
     white-space: nowrap;
     font-size: 12px;
    }
    .table tr th{
     /* border: 1px solid #000000 !important; */
     text-align: center;
     vertical-align: middle !important;
     min-width: 65px;
    }
    .table tr th label{
     width:100%;
     margin:0px;
     border: 0px;
     font-size: 12px;
    }	
    tr.selected{
     background-color: #f5f5f5;
    }
    
    tr:HOVER{
     background-color: #f5f5f5;
    }
 </style>
</head>
<body>
<div class="panel panel-default" style="margin-bottom: 0px;">
	<div class="panel-body">
	<form class="form-inline" action="">
		<div class="form-group">
			<label class="control-label" style="font-weight: normal;">开始时间：</label>
			<input class="form-control" name="startTime" placeholder="开始时间" value="${params.startTime}" type="text" id="startTime" autocomplete="off" onclick="laydate()"/>
		</div>
		<div class="form-group">
			<label class="control-label" style="font-weight: normal;margin-left: 20px;">截至时间：</label>
			<input class="form-control" name="endTime" placeholder="默认当前时间" value="${params.endTime}" id="endTime" type="text" autocomplete="off" onclick="laydate()"/>
		</div>
		<div class="form-group">
			<label class="control-label" style="font-weight: normal;margin-left: 20px;">检测状态：</label>
			<select name="status" class="form-control">
				<option value="ALL">全部</option>
				<option value="0">进行中</option>
				<option value="1">完成</option>
			</select>
		</div>
		<div class="form-group">
			<button type="submit" class="btn btn-success" style="margin-left: 30px;">查询</button>
		</div>
	</form>
	</div>
</div>
<div class="panel panel-default">
	
    <div id="tableContaint" class="panel-body" style="padding: 0px;overflow-y:auto;position:relative;">
	<table class="table table-bordered">
		<thead id="fixheader"><tr class="active">
			<th><label>序号</label></th>
			<th><label>样品编号</label></th>
			<th><label>样品名称</label></th>
			<th><label>车间</label></th>
			<th><label>采样点</label></th>
			<th><label>任务类型</label></th>
			<th><label>样品类型</label></th>
			<th><label>创建时间</label></th>
			<th><label>样品备注</label></th>	
			<th><label>状态</label></th>	
			<th><label>链接</label></th>
		</tr></thead>
		<tbody>
			<c:forEach items="${data}" var="d" varStatus="status">
				<tr>
					<td>${status.count}</td>
					<td>${d.ordno}</td>
					<td>${d.matname}</td>
					<td>${d.areaName}</td>
					<td>${d.pointdesc}</td>
					
					<td>${d.tasktype}</td>
					<td>
						<c:choose>
							<c:when test="${d.type=='RAW' }">原辅料</c:when>
							<c:when test="${d.type=='FP' }">成品</c:when>
							<c:when test="${d.type=='LP' }">委托样</c:when>
							<c:otherwise>中控样</c:otherwise>
						</c:choose>				
					</td>
					<td>${d.sampdate}</td>
					<td>${d.batchname}</td>
					<td>
						<c:choose>
							<c:when test="${d.status=='Planned'}">
								<span class="label label-default"  style="margin:0px;">计划</span>
							</c:when>
							<c:when test="${d.status=='Prelogged'}">
								<span class="label label-default"  style="margin:0px;">待接收</span>
							</c:when>
							<c:when test="${d.status=='Started'}">
								<span class="label label-default"  style="margin:0px;">待采样</span>
							</c:when>
                            <c:when test="${d.status=='Logged'}">
                             <span class="label label-info"  style="margin:0px;">检测中</span>
                            </c:when>
                            <c:when test="${d.status=='Actived'}">
                             <span class="label label-info"  style="margin:0px;">检测中</span>
                            </c:when>
                            <c:when test="${d.status=='Active'}">
                             <span class="label label-info"  style="margin:0px;">检测中</span>
                            </c:when>
                            <c:when test="${d.status=='Resulted'}">
                             <span class="label label-info"  style="margin:0px;">结果完成</span>
                            </c:when>
                            <c:when test="${d.status=='Done'}" >
                              <c:if test="${d.dispsts=='Released'}">
                                <span class="label label-primary"  style="margin:0px;">已发布</span>
                              </c:if>
                              <c:if test="${d.dispsts=='...'}">
                                <span class="label label-primary"  style="margin:0px;">待发布</span>
                              </c:if>
                              <c:if test="${empty d.dispsts}">
                                <span class="label label-primary"  style="margin:0px;">待发布</span>
                              </c:if>
                            </c:when>
                           <c:when test="${d.status=='OOS'}">
                             <c:if test="${d.dispsts=='Released'}">
                                <span class="label label-primary"  style="margin:0px;">已发布</span>
                              </c:if>
                              <c:if test="${d.dispsts=='...'}">
                                <span class="label label-primary"  style="margin:0px;">超标待发布</span>
                              </c:if>
                              <c:if test="${empty d.dispsts}">
                                <span class="label label-warning"  style="margin:0px;">超标待发布</span>
                              </c:if>
                            </c:when>
							
							<c:when test="${d.status=='Cancelled'}">
								<span class="label label-danger"  style="margin:0px;">样品终止</span>
							</c:when>
							<c:otherwise>
								${d.status}
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<lable class="btn btn-link" onclick="showProcess('${d.ordno}');" style="padding:0px 5px;">流程</lable>|<lable class="btn btn-link" onclick="showDetail('${d.ordno}');" style="padding:0px 5px;">详情</lable>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
 <table class="table table-bordered" style="margin:0px;position:absolute;top:0px;background-color: #FFF;width: 100%;" id="hidefix">
      <thead>
       <tr>
        <th><label>序号</label></th>
		<th><label>样品编号</label></th>
		<th><label>样品名称</label></th>
		<th><label>车间</label></th>
		<th><label>采样点</label></th>
		<th><label>任务类型</label></th>
		<th><label>样品类型</label></th>
		<th><label>创建时间</label></th>
		<th><label>样品备注</label></th>	
		<th><label>状态</label></th>	
		<th><label>链接</label></th>
       </tr>
      </thead>
     </table>
</div>
</div>
</body>
<script type="text/javascript">
$(window).on('resize', function() {
	$("#tableContaint").height($("body").height()-100);
	changeWidth();
}).resize();
function changeWidth(){
	//获取高度信息
	var h1 = $("#fixheader").height();
	$("#hidefix thead").height(h1);
	var w1 = $("#fixheader").width();
	
	$("#hidefix").width(w1);
	$("#fixheader tr th").each(function(index,element){
		var w = $(element).children("label").width();
		$("#hidefix thead tr th:eq("+index+") label").width(w);
	});
	
	
}
$('#tableContaint').on('scroll',function(){
	var top = $('#tableContaint').scrollTop();
	$("#hidefix").css("top",top);
});
	function showProcess(ordno){
		layer.open({
			  type: 2 //Page层类型
			  ,scrollbar:false
			  ,area: ['70%', '90%']
			  ,title: '检测进度查看('+ordno+')'
			  ,content: '${pageContext.request.contextPath}/lims/tasks/ordSchedule/detail?ordNo='+ordno
		});    
	}
	
	function showDetail(ordno){
		layer.open({
			  type: 2 //Page层类型
			  ,scrollbar:false
			  ,area: ['70%', '90%']
			  ,title: '检测结果'
			  ,content: '${pageContext.request.contextPath}/lims/dataSearch/daily/ordDetail?ordNo='+ordno
		});    
	}

</script>
</html>
