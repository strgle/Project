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
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/laydate/laydate.js"></script>
 
<style type="text/css">
	html,body{
		margin: 0px;
		padding: 5px;
		height: 100%;
		background-color: #F5F5F5;
	}
	th{
		white-space: nowrap;
		background-color: #5CB85C;
		color: #FFF;
        font-size: 12px;
	}
	th,td{
		text-align: center;
		vertical-align: middle;
        font-size: 12px;
	}
</style>
</head>
<body>

<div class="panel panel-default" style="margin-bottom: 0px;">
	<div class="panel-body">
	<form class="form-inline" action="">
		<div class="form-group">
			<label class="control-label" style="font-weight: normal;">车间：</label>
			<select class="form-control" name="area">
				<c:forEach items="${areaList }" var="v">
					<c:if test="${v==area }">
						<option value="${v}" selected="selected">${v}</option>
					</c:if>
					<c:if test="${v!=area}">
						<option value="${v}">${v}</option>
					</c:if>
				</c:forEach>
			</select>
		</div>
		<div class="form-group">
			<label class="control-label" style="font-weight: normal;">开始时间：</label>
			<input class="form-control" name="startDate" placeholder="开始时间" value="${startDate}" type="text" id="startDate" autocomplete="off" readonly="readonly"/>
		</div>
		<div class="form-group">
			<label class="control-label" style="font-weight: normal;margin-left: 20px;">截至时间：</label>
			<input class="form-control" name="endDate" placeholder="默认当前时间" value="${endDate}" id="endDate" type="text" autocomplete="off"  readonly="readonly"/>
		</div>
		<div class="form-group">
			<button type="submit" class="btn btn-success" style="margin-left: 30px;">查询</button>
		</div>
	</form>
	</div>
</div>
<div class="panel panel-default">
	<div class="panel-body" style="padding: 0px;margin: 0px;">
	<table class="table table-bordered table-striped table-condensed" style="margin: 0px;">
		<thead><tr>
			<th>序号</th>
			<th>样品编号</th>
			<th>装置</th>
			<th>采样点</th>
			<th>样品名称</th>
			<th>样品备注</th>
			<th>提交时间</th>
            <th>检测项目</th>
			<th>检测成本</th>
			<th>提交人</th>
			<th>链接</th>
		</tr></thead>
		<tbody>
			<c:forEach items="${jyDetail}" var="d" varStatus="status">
				<tr>
					<td>${status.count}</td>
					<td>
						${d.ordno}
					</td>
					<td>${d.plant}</td>
					<td>
						<c:if test="${fn:length(d.pointdesc) >12}">
							${fn:substring(d.pointdesc,0,12) }…
						</c:if>
						<c:if test="${fn:length(d.pointdesc)<=12 }">${d.pointdesc}</c:if>
					</td>
					<td>${d.matname}</td>
					<td>
						<c:if test="${fn:length(d.batchname) >12}">
							${fn:substring(d.batchname,0,12) }…
						</c:if>
						<c:if test="${fn:length(d.batchname)<=12 }">${d.batchname}</c:if>
					</td>
					<td>
						<fmt:formatDate value="${d.logdate}" type="both" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate>
					</td>
                    <td>
                     ${testList[d.ordno]}
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
				<th colspan="8">合计</th>
				<td><fmt:formatNumber value="${countPrice}" pattern="￥0.00" /></td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		</tbody>
		
	</table>
	</div>
</div>
	


<script type="text/javascript">
//执行一个laydate实例
laydate.render({
  elem: '#startDate' //指定元素
  ,theme: 'molv'
});
laydate.render({
  elem: '#endDate' //指定元素
  ,theme: 'molv'
}); 
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