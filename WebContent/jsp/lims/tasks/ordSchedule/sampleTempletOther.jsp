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
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/css/formSelects-v4.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/js/formSelects-v4.min.js"></script>

<title>Insert title here</title>
<style type="text/css">
 html,body{
  margin: 0px;
  padding: 0px;
  height: 100%;
 }
 .left-side {
  position:fixed;
  top:0;
  bottom:0;
  width: 400px;
  border:2px solid #F5F5F5;
  height: 100%;
 }
 
 .articleDiv {
  position: absolute;
  left: 400px;
  right: 0;
  top: 0;
  bottom: 0;
  width: auto;
  overflow:hidden ;
  overflow-y: scroll;
  border:2px solid #F5F5F5;
  background-color: #FFFFFF;
  padding: 0px;
  margin: 0px;
 }

</style>
</head>
<body>
<div class="left-side"> 
<div class="panel panel-default" style="margin-right: -2PX;">
  
  <div class="panel-body" style="padding-top: 0px;padding-bottom: 0px;" >
  <div id="treeContent" style="overflow-y: scroll;width: 400px;padding-right: 2px;">
    <div class="layui-card">
      <div class="layui-card-header">表单组合</div>
      <div class="layui-card-body" style="padding: 15px;">

    <form class="layui-form" action="">
       <div class="layui-form-item">
         
         <label class="layui-form-label">样品来源</label>
          <div class="layui-input-block" style="margin-bottom: 5px;">
              <input type="text" name="source" id="source" lay-verify="required"  autocomplete="off" class="layui-input">
          </div>
          <label class="layui-form-label">送样人员</label>
          <div class="layui-input-block" style="margin-bottom: 5px;">
              <input type="text" name="sender" id="sender" lay-verify="required"  autocomplete="off" class="layui-input">
          </div>
          <label class="layui-form-label">样品描述</label>
          <div class="layui-input-block" style="margin-bottom: 5px;">
              <input type="text" name="sampledesc" id="sampledesc" lay-verify="required"  autocomplete="off" class="layui-input">
          </div>
          <label class="layui-form-label">采样日期</label>
           <div class="layui-input-block" style="margin-bottom: 5px;">
               <!-- <input type="text" name="sampdate"  id="sampdate" autocomplete="off"/> -->
               <input type="text" name="sampdate" id="sampdate" lay-verify="date" placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
               
           </div>
           <label class="layui-form-label">车间</label>
            <div class="layui-input-block" style="margin-bottom: 5px;">
               <!-- <input type="text" name="sampdate"  id="sampdate" autocomplete="off"/> -->
               
               <select name="area" lay-verify="required" lay-filter="area" id="area" lay-search="">
                  <c:forEach items="${areaList }" var="area">
                   <option value="${area.areaname}">${area.areaname}</option>
                  </c:forEach>
               </select>
            </div>
            <label class="layui-form-label">装置</label>
            <div class="layui-input-block" style="margin-bottom: 5px;">
               <!-- <input type="text" name="sampdate"  id="sampdate" autocomplete="off"/> -->
               <select name="plant" lay-verify="required" lay-filter="plant" id="plant" lay-search="">
            
               </select>
            </div>
            <label class="layui-form-label">采样点</label>
            <div class="layui-input-block" style="margin-bottom: 5px;">
                <select name="point" lay-verify="required" lay-filter="point" id="point" lay-search="">
            
                </select>
            </div>
            <label class="layui-form-label">采样点描述</label>
           <div class="layui-input-block" style="margin-bottom: 5px;">
               <!-- <input type="text" name="sampdate"  id="sampdate" autocomplete="off"/> -->
               <input type="text" name="pointDesc" id="pointDesc" lay-verify="required" value="${pointList[0].description }" autocomplete="off" class="layui-input">
               
           </div>
           <label class="layui-form-label">批号</label>
            <div class="layui-input-block" style="margin-bottom: 5px;">
               <!-- <input type="text" name="sampdate"  id="sampdate" autocomplete="off"/> -->
               <input type="text" name="batchno" id="batchno" lay-verify="required" placeholder="批号" autocomplete="off" class="layui-input">
               
            </div>
            <label class="layui-form-label">备注</label>
            <div class="layui-input-block" style="margin-bottom: 5px;">
              <textarea name="remark" id="remark" placeholder="请输入备注" class="layui-textarea"></textarea>
            </div>
       </div>
    </form>
   </div>
   </div>
   </div>
 </div>
 </div>
</div>
 <div class="articleDiv">
    <div class="layui-fluid">
      <div class="layui-card">
        <div class="layui-card-header">
       
          <div class="layui-inline">
              
              <div class="layui-input-inline" style="margin-bottom: 5px;">
              <label class="layui-form-label">实验室</label>
                 <select name="dept"  id="dept" >
                    <c:forEach items="${deptList }" var="dept">
                     <option value="${dept.dept}">${dept.dept}</option>
                    </c:forEach>
                 </select>
              </div>
              
              <div class="layui-input-inline" style="margin-bottom: 5px;">
              <label class="layui-form-label">任务类型</label>
                 <select name="tasktype"  id="tasktype">
                    <c:forEach items="${tasktypeList }" var="task">
                     <option value="${task.tasktype}">${task.tasktype}</option>
                    </c:forEach>
                 </select>
              </div>
          </div>
        
        </div>
        <div class="layui-card-body" style="padding: 15px;">
        <form class="layui-form" action="">
           <div class="layui-form-item">
             <label class="layui-form-label">测试分类</label>
             <div class="layui-input-block" style="margin-bottom: 5px;">
                 <select name="testType" lay-verify="required" lay-filter="testType" id="testType" lay-search="">
                    <c:forEach items="${testtypeList }" var="testtype">
                     <option value="${testtype.testcatcode}">${testtype.testcatcode}</option>
                    </c:forEach>
                 </select>
             </div>
           </div>
           <div class="layui-form-item">
              <label class="layui-form-label">待测试列表</label>
              <div class="layui-input-block" style="margin-bottom: 5px;">
               <select name="test" id="test" xm-select="select1" xm-select-search=""  >
                 
               </select>
               </div>
           </div>
           <div class="layui-form-item">
               <label style='color:red;'>先选择测试分类，选择当前测试分类下测试列表，也可切换测试分类下，选择测试。最后点击"确认选择"，完成测试选择。</label>
           </div>
           <div class="layui-form-item">
              <label class="layui-form-label">测试列表</label>
              <div class="layui-input-block" style="margin-bottom: 5px;">
               <select name="tests" id="tests" xm-select="select2" >
                 
               </select>
               </div>
           </div> 
           
        </form>
        <div class="layui-form-item">
                <button type="button" class="layui-btn layui-btn-sm"  onclick="submit()">立即提交</button>
                <button type="button" class="layui-btn layui-btn-sm" onclick="test()">确认选择</button>
           </div>
     </div>
    </div>
   </div>
 </div>
<!--  <div class="layui-form-item layui-layout-admin" >
            <div class="layui-input-block">
              <div class="layui-footer" style="left: 0;">
                <button type="button" class="layui-btn layui-btn-sm"  onclick="submit()">立即提交</button>
                <button type="button" class="layui-btn layui-btn-sm" onclick="test()">确认选择</button>
              </div>
            </div>
          </div> -->

 <script>
 var formSelects = layui.formSelects;

 
 $(window).on('resize', function() {
	 $("#treeContent").height($("body").height()-20);
	}).resize(); 
 laydate({
	    elem: '#sampdate',
	    format: 'YYYY-MM-DD hh:mm:ss',
	    istime: true
	});

 layui.use('form', function(){
	  var form = layui.form;
	  form.on('select(testType)', function(data){
		  
		   var testType = data.value;
		   var url = '${pageContext.request.contextPath}/lims/tasks/ordSchedule/getTests?testType='+testType;
		   formSelects.data('select1', 'server', {
       	    url: url
       	   });
	   }); 
	  
	  form.on('select(area)', function(data){
		   var dept = '';  
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
		   var dept = '';  
		   
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
	          		html +='<option value="'+data[i].point+'">'+data[i].point+'</option>';
	          	  }
	              
	              $("#point").html(html);
	              $("#pointDesc").html(data[0].description);
	              form.render();
	           },
	           error : function() {
	               
	           }
	       });
	   }); 
	   
	});
   var arrs = new Array();
   
   function test(){
	   //console.log(formSelects.value('select1'));
	   var arr = formSelects.value('select1');
	   for(var i=0;i<arr.length;i++){
		   if(arrs.indexOf(arr[i])<=-1){
			   arrs.push(arr[i]);
		   }
	   }
	   formSelects.data('select2', 'local', {
		    arr: arrs
		}); 
	   var selectArr = new Array();
	   for(var i=0;i<arrs.length;i++){
		   selectArr.push(arrsp[i].value);
	   }
	   formSelects.value('select2', selectArr);
   }
   //提交
   function submit(){
	  if(arrs.length <1){
		   layer.msg('测试不允许为空', {time: 5000, icon:6});
		   return;
	  }
	  var source =  $("#source").val();
	  var sender = $("#sender").val();
	  var sampledesc = $("#sampledesc").val();
	  var sampdate = $("#sampdate").val();
	  var area = $("#area").val();
	  var plant = $("#plant").val();
	  var point = $("#point").val();
	  var pointDesc = $("#pointDesc").val();
	  var batchno = $("#batchno").val();
	  var remark = $("#remark").val();
	  var dept = $("#dept").val();
	  var tasktype = $("#tasktype").val();
	  
	  var testArr = formSelects.value('select2', 'valStr');

	  if(tasktype == null || tasktype.length ==0){
		  layer.msg('任务类型不允许为空', {time: 5000, icon:6});
		  return;
	  }
	  if(dept == null || dept.length ==0){
		  layer.msg('实验室不允许为空', {time: 5000, icon:6});
		  return;
	  }
	  if(sampdate == null || sampdate.length ==0){
		  layer.msg('采样时间不允许为空', {time: 5000, icon:6});
		  return;
	  }
	  if(area == null || area.length ==0){
		  layer.msg('车间不允许为空', {time: 5000, icon:6});
		  return;
	  }
	  if(plant == null || plant.length ==0){
		  layer.msg('装置不允许为空', {time: 5000, icon:6});
		  return;
	  }
	  if(point == null || point.length ==0){
		  layer.msg('采样点编号不允许为空', {time: 5000, icon:6});
		  return;
	  }
	  if(pointDesc == null || pointDesc.length ==0){
		  layer.msg('采样点不允许为空', {time: 5000, icon:6});
		  return;
	  }
	  if(testArr == null || testArr.length ==0){
		  layer.msg('测试不允许为空', {time: 5000, icon:6});
		  return;
	  }
	  //ajax处理
	  var url = '${pageContext.request.contextPath}/lims/tasks/ordSchedule/addTPSample';
      $.ajax({
          type : "POST",
          url : url,
          async : false,
          data : {
              dept : dept,
              source :source,
    	      sender :sender,
    	      sampledesc :sampledesc,
    	      sampdate :sampdate,
    	      area :area,
    	      plant :plant,
    	      point :point,
    	      pointDesc :pointDesc,
    	      batchno :batchno,
    	      remark :remark,
    	      tasktype :tasktype,
    	      testArr :testArr
          },
          success : function(data) {
        	  if(data.code=="SUCCESS"){
        		  layer.msg('添加成功', {time: 5000, icon:6});
        		  
        	  }
        	  else{
        		  layer.msg('添加失败', {time: 5000, icon:6});
        	  }
        	  var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
        	  parent.layer.close(index);
  			  //alert(data);
          },
          error : function() {
        	  layer.msg('添加异常', {time: 5000, icon:6});
        	  var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
        	  parent.layer.close(index);
          }
      }); 
   }
  
 </script>
</body>
</html>
