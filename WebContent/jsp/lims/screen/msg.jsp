<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>生产流程体系</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/jquery.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/layui/css/layui.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/layui/layui.js"></script>
<style type="text/css">
	html,body{
		padding: 10px;
	}
</style>
</head>
<body>

<fieldset class="layui-elem-field layui-field-title" style="margin-top:10px;">
  <legend>实验室大屏通知设置</legend>
</fieldset>
 
<form class="layui-form layui-form-pane" action="${pageContext.request.contextPath}/lims/screen/msg/save" method="post">
	<div class="layui-form-item layui-form-text">
	    <label class="layui-form-label">通知1</label>
	    <div class="layui-input-block">
	      <textarea name="msg1" placeholder="请输入内容" class="layui-textarea">${msg['msg1'] }</textarea>
	    </div>
	 </div>
	 <div class="layui-form-item layui-form-text">
	    <label class="layui-form-label">通知2</label>
	    <div class="layui-input-block">
	      <textarea name="msg2" placeholder="请输入内容" class="layui-textarea">${msg['msg2'] }</textarea>
	    </div>
	 </div>
	 <div class="layui-form-item layui-form-text">
	    <label class="layui-form-label">通知3</label>
	    <div class="layui-input-block">
	      <textarea name="msg3" placeholder="请输入内容" class="layui-textarea">${msg['msg3'] }</textarea>
	    </div>
	 </div>
	 <div class="layui-form-item">
	      <button class="layui-btn" lay-submit="" lay-filter="demo1">立即提交</button>
	      <button type="reset" class="layui-btn layui-btn-primary">重置</button>
	 </div>
</form>

</body>

</html>