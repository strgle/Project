<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>中控巡检点设置</title>
<%@include file="/common/header/jquery.jsp"%>
<%@include file="/common/header/font-awesome.jsp"%>
<%@include file="/common/header/bootstrap.jsp"%>
<%@include file="/common/header/layui.jsp"%>
<%@include file="/common/header/default.jsp"%>
 <style type="text/css">
	html,body{
		background-color: #F5F5F5;
		overflow: hidden;
		height: 100%;
	}
	th{
		white-space: nowrap;
	}
	tr.selected{
		background-color: #f5f5f5;
	}
	
	tr:HOVER{
		background-color: #f5f5f5;
	}
	ul{
		margin:0px;
		padding: 0px;
	}
	ul li{
		padding:0px 10px;
		height: 35px;
		margin:0px;
	}
	
	ul li label span{
		font-weight: normal;
		margin:0 0 0 5px;
	}
	
	ul li input{
		font-weight: normal;
		margin:0px;
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
	
	.table-condensed td,.table-condensed th{
		text-align: center;
	}
 </style>
</head>
<body>
<div class="panel panel-default" style="margin-bottom: 0px;">
	<div class="panel-body" style="padding:10px 20px;text-align: right;">
		<label style="font-weight: bold;color: #F00;">用于质量报表、质量巡检记录、中控不合格点检查表功能。</label>
		<button type="button" class="btn btn-success" style="margin-left: 20px;" onclick="forAdd()">添加巡检点</button>
	</div>
</div>
<div class="panel panel-default" style="margin-bottom:0px;padding: 0px;">
	<div class="panel-body" style="padding:0px;overflow-y:auto;margin: 0px;" id="dataContent">
	<div id="panelContent" style="width:100%;">
	<table class="table table-bordered table-condensed">
		<thead><tr class="active">
			<th style="text-align: center;">序号</th>
			<th style="text-align: center;">车间</th>
			<th style="text-align: center;">装置</th>
			<th style="text-align: center;">采样点</th>
			<th style="text-align: center;">样品代码</th>
			<th style="text-align: center;">测试代码</th>
			<th style="text-align: center;">分析项名称</th>
			<th style="text-align: center;">分析项别名</th>
			<th style="text-align: center;">操作</th>
		</tr></thead>
		<tbody id="dailyContent">
		<c:forEach items="${xjList}" var="v" varStatus="status">
		<tr data-id="${v.id }">
			<td>
				<input type="text" style="padding: 5px 5px;width:30px;text-align:center;" data-id="${v.id}" value="${v.sort}">
			</td>
			<td>${v.area }</td>
			<td>${v.plant }</td>
			<td>${v.pointId }</td>
			<td>${v.matcode }</td>
			<td>${v.testcode }</td>
			<td>${v.analyte }</td>
			<td>${v.sinonym }</td>
			<td>
				<lable class="btn btn-link" style="padding:0px 2px;" onclick="forUpdate('${v.id}')">修改</lable>|<lable class="btn btn-link" style="padding:0px 2px;" onclick="del('${v.id}')">删除</lable>
			</td>
		</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
	</div>
</div>
</body>
</html>
<script type="text/javascript">
layui.use(['layer',"form"],function(){
	var layer = layui.layer,
	form = layui.form;
});

function forAdd(){
	window.location.href= "${pageContext.request.contextPath}/statistics/jbxj/xunjianSetting/forAdd/${matType}"; 
}

function forUpdate(id){
	window.location.href = "${pageContext.request.contextPath}/statistics/jbxj/xunjianSetting/forUpdate/"+id; 
}

function del(id){
	var url= "${pageContext.request.contextPath}/statistics/jbxj/xunjianSetting/del/"+id;
	$.ajax({
		type: 'POST',
		url:url,
		dataType: "json",
		success:function(data){
			if(data.code=="SUCCESS"){
				layer.msg('删除成功！',{
					time: 500
					},function(){
						$("tr[data-id='"+id+"']").remove();
					}
				)
			}
		}
	});
}

$(window).on('resize', function() {
	var height = $("body").height()-65;
	$("#dataContent").height(height);
}).resize();


$(":text").blur(function(){
	var value = $(this).val();
	var id = $(this).data("id");
	updateValue(id,value);
});

$(":text").keyup(function(event){
	if(event.keyCode==13){
		var value = $(this).val();
		var id = $(this).data("id");
		updateValue(id,value);
	}
});

function updateValue(id,value){
	var node={};
	node.id=id;
	node.value=value;
	var params = $.param(node);
	var urls="${pageContext.request.contextPath}/statistics/jbxj/xunjianSetting/sort";
	$.ajax({
		url:urls,
		data:params,
		type: 'POST',
		dataType: "json"
	 });
}
</script>