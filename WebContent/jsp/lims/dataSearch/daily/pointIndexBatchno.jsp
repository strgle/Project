<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/header/meta.jsp"%>
<title>${title}</title>
<%@include file="/common/header/jquery.jsp"%>
<%@include file="/common/header/laydate.jsp"%>
<%@include file="/common/header/font-awesome.jsp"%>
<%@include file="/common/header/bootstrap.jsp"%>
<%@include file="/common/header/default.jsp"%>
<%@include file="/common/header/layui.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/fuelux/css/ace.min.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/fuelux/js/fuelux.tree.min.js"></script>

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
		width: 250px;
		border-right:2px solid #F5F5F5;
		height: 100%;
	}
	
	.articleDiv {
		position: absolute;
		left: 250px;
		right: 0;
		top: 0;
		bottom: 0;
		width: auto;
		overflow:hidden ;
		background-color: #FFFFFF;
		padding: 0px;
		margin: 0px;
	}

</style>
</head>
<body>

<div class="left-side">	
	<!-- 查询条件 -->
	<div style="margin-right: -2PX;padding: 10px 10px 0px;">
	<form action="" id="form" target="ordList" method="post">
		<input type="hidden" name="areaName" id="areaName"/>
		<input type="hidden" name="plant" id="plant"/>
		<input type="hidden" name="pointId" id="pointId">
		<p>开始日期：<input type="text" name="startDate" style="width: 120px;height:25px;padding-left: 10px;margin-top: 5px;margin-bottom: 5px;" value="${startDate}" id="startDate" autocomplete="off"/></p>
		<p>结束日期：<input type="text" name="endDate" style="width: 120px;height:25px;padding-left: 10px;margin-top: 5px;margin-bottom: 5px;" value="${endDate}" id="endDate" autocomplete="off"/></p>
        <p>批&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号：<input type="text" name="batchno" id="batchno" style="width: 120px;height:25px;margin-top: 5px;margin-bottom: 5px;padding-left: 10px;" autocomplete="off"/></p>
	</form>
	</div>
	<!-- 车间装置列表-->
	<div class="panel panel-default" style="margin-right: -2PX;">
		<div class="panel-heading" style="position: relative;">
			车间装置列表
			<div class="panel-tools" style="position: absolute;top: 5px;right: 20px;">
				<button class="btn btn-info btn-sm" type="button" onclick="query()">查询</button>
			</div>
		</div>
		<div class="panel-body" style="padding-top: 0px;padding-bottom: 0px;" >
		   	<div id="treeContent" style="overflow-y: scroll;width: 250px;padding-right: 2px;">
		   		<div id="tree1" class="tree tree-unselectable" style="margin-left:8px;margin-top: 0PX;"></div>
		  	</div>
		</div>
	</div>
</div>
	
<div class="articleDiv">
	<iframe id="ordList"  name="ordList" src="about:blank" style="border: 0px;width: 100%;height:100%;overflow:auto;"></iframe>
</div>
<script type="text/javascript">
$(function(){
	query();
});
$(window).on('resize', function() {
	$("#treeContent").height($("body").height()-125);
}).resize();

laydate.render({
	elem: '#startDate',
	btns: [],
});

laydate.render({
	elem: '#endDate',
	btns: [],
});
	
	
function selectFolder(node){
	var url = "${pageContext.request.contextPath}/lims/dataSearch/daily/samplelistBatchno";
	//获取车间、装置
	if(node.type=="area"){
		$("#areaName").val(node.id);
		$("#plant").val("");
	}else{
		$("#areaName").val(node.areaname);
		$("#plant").val(node.id);
	}
	$("#pointId").val("");
	var batchno = $("#batchno").val();
	
	if(batchno.length ==0){
		 layui.use(['layer'], function(){
			  var layer = layui.layer;
			  
			  //layer.msg('Hello World');
			  layer.confirm('批号为空，是否继续查询？', {
				  btn: ['是','否'] //按钮
				},function(){
					$("#form").attr("action",url);
					$("#form").submit();
					layer.closeAll('dialog');
				});
			});
		
	}else{
		$("#form").attr("action",url);
		$("#form").submit();
	}
		
	
	
}

function selectItem(node){
	var url = "${pageContext.request.contextPath}/lims/dataSearch/daily/samplelistBatchno";
	$("#pointId").val(node.id);
	$("#areaName").val(node.areaname);
	$("#plant").val(node.plant);
	var batchno = $("#batchno").val();
	
	if(batchno.length ==0){
		layui.use(['layer'], function(){
			  var layer = layui.layer;
			  
			  //layer.msg('Hello World');
			  layer.confirm('批号为空，是否继续查询？', {
				  btn: ['是','否'] //按钮
				},function(){
					$("#form").attr("action",url);
					$("#form").submit();
					layer.closeAll('dialog');
				});
			});
	}
	else{
		$("#form").attr("action",url);
		$("#form").submit();
	}
	
	
}

function query(){
	var param= $("#form").serialize();
	$('#tree1').tree({
		url:"${pageContext.request.contextPath}/api/lims/areaPlants/points",
		data:param,
		selectItem: selectItem,
		selectFolder:selectFolder,
		leafNode:"point"
	});
	
}

</script>
</body>
</html>