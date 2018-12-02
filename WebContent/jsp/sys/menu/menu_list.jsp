<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@include file="/common/header/meta.jsp"%>
    <title>山东京博石油化工有限公司LIMS</title>
    <%@include file="/common/header/jquery.jsp"%>
    <%@include file="/common/header/layui.jsp"%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/skins/js/tableTree.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/sys/menu/js/menu.js"></script>
	
</head>
<style type="text/css">
 
	th{
		white-space: nowrap;
	}
	
	tr.selected{
		background-color: #f5f5f5;
	}
	
	tr:HOVER{
		background-color: #f5f5f5;
	}
	
	td{
		overflow:hidden;
		white-space:nowrap;
		text-overflow:ellipsis;
		-o-text-overflow:ellipsis;
		-moz-text-overflow: ellipsis;
		-webkit-text-overflow: ellipsis;
		max-width: 150px;
	}
	
	
	 
</style>
<body style="padding:0 10px;">
<div class="table-list">
		<table class="layui-table layui-form" id="tableTree"  lay-size="sm">
			<colgroup>
				<col width="50">
				<col width="150">
				<col width="250">
				<col width="250">
				<col width="200">
				<col>
			</colgroup>
			<thead>
				<tr>
					<th data-column="code">选择</th>
					<th data-column="name">菜单名称</th>
					<th data-column="url">菜单链接</th>
					<th data-column="treeauth">权限说明</th>
					<th data-column="option">操作</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${menuList}" var="menu" varStatus="status">
              <tr>
				<c:choose>
					<c:when test="${menu.parentId=='-1'}">
						<tr data-tb-pid="${menu.parentId}" data-tb-id="${menu.id }">
					</c:when>
					<c:otherwise>
						<tr data-tb-pid="${menu.parentId}" data-tb-id="${menu.id }" style="display: none;">
					</c:otherwise>
				</c:choose>
				
					<td>${menu.code}</td>
					<td>${menu.name}</td>
					<td>${menu.url}</td>
					<td style="white-space: nowrap;cursor:Pointer;" onclick="getRoles('${menu.id}','${menu.treeauth}')">${menu.treeauth}</td>
					
					
					<td>
						  <button class="layui-btn layui-btn-xs"  onclick="addMenu('${menu.id}')" >
						    <i class="layui-icon">&#xe654;</i>
						  </button>
						  <button class="layui-btn layui-btn-xs" onclick="editMenu('${menu.id}')">
						    <i class="layui-icon">&#xe642;</i>
						  </button>
						  <button class="layui-btn layui-btn-xs" onclick="delMenu('${menu.id}')">
						    <i class="layui-icon">&#xe640;</i>
						  </button>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
</div>
</body>
<script type="text/javascript">
	$(document).ready(function(){
		TableTree.init();
	});
	/* layui.use('table', function(){
		var table = layui.table;
		//转换静态表格
		/* table.init('demo', {
		  height: 315 //设置高度
		  ,limit: 10 //注意：请务必确保 limit 参数（默认：10）是与你服务端限定的数据条数一致
		  //支持所有基础参数
		}) 
	}); */
	function getRoles(e,roles){
		var roleArr = roles.split(",");
		
		$.ajax({type: 'POST',
			async: false,
			dataType:"json",
			url:"${pageContext.request.contextPath}/sys/menu/getRoles",
			success: function(data){
				
				var _html = '<ul id="list">';
				for(var i=0;i<data.length;i++){
					
					if(roleArr.indexOf(data[i].role)>-1){
						_html = _html + '<li><label ><input type="checkbox" checked="checked" name="treeauth" value="'+data[i].role+'"><span>'+data[i].role+'</span></label></li>';
					} 
					else{
						_html = _html + '<li><label ><input type="checkbox" name="treeauth" value="'+data[i].role+'"><span>'+data[i].role+'</span></label></li>';
					}
					
				}
				_html +='</ul>';
				 layui.use(['layer'], function(){
					var layer = layui.layer;
     				layer.open({
     					  type: 1,
     					  title:'角色列表',
     					  content: _html,
     					  btn: ['确定'],
     					  yes:function(index,layero){
     						
      						var treeauthList ="";
      						$.each($('input:checkbox:checked'),function(){
      							treeauthList +=$(this).val()+',';
      			            });
      						layer.close(index);
        					var data ="treeauth="+treeauthList+"&&id="+e;
        					
        					$.ajax({
        				        url: "${pageContext.request.contextPath}/sys/menu/updateTreeauth",
        				        data: data,
        				        dataType: "json",
        				        type: "get",
        				        success: function (data) {
        				        	layer.msg('更新成功');
        				        	
        				        }
        				    }); 
     						
     					  }
     					});
				 });
				
			}
		});
		
	}
	
</script>
</html>


			

