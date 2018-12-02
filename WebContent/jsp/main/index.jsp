<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@include file="/common/header/meta.jsp"%>
    <title>产品管理系统</title>
    <%@include file="/common/header/jquery.jsp"%>
    <%@include file="/common/header/font-awesome.jsp"%>
    <%@include file="/common/header/frameWork.jsp"%> 
</head>
<body>
<div class="header">
	    <!-- 头部区域 -->
	    <div class="side-toggle">
	    	<i class="fa fa-bars" aria-hidden="true"></i>
	    </div>
	    
	    <ul class="nav">
	      <li class="nav-item curNav"><label>商品管理</label></li>
	      <li class="nav-item"><label>页面设置</label></li>
	      <li class="nav-item"><label>用户管理</label></li>
	      <li class="nav-item"><label>其它系统</label></li>
	    </ul>
</div>
<div class="side">
	<div class="side-msg">
		<div class="logo">千云鲜蔬</div>
		<div class="msg">天齐奥东花园</div>
	</div>
	<ul class="nav-tree">
        <li class="nav-item">
          <label data-src="sys/sys">系统管理</label>
          <dl class="nav-child">
          	<dd><label data-src="sys/func/group">功能分组</label></dd>
            <dd class="selectItem"><label data-src="sys/func">功能资源</label></dd>
            <dd><label data-src="sys/roles">角色管理</label></dd>
            <dd><label data-src="sys/menus">菜单管理</label></dd>
            <dd><label data-src="sys/users">用户管理</label></dd>
          </dl>
        </li>
        <li class="nav-item">
          <label>商城管理</label>
          <dl class="nav-child">
            <dd><label>首页信息</label></dd>
            <dd><label>在售商品</label></dd>
            <dd><label>首页信息</label></dd>
            <dd><label>在售商品</label></dd>
          </dl>
        </li>
        <li class="nav-item"><label>云市场</label></li>
        <li class="nav-item"><label>发布商品</label></li>
      </ul>
</div>
<div class="mainContent">
	<div class="title">当前位置&nbsp;:&nbsp;<label>我的首页</label></div>
	<div class="content">
		<iframe src="http://www.baidu.com"></iframe>
	</div>
</div>

</body>
</html>


			

