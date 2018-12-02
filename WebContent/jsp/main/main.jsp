<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@include file="/common/header/meta.jsp"%>
    <title>山东京博石油化工有限公司LIMS</title>
    <%@include file="/common/header/jquery.jsp"%>
    <%@include file="/common/header/font-awesome.jsp"%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/skins/layui/css/layui.css">
   
    <link rel="stylesheet" href="${pageContext.request.contextPath}/common/main/main.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/skins/layui/layui.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jsp/main/js/index.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/jquery.min.js"></script>
    <%-- <%@include file="/common/header/frameWork.jsp"%> --%>
   
</head>

<body>
<div class="layui-layout layui-layout-admin" id="layui_layout_admin">
<div class="layui-header">
 <div class="layui-main">
  <div class="admin-logo-box">
	<div class="admin-side-toggle">
     <i class="fa fa-bars" aria-hidden="true"></i>
    </div>
	<a class="logo" style="left: 0;" href="javascript::">
     <span style="font-size: 12px;">${sessionScope.dept}</span>
    </a>
    
  </div>
 <!-- 提示信息开始 -->
   <ul class="layui-nav">
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
     <a href="javascript:void(0)" onclick="qiehuan()"><i class="fa fa-superpowers" aria-hidden="true"></i> 主题切换</a>
    </li>
    <li class="layui-nav-item">
     <a href="${pageContext.request.contextPath}/framework/loginOut"><i class="fa fa-sign-out" aria-hidden="true"></i> 注销</a>
    </li>
    
   </ul>
   <!-- 提示信息结束 --> 
   </div>
</div>

<!-- 左侧菜单开始 -->
<div class="layui-side" id="admin-side">
<div class="layui-side-scroll" lay-filter="side">
	<ul class="layui-nav layui-nav-tree beg-navbar" id="admin-navbar-side">
	
	</ul>
</div>
</div>
<!-- 左侧菜单结束 -->

<!-- 右侧主体部分开始 -->
<div class="layui-body" id="admin-body">
        
		<div class="layui-tab layui-tab-card" id="admin-tab" lay-filter="demo" lay-allowClose="true">
			
            <ul class="layui-tab-title">
				<li class="layui-this" id="admin-home" lay-id="https://www.baidu.com">首页</li>
			</ul>
			<div class="layui-tab-content">
				<div class="layui-tab-item layui-show">
					<iframe data-id='0' src="${pageContext.request.contextPath}/jsp/main/home.jsp"></iframe>
				</div>
			</div>
		</div>    
	</div>
<!-- 右侧主体部分结束-->
<div class="layui-footer layui-admin-foot" id="admin-footer">
  山东京博石油化工有限公司
 </div> 
</div>
</body>
<script type="text/javascript">
  $(function(){
	  qiehuan();
  });
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
  function qiehuan(){
		$("#layui_layout_admin").toggleClass("layui-layout-admin_lanse");
  }
</script>
</html>

			

