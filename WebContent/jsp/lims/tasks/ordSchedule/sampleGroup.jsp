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
<%@include file="/common/header/layer.jsp"%>
<%@include file="/common/header/laydate.jsp"%>
<%-- 
 <link rel="stylesheet" href="${pageContext.request.contextPath}/skins/links/layui/css/layuiadmin.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/links/layui/admin.css">  --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/layui/layuiadmin.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/fuelux/css/ace.min.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/fuelux/js/fuelux.tree.min.js"></script>

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
  width: 250px;
  border:2px solid #F5F5F5;
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
<form action="" id="form" target="ordList" method="post">
  <input type="hidden" name="type" id="type"  value="${type }"/>
 </form>
 <!-- 车间装置列表-->
 <div class="panel panel-default" style="margin-right: -2PX;">
  
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
 $("#treeContent").height($("body").height()-50);
}).resize(); 
laydate.render({
	elem: '#sampdate',
	btns: [],
}); 
function query(){
	var param= $("#form").serialize();
	//alert(param);
	$('#tree1').tree({
		url:"${pageContext.request.contextPath}/lims/tasks/ordSchedule/sampleGroupDetail",
		data:param,
		selectItem: selectItem,
		leafNode:"sp"
	});
	
}
function selectItem(node){
	var type = $("#type").val();
	var params = "spcode="+node.id+"&samplegroupcode="+node.samplegroupcode+"&type="+type;
	
	$("#ordList").attr("src","${pageContext.request.contextPath}/lims/tasks/ordSchedule/getSampleProgramIfo?"+params)
	
} 
</script>
</body>
</html>