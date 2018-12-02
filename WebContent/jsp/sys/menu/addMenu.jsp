<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
     <%@include file="/common/header/meta.jsp"%>
    <title>京博橡胶LIMS</title>
    <%@include file="/common/header/jquery.jsp"%>
    <%@include file="/common/header/layui.jsp"%>
     <%@include file="/common/header/layer.jsp"%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/skins/plugins/selectTree.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/skins/js/common.js"></script>
   <%--  <script type="text/javascript" src="${pageContext.request.contextPath}/skins/plugins/formSelects-v3.js"></script> --%>
</head>
<body>
<form class="layui-form"  action="${pageContext.request.contextPath}/sys/menu/addMenu" style="padding:20px 30px 20px 0px;" method="post">
	<input type="hidden" name="id" value="${id}"/>
	<div class="layui-form-item">
		<label class="layui-form-label">上级菜单</label>
		<div class="layui-input-block">
			<select name="parentId" lay-verify="" id="selectTree">
				<option value="-1" data-level="-1">顶级菜单</option>
				<c:forEach items="${menuList}" var="m" >
					<c:choose>
						<c:when test="${m.id==parentId }">
							<option value="${m.id }" data-level="${m.levels }" selected="selected">${m.name }</option>
						</c:when>
						<c:otherwise>
							<option value="${m.id }" data-level="${m.levels }">${m.name }</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		</div>
	</div>
	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label">菜单编号</label>
			<div class="layui-input-inline">
				<input type="text" name="code" class="layui-input" value="${code}"/>
			</div>
		</div>
		<div class="layui-inline" style="margin-right: 0px;">
			<label class="layui-form-label">菜单名称</label>
			<div class="layui-input-inline" style="margin-right: 0px;">
				<input type="text" name="name" class="layui-input" lay-verify="required" value="${name}"/>
			</div>
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">菜单链接</label>
		<div class="layui-input-block">
			<input type="text" name="url" class="layui-input" value="${url}"/>
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">菜单权限</label>
		<div class="layui-input-block">
			<input type="text" name="treeauth" class="layui-input" value="${treeauth}"/>
		</div>
        
	</div>
	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label">菜单状态</label>
			<div class="layui-input-inline">
				<c:choose>
					<c:when test="${status=='1' }">
						<input type="radio" name="status" value="1" title="启用" checked/>
						<input type="radio" name="status" value="0" title="停用" />
					</c:when>
					<c:when test="${status=='0' }">
						<input type="radio" name="status" value="1" title="启用" />
						<input type="radio" name="status" value="0" title="停用" checked/>
					</c:when>
					<c:otherwise>
						<input type="radio" name="status" value="1" title="启用" checked/>
						<input type="radio" name="status" value="0" title="停用" />
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="layui-inline" style="margin-right: 0px;">
			<label class="layui-form-label">菜单排序</label>
			<div class="layui-input-inline" style="margin-right: 0px;">
				<input type="text" name="sort" class="layui-input" value="${sort}"/>
			</div>
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">显示图标</label>
		<div class="layui-input-inline">
			<input type="text" name="icon" class="layui-input" value="${icon}"/>
		</div>
		<div class="layui-input-inline" style="width:55px;text-align: center;border: 1px solid #C9C9C9;">	
			<i class="fa" style="line-height: 36px;">&nbsp;</i>
		</div>
		<div class="layui-input-inline" style="width:80px;">	
			<input  type="button" value="选择图标" class="layui-btn layui-btn-primary"/>
		</div>
	</div>
	
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
		  submitForm({url:'${pageContext.request.contextPath}/sys/menu/addMenu',data:$("form").serialize(),success:addSuccess});
		  return false;
		 });
	});
	

	function addSuccess(data){
		if(data.code=="SUCCESS"){
			layer.msg('操作成功！',{
						time: 1000
						},function(){
							parent.layer.close(index);
							parent.receiveReturn(index);
						}
					  )			
		}else{
			layer.alert(data.message, {icon: 1});
		}
	}
	
	selectTree({elem:'#selectTree'});
	
	
	
</script>

</html>


			

