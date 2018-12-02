<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${title}</title>
 <link rel="stylesheet" href="${pageContext.request.contextPath}/skins/font-awesome/css/font-awesome.min.css">
 <link rel="stylesheet" href="${pageContext.request.contextPath}/skins/bootstrap/css/bootstrap.min.css">
 <link rel="stylesheet" href="${pageContext.request.contextPath}/skins/fuelux/css/ace.min.css">
 <script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/jquery.min.js"></script>
 <script type="text/javascript" src="${pageContext.request.contextPath}/skins/bootstrap/js/bootstrap.min.js"></script>
 <script type="text/javascript" src="${pageContext.request.contextPath}/skins/fuelux/js/fuelux.tree.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/laydate/laydate.js"></script>
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
	<div class="panel panel-default" style="margin-right: -2PX;margin-bottom: 0px;">
		<div class="panel-body" style="padding-bottom: 0px;">
		<form action="${pageContext.request.contextPath}/lims/dataLedger/care" method="post" class="form-horizontal" style="margin-bottom: 0px;padding-bottom: 0px;">
		  	<div class="form-group">
		  		<label for="startRq" class="control-label col-sm-5">开始日期：</label>
		  		<div class="col-sm-7" style="padding-left: 0px;">
		  			<input type="text" class="form-control" onclick="laydate()" value="${startRq}" name="startRq" id="startRq"/> 
		  		</div>
			</div>
			<div class="form-group">
		  		<label for="startRq" class="control-label col-sm-5">截止日期：</label>
		  		<div class="col-sm-7" style="padding-left: 0px;">
		  			<input type="text" class="form-control" onclick="laydate()" value="${endRq}" name="endRq" id="endRq"/> 
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-6">
		  			<button type="button" class="btn btn-warning" style="width: 100%;" onclick="expExcel()">导出EXCEL</button>
				</div>
				<div class="col-sm-6">
		  			<button type="submit" class="btn btn-success" style="width: 100%;">查询</button>
				</div>
			</div>
		</form>
		</div>
	</div>
	
	<div class="panel panel-default" style="margin-right: -2PX;">
		<div class="panel-heading">原辅料/成品列表</div>
		<div class="panel-body" style="padding-top: 0px;padding-bottom: 0px;" >
		   	<div id="treeContent" style="overflow-y: scroll;width: 250px;padding-right: 2px;">
		   		<div id="tree1" class="tree tree-unselectable" style="margin-left:8px;margin-top: 0PX;"></div>
		  	</div>
		</div>
	</div>
</div>
	
<div class="articleDiv">
	<iframe id="contentDetail" src="about:blank" style="border: 0px;width: 100%;height:100%;overflow:auto;"></iframe>
</div>
<script type="text/javascript">

$(window).on('resize', function() {
	$("#treeContent").height($("body").height()-200);
}).resize();

var treeData = eval('${tree}');

$('#tree1').tree({
	dataSource:treeData,
	selectItem: selectItem,
	selectFolder:selectFolder
});

function selectItem(node){
	node.startRq = $("#startRq").val();
	node.endRq = $("#endRq").val();
	var params = $.param(node);
	$("#contentDetail").attr("src","${pageContext.request.contextPath}/lims/dataLedger/care/careDetail2?"+params);
}

function selectFolder(node){
	node.startRq = $("#startRq").val();
	node.endRq = $("#endRq").val();
	var params = $.param(node);
	$("#contentDetail").attr("src","${pageContext.request.contextPath}/lims/dataLedger/care/careDetail?"+params);
}

function expExcel(){
	document.getElementById("contentDetail").contentWindow.expExcel();
}
</script>
</body>
</html>