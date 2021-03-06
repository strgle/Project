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
 <link rel="stylesheet" href="${pageContext.request.contextPath}/skins/layui/css/layui.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/layui/layui.js"></script><script type="text/javascript" src="${pageContext.request.contextPath}/skins/js/common.js"></script>
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
	<form class="form-inline" action="${pageContext.request.contextPath}/lims/zltj/jy/queryApproveAddSample">
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
           <th><label>序号</label></th>
			<th><label>审批状态</label></th>
             <th><label>装置</label></th>
			<th><label>加样时间</label></th>
			<th><label>样品编号</label></th>
			<th><label>样品名称</label></th>
			<th><label>加样原因</label></th>
			<th><label>检测项目</label></th>
			<th><label>检测成本</label></th>
			<th><label>不统计原因</label></th>
			<th><label>审批人</label></th>
			<th><label>审批结论</label></th>
            <th><label>明细链接</label></th>
		</tr></thead>
		<tbody>
			<c:forEach items="${addSampList }" var="samp" varStatus="status">
			<tr>
			<td>${status.count }</td>
			<td>
				<c:if test="${samp.status=='0' }">
					草稿
				</c:if>
				<c:if test="${samp.status=='1' }">
					审批通过
				</c:if>
				<c:if test="${samp.status=='-1' }">
					拒绝
				</c:if>
			</td>
            <td>${samp.plant}</td>
			<td nowrap="nowrap">${samp.sampeDate}</td>
			<td>${samp.ordNo}</td>
			<td>${samp.matname}</td>
			<td>${samp.comments}</td>
			<td>
				<c:forEach items="${samp.testList }" var="testNo">
					<label style="margin-left: 5px;margin-right: 5px;">${testNo }</label>
				</c:forEach>
      			</td>
      			<td>${samp.price}</td>
      			<td>${samp.remark}</td>
      			<td>${samp.approve}</td>
      			<td>${samp.approveRemark}</td>
                <td>
               <label class="btn btn-link" style="margin: 0px;padding: 0px;" onclick="showDetail('${samp.ordNo}')">明细</label>
              </td>
      			</tr>
              
      		</c:forEach>	
			
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