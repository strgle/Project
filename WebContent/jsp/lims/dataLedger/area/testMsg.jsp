<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>检测台账</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/laydate/laydate.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/js/common.js"></script>

<style type="text/css">
	html,body{
		padding:5px;
		background-color: #ECECEC;
		height: 100%;
		overflow:hidden;
	}

	.panel-default>.panel-heading{
		background-color: #FFFFFF;
	}
	
	.form-input {
	  width:140px;
	  padding-left:12px;
	  font-size: 14px;
	  line-height: 1.42857143;
	  color: #555;
	  background-color: #fff;
	}
	.panel,.panel-heading,.panel-title,.panel-body{
		border-radius:0px!important;
		
	}
	.table tr td{
		border: 1px solid #000000 !important;
		text-align: center;
		vertical-align: middle;
		white-space: nowrap;
	}
	.table tr th{
		border: 1px solid #000000 !important;
		text-align: center;
		vertical-align: middle !important;
		min-width: 65px;
	}
	.table tr th label{
		width:100%;
		margin:0px;
		border: 0px;
	}
</style>
</head>
<body>
<form action="" method="post">
	<input type="hidden" name="areaName" value="${areaName}"/>
	<input type="hidden" name="plant" value="${plant}"/>
<div class="panel panel-default" style="margin-bottom:0px;">
	<div class="panel-heading">
		<div class="panel-title">
			<labe style="margin-right:20px;padding:5px 10px;font-size:14px;background-color:#5CB85C;color:#FFFFFF;">${areaName}&nbsp;—&nbsp;${plant}</labe>
		</div>
		<div class="panel-tools" style="top: 5px;right: 100px;">
			开始日期：<input name="startDate" class="laydate-icon form-input" placeholder="日期" value="${startDate}" id="startDate" type="text" autocomplete="off" onclick="laydate()"/>
			开始日期：<input name="endDate" class="laydate-icon form-input" placeholder="日期" value="${endDate}" id="endDate" type="text" autocomplete="off" onclick="laydate()"/>	
			<button class="btn btn-success btn-sm" onclick="query()">查询</button>
			<button type="button" class="btn btn-success btn-sm" onclick="expExcel()">导出EXCEL</button>	
		</div>
	</div>
</div>
</form>
<div class="panel panel-success" style="margin-bottom: 0px;">
	<div class="panel-heading" style="border: 1px solid #000;">
		<div class="panel-title" style="text-align: center;font-weight: bold;font-size: 16px;letter-spacing: 5px;">样品检测台账</div>
	</div>
	<div  id="tableContaint" class="panel-body" style="padding: 0px;overflow-y:auto;position:relative;">
		<table class="table table-bordered" style="margin: 0px;" id="expTable">
			<thead id="fixheader">
				<tr>
					<th><label>采样点</label></th>
					<th><label>样品</label></th>
					<th><label>分析项</label></th>
					<c:forEach items="${sampDate }" var="time">
						<th><label>${fn:substring(time,5,16) }</label></th>
					</c:forEach>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${data}" var="d">
					<tr>
						<td rowspan="${fn:length(d[1])}" style="vertical-align: middle;white-space: normal;">${d[0][0]}</td>
						<td rowspan="${fn:length(d[1])}" style="vertical-align: middle;">${d[0][1]}</td>
						<td style="text-align: left;">${d[1][0]}</td>
						<c:forEach items="${sampDate }" var="time" >
							<td><c:forEach items="${d[2][0]}" var="ordTime" varStatus="sta2"><c:if test="${ordTime==time}">${d[2][1][sta2.index]['finalnum'] }</c:if></c:forEach></td>
						</c:forEach>
					</tr>
					<c:if test="${fn:length(d[1])>1 }">
						<c:forEach items="${d[1]}" begin="1" varStatus="sta3" var="title">
							<tr>
								<td style="text-align: left;">${title}</td>
								<c:forEach items="${sampDate }" var="time" >
									<td><c:forEach items="${d[2][0]}" var="ordTime" varStatus="sta4"><c:if test="${ordTime==time}"><c:choose><c:when test="${d[2][sta3.count+1][sta4.index]['s']=='OOS-A'}"><label style="background-color: #F2DEDE;width: 100%;height: 100%;color: #ff0000;">${d[2][sta3.count+1][sta4.index]['finalnum']}</label></c:when><c:when test="${d[2][sta3.count+1][sta4.index]['s']=='OOS-B'}">${d[2][sta3.count+1][sta4.index]['finalnum']}</c:when><c:otherwise>${d[2][sta3.count+1][sta4.index]['finalnum']}</c:otherwise></c:choose></c:if></c:forEach></td>
								</c:forEach>
							</tr>
						</c:forEach>
					</c:if>
				</c:forEach>
			</tbody>
		</table>

		<table class="table table-bordered" style="margin:0px;position:absolute;top:0px;background-color: #FFF;width: 100%;" id="hidefix" >
			<thead>
				<tr>
					<th><label>采样点</label></th>
					<th><label>样品</label></th>
					<th><label>分析项</label></th>
					<c:forEach items="${sampDate }" var="time">
						<th><label>${fn:substring(time,5,16) }</label></th>
					</c:forEach>
				</tr>
			</thead>
		</table>
	
	</div>
</div>

</body>
<script type="text/javascript">
	function query(){
		var url="${pageContext.request.contextPath}/lims/dataLedger/area/testDetail"
		$("form").attr("action",url);
		$("form").submit();
	}
	
	$(window).on('resize', function() {
		$("#tableContaint").height($("body").height()-100);
		changeWidth();
	}).resize();
	
	function changeWidth(){
		//获取高度信息
		var h1 = $("#fixheader").height();
		$("#hidefix thead").height(h1);
		var w1 = $("#fixheader").width();
		$("#hidefix").width(w1);
		$("#fixheader tr th").each(function(index,element){
			var w = $(element).children("label").width();
			$("#hidefix thead tr th:eq("+index+") label").width(w);
		});
	}
	$('#tableContaint').on('scroll',function(){
		var top = $('#tableContaint').scrollTop();
		$("#hidefix").css("top",top);
	});
	
	function expExcel(){
		 //判断下载form是否存在
		 var $elemForm = $("#expExcelForm");
		 if($elemForm.length==0){
			 $eleForm = $("<form method='post' id='expExcelForm' enctype='multipart/form-data'></form>");
			 var expContent = $("<input type='hidden' id='expContent' name='expContent' />");
			 $eleForm.append(expContent);
		     $eleForm.attr("action","${pageContext.request.contextPath}/lims/common/expExcel");
		     $(document.body).append($eleForm);
		 }
		 var content ="<table>"+ $("#expTable").html()+"</table>";
		 
		 $("#expContent").val(content);
		 
	   //提交表单，实现导出
	   $eleForm.submit();
	}
</script>
</html>