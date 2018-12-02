<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>报告单查询</title>
 <link rel="stylesheet" href="${pageContext.request.contextPath}/skins/layui/css/layui.css">
 <link rel="stylesheet" href="${pageContext.request.contextPath}/skins/font-awesome/css/font-awesome.min.css">
 <link rel="stylesheet" href="${pageContext.request.contextPath}/skins/links/bootstrap-3.3.7/css/bootstrap.min.css">
 <link rel="stylesheet" href="${pageContext.request.contextPath}/skins/css/style.css">
 <script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/jquery.min.js"></script>
 <script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/laydate/laydate.js"></script>
 <script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/layer/layer.js"></script> 
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
     font-size: 12px;
    }
    .table tr th label{
     width:100%;
     margin:0px;
     border: 0px;
     font-size: 12px;
    }	
</style>
</head>
<body>
<div class="panel panel-default" style="margin-bottom: 0px;">
 <div class="panel-body" style="padding:10px 20px;">
   <form action="" id="form" target="ordList" method="post">
     <input type="hidden" name="areaName" id="areaName" value="${areaName }"/>
     <input type="hidden" name="plant" id="plant" value="${plant }"/>
     <input type="hidden" name="pointId" id="pointId" value="${pointId }">
    </form>
   <div class="form-group">
   <button type="button" class="btn btn-success" style="margin-left: 30px;" onclick="onSubmit()">审核报告</button>
   <button type="button" class="btn btn-danger" style="margin-left: 20px;" onclick="onRefuse()" >退回报告</button>
  </div>
 </div>
</div>
<div class="panel panel-success" style="margin-bottom: 0px;">
	
	<div id="tableContaint" class="panel-body" style="padding: 0px;overflow-y:auto;position:relative;">
	<table class="table table-bordered">
		<thead id="fixheader"><tr class="active">
            <th><label>&nbsp;</label></th>
			<th><label>序号</label></th>
            <th><label>报告单</label></th>
            <th><label>合格证</label></th>
           <th><label>批号</label></th>
          <th><label>样品编号</label></th>
          <th><label>样品名称</label></th>
          <th><label>装置</label></th>
          <th><label>采样点</label></th>
          <th><label>检验结论</label></th>
          <th><label>采样时间</label></th>
          <th><label>备注</label></th>
          
		</tr></thead>
		<tbody>
			<c:forEach items="${ordList}" var="v" varStatus="status">
			<tr>
                <td><input type="checkbox" name="selectId" value="${v['ordno']}"/></td>
				<td style="text-align: center;">${status.count }</td>
                <td><button class="btn btn-link" onclick="showFile2('${v.url}')">预览</button></td>
                <td><button class="btn btn-link"  onclick="showFile2('${v.coaurl}')">合格证${v.coaurl}</button></td>
                <td>${v['batchno']}</td>
				<td style="white-space: nowrap;cursor:Pointer;" onclick="showDetail('${v['ordno']}')">${v['ordno']}</td>
				<td>${v['matname']}</td>
				<td>${v['plant']}</td>
				<td>${v['pointdesc']}</td>
				<td style="text-align: center;">${v['conclusion'] }</td>
				<td><fmt:formatDate value="${v['sampdate']}" pattern="yyyy-MM-dd HH:mm"/></td>
				<td>${v['batchname']}</td>
				
			</tr>
			</c:forEach>
		</tbody>
	</table>
    <table class="table table-bordered" style="margin:0px;position:absolute;top:0px;background-color: #FFF;width: 100%;" id="hidefix">
     <thead><tr>
     <th><label>&nbsp;</label></th>
      <th><label>序号</label></th>
      <th><label>报告单</label></th>
      <th><label>合格证</label></th>
       <th><label>批号</label></th>
      <th><label>样品编号</label></th>
      <th><label>样品名称</label></th>
      <th><label>装置</label></th>
      <th><label>采样点</label></th>
      <th><label>检验结论</label></th>
      <th><label>采样时间</label></th>
      <th><label>备注</label></th>
      
     </tr></thead>
     </table>
	</div>
	</div>
<div id="analyte" style="display: none;">
 <form action="" id="analyteForm" method="post" target="_blank">
   <label>报告单合格标识:</label> 
   <select class="form-control" name="status" id="status">
     <option value="Y" selected="selected">合格</option>
     <option value="N">超标</option>
     <option value="YT">优等品</option>
     <option value="YF">一等品</option>
   </select> 
  <div class="form-group">
    <label for="name">检验结论:</label>
    <textarea class="form-control" rows="3" id="remark" name="remark"></textarea>
  </div>
   <!-- <label>检验结论:</label>
   <textarea rows="3" cols="30" name="remark"></textarea> -->
 </form>
</div>
<script type="text/javascript">
	
   $(window).on('resize', function() {
    var height = $("body").height()-100;
    $("#tableContaint").height(height);
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
	
	function showDetail(ordno){

		 layer.open({
		    type: 2 //Page层类型
		    ,area: ['70%', '90%']
		    ,title: '检测结果'
		    ,content: '${pageContext.request.contextPath}/lims/dataSearch/daily/ordDetail?ordNo='+ordno
		 });  
		}
	function onRefuse(){
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
	    layui.use(['layer'],function(){
	    	layer.prompt({title: '退回原因', formType: 2}, function(text, index){
	    		  $.ajax({
	    		        type:"post",
	    		        url:"${pageContext.request.contextPath }/lims/dataSearch/report/approveRefuse",
	    		        data:{'selectId':chk_value,'reason':text},
	    		        traditional :true, 
	    		        async: false,
	    		        success:function(data){
	    		         //window.location.replace(window.location.href);
	    		           var param= $("#form").serialize();
	    		     	   location.href ="${pageContext.request.contextPath }/lims/dataSearch/report/reportListApprove?"+param;
	    		     	 /*  var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	    		  		  parent.layer.close(index); */
	    		        },
	    		        error:function(request){
	    		         return;
	    		        }
	    		     });
	    		layer.close(index);
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
	    layui.use(['layer'],function(){
	   	 var layer = layui.layer;
	   	 layer.open({
	   	  type:1,
	   	  title:"审核报告",
	   	  area: ['40%', '60%'],
	   	  content:$('#analyte'),
	   	  btn: ['确定'],
	   	  yes:function(index,layero){
	   	   
	   	   	var status = $("#status").val();
	   	   	var remark = $("#remark").val();
	   	   
       	   	 $.ajax({
       		        type:"post",
       		        url:"${pageContext.request.contextPath }/lims/dataSearch/report/approveDeal",
       		        data:{'selectId':chk_value,'reason':remark,'status':status},
       		        traditional :true, 
       		        async: false,
       		        success:function(data){
       		         //window.location.replace(window.location.href) parent.window.;
       		        	 var param= $("#form").serialize();
       		        	
       		     	    location.href ="${pageContext.request.contextPath }/lims/dataSearch/report/reportListApprove?"+param;
       		        },
       		        error:function(request){
       		         return;
       		        }
       		     }); 
        	   	layer.close(index);
	   	  }
	   	  
	   	 });
	    });
		  
	    
	}
  function showFile2(fileurl){
	  if(fileurl.length == 0  ){
	    	layer.msg('无合格证', {time: 5000, icon:6});
	    	return;
	    }
	    else{
	    	if(fileurl.indexOf(".PDF")<=0){
	    		layer.msg('无文件', {time: 5000, icon:6});
	        	return;
	    	}	
	    }
   //判断下载form是否存在
   var $elemForm = $("#downLoadForm2");
   if($elemForm.length==0){
    $eleForm = $("<form method='post' id='downLoadForm2'></form>");
    var fileUrlInput = $("<input type='hidden' id='fileurl' name='fileurl' />");
    $eleForm.append(fileUrlInput);
       $eleForm.attr("action","${pageContext.request.contextPath}/lims/common/ftpdownLoad");
       $(document.body).append($eleForm);
   }
   
   $("#fileurl").val(fileurl);
      //提交表单，实现下载
      $eleForm.submit();
  }
  
</script>
</body>
</html>
