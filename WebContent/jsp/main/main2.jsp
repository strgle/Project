<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@include file="/common/header/meta.jsp"%>
    <title>山东京博石油化工有限公司LIMS</title>
    <%@include file="/common/header/jquery.jsp"%>
    <%@include file="/common/header/font-awesome.jsp"%>
    <%@include file="/common/header/frameWork.jsp"%>
    <%@include file="/common/header/layui.jsp"%> 
 
</head>

<body>

<div class="header">
	<div class="logo">${sessionScope.dept}LIMS</div>
	<!-- 头部区域 -->
	<!-- <div class="side-toggle">
	  <i class="fa fa-bars" aria-hidden="true"></i>
	</div>   -->
 <!-- 提示信息开始 -->
   <ul class="layui-nav layui-layout-right">
   <li class="layui-nav-item">
     <a href="javascript:void(0)"><img src="${pageContext.request.contextPath}/common/img/user.png" class="layui-nav-img"/>${sessionScope.fullName}</a>
    </li>
    <li class="layui-nav-item">
     <a href="javascript:void(0)"><img src="${pageContext.request.contextPath}/common/img/role.png" class="layui-nav-img"/>${sessionScope.role}</a>
    </li>
     <li class="layui-nav-item">
     <a href="javascript:void(0)" onclick="change('${sessionScope.deptlist}')"><i class="fa fa-superpowers" aria-hidden="true"></i> 部门切换</a>
     
    </li>
    
    <li class="layui-nav-item">
     <a href="${pageContext.request.contextPath}/framework/loginOut"><i class="fa fa-sign-out" aria-hidden="true"></i> 注销</a>
    </li>
    
   
   </ul>
   <!-- 提示信息结束 --> 
</div>

<!-- 左侧菜单开始 -->
<div class="side">
	<ul class="nav-tree">
	
	</ul>
</div>
<!-- 左侧菜单结束 -->

<!-- 右侧主体部分开始 -->
<div class="mainContent">
	<div class="title">当前位置&nbsp;:&nbsp;<label>我的首页</label></div>
	<div class="content">
		<iframe src="about:blank"></iframe>
	</div>
</div>
<!-- 右侧主体部分结束-->


</body>
<script type="text/javascript">
 
   function change(e)
  {
	   
	  var list ="";
	  var deptlist = e.split(",");
	  list +='<ul class="site-dir layui-layer-wrap" style="display: block;">';
	  for (var i = 0; i < deptlist.length; i++) {
		   
		   
           list +='<li style="display: block;"><a style="display: block; line-height: 1.35em;padding: 4px 20px;font-size: 1.0em;" onclick="deptChange(\''+deptlist[i]+'\')">' + deptlist[i] + '</a></li>';
           
      }
	  list +='</ul>';
	  layui.use(['layer'], function(){
		  var layer = layui.layer;
		  
		  //layer.msg('Hello World');
		  layer.open({
			  type: 1,
			  title:'部门列表',
			  content: list
			});
		});
	 
  } 
   
  function deptChange(e){
	// pageContext.request
	var data = "username=${sessionScope.usrNam}&&dept="+e+"&&role=${sessionScope.role}";
	$.ajax({
        url: "${pageContext.request.contextPath}/framework/loginOn",
        data: data,
        dataType: "json",
        type: "get",
        success: function (data) {
      	 
      	  location.href ="${pageContext.request.contextPath}/jsp/main/main.jsp";
           
        }
    }); 
  }
   
</script>
</html>

			

