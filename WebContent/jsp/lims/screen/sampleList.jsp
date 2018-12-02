<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>装置日报信息</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/layui/css/layui.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/bootstrap/css/bootstrap.min.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/layui/layui.js"></script>

 <style type="text/css">
	html,body{
		background-color: #F5F5F5;
		overflow: auto;
		height: 100%;
	}
	th{
		white-space: nowrap;
	}
	td{
		text-align: center;
	}	
 </style>
</head>
<body>

<div class="panel panel-default" style="margin-bottom:0px;padding: 0px;">
	
	<div class="panel-body" style="padding:0px;overflow-y:auto;margin: 0px;">
	<table class="table table-bordered table-condensed">
		<thead><tr class="active">
			<th style="text-align: center;">序号</th>
			<th style="text-align: center;">样品编号</th>
			<th style="text-align: center;">样品名称</th>
			<th style="text-align: center;">装置名称</th>
			<th style="text-align: center;">采样点</th>
			<th style="text-align: center;">采样时间</th>
			<th style="text-align: center;">发布时间</th>			
			<th style="text-align: center;">样品类型</th>
			<th style="text-align: center;">任务类型</th>
			<th style="text-align: center;">样品状态</th>
			<th style="text-align: center;">链接</th>
		</tr></thead>
		<tbody>
		<c:forEach items="${points }" var="v" varStatus="st">
			<tr>
				<td>${st.count }</td>
				<td>${v.ordno}</td>
				<td>${v.matname}</td>
				<td>${v.plant}</td>
				<td>${v.pointdesc}</td>
				<td>${v.sampdate}</td>
				<td>${v.apprdate}</td>
				<td>
					<c:choose>
						<c:when test="${v.type=='RAW' }">
							原辅料
						</c:when>
						<c:when test="${v.type=='FP' }">
							成品
						</c:when>
						<c:when test="${v.type=='LP' }">
							其它样品
						</c:when>
						<c:otherwise>
							中控样
						</c:otherwise>
					</c:choose>
				</td>
				<td>${v.tasktype }</td>
				
				<c:choose>
					<c:when test="${v.grade=='OOS-A'}">
						<td style="white-space: nowrap;cursor:Pointer;color: #FF0000;" onclick="showDetail('${v.ordno}')"><i class="fa fa-star"></i>&nbsp;超标</td>
					</c:when>
					<c:when test="${v.grade=='OOS-B'}">
						<td style="white-space: nowrap;color: #FFDB4C;" onclick="showDetail('${v.ordno}')"><i class="fa fa-bell" style="color: #FFDB4C;" ></i>超标</td>
					</c:when>
					<c:when test="${v.grade=='Done'}">
						<td style="white-space: nowrap;" onclick="showDetail('${v.ordno}')"><i class="fa fa-circle" style="color: #5CB85C;" ></i>&nbsp;合格</td>
					</c:when>
					<c:otherwise>
						<td style="white-space: nowrap;" onclick="showDetail('${v.ordno}')">进行中</td>
					</c:otherwise>
				</c:choose>
				<td><lable class="btn btn-link" style="padding:0px 2px;" onclick="showDetail('${v.ordno}')">检测结果</lable></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
</div>


</body>
</html>
<script type="text/javascript">
layui.use(['layer','form'],function(){
	var layer = layui.layer,
	form = layui.form();
});


function showDetail(ordno){
	layer.open({
		  type: 2 //Page层类型
		  ,area: ['80%', '90%']
		  ,title: "检测结果明细"
		  ,content: '${pageContext.request.contextPath}/lims/dataSearch/daily/ordDetail?ordNo='+ordno
	});  
}

$(window).on('resize', function() {
	var height = $("body").height()-100;
	$("#dataContent").height(height);
}).resize();


</script>