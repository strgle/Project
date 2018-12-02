<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/header/meta.jsp"%>
<%@include file="/common/header/jquery.jsp"%>
<%@include file="/common/header/laydate.jsp"%>
<%@include file="/common/header/font-awesome.jsp"%>
<%@include file="/common/header/bootstrap.jsp"%>
<%@include file="/common/header/default.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/fuelux/css/ace.min.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/fuelux/js/fuelux.tree.min.js"></script>

<title>车间质量统计</title>

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
		width: 200px;
		border-right:2px solid #F5F5F5;
		height: 100%;
	}
	
	.articleDiv {
		position: absolute;
		left: 200px;
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
 
  <div class="panel panel-default" style="margin-right: -2PX;">
   <div class="panel-heading">车间列表</div>
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
    $("#treeContent").height($("body").height()-45);
   }).resize();
   var dataSource = JSON.parse('${areas}');
   
   $('#tree1').tree({
    dataSource:dataSource,
    selectItem:selectItem
   });
   function selectItem(node){
      var params = $.param(node);
    
      $("#contentDetail").attr("src","${pageContext.request.contextPath}/lims/basic/zlhk/mateListTJ?"+params);
   }

</script>
</body>
</html>
