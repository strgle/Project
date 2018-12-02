<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>加样明细</title>
 <link rel="stylesheet" href="${pageContext.request.contextPath}/skins/font-awesome/css/font-awesome.min.css">
 <link rel="stylesheet" href="${pageContext.request.contextPath}/skins/links/bootstrap-3.3.7/css/bootstrap.min.css">
 <link rel="stylesheet" href="${pageContext.request.contextPath}/skins/fuelux/css/ace.min.css">
 <script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/bootstrap-3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/layer/layer.js"></script>
 
<style type="text/css">
	html,body{
		margin: 0px;
		padding: 0px;
		height: 100%;
	}
	
	th,td{
		white-space: nowrap;
		text-align: center;
        font-size: 12px;
	}
	

</style>
</head>
<body>

<div class="panel panel-default">
	<div class="panel-body" style="padding: 0px;margin: 0px;">
	<table class="table table-bordered table-striped table-condensed" style="margin: 0px;">
		<thead><tr>
			<th>序号</th>
			<th>装置</th>
			<th>采样点</th>
			<th>样品名称</th>
			<th>样品备注</th>
			<th>采样时间</th>
			<th>检测成本</th>
			<th>提交人</th>
			<th>链接</th>
		</tr></thead>
		<tbody>
			<c:forEach items="${jyDetail}" var="d" varStatus="status">
				<tr>
					<td>${status.count}</td>
					<td>${d.plant}</td>
					<td>${d.pointdesc}</td>
					<td>${d.matname}</td>
					<td>
						<c:if test="${fn:length(d.batchname) >12}">
							${fn:substring(d.batchname,0,12) }…
						</c:if>
						<c:if test="${fn:length(d.batchname)<=12 }">${d.batchname}</c:if>
					</td>
					<td>
						<fmt:formatDate value="${d.sampdate}" type="both" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate>
					</td>
					<td>
						<fmt:formatNumber value="${priceMap[d.ordno]}" pattern="￥0.00" />
					</td>
					<td>
						${d.fullname}
					</td>
					<td>
						<label class="btn btn-link" style="margin: 0px;padding: 0px;" onclick="showDetail('${d.ordno}')">明细</label>
					</td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="6" style="background-color: #5CB85C;color: #FFF;">合计</td>
				<td><fmt:formatNumber value="${countPrice}" pattern="￥0.00" /></td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		</tbody>
	</table>
	</div>
</div>
	


<script type="text/javascript">

$(window).on('resize', function() {
	$("#treeContent").height($("body").height()-45);
}).resize();

function showDetail(ordno){
	layer.open({
		  type: 2 //Page层类型
		  ,scrollbar:false
		  ,area: ['70%', '90%']
		  ,title: '检测结果'
		  ,content: '${pageContext.request.contextPath}/lims/dataSearch/daily/ordDetail?ordNo='+ordno
	});    
}
</script>
</body>
</html>