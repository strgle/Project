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
<%@include file="/common/header/bootstrap.jsp"%>
<%@include file="/common/header/default.jsp"%>
</head>
<body>
<div class="search">
<!-- 查询条件部分 -->
<div class="condition">
	<form action="${pageContext.request.contextPath}/statistics/jbxj/quality" method="post">
	<input type="hidden" name="area" id="area"/>
	<table>
		<tr>
			<td>时间：</td>
			<td><input type="text" id="month" name="month" style="width: 100px;" readonly="readonly" class="dateTime" value="${month }"/>
			</td>
			<td style="padding-left: 20px;"><button class="btn btn-primary btn-sm" type="button" onclick="requery()">统计</button></td>
			<td style="padding-left: 20px;"><button class="btn btn-success btn-sm" type="button" onclick="expExcel()">导出excel</button></td>
			<td style="padding-left: 15px;"><label style="font-weight: bold;color: red;">合成车间从装置日报中统计，通过中控考核点设置；精制车间从审批报告单中统计，通过产品关键指标设置。</label></td>
		</tr>
	</table>
	</form>
</div>

<!-- 结果展示部分 -->
<div class="result">
	<div class="tab" id="tab">
			<ul class="tab-title">
				<li data-href="${pageContext.request.contextPath}/statistics/jbxj/quality/keyPoint" data-id="tab01" data-load="loadTab" data-area="合成车间">合成中控合格率</li>
				<li data-href="${pageContext.request.contextPath}/statistics/jbxj/quality/keyLimit" data-id="tab04" data-load="loadTab" data-area="精制车间">精制车间关键指标考核</li>
			</ul>
			
			<div class="tab-content">
				<div class="tab-item" data-id="tab01" data-load="false">
					<iframe src="about:blank" name="tab01" id="tab01"></iframe>
				</div>
				<div class="tab-item" data-id="tab04" data-load="false">
					<iframe src="about:blank" name="tab04" id="tab04"></iframe>
				</div>
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
		   $("#month").val(value)
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