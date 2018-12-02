<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>加样明细</title>
 <link rel="stylesheet" href="${pageContext.request.contextPath}/skins/font-awesome/css/font-awesome.min.css">
 <link rel="stylesheet" href="${pageContext.request.contextPath}/skins/links/bootstrap-3.3.7/css/bootstrap.min.css">
 <link rel="stylesheet" href="${pageContext.request.contextPath}/skins/fuelux/css/ace.min.css">
 <script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/bootstrap-3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/layer/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/laydate/laydate.js"></script>
 <link rel="stylesheet" href="${pageContext.request.contextPath}/skins/layui/css/layui.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/layui/layui.js"></script>
 
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
		color: #FFF;
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
	<form class="form-inline" action="${pageContext.request.contextPath}/lims/zltj/jy/approveJy">
		<div class="form-group">
			<label class="control-label" style="font-weight: normal;">车间：</label>
			<select class="form-control" name="area">
            <c:forEach items="${areaList }" var="v">
             <c:if test="${v==area }">
              <option value="${v}" selected="selected">${v}</option>
             </c:if>
             <c:if test="${v!=area}">
              <option value="${v}">${v}</option>
             </c:if>
            </c:forEach>
           </select>
		</div>
		<%-- <div class="form-group">
			<label class="control-label" style="font-weight: normal;">开始时间：</label>
			<input class="form-control" name="startDate" placeholder="开始时间" value="${startDate}" type="text" id="startDate" autocomplete="off" readonly="readonly"/>
		</div>
		<div class="form-group">
			<label class="control-label" style="font-weight: normal;margin-left: 20px;">截至时间：</label>
			<input class="form-control" name="endDate" placeholder="默认当前时间" value="${endDate}" id="endDate" type="text" autocomplete="off"  readonly="readonly"/>
		</div> --%>
		<div class="form-group">
            <button type="submit" class="btn btn-success" style="margin-left: 30px;">查询</button>
			<button type="button" class="btn btn-info" style="margin-left: 20px;" onclick="onSubmit()" >通过</button>
            <button type="button" class="btn btn-danger" style="margin-left: 20px;" onclick="onRefuse()">拒绝</button>
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
			<th>装置</th>
			<th>加样时间</th>
			<th>样品编号</th>
			<th>样品名称</th>
			<th>不统计原因</th>
			<th>检测项目</th>
			<th>检测成本</th>
             <th>明细链接</th>
		</tr></thead>
		<tbody>
			<c:forEach items="${addSampList}" var="d" varStatus="status">
				<tr>
                    <td><input type="checkbox" name="selectId" value="${d.ordNo}"/></td>
					<td>${status.count}</td>
                    <td>${d.decription}</td>
                    <td nowrap="nowrap">${d.sampeDate}</td>
					<td>
						${d.ordNo}
					</td>
					<td>${d.matname}</td>
					<td>${d.comments}</td>
					
                     <td>
                     <c:forEach items="${d.testList }" var="testNo">
                      <label style="margin-left: 5px;margin-right: 5px;">${testNo }</label>
                     </c:forEach>
                    </td>
					<td>${d.price}</td>
					<td>
						<label class="btn btn-link" style="margin: 0px;padding: 0px;" onclick="showDetail('${d.ordNo}')">明细</label>
					</td>
				</tr>
			</c:forEach>
			
		</tbody>
		
	</table>
	</div>
</div>
	
<script type="text/javascript">
//执行一个laydate实例
laydate.render({
  elem: '#startDate' //指定元素
  ,theme: 'molv'
});
laydate.render({
  elem: '#endDate' //指定元素
  ,theme: 'molv'
}); 
$(window).on('resize', function() {
	$("#treeContent").height($("body").height()-45);
}).resize();

function showDetail(ordno){
	layer.open({
		  type: 2 //Page层类型
		  ,scrollbar:false
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
    	layer.prompt({title: '不统计原因', formType: 2}, function(text, index){
    		  $.ajax({
    		        type:"post",
    		        url:"${pageContext.request.contextPath }/lims/zltj/jy/approveRefuse",
    		        data:{'selectId':chk_value,'reason':text},
    		        traditional :true, 
    		        async: false,
    		        success:function(data){
    		         //window.location.replace(window.location.href);
    		     	   location.href ="${pageContext.request.contextPath }/lims/zltj/jy/approveJy";
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
    
	  $.ajax({
	        type:"post",
	        url:"${pageContext.request.contextPath }/lims/zltj/jy/approveDeal",
	        data:{'selectId':chk_value},
	        traditional :true, 
	        async: false,
	        success:function(data){
	         //window.location.replace(window.location.href);
	     	   location.href ="${pageContext.request.contextPath }/lims/zltj/jy/approveJy";
	        },
	        error:function(request){
	         return;
	        }
	     });
    		
    
}
</script>
</body>
</html>