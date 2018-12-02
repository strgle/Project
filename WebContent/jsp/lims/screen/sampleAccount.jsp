<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
 <script type="text/javascript" src="${pageContext.request.contextPath}/skins/bootstrap/js/bootstrap.min.js"></script>
 <style type="text/css">
 	html,body{
 		width: 100%;
 		height: 100%;
 	}
 	.table-bordered th,.table-bordered td{
 		text-align: center;
 		vertical-align: middle!important;
 		min-width: 80px;
 	}
 	
	.control-label{
		font-weight: normal;
	}
	
	.link-label{
		color: #1E91E2;
		text-decoration: underline;
		cursor: pointer;
	}
	.dropdown-menu li{
		padding:5px 10px;
		white-space: nowrap;
	}
</style>
</head>
<body>

<div class="panel panel-default" style="margin-bottom: 0px;">
	<div class="panel-body">
	<form class="form-inline" action="${pageContext.request.contextPath}/lims/screen/gq/sampleAccount" method="post">
		<input type="hidden" name="pointId" value="${params.pointId}"/>
		<div class="form-group col-xs-12 col-sm-12 col-md-12 col-lg-12">
    		<label class="control-label">采样时间：</label>
      		<input type="text" class="form-control" id="startTime" name="startTime" style="width:180px;" placeholder="开始时间"  value="${params.startTime}" autocomplete="off"/>
  			<label class="control-label" style="margin: 0px 10px 0px 10px;">至</label>
  			<input class="form-control" name="endTime" placeholder="默认当前时间" style="width:180px;" value="${params.endTime}" id="endTime" type="text" autocomplete="off"/>
    		<button type="submit" class="btn btn-success" style="margin-left:10px;">查询</button>
    	</div>  		
	</form>
	</div>
</div>
<div class="panel panel-default" style="margin-bottom: 0px;padding: 0px;">
	<div class="panel-body" style="overflow: auto;padding: 0px;margin: 0px;" id="tableContent">
	<table class="table table-bordered table-hover table-condensed" id="contentTable">
		<col width="60">
		<col width="150">
		<col width="150">
		<col width="200">
		<col width="150">
		<thead>
			<tr class="active">
			<th>序号</th>
			<th data-mynum="1">样品名称</th>
			<th>装置</th>
			<th>采样点</th>
			<th>采样时间</th>
			<th data-mynum="4">状态</th>
			<th data-mynum="6" style="display: none;">样品编号</th>
			<th data-mynum="7" style="display: none;">任务类型</th>
			<th data-mynum="8" style="display: none;">样品类型</th>
			<th data-mynum="9" style="display: none;">供应商</th>
			<c:forEach items="${titles}" var="t" varStatus="status">
				<th data-mynum="${status.count+9}">${t}</th>
			</c:forEach>
			<th data-mynum="${fn:length(titles)+10}">样品备注</th>
		</tr></thead>
		<tbody>
			<c:forEach  items="${data}" var="v" varStatus="status">
				<tr>
					<td style="text-align: center;" >${status.count}</td>
					<td>${v.matName}</td>
					<td nowrap="nowrap">${v.plant}</td>
					<td>${v.pointdesc}</td>
					<td nowrap="nowrap">${v.sampDate}</td>
					<td onclick="showDetail('${v.ordNo}')" style="cursor: pointer;">
						<c:choose>
							<c:when test="${v.grade=='OOS-A'}">
								<i class="fa fa-star" style="color:#D9534F;"></i>&nbsp;超标
							</c:when>
							<c:when test="${v.grade=='OOS-B'}">
								<i class="fa fa-bell" style="color: #FFDB4C;" ></i>&nbsp;超标
							</c:when>
							<c:when test="${v.grade=='Done' }">
								<i class="fa fa-circle" style="color:#5CB85C;"></i>&nbsp;合格
							</c:when>
							<c:otherwise>进行中</c:otherwise>
						</c:choose>
					</td>
					
					<td style="display: none;">${v.ordNo}</td>
					<td style="display: none;">${v.taskType}</td>
					<td style="display: none;">
						<c:choose>
							<c:when test="${v.type=='RAW'}">原辅料</c:when>
							<c:when test="${v.type=='Process'}">中控样</c:when>
							<c:when test="${v.type=='FP'}">成品</c:when>
							<c:when test="${v.type=='LP'}">其它样品</c:when>
						</c:choose>
					</td>
					<td style="display: none;">${v.suppCode}</td>
					<c:forEach items="${v.analytes}" var="a">
						<td>
							<c:if test="${!empty a.finalnum}">
								<c:if test="${a.analtype=='AT'}"><a href="javascript:void(0)" onclick="showFile('${v.ordNo}','${a.testcode}','${a.analyte}')"></c:if>
									${a.finalnum}
								<c:if test="${a.analtype=='AT'}"></a></c:if>		
							</c:if>
							<c:if test="${empty a.finalnum}">/</c:if>
						</td>
					</c:forEach>
					<td>${v.batchName}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
</div>
</body>
</html>
<script type="text/javascript">

layui.use(['form','laydate'],function(){
	var form = layui.form(),
	laydate = layui.laydate;
	var start = {
			  format: 'YYYY-MM-DD',
			  min: '2017-01-01', 
			  max: laydate.now(),
			  choose: function(datas){
				 end.min = datas;
				 end.start = datas;
			  }
			};
	var end = {
			  format: 'YYYY-MM-DD',
			  min: '2017-01-01',
			  max: laydate.now(),
			  choose: function(datas){
			    start.max = datas; //结束日选好后，重置开始日的最大日期
			  }
			};
	$("#startTime").on("click",function(){
		start.elem = this;
	    laydate(start);
	})
	
	$("#endTime").on("click",function(){
		end.elem = this;
	    laydate(end);
	})
			
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

function showFile(ordno,testcode,analyte){
	 //判断下载form是否存在
	 var $elemForm = $("#downLoadForm");
	 if($elemForm.length==0){
		 $eleForm = $("<form method='post' id='downLoadForm'></form>");
		 var ordNoInput = $("<input type='hidden' id='ordNo' name='ordNo' />");
		 var testCodeInput = $("<input type='hidden' id='testCode' name='testCode' />");
		 var analyteInput = $("<input type='hidden' id='analyte' name='analyte' />");
		 $eleForm.append(ordNoInput);
		 $eleForm.append(testCodeInput);
		 $eleForm.append(analyteInput);
	     $eleForm.attr("action","${pageContext.request.contextPath}/lims/common/downLoad");
	     $(document.body).append($eleForm);
	 }
	 
	 $("#ordNo").val(ordno);
	 $("#testCode").val(testcode);
	 $("#analyte").val(analyte);
	 
    //提交表单，实现下载
    $eleForm.submit();
}

$(window).on('resize', function() {
	var height = $("body").height()-68;
	$("#tableContent").height(height);
}).resize();
</script>