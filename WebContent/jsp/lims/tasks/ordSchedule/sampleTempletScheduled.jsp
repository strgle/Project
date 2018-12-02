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
      <div class="layui-card-header">
         <div class="layui-inline">
              <input type="hidden" id="spcode" value="${spcode}"/>
             <input type="hidden" id="samplegroupcode" value="${samplegroupcode}"/>
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
         <div class="layui-inline">
        
         <label class="layui-form-label">检验方案</label>
         
          <div class="layui-input-inline">
              <select name="profile" lay-verify="required" id="profile">
             
                  <c:forEach items="${profileList }" var="profile">
                   <option value="${profile.profile}">${profile.profile}</option>
                  </c:forEach>
              </select>
          </div>
          <label class="layui-form-label">检验指标</label>
          <div class="layui-input-inline">
              <select name="specno" lay-verify="required" id="specno">
                 
                  <c:forEach items="${specnoList }" var="spec">
                   <option value="${spec.specno}">${spec.specname}</option>
                  </c:forEach>
                </select>
          </div>
           
       </div>
       <div class="layui-form-item">
        <div class="layui-inline">
          <label class="layui-form-label">采样点</label>
          <div class="layui-input-inline">
              <select name="point" lay-verify="required" id="point">
                  <c:forEach items="${pointList }" var="point">
                   <option value="${point.point}">${point.point}</option>
                  </c:forEach>
                </select>
          </div>
           <label class="layui-form-label">采样点描述</label>
           <div class="layui-input-inline">
               <!-- <input type="text" name="sampdate"  id="sampdate" autocomplete="off"/> -->
               <input type="text" name="pointDesc" id="pointDesc" lay-verify="required" value="${pointList[0].description }" autocomplete="off" class="layui-input">
               
           </div>
          </div>
        </div>
       </div>
       <div class="layui-form-item">
         <div class="layui-inline">
            <label class="layui-form-label">车间</label>
            <div class="layui-input-inline">
               <!-- <input type="text" name="sampdate"  id="sampdate" autocomplete="off"/> -->
               <input type="text" name="areaname" id="areaname" lay-verify="required" value="${pointList[0].areaname }" autocomplete="off" class="layui-input">
               
            </div>
            <label class="layui-form-label">装置</label>
            <div class="layui-input-inline">
               <!-- <input type="text" name="sampdate"  id="sampdate" autocomplete="off"/> -->
               <input type="text" name="plant" id="plant" lay-verify="required" value="${pointList[0].plant }" autocomplete="off" class="layui-input">
               
            </div>
         </div>
       </div>
       <div class="layui-form-item">
         <div class="layui-inline">
            <label class="layui-form-label">批号</label>
            <div class="layui-input-inline">
               <!-- <input type="text" name="sampdate"  id="sampdate" autocomplete="off"/> -->
               <input type="text" name="batchno" id="batchno" lay-verify="required" placeholder="批号" autocomplete="off" class="layui-input">
               
            </div>
           <label class="layui-form-label">采样日期</label>
           <div class="layui-input-inline">
               <!-- <input type="text" name="sampdate"  id="sampdate" autocomplete="off"/> -->
               <input type="text" name="sampdate" id="sampdate" lay-verify="required" placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
               
           </div>
         </div>
       </div>
      
       <div class="layui-form-item">
         <label class="layui-form-label">备注</label>
         <div class="layui-input-block">
           <textarea name="remark" id="remark" placeholder="请输入备注" class="layui-textarea"></textarea>
         </div>
       </div>
       <div class="layui-form-item">
            <div class="layui-input-block">
              <button class="layui-btn" lay-submit lay-filter="component-form-element">立即提交</button>
              <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
       </div>
    </form>
   </div>
   </div>
   </div>
 <script>
 layui.use('form', function(){
	  var form = layui.form;
	  
	  //监听提交
	  form.on('submit(component-form-element)', function(data){
		var dept = $("#dept").val();
		var tasktype = $("#tasktype").val();
		var specno = data.field.specno;
		var profile = data.field.profile;
		var point = data.field.point;
		var pointDesc = data.field.pointDesc;
		var areaname = data.field.areaname;
		var plant = data.field.plant;
		var batchno = data.field.batchno;
		var sampdate = data.field.sampdate;
		var remark = data.field.remark;
		var spcode = $("#spcode").val();
		var samplegroupcode = $("#samplegroupcode").val();
		var url = '${pageContext.request.contextPath}/lims/tasks/ordSchedule/addScheduledSample';
	      $.ajax({
	          type : "POST",
	          url : url,
	          async : false,
	          data : {
	              dept : dept,
	              tasktype :tasktype,
	              specno :specno,
	              profile :profile,
	              point :point,
	              pointDesc :pointDesc,
	              areaname :areaname,
	              plant :plant,
	              batchno :batchno,
	              sampdate :sampdate,
	              remark :remark,
	              spcode :spcode,
	              samplegroupcode :samplegroupcode
	          },
	          success : function(data) {
	        	  
	        	  if(data.code=="SUCCESS"){
	        		  layer.msg('添加成功', {time: 5000, icon:6});
	        		  
	        	  }
	        	  else{
	        		  layer.msg('添加失败', {time: 5000, icon:6});
	        	  }
	        	  var index = parent.parent.layer.getFrameIndex(window.name); //获取窗口索引
	        	  parent.parent.layer.close(index);
	  			  //alert(data);
	          },
	          error : function() {
	        	  
	        	  layer.msg('添加完成', {time: 5000, icon:6});
	        	  var index = parent.parent.layer.getFrameIndex(window.name); //获取窗口索引
	        	  parent.parent.layer.close(index);
	          }
	      }); 
	    return false;
	  });
	});
   laydate({
	    elem: '#sampdate',
	    format: 'YYYY-MM-DD hh:mm:ss',
	    istime: true
	});
   laydate({
	    elem: '#expirytdate',
	    format: 'YYYY-MM-DD hh:mm:ss',
	    istime: true
	});
   laydate({
	    elem: '#productdate',
	    format: 'YYYY-MM-DD hh:mm:ss',
	    istime: true
	});
  
 </script>
</body>
</html>
