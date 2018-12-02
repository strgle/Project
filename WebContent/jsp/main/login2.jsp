<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>山东京博石油化工有限公司</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/skins/links/layui/css/layui.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/layui/layui.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/jQuery-2.2.0.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/sys/menu/js/login.js"></script>
    <script type="text/javascript">
	
	if(self!=top){
		top.location.href="${pageContext.request.contextPath}/jsp/main/login.jsp";
	}
	
	</script>

<style type="text/css">
	.x-top{height:70px;line-height:70px;font-size:21px}
	
	.x-bg{background:#393d49}
	.x-text-white{color:#fff}
	
	.x-h1{margin-bottom:20px;line-height:60px;padding-bottom:10px;color:#393d49;border-bottom:1px solid #ddd;font-size:28px;font-weight:300}
	
	.x-right{float:right;color: #FF0000;font-weight: bold;}.x-left{float:left}
	
	.x-layout{width:100%}
	.login-top{
		position: relative;
		width:980px;
		margin: 0 auto;
	}
	
</style>
</head>
<body>
<!-- action="${pageContext.request.contextPath}/framework/login" -->
<div class="x-top x-bg">
    <div class="login-top">
    	<div style="width: 50%;float: left;"><strong class="x-text-white">山东京博控股股份有限公司</strong></div>
    	<div style="width: 50%;float: left;text-align: right;"> <strong class="x-text-white">京博${pageContext.request.contextPath}</strong></div>
   </div>
</div>
<div style="position:absolute;width:100%;height:420px;top:50%;margin-top:-190px;background: url(${pageContext.request.contextPath}/jsp/main/images/login.jpg) no-repeat center center;">
	<form method="post" class="layui-form" action="${pageContext.request.contextPath}/framework/login" style="width:400px;height:250px;position:absolute;top:50%;left:50%;margin-left:-200px;margin-top:-80px;">
	    <div class="layui-form-item layui-form-pane">
	        <label class="layui-form-label">用户名：</label>
	        <div class="layui-input-block">
	            <input id="usrname" type="text" class="layui-input" name="userName" placeholder="请输入您的用户名" autocomplete="on" lay-verify="required" style="text-transform:uppercase;"/>
	        </div>
         
	    </div>
	    <div class="layui-form-item layui-form-pane" style="margin-top: 30px;">
	        <label class="layui-form-label">密&nbsp;&nbsp;&nbsp;&nbsp;码：</label>
	        <div class="layui-input-block">
	            <input id="password" type="password" class="layui-input" name="password" placeholder="请输入密码" lay-verify="required" autocomplete="off" />
	        </div>
	    </div>
	    <div class="layui-form-item layui-form-pane" style="margin-top: 30px;">
	        <label class="layui-form-label">部&nbsp;&nbsp;&nbsp;&nbsp;门：</label>
	        <div class="layui-input-block">
	           <select id="dept" lay-verify="required" >
	            </select>
	        </div>
	    </div>
	    
	    <div class="layui-form-item" style="margin-top: 30px;">
	        <button class="layui-btn x-layout" lay-submit lay-filter="login">登录</button>
	    </div>
	    <div class="layui-form-item">
	        <div class="x-left">
	            &nbsp;
	        </div>
	        <div class="x-right">
	     		${error}
	        </div>
	    </div>
	</form>
	
</div>

<div style="position:absolute;bottom:0px;left:0px;right:0px;height:30px;width: 100%;text-align: center;background-color: #EEEEEE;line-height: 30px;color: #666;font-size: 12px;">
	技术支持：北京三维天地科技有限公司
	
</div>
</body>
<script type="text/javascript">
  
	layui.use('form', function(){
	
	});  
	var passwordDoc = document.getElementById('password');
	var usrnameDoc = document.getElementById('usrname');
	//获取部门列表
	passwordDoc.addEventListener('mouseleave', function (event) {
		var usrname  = usrnameDoc.value;
		var password = passwordDoc.value;
		var deptlist = getDeptList(usrname,password);
		//alert(deptlist);
	});
	
	passwordDoc.addEventListener('keypress', function (event) {
		return;
	});
	
	
</script>
</html>