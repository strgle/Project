<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${title}</title>
 <%-- <link rel="stylesheet" href="${pageContext.request.contextPath}/skins/font-awesome/css/font-awesome.min.css">
 <link rel="stylesheet" href="${pageContext.request.contextPath}/skins/bootstrap/css/bootstrap.min.css">
 <link rel="stylesheet" href="${pageContext.request.contextPath}/skins/fuelux/css/ace.min.css">
 <script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/jquery.min.js"></script>
 <script type="text/javascript" src="${pageContext.request.contextPath}/skins/bootstrap/js/bootstrap.min.js"></script>
 <script type="text/javascript" src="${pageContext.request.contextPath}/skins/fuelux/js/fuelux.tree.min.js"></script>
 --%>
<%@include file="/common/header/jquery.jsp"%>
<%@include file="/common/header/laydate.jsp"%>
<%@include file="/common/header/font-awesome.jsp"%>
<%@include file="/common/header/bootstrap.jsp"%>
<%@include file="/common/header/default.jsp"%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/fuelux/css/ace.min.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/fuelux/js/fuelux.tree.min.js"></script>
 <script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/layer/layer.css"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/layer/layer.js"></script>
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
		border-right:2px solid #F5F5F5;
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
	<!-- 车间装置列表-->
	<div class="panel panel-default" style="margin-right: -2PX;">
		<div class="panel-heading">车间装置</div>
		<div class="panel-body" style="padding-top: 0px;padding-bottom: 0px;" >
		   	<div id="treeContent" style="overflow-y: scroll;width: 220px;padding-right: 2px;">
		   		<div id="tree1" class="tree tree-unselectable" style="margin-left:8px;margin-top: 0PX;"></div>
		  	</div>
		</div>
	</div>
</div>
	
<div class="articleDiv">
	<iframe id="ordList" src="about:blank" style="border: 0px;width: 100%;height:100%;overflow:auto;"></iframe>
</div>
</body>
<script type="text/javascript">

$(window).on('resize', function() {
	$("#treeContent").height($("body").height()-45);
}).resize();
$(function(){
	
	query();
});

/* $('#tree1').tree({
	url:"${pageContext.request.contextPath}/api/lims/areaPlants/plants/0",
	selectItem: selectItem,
	selectFolder:selectFolder,
	loadChildren:loadChildren
}); */
function query(){
	 layer.load(0, {
        shade: [0.1, '#fff'] 
    });
	$('#tree1').tree({
		url:"${pageContext.request.contextPath}/api/lims/areaPlants/matAnalyte",
		selectItem:selectItem,
		leafNode:"mat"
	});
	layer.closeAll('loading'); 	
}
function selectFolder(node){
	node.searchType="byApprDate";
	//获取车间、装置
	if(node.type=="area"){
		node.areaName=node.id;
		var params = $.param(node);
		$("#ordList").attr("src","${pageContext.request.contextPath}/lims/tasks/ordSchedule/samplelist?"+params);
	}else{
		var areaPlant = node.id.split("@@");
		node.areaName=areaPlant[0];
		node.plant=areaPlant[1];
		var params = $.param(node);
		$("#ordList").attr("src","${pageContext.request.contextPath}/lims/tasks/ordSchedule/samplelist?"+params);
	}
} 

function selectItem(node){
	
	$("#ordList").attr("src","${pageContext.request.contextPath}/lims/tasks/ordSchedule/samplelist?type=mat&keyValue="+node.id+"&areaName="+node.areaname+"&plant="+node.plant);
}
/* 
function loadChildren(node){
	var ap=node.id.split("@@");
	node.areaName=ap[0];
	node.plant=ap[1];
	var params = $.param(node);
	
	return {data:params,url:"${pageContext.request.contextPath}/api/lims/areaPlants/planpoints"};
}
 */
</script>

</html>