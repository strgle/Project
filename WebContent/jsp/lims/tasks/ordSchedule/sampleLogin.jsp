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
<%@include file="/common/header/layer.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/links/layui/css/layuiadmin.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/links/layui/admin.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/layui/layuiadmin.js"></script>

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
<title>Insert title here</title>
</head>
<body>

 <div class="layui-tab layui-tab-brief" lay-filter="test1">
  <ul class="layui-tab-title">
    <li class="layui-this" lay-id="RAWMAT">原辅料检验</li>
    <li lay-id="SCHEDULED">中控检验</li>
    <li lay-id="PRODUCT">成品检验</li>
    <li lay-id="OTHER">其他检验</li>
  </ul>
  <div class="layui-tab-content">
     <div class="layui-tab-item layui-show">
        <div class="articleDiv">
            <iframe  name="ordList" src="${pageContext.request.contextPath}/lims/tasks/ordSchedule/sampleGroup?type=RAWMAT" style="border: 0px;width: 100%;height:100%;overflow:auto;"></iframe>
        </div>
     </div>
    <div class="layui-tab-item">
        <div class="articleDiv">
            <iframe  name="ordList2" src="${pageContext.request.contextPath}/lims/tasks/ordSchedule/sampleGroup?type=SCHEDULED" style="border: 0px;width: 100%;height:100%;overflow:auto;"></iframe>
        </div> 
    </div>
    <div class="layui-tab-item">
        <div class="articleDiv">
            <iframe  name="ordList3" src="${pageContext.request.contextPath}/lims/tasks/ordSchedule/sampleGroup?type=PRODUCT" style="border: 0px;width: 100%;height:100%;overflow:auto;"></iframe>
        </div> 
    </div>
    <div class="layui-tab-item">
       <div class="articleDiv">
            <iframe  name="ordList4" src="${pageContext.request.contextPath}/lims/tasks/ordSchedule/sampleGroupOTHER" style="border: 0px;width: 100%;height:100%;overflow:auto;"></iframe>
        </div>
    </div>
     
  </div>
</div>

<script>
//注意：选项卡 依赖 element 模块，否则无法进行功能性操作
layui.use('element', function(){
  var element = layui.element;

  //获取hash来切换选项卡，假设当前地址的hash为lay-id对应的值
  var layid = location.hash.replace(/^#test1=/, '');

  element.tabChange('test1', layid); //假设当前地址为：http://a.com#test1=222，那么选项卡会自动切换到“发送消息”这一项
  
  //监听Tab切换，以改变地址hash值
  element.on('tab(test1)', function(){
    location.hash = 'test1='+ this.getAttribute('lay-id');
  });
});
</script>
 
</body>
</html>