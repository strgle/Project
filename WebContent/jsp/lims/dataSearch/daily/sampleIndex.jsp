<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${title}</title>
<%@include file="/common/header/jquery.jsp"%>
<%@include file="/common/header/laydate.jsp"%>
<%@include file="/common/header/font-awesome.jsp"%>
<%@include file="/common/header/bootstrap.jsp"%>
<%@include file="/common/header/default.jsp"%>
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
		width: 220px;
		border-right:2px solid #D9EDF7;
		height: 100%;
	}
	
	.articleDiv {
		position: absolute;
		left: 220px;
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
	<form action="${pageContext.request.contextPath}/lims/dataSearch/daily/sampleAccount" id="form" target="ordList" method="post">
		<input type="hidden" name="areaName" id="areaName"/>
		<input type="hidden" name="plant" id="plant"/>
		<input type="hidden" name="matCode" id="matCode"/>
		<input type="hidden" name="matName" id="matName"/>
		<p>开始日期：<input type="text" name="startDate" style="width: 120px;padding-left: 10px;" value="${startDate}" id="startDate" autocomplete="off"/></p>
		<p>结束日期：<input type="text" name="endDate" style="width: 120px;padding-left: 10px;" value="${endDate}" id="endDate" autocomplete="off"/></p>
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
	<iframe id="ordList" src="about:blank" style="border: 0px;width: 100%;height:100%;overflow:auto;" name="ordList"></iframe>
</div>
<script type="text/javascript">

$(window).on('resize', function() {
	$("#treeContent").height($("body").height()-125);
}).resize();

function query(){
	var param= $("#form").serialize();
	$('#tree1').tree({
		url:"${pageContext.request.contextPath}/api/lims/areaPlants/mat",
		data:param,
		selectItem:selectItem,
		leafNode:"mat"
	});
}

$(function(){
	query();
});

laydate.render({
	elem: '#startDate',
	btns: [],
});

laydate.render({
	elem: '#endDate',
	btns: [],
});


function selectItem(node){
	var url = "${pageContext.request.contextPath}/lims/dataSearch/daily/sampleAccount";
	$("#matCode").val(node.id);
	$("#matName").val(node.title);
	$("#areaName").val(node.areaname);
	$("#plant").val(node.plant);
	$("#form").attr("action",url);
	$("#form").submit();
}
		
</script>
</body>
</html>