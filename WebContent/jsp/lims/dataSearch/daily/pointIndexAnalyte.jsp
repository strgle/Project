<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>${title}</title>
<%@include file="/common/header/meta.jsp"%>
<%@include file="/common/header/jquery.jsp"%>
<%@include file="/common/header/font-awesome.jsp"%>
<%@include file="/common/header/bootstrap.jsp"%>
<%@include file="/common/header/default.jsp"%>
<%@include file="/common/header/laydate.jsp"%>
<%-- <link rel="stylesheet" href="${pageContext.request.contextPath}/skins/tree/layui/css/layui.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/layui/layui.js"></script> --%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/fuelux/css/ace.min.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/fuelux/js/fuelux.tree.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/layer/layer.css"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/layer/layer.js"></script>
<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/skins/tree/layui-xtree/layui-xtree.js"></script> --%>
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
	
	<div class="panel panel-default" style="margin-right: -2PX;">
   <div class="panel-heading">车间列表</div>
   <div class="panel-body" style="padding-top: 0px;padding-bottom: 0px;" >
       <div id="treeContent" style="overflow-y: scroll;width: 250px;padding-right: 2px;"> 
       
        <div id="tree1" class="tree tree-unselectable" style="margin-left:8px;margin-top: 0PX;"></div>

      </div>
   </div>
  </div>
</div> 
<!-- class="tree tree-unselectable" style="margin-left:8px;margin-top: 0PX;" -->
<div class="articleDiv">
	<iframe id="ordList"  name="ordList" src="about:blank" style="border: 0px;width: 100%;height:100%;overflow:auto;"></iframe>
</div>
<script type="text/javascript">
$(function(){
	query();
});
$(window).on('resize', function() {
	$("#treeContent").height($("body").height()-55);
}).resize();
laydate.render({
	elem: '#startDate',
	btns: [],
});
laydate.render({
	elem: '#endDate',
	btns: [],
});
function selectItem(node){
	
	var params = $.param(node);
	layer.load(0, {
        shade: [0.1, '#fff'] 
    });
    $("#ordList").attr("src","${pageContext.request.contextPath}/lims/dataSearch/daily/samplelistAnalyte?"+params);
    layer.closeAll('loading'); 	
}

function query(){
	layer.load(0, {
        shade: [0.1, '#fff'] 
    });
	 $('#tree1').tree({
		url:"${pageContext.request.contextPath}/api/lims/areaPlants/matAnalyte",
		selectItem:selectItem,
		multiSelect: true,
		leafNode:"mat"
	}); 
	 layer.closeAll('loading'); 	
	 /* layui.use(['form'], function () {
      	 var form = layui.form;
     	 var tree1 = new layuiXtree({
              elem: 'tree1'                  //必填三兄弟之老大
              , form: form                    //必填三兄弟之这才是真老大
              , data: '${pageContext.request.contextPath}/api/lims/areaPlants/matAnalyte' //必填三兄弟之这也算是老大
              , isopen: false  //加载完毕后的展开状态，默认值：true
              , ckall: true    //启用全选功能，默认值：false
              , click: function (data) {  //节点选中状态改变事件监听，全选框有自己的监听事件
                  console.log(data.elem); //得到checkbox原始DOM对象
                  console.log(data.elem.checked); //开关是否开启，true或者false
                  console.log(data.value); //开关value值，也可以通过data.elem.value得到
                  console.log(data.othis); //得到美化后的DOM对象
              }
          });
	 }); */
	
}

</script>
</body>
</html>