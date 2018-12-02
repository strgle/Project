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
<body style="width: 100%;height: 100%;">
<div class="tab" id="tab">
			<ul class="tab-title">
				<li data-href="${pageContext.request.contextPath}/statistics/jbxj/rate/week/${matcode}" data-id="tab01" data-load="loadTab" data-report="week">周报</li>
				<li data-href="${pageContext.request.contextPath}/statistics/jbxj/rate/ten/${matcode}" data-id="tab02" data-load="loadTab" data-report="ten">旬报</li>
				<li data-href="${pageContext.request.contextPath}/statistics/jbxj/rate/month/${matcode}" data-id="tab03" data-load="loadTab" data-report="month">月报</li>
			</ul>
			
			<div class="tab-content">
				<div class="tab-item" data-id="tab01" data-load="false">
					<iframe src="about:blank" name="tab01"></iframe>
				</div>
				<div class="tab-item" data-id="tab02" data-load="false">
					<iframe src="about:blank" name="tab02"></iframe>
				</div>
				<div class="tab-item" data-id="tab03" data-load="false">
					<iframe src="about:blank" name="tab03"></iframe>
				</div>
				
			</div>
</div>    

</body>
<script type="text/javascript">
	laydate.render({
	   elem: '#month',
	   type: 'month',
	   btns: ['confirm']
	});
	
	function loadTab(node){
		//判断数据是否已经加载
		var id = node.id;
		var href = node.href;
		
		//判断显示的tab
		var tabDiv = $(".tab-content .tab-show");
		if(!$(tabDiv).data("load")){
			$(tabDiv).children("iframe").prop("src",href);
			$(tabDiv).data("load",true);
		}
	}
	

	$("#tab").tab({
		loadTab:loadTab
	});
</script>
</html>