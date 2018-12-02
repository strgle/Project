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
<form class="layui-form layui-form-pane" action="${pageContext.request.contextPath}/statistics/jbxj/dailySetting/update" id="form" method="post">
  <input type="hidden" name="id" value="${analyte.id}"/>
  <input type="hidden" name="matcode" value="${analyte.matcode}"/>
  <div class="layui-form-item" pane>
    <label class="layui-form-label">测试代码</label>
    <div class="layui-input-block">
        <input type="text" name="testcode" required  lay-verify="required" placeholder="测试代码" autocomplete="off" class="layui-input" value="${analyte.testcode }">
    </div>
  </div>
  <div class="layui-form-item" pane>
    <label class="layui-form-label">分析项</label>
    <div class="layui-input-block">
        <input type="text" name="analyte" required  lay-verify="required" placeholder="分析项" autocomplete="off" class="layui-input" value="${analyte.analyte }">
    </div>
  </div>
  <div class="layui-form-item" pane>
    <label class="layui-form-label">分析项别名</label>
    <div class="layui-input-block">
        <input type="text" name="sinonym" required  lay-verify="required" placeholder="分析项别名" autocomplete="off" class="layui-input" value="${analyte.sinonym }">
    </div>
  </div>
  
  <div class="layui-form-item" pane>
    <label class="layui-form-label">关键指标</label>
    <div class="layui-input-block">
    	<c:choose>
    		<c:when test="${analyte.isMonitor=='1' }">
    			<input type="radio" name="isMonitor" value="1" title="是" checked="checked">
      			<input type="radio" name="isMonitor" value="0" title="否">
    		</c:when>
    		<c:when test="${analyte.isMonitor=='0' }">
    			<input type="radio" name="isMonitor" value="1" title="是">
      			<input type="radio" name="isMonitor" value="0" title="否" checked="checked">
    		</c:when>
    		<c:otherwise>
    			<input type="radio" name="isMonitor" value="1" title="是">
      			<input type="radio" name="isMonitor" value="0" title="否">
    		</c:otherwise>
    	</c:choose>
    </div>
  </div>
 
 <div class="layui-form-item" pane>
    <label class="layui-form-label">巡检项</label>
    <div class="layui-input-block">
    	<c:choose>
    		<c:when test="${analyte.isXj=='1' }">
    			<input type="radio" name="isXj" value="1" title="是" checked="checked">
      			<input type="radio" name="isXj" value="0" title="否">
    		</c:when>
    		<c:when test="${analyte.isXj=='0' }">
    			<input type="radio" name="isXj" value="1" title="是">
      			<input type="radio" name="isXj" value="0" title="否" checked="checked">
    		</c:when>
    		<c:otherwise>
    			<input type="radio" name="isXj" value="1" title="是">
      			<input type="radio" name="isXj" value="0" title="否">
    		</c:otherwise>
    	</c:choose>
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