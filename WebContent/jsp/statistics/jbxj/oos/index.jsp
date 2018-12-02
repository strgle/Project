<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/header/meta.jsp"%>
<title>质量统计</title>
<%@include file="/common/header/jquery.jsp"%>
<%@include file="/common/header/laydate.jsp"%>
<%@include file="/common/header/layer.jsp"%>
<%@include file="/common/header/bootstrap.jsp"%>
<%@include file="/common/header/default.jsp"%>
</head>
<body>
<div class="search">
<!-- 查询条件部分 -->
<div class="condition">
	<form action="" method="post">
	<table>
		<tr>
			<td>时间：</td>
			<td><input type="text" id="month" name="month" style="width: 100px;" readonly="readonly" class="dateTime" value="${month }"/>
			</td>
			<td style="padding-left: 20px;"><button class="btn btn-primary btn-sm" type="button" onclick="requery()">统计</button></td>
			<td style="padding-left: 20px;"><button class="btn btn-primary btn-sm" type="button" onclick="plt()">帕累托分析</button></td>
			<td style="padding-left: 20px;"><button class="btn btn-success btn-sm" type="button" onclick="expExcel()">导出excel</button></td>
		</tr>
	</table>
	</form>
</div>

<!-- 结果展示部分 -->
<div class="result">
	<div class="tab" id="tab">
		<ul class="tab-title">
			<c:forEach items="${matList}" var="mat">
			<li data-href="${pageContext.request.contextPath}/statistics/jbxj/oos/product/${mat.matcode}" data-id="${mat.matcode}">${mat.matname}</li>
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
	   elem: '#month',
	   type: 'month',
	   btns: ['confirm'],
	   done: function(value){
		   $("#month").val(value);
		   requery();
	   }
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
		var area = $(curTab).data("area");
		$("form").prop("action",href);
		$("form").prop("target",id);
		$("#area").val(area);
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
	
	function receiveReturn(){
		var curTab = $(".tab-title .tab-this");
		var framename = $(curTab).data("id");
		var pltdata = document.getElementById(framename).contentWindow.pltdata();
		return pltdata;
	}
	
	function plt(){
		layer.open({
			content:"${pageContext.request.contextPath}/jsp/statistics/jbxj/oos/chart.jsp",
			type:2,
			area:['700px','480px']
		});
	}
	$("#tab").tab({
		loadTab:loadTab
	});
	
	function expExcel(){
		var curTab = $(".tab-title .tab-this");
		var id = $(curTab).data("id");
		document.getElementById(id).contentWindow.expExcel();
	}
</script>
</html>