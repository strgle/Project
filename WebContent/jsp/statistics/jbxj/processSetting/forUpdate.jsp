<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<%@include file="/common/header/meta.jsp"%>
<%@include file="/common/header/jquery.jsp" %>
<%@include file="/common/header/layui.jsp"%>
<%@include file="/common/header/default.jsp"%>
</head>
<body style="background-color: #FFF;padding: 20PX;">     
<form class="layui-form layui-form-pane" action="${pageContext.request.contextPath}/statistics/jbxj/processSetting/update" id="form" method="post">
  <input type="hidden" name="id" value="${keyPoint.id}"/>
  <input type="hidden" name="parentId" value="${keyPoint.parentId}"/>
   <div class="layui-form-item" pane>
    <label class="layui-form-label">采样点</label>
    <div class="layui-input-block">
        <input type="text" name="pointId" required  lay-verify="required" placeholder="采样点" autocomplete="off" class="layui-input" value="${keyPoint.pointId }">
    </div>
  </div>
   <div class="layui-form-item" pane>
    <label class="layui-form-label">样品代码</label>
    <div class="layui-input-block">
        <input type="text" name="matcode" required  lay-verify="required" placeholder="样品代码" autocomplete="off" class="layui-input" value="${keyPoint.matcode }">
    </div>
  </div>
  <div class="layui-form-item" pane>
    <label class="layui-form-label">测试代码</label>
    <div class="layui-input-block">
        <input type="text" name="testcode" required  lay-verify="required" placeholder="测试代码" autocomplete="off" class="layui-input" value="${keyPoint.testcode }">
    </div>
  </div>
  <div class="layui-form-item" pane>
    <label class="layui-form-label">分析项</label>
    <div class="layui-input-block">
        <input type="text" name="analyte" required  lay-verify="required" placeholder="分析项" autocomplete="off" class="layui-input" value="${keyPoint.analyte }">
    </div>
  </div>
  <div class="layui-form-item" pane>
    <label class="layui-form-label">分析项别名</label>
    <div class="layui-input-block">
        <input type="text" name="sinonym" required  lay-verify="required" placeholder="分析项别名" autocomplete="off" class="layui-input" value="${keyPoint.sinonym }">
    </div>
  </div>
  
 <div class="layui-form-item">
    <div class="layui-input-block">
        <button class="layui-btn" lay-submit lay-filter="formDemo">提交</button>
    	<button type="reset" class="layui-btn layui-btn-primary">重置</button>
    </div>
  </div>
</form>
</body>
<script type="text/javascript">
layui.use(['form'], function(){
	var form = layui.form;
});
</script>
</html>