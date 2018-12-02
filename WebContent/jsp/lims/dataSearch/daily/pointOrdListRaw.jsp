<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>装置日报信息</title>
<%@include file="/common/header/meta.jsp"%>
<%@include file="/common/header/font-awesome.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/fuelux/css/ace.min.css">
<%@include file="/common/header/jquery.jsp"%>
<%@include file="/common/header/layer.jsp"%>
<%@include file="/common/header/bootstrap.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/layui/css/layui.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/layui/layui.js"></script>
<%@include file="/common/header/table.jsp"%>
 <style type="text/css">

 html,body{
  margin: 0px;
  padding: 5px;
  height: 100%;
  background-color: #F5F5F5;
 }
 th{
  white-space: nowrap;
  background-color: #5CB85C;
  text-align: center !important;
 }
  tr:HOVER{
   background-color: #f5f5f5;
  }
  tr.selected{
   background-color: #f5f5f5;
  }
 th,td{
  text-align: center;
  vertical-align: middle;
  font-size: 12px;
 }
	
 </style>
</head>
<body>
<div class="panel panel-default" style="margin-bottom:0px;padding: 0px;">
	<div class="panel-heading">
		结果列表
	</div>
	<div class="panel-body" style="padding:0px;overflow-y:auto;margin: 0px;" id="dataContent">
	<div id="panelContent" style="width:100%;">
	<table class="table table-bordered table-striped table-condensed" id="table">
		<thead><tr class="active">
			<th style="text-align: center;">样品编号</th>
			<th style="text-align: center;">样品名称</th>
			<th style="text-align: center;">业务号</th>
			<th style="text-align: center;">车牌号(采样点描述)</th>
			<th style="text-align: center;">采样时间</th>
			<th style="text-align: center;">发布时间</th>
			<th style="text-align: center;">状态</th>
			<th style="text-align: center;">检测实验室</th>
			
		</tr></thead>
		<tbody id="dailyContent">
			<c:forEach items="${list}" var="info" varStatus="status">
              <tr>
                 <td style="white-space: nowrap;cursor:Pointer;" onclick="showDetail('${info.ordno }',this)">${info.ordno }</td>
                 <td>${info.matname }</td>
                 <td>${info.businessno }</td>
                 <td>${info.samplePointId }</td>
                 <td>${info.sampdate }</td>
                 <td>${info.eventdate }</td>
                 <td>
                 <c:choose>
                  <c:when test="${info.status=='OOS' }">
                       <i class="fa fa-star" style="color:red;"></i>&nbsp;超标
                  </c:when>
                  <c:otherwise>
                       <i class="fa fa-circle" style="color:#5CB85C;"></i>&nbsp;合格
                  </c:otherwise>
                  </c:choose>
                 </td>
                 <td>${info.dept }</td>
              </tr>
            </c:forEach>
		</tbody>
	</table>
	</div>
	</div>
</div>
<script type="text/javascript">
new TableSorter("table");
function showDetail(ordno,obj){
	$(".selected").removeClass("selected");
	$(obj).parent().addClass("selected");
	layer.open({
		  type: 2 //Page层类型
		  ,area: ['70%', '90%']
		  ,title: '检测结果'
		  ,content: '${pageContext.request.contextPath}/lims/dataSearch/daily/ordDetail?ordNo='+ordno
	});  
}
</script>

</body>
</html>
