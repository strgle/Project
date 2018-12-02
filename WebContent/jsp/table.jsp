<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/header/meta.jsp"%>
<title>Insert title here</title>
<%@include file="/common/header/jquery.jsp"%>
<%@include file="/common/header/default.jsp"%>
<%@include file="/common/header/layui.jsp"%>
</head>
<body>
<div id="tablediv">
<table class="layui-hide" id="test"></table>
              
 </div>    
 <input type="button" value="选择" onclick="show()" />
 <textarea rows="10" cols="200" id="tablearea"></textarea>     
<script src="layui.js" charset="utf-8"></script>
<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 --> 
 
<script>
layui.use('table', function(){
  var table = layui.table;
  
  table.render({
    elem: '#test'
    ,url:'${pageContext.request.contextPath}/demo/table'
    ,width: 892
    ,height: 332
    ,cols: [[
      {field:'id', width:80, title: 'ID', sort: true, fixed: 'left'}
      ,{field:'username', width:80, title: '用户名'}
      ,{field:'sex', width:80, title: '性别', sort: true}
      ,{field:'city', width:80, title: '城市'}
      ,{field:'sign', width: 219, title: '签名'}
      ,{field:'experience', width:80, title: '积分', sort: true}
      ,{field:'score', width:80, title: '评分', sort: true}
      ,{field:'classify', width:80, title: '职业'}
      ,{field:'wealth', width:120, title: '财富', sort: true, fixed: 'right'}
    ]]
    ,page: true
  });
});

function show(){
	var html = $("#tablediv").html();
	$("#tablearea").val(html);
}
</script>
</body>
</html>