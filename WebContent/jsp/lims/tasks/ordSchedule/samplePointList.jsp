<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/header/meta.jsp"%>
<%@include file="/common/header/jquery.jsp"%>
<%@include file="/common/header/layer.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/css/laydateneed.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/js/laydate.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/js/laydate.dev.js"></script>
<%-- <%@include file="/common/header/layui.jsp"%> --%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/links/layui/css/layuiadmin.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/links/layui/admin.css"> 
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/layui/layuiadmin.js"></script>

<title>Insert title here</title>
</head>
<body>
<div class="layui-fluid">
    <div class="layui-card">
      
      <div class="layui-card-body" style="padding: 15px;">

    <form class="layui-form" action="">
       <div class="layui-form-item">
          <label class="layui-form-label">实验室</label>
          <select name="dept" lay-verify="required" lay-filter="dept" id="dept" lay-search="" >
           <c:forEach items="${deptList }" var="dept">
            <option value="${dept.dept}">${dept.dept}</option>
           </c:forEach>
           </select>
       </div>
       <div class="layui-form-item">
          <label class="layui-form-label">车间</label>
          <select name="area" lay-verify="required" lay-filter="area" id="area" lay-search="">
            
          </select>
       </div>
       <div class="layui-form-item">
          <label class="layui-form-label">装置</label>
          <select name="plant" lay-verify="required" lay-filter="plant" id="plant" lay-search="">
            
          </select>
       </div>
       <div class="layui-form-item">
          <label class="layui-form-label">采样点</label>
          <select name="point" lay-verify="required" lay-filter="point" id="point" lay-search="">
            
          </select>
       </div>
       <button type="button" class="layui-btn" onclick="ok()">
             <i class="layui-icon">&#xe608;</i> 确定
       </button>
 </form>
 </div>
 </div>
 </div>
 <script>
 layui.use(['form','jquery'], function(){
   var form = layui.form;
   form.on('select(dept)', function(data){
	  
	   var dept = data.value;
	
	   var url = '${pageContext.request.contextPath}/lims/tasks/ordSchedule/getArea';
       $.ajax({
           type : "GET",
           url : url,
           async : false,
           data : {
               dept : dept
           },
           success : function(data) {
        	  var html =''; 
          	  for(var i = 0;i<data.length;i++){
          		html +='<option value="'+data[i].areaname+'">'+data[i].areaname+'</option>';
          	  }
          
              $("#area").html(html);
              form.render();
           },
           error : function() {
               
           }
       });
   }); 
   
   form.on('select(area)', function(data){
	   var dept = $("#dept").val();  
	   var area = data.value;
	
	   var url = '${pageContext.request.contextPath}/lims/tasks/ordSchedule/getPlant';
       $.ajax({
           type : "GET",
           url : url,
           async : false,
           data : {
               dept : dept,
               area : area
           },
           success : function(data) {
        	  var html =''; 
          	  for(var i = 0;i<data.length;i++){
          		html +='<option value="'+data[i].plant+'">'+data[i].plant+'</option>';
          	  }
          	
              $("#plant").html(html);
              form.render();
           },
           error : function() {
               
           }
       });
   }); 
   
   form.on('select(plant)', function(data){
	   var dept = $("#dept").val(); 
	   var area = $("#area").val();
	   var plant = data.value;
	   
	   var url = '${pageContext.request.contextPath}/lims/tasks/ordSchedule/getSamplepoint';
       $.ajax({
           type : "GET",
           url : url,
           async : false,
           data : {
               dept : dept,
               area : area,
               plant : plant
           },
           success : function(data) {
        	  var html =''; 
          	  for(var i = 0;i<data.length;i++){
          		html +='<option value="'+data[i].point+'">'+data[i].description+'</option>';
          	  }
          
              $("#point").html(html);
              form.render();
           },
           error : function() {
               
           }
       });
   }); 
 });
 $(document).ready(function(){
   	var dept = $("#dept").val();
   	var url = '${pageContext.request.contextPath}/lims/tasks/ordSchedule/getArea';
       $.ajax({
           type : "GET",
           url : url,
           async : false,
           data : {
               dept : dept
           },
           success : function(data) {
        	   var html =''; 
           	  for(var i = 0;i<data.length;i++){
           		html +='<option value="'+data[i].areaname+'">'+data[i].areaname+'</option>';
           	  }
           
              $("#area").html(html);
   			  
           },
           error : function() {
               
           }
       }); 
   
 });
  function ok(){
	  var area = $("#area").val();
	  var plant = $("#plant").val();
	  var point = $("#point").val();
	  var desc = $("#point").find("option:selected").text();
	  
	  parent.$('#point').val(point);
	  parent.$('#pointDesc').val(desc);
	  parent.$('#areaname').val(area);
	  parent.$('#plant').val(plant);
	  
	  var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	  parent.layer.close(index);
  }
 </script>
</body>
</html>