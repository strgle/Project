<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%@include file="/common/header/meta.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/css/laydateneed.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/js/laydate.css">
<%@include file="/common/header/jquery.jsp"%>
<%@include file="/common/header/layer.jsp"%>
<%@include file="/common/header/bootstrap.jsp"%>
<%@include file="/common/header/font-awesome.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/fuelux/css/ace.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/layui/css/layui.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/layui/layui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/js/laydate.dev.js"></script>

<title>Insert title here</title>
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
        font-size: 12px;
	}
	th,td{
		text-align: center;
		vertical-align: middle;
        font-size: 12px;
	}
 
</style>

</head>
<body>
<div class="panel panel-default" style="margin-bottom: 0px;">
 <div class="panel-body">
 <form class="form-inline" action="${pageContext.request.contextPath }/lims/zltj/kh/queryApply" >
  <div class="form-group">
   <label class="control-label" style="font-weight: normal;">${areaName }</label>
   
  </div>
  <div class="form-group">
   <span style="margin-left:10px;">时间：&nbsp;&nbsp;</span>
   <input class="form-control" name="startTime" id="startTime" placeholder="开始时间" value="${startTime}" type="text" autocomplete="off" />
  </div>
  <div class="form-group">
   <label style="margin-left: 5px;margin-right:5px;">至</label>
   <input class="form-control" name="endTime" id="endTime" placeholder="默认当前时间" value="${endTime}" type="text" autocomplete="off" />
  </div>
  <div class="form-group">
 
   <button type="submit" class="btn btn-success" style="margin-left: 20px;"  >查询</button>
   
  </div>
 </form>
 </div>
</div>
<div class="panel panel-default">
 <div class="panel-body" style="padding: 0px;margin: 0px;">
 <table class="table table-bordered table-striped table-condensed" style="margin: 0px;">
  <thead><tr>
   
   <th>序号</th>
   <th>状态</th>
   <th>装置</th>
   <th>样品</th>
   <th>开始时间</th>
   <th>结束时间</th>
   <th>审核人</th>
   <th>审核时间</th>
   <th>不考核原因</th>
   <th>审核意见</th>
   <th>提交时间</th>
  </tr></thead>
  <tbody>
   <c:forEach items="${draftInFO}" var="info" varStatus="status">
    <tr>
     
     <td>${status.count }</td>
     <td nowrap="nowrap">
      <c:if test="${info.status=='0' }">草稿</c:if>
      <c:if test="${info.status=='1' }">待审核</c:if>
      <c:if test="${info.status=='2' }">审核完成</c:if>
      <c:if test="${info.status=='-1' }">退回</c:if>
     </td>
     <td nowrap="nowrap">${info.plant}</td>
     <td nowrap="nowrap">${info.matname}</td>
     <td>${info.starttime}</td>
     <td>${info.endtime}</td>
     <td>${info.approve}</td>
     <td>${info.approvedate}</td>
     <td>${info.remark}</td>
     <td>${info.approveremark}</td>
     <td>${info.applydate}</td>
    </tr>
   </c:forEach>
  </tbody>
 </table>
 </div>
</div>

<script type="text/javascript">
   laydate({
        elem: '#startTime',
        format: 'YYYY-MM-DD'
    });
    
    laydate({
        elem: '#endTime',
        format: 'YYYY-MM-DD'
    }); 
   
    $("input[readOnly]").keydown(function(e) {
         e.preventDefault();
    });
	/* function onSearch(){
		
	    $.ajax({
	        type:"post",
	        url:"${pageContext.request.contextPath }/lims/zltj/kh/queryApply",
	        data:$('#form_action').serialize(),
	        traditional :true, 
	        async: false,
	        success:function(data){
	         //window.location.replace(window.location.href);
	     	   location.href ="${pageContext.request.contextPath }/lims/zltj/kh/queryApply";
	        },
	        error:function(request){
	         return;
	        }
	     });
	 } */
</script>
</body>

</html>