<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/header/meta.jsp"%>
<title>产品合格率统计</title>
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
	<input type="hidden" name="matCode" id="matCode"/>
	<table>
		<tr>
			<td>时间：</td>
			<td><input type="text" id="year" name="year" style="width: 100px;" readonly="readonly" class="dateTime" value="${year}"/>
			</td>
			<td style="padding-left: 20px;"><button class="btn btn-primary btn-sm" type="button" onclick="requery()">统计</button></td>
			<td style="padding-left: 20px;"><button class="btn btn-success btn-sm" type="button" onclick="expExcel()">导出excel</button></td>
			<td style="padding-left: 15px;"><label style="font-weight: bold;color: red;">从审批报告单中统计。</label></td>
		</tr>
	</table>
	</form>
</div>

<!-- 结果展示部分 -->
<div class="result">
	<div class="tab" id="tab">
			<ul class="tab-title">
				<li data-href="${pageContext.request.contextPath}/statistics/jbxj/rate/product" data-id="tab01" data-matcode="XJ0135">普通丁基橡胶</li>
				<li data-href="${pageContext.request.contextPath}/statistics/jbxj/rate/product" data-id="tab04" data-matcode="XJ0111">卤化丁基橡胶</li>
				<%-- 
				<li data-href="${pageContext.request.contextPath}/statistics/jbxj/rate/process" data-id="tab05" data-matcode="">中控</li>
				<li data-href="${pageContext.request.contextPath}/statistics/jbxj/rate/raw" data-id="tab03" data-matcode="">原辅料</li>
				--%>
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
	   elem: '#year',
	   type: 'year',
	   btns: ['confirm'],
	   done: function(value){
		   $("#year").val(value)
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
		var matCode = $(curTab).data("matcode");
		$("form").prop("action",href);
		$("form").prop("target",id);
		$("#matCode").val(matCode);
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