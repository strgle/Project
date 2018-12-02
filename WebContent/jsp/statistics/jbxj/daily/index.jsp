<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/header/meta.jsp"%>
<title>检测数据汇总</title>
<%@include file="/common/header/jquery.jsp"%>
<%@include file="/common/header/laydate.jsp"%>
<%@include file="/common/header/bootstrap.jsp"%>
<%@include file="/common/header/default.jsp"%>
</head>
<body>
<div class="search">
<!-- 查询条件部分 -->
<div class="condition">
	<form action="${pageContext.request.contextPath}/statistics/jbxj/daily" method="post">
	<input type="hidden" name="startTime" id="startTime"/>
	<input type="hidden" name="endTime" id="endTime">
	<table>
		<tr>
			<td>采样时间：</td>
			<td><input type="text" id="startDate" name="startDate" style="width: 100px;" readonly="readonly" class="dateTime" value="${startDate }"/>
				<select name="starthour" class="dateTime" id="startHour">
					<option value="03">03</option>
					<option value="11">11</option>
					<option value="20">20</option>
				</select>
				
				<label class="Connector">至 </label>
				<input type="text" id="endDate" name="endDate" style="width: 100px;" readonly="readonly" class="dateTime" value="${endDate }"/>
				<select name="endhour" class="dateTime" id="endHour">
					<option value="03">03</option>
					<option value="11">11</option>
					<option value="20">20</option>
				</select>				
			</td>
			<td style="padding-left: 20px;"><button class="btn btn-primary btn-sm" type="button" onclick="requery()">查询</button></td>
			<td style="padding-left: 20px;"><button class="btn btn-success btn-sm" type="button" onclick="expExcel()">导出excel</button></td>
			<td style="padding-left: 15px;"><label style="font-weight: bold;color: red;">统计批号1、2开头，切结尾不为24点的检测结果，从审批报告单中统计。</label></td>
		</tr>
	</table>
	</form>
</div>

<!-- 结果展示部分 -->
<div class="result">
	<div class="tab" id="tab">
			<ul class="tab-title">
				<c:forEach items="${matList}" var="mat">
				<li data-href="${pageContext.request.contextPath}/statistics/jbxj/daily/matHz/${mat.matcode}" data-id="${mat.matcode}">${mat.matname}</li>
				</c:forEach>
			</ul>
			
			<div class="tab-content" >
				<c:forEach items="${matList}" var="mat">
				<div class="tab-item" data-id="${mat.matcode}" data-load="false">
					<iframe src="about:blank" name="${mat.matcode}" id="${mat.matcode}"></iframe>
				</div>
				</c:forEach>
			</div>
	</div>    
</div>
</div>
</body>
<script type="text/javascript">
	laydate.render({
	   elem: '#startDate',
	   format: 'yyyy-MM-dd',
	   btns: ['now', 'confirm']
	});
	
	laydate.render({
		elem: '#endDate',
		format: 'yyyy-MM-dd',
		btns: ['now', 'confirm']
	});
	
	function loadTab(node){
		//判断数据是否已经加载
		var id = node.id;
		var href = node.href;
		
		//判断显示的tab
		var tabDiv = $(".tab-content .tab-show");
		if(!$(tabDiv).data("load")){
			query();
			$(tabDiv).data("load",true);
		}
	}
	
	function query(){
		//
		var curTab = $(".tab-title .tab-this");
		var id = $(curTab).data("id");
		var href = $(curTab).data("href");
		$("form").prop("action",href);
		$("form").prop("target",id);
		//获取开始时间
		var startTime = $("#startDate").val()+" "+$("#startHour").val()+":00";
		var endTime = $("#endDate").val()+" "+$("#endHour").val()+":00";
		$("#startTime").val(startTime);
		$("#endTime").val(endTime);
		$("form").submit();
	}
	
	function requery(){
		var allTab = $(".tab-content .tab-item");
		$.each(allTab,function(index,obj){
			$(obj).data("load",false);
		});
		//判断显示的tab
		var tabDiv = $(".tab-content .tab-show");
		if(!$(tabDiv).data("load")){
			query();
			$(tabDiv).data("load",true)
		}
	}
	
	function expExcel(){
		var curTab = $(".tab-title .tab-this");
		var id = $(curTab).data("id");
		document.getElementById(id).contentWindow.expExcel();
	}
	
	$("#tab").tab({
		loadTab:loadTab
	});
</script>
</html>