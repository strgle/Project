<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/header/meta.jsp"%>
<%@include file="/common/header/font-awesome.jsp"%>
<%@include file="/common/header/jquery.jsp"%>
<%@include file="/common/header/bootstrap.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/links/layui/css/layui.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/layui/layui.js"></script>

<title>下样</title>
</head>
<body>
<script type="text/html" id="toolbarDemo">
  <div class="layui-btn-container">
    <button class="layui-btn layui-btn-sm" lay-event="add">登录</button>
    <button class="layui-btn layui-btn-sm" lay-event="delete">删除</button>
    <button class="layui-btn layui-btn-sm" lay-event="submit">提交</button> 
  </div>
</script>

<table id="demo" lay-filter="test"></table>


<script>
layui.use('table', function(){
  var table = layui.table;
  
  //第一个实例
  table.render({
    elem: '#demo'
    ,height: 500
    ,url: '/lims/tasks/ordSchedule/orderLoginData' //数据接口
    ,toolbar: '#toolbarDemo'
    
    ,loading:true
    ,cols: [[ //表头
    	{type: 'checkbox', fixed: 'left'}
      ,{field: 'batchid', title: '批代码', width:90, sort: true, fixed: 'left'}
      ,{field: 'batchno', title: '批号', width:100, sort: true, event: 'setSign', style:'cursor: pointer;'}
      ,{field: 'matname', title: '样品名称', width:180, sort: true}
      ,{field: 'areaName', title: '车间', width: 120}
      ,{field: 'plant', title: '装置', width:120}
      ,{field: 'description', title: '采样点', width:120} 
      ,{field: 'ordno', title: '样品编号', width: 177, sort: true ,event: 'setSign', style:'cursor: pointer;'}
      ,{field: 'type', title: '类型', width: 80, sort: true}
      ,{field: 'createdby', title: '创建人', width: 80, sort: true}
      ,{field: 'sampdate', title: '采样时间', width: 100, sort: true}
      ,{field: 'tasktype', title: '来源类型', width: 135, sort: true}
    ]]
  });
//工具栏事件
  table.on('toolbar(test)', function(obj){
    var checkStatus = table.checkStatus(obj.config.id);
    var data = checkStatus.data;
    switch(obj.event){
      case 'add':
    	  layer.open({
        	  type: 2 //Page层类型
        	  ,scrollbar:false
        	  ,area: ['100%', '100%']
        	  ,title: '样品登录'
        	  ,maxmin: true
        	  ,content: '${pageContext.request.contextPath}/lims/tasks/ordSchedule/sampleLogin'
        	 
        	});  
        	//tab层
		

      break;
      case 'delete':
    	  layer.confirm('真的删除行么？', {
    		  btn: ['确定','取消'] //按钮
    		}, function(){
    		  var arr = new Array();
    		  for(var i = 0;i<data.length;i++){
    			  //alert(JSON.stringify(data[i].batchid));
    			  arr.push(data[i].batchid);
    		  }
    		  
    		  if(arr.length>0){
    			  arr = arr.join(',');
    			  var url = '${pageContext.request.contextPath}/lims/tasks/ordSchedule/deleteSample';
    		      $.ajax({
    		          type : "POST",
    		          url : url,
    		          async : false,
    		          data : {
    		        	  arr : arr
    		          },
    		          success : function(data) {
    		        	  if(data.code=="SUCCESS"){
    		        		  layer.msg('删除成功', {time: 3000, icon:6},function(){
    		        			  location.href ="${pageContext.request.contextPath}/lims/tasks/ordSchedule/orderLogin";
    		        		  });
    		        		  
    		        	  }
    		        	  else{
    		        		  layer.msg('删除失败', {time: 5000, icon:6});
    		        	  }
    		        	 
    		  			  //alert(data);
    		          },
    		          error : function() {
    		        	  
    		        	  layer.msg('删除异常', {time: 5000, icon:6});
    		        	 
    		          }
    		      }); 
    		  }
    		  
    		});
      break;
      case 'submit':
    	  layer.confirm('真的提交行么？', {
    		  btn: ['确定','取消'] //按钮
    		}, function(){
    		  var arr = new Array();
    		  for(var i = 0;i<data.length;i++){
    			  //alert(JSON.stringify(data[i].batchid));
    			  arr.push(data[i].batchid);
    		  }
    		  
    		  if(arr.length>0){
    			  arr = arr.join(',');
    			  var url = '${pageContext.request.contextPath}/lims/tasks/ordSchedule/submitSample';
    		      $.ajax({
    		          type : "POST",
    		          url : url,
    		          async : false,
    		          data : {
    		        	  arr : arr
    		          },
    		          success : function(data) {
    		        	  if(data.code=="SUCCESS"){
    		        		  layer.msg('提交成功', {time: 3000, icon:6},function(){
    		        			  location.href ="${pageContext.request.contextPath}/lims/tasks/ordSchedule/orderLogin";
    		        		  });
    		        		  
    		        	  }
    		        	  else{
    		        		  layer.msg('提交失败', {time: 5000, icon:6});
    		        	  }
    		        	 
    		  			  //alert(data);
    		          },
    		          error : function() {
    		        	  
    		        	  layer.msg('提交异常', {time: 5000, icon:6});
    		        	 
    		          }
    		      }); 
    		  }
    		  
    		});
      break;
    
    };
  });
  //监听行单击事件（单击事件为：rowDouble）
   /* table.on('row(test)', function(obj){
    var data = obj.data;
    
  
    layer.open({
		  type: 2 //Page层类型
		  ,scrollbar:false
		  ,area: ['70%', '90%']
		  ,title: '检测结果'
		  ,content: '${pageContext.request.contextPath}/lims/dataSearch/daily/ordDetail?ordNo='+data.ordno
	});    

    obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
  });  */
  //监听单元格事件
  table.on('tool(test)', function(obj){
    var data = obj.data;
    if(obj.event === 'setSign'){
    	 layer.open({
   		  type: 2 //Page层类型
   		  ,scrollbar:false
   		  ,area: ['70%', '90%']
   		  ,title: '检测结果'
   		  ,content: '${pageContext.request.contextPath}/lims/dataSearch/daily/ordDetail?ordNo='+data.ordno
   		});    
    }
  });

});
</script>
</body>
</html>