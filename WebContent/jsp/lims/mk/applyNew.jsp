<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/header/meta.jsp"%>
<%@include file="/common/header/font-awesome.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/fuelux/css/ace.min.css">
<%@include file="/common/header/jquery.jsp"%>
<%@include file="/common/header/laydate.jsp"%>
<%@include file="/common/header/bootstrap.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/layui/css/layui.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/layui/layui.js"></script>

<title>免考核</title>
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
<div class="panel panel-default" style="margin-bottom: 0px;">
 <div class="panel-body">
 <form class="form-inline" action="" method="post">
  <div class="form-group">
   <label class="control-label" style="font-weight: normal;">${areaName }</label>
   
  </div>
  <div class="form-group">
   <button type="button" class="btn btn-success" style="margin-left: 30px;" onclick="addPlant()">添加</button>
   <button type="button" class="btn btn-info" style="margin-left: 20px;" onclick="onSubmit()" >提交</button>
   <button type="button" class="btn btn-danger" style="margin-left: 20px;" onclick="deletePlant()">删除</button>
  </div>
 </form>
 </div>
</div>
<div class="panel panel-default">
 <div class="panel-body" style="padding: 0px;margin: 0px;">
 <table class="table table-bordered table-striped table-condensed" style="margin: 0px;">
  <thead><tr>
   <th>&nbsp;</th>
   <th>序号</th>
   <th>状态</th>
   <th>装置</th>
   <th>样品</th>
   <th>开始时间</th>
   <th>结束时间</th>
   <th>不考核原因</th>
   <th>不通过原因</th>
  </tr></thead>
  <tbody>
   <c:forEach items="${draftInFO}" var="info" varStatus="status">
    <tr>
     <td><input type="checkbox" name="selectId" value="${info.id}"/></td>
     <td>${status.count }</td>
     <td nowrap="nowrap">
      <c:if test="${info.status=='0' }">草稿</c:if>
      <c:if test="${info.status=='1' }">已提交</c:if>
      <c:if test="${info.status=='-1' }">退回</c:if>
     </td>
     <td nowrap="nowrap">${info.plant}</td>
     <td nowrap="nowrap">${info.matname}</td>
     <td>${info.starttime}</td>
     <td>${info.endtime}</td>
     <td>${info.remark}</td>
     <td>${info.approveremark}</td>
    </tr>
   </c:forEach>
  </tbody>
 </table>
 </div>
</div>

<script type="text/javascript">
 function addPlant(){
	 var random = Math.random();
	 layui.use(['layer','laydate',"form"],function(){
		 var layer = layui.layer,
		 laydate = layui.laydate,
     	 form = layui.form();
          layer.open({
        	  type: 2 //Page层类型
        	  ,scrollbar:false
        	  ,area: ['50%', '90%']
        	  ,title: '免考核信息添加'
        	  ,content: '${pageContext.request.contextPath}/lims/zltj/kh/forAddPlant'
        	 
        	});    
	 });    
 }
 
 function onSubmit(){
	 var chk_value = new Array(); 
    $('input[name="selectId"]:checked').each(function(){ 
     chk_value.push($(this).val()); 
    });
    
    if(chk_value.length<1){
    	
    	 layui.use(['layer'],function(){
    		 var layer = layui.layer;
    		 layer.msg('未选择数据', {icon: 2});
    	 });
    	return;
    }
    $.ajax({
        type:"post",
        url:"${pageContext.request.contextPath }/lims/zltj/kh/submitApply",
        data:{'selectId':chk_value},
        traditional :true, 
        async: false,
        success:function(data){
         //window.location.replace(window.location.href);
     	   location.href ="${pageContext.request.contextPath }/lims/zltj/kh/apply";
        },
        error:function(request){
         return;
        }
     });
 }
 function deletePlant(){
    var chk_value = new Array(); 
    $('input[name="selectId"]:checked').each(function(){ 
     chk_value.push($(this).val()); 
    });
    if(chk_value.length<1){
    	
    	 layui.use(['layer'],function(){
    		 var layer = layui.layer;
    		 layer.msg('未选择数据', {icon: 2});
    	 });
    	return;
   }
    $.ajax({
     type:"post",
     url:"${pageContext.request.contextPath }/lims/zltj/kh/deletePlant",
     data:{'selectId':chk_value},
     traditional :true, 
     async: false,
     success:function(data){
      //window.location.replace(window.location.href);
  	   location.href ="${pageContext.request.contextPath }/lims/zltj/kh/apply";
     },
     error:function(request){
      return;
     }
  });
 }

</script>
</body>

</html>