<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
     <%@include file="/common/header/meta.jsp"%>
    <title>添加异常检查</title>
    <%@include file="/common/header/jquery.jsp"%>
    <%@include file="/common/header/layui.jsp"%>
     <%@include file="/common/header/layer.jsp"%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/skins/css/laydateneed.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/skins/js/laydate.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/skins/js/laydate.dev.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/skins/js/common.js"></script>
   <%--  <script type="text/javascript" src="${pageContext.request.contextPath}/skins/plugins/formSelects-v3.js"></script> --%>
</head>
<body>
<form class="layui-form"  action="${pageContext.request.contextPath}/lims/dataSearch/daily/addCheckIn" style="padding:20px 30px 20px 0px;" method="post">
	<c:forEach  items="${list}" var="v" varStatus="status">
    <input type="hidden" name="origrec" value="${origrec}"/>
	<div class="layui-form-item layui-form-text">
		<label class="layui-form-label">异常原因</label>
		<div class="layui-input-block">
			 <textarea  name="remark" id="remark" class="layui-textarea">${v.remark}</textarea>
		</div>
	</div>
	<div class="layui-form-item layui-form-text">
		<label class="layui-form-label">整改措施</label>
		<div class="layui-input-block">
			<textarea   name="method" id="method" class="layui-textarea">${v.updatemethod}</textarea>
		</div>
	</div>
	<div class="layui-form-item layui-form-text">
		<label class="layui-form-label">整改效果</label>
		<div class="layui-input-block" >
			<textarea  name="check" id="check" class="layui-textarea">${v.resultCheck}</textarea>
		</div>
	</div>
	<div class="layui-form-item layui-form-text">
		<label class="layui-form-label">是否影响罐区</label>
		<div class="layui-input-block">
            <c:choose>
              <c:when test="${v.effectPlant=='否' }">
                 <input type="radio" name="effect" value="是" title="是" />
                 <input type="radio" name="effect" value="否" title="否"  checked/>
                 
              </c:when>
              <c:when test="${v.effectPlant=='是' }">
                 <input type="radio" name="effect" value="是" title="是"  checked/>
                 <input type="radio" name="effect" value="否" title="否" />
              </c:when>
              <c:otherwise>
			     <input type="radio" name="effect" value="是" title="是" checked/>
                 <input type="radio" name="effect" value="否" title="否" />
			  </c:otherwise>
            </c:choose>
		</div>
        
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">实施人</label>
		<div class="layui-input-block">
			<input type="text" name="user" value="${v.checkUsr}" class="layui-input" />
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">整改时间</label>
		<div class="layui-input-block">
			<input type="text" name="checkdate" id="checkdate" value="${v.checkdate}" autocomplete="off" class="layui-input"/>
		</div>
		
	</div>
	</c:forEach>
	<div class="layui-form-item">
	    <div class="layui-input-block">
	      <button class="layui-btn" type="submit" lay-submit lay-filter="formDemo">立即提交</button>
	      <button type="reset" class="layui-btn layui-btn-primary">重置</button>
	    </div>
  </div>
</form>

</body>
<script>
    
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	
	layui.use('form', function(){
		var form = layui.form;
		//监听提交
		form.on('submit(formDemo)', function(data){
		  submitForm({url:'${pageContext.request.contextPath}/lims/dataSearch/daily/addCheckIn',data:$("form").serialize(),success:addSuccess});
		  return false;
		 });
	});
	laydate({
        elem: '#checkdate'
        ,type: 'datetime'
    });

	function addSuccess(data){
		if(data.code=="SUCCESS"){
			layer.msg('操作成功！',{
			time: 1000
			},function(){
				parent.window.location.href="${pageContext.request.contextPath}/lims/dataSearch/daily/checkOOSData?origrec="+data.message;
				parent.layer.close(index);
				parent.receiveReturn(index);
			}
		  )			
		}else{
			layer.alert(data.message, {icon: 1});
		}
	}
	
</script>

</html>


			

