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
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/js/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/laydate/laydate.js"></script>
<style type="text/css">
	html,body{
		padding: 5px;
		background-color: #ECECEC;
	}

	.panel-default>.panel-heading{
		background-color: #FFFFFF;
	}
	input{
		width:80px;
	}
	.form-input {
	  width:140px;
	  padding-left:12px;
	  font-size: 14px;
	  line-height: 1.42857143;
	  color: #555;
	  background-color: #fff;
	}
</style>
</head>
<body>
<div class="panel panel-default" style="margin-bottom:0px;">
	<div class="panel-heading">
		<div class="panel-title">
			<labe style="margin-right:20px;padding:5px 10px;font-size:14px;background-color:#5CB85C;color:#FFFFFF;">${areaName}&nbsp;—&nbsp;${plant}</labe>
		</div>
	</div>
</div>
<form action="" method="post">
<div class="panel panel-default">
	<input type="hidden" name="areaName" value="${areaName}"/>
	<input type="hidden" name="plant" value="${plant}"/>
	<input type="hidden" name="oldProdDate" value="${prodDate}" id="oldProdDate"/>
	<div class="panel-heading">
		生产日期：<input name="prodDate" class="laydate-icon form-input" placeholder="日期" value="${prodDate}" id="prodDate" type="text" autocomplete="off" onclick="laydate({choose:showDate})"/>		
		<div class="panel-tools" style="top: 5px;">
			<label class="btn btn-sm btn-success" onclick="save()">保存</label>
			<label class="btn btn-sm btn-success" onclick="mesShow()">返回</label>
		</div>
	</div>
	<div class="panel-body">
		<table class="table table-bordered">
			<thead>
				<tr>
					<th colspan="2" style="text-align: center;vertical-align: middle;">项目/时间</th>
					<c:forEach items="${mdTimes}" var="time">
						<th colspan="2" style="text-align: center;">${time}</th>
					</c:forEach>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty inpcs}">
					<tr>
						<td rowspan="${fn:length(inpcs)+1}">进方</td>
						<td style="text-align: center;">项目</td>
						<c:forEach items="${mdTimes}">
							<td style="text-align: center;">加工量</td>
							<td style="text-align: center;">收率</td>
						</c:forEach>
					</tr>
				</c:if>
				<c:forEach items="${inpcs}" var="v" varStatus="row">
					<tr>
						<td>${v['prodCondition'] }</td>
						<c:forEach begin="0" end="${columnNum-1}" var="column" >
							<td style="text-align: center;">
								<input type="text" name="${v['prodCondition']}@@${mdTimes[column]}@@IN" value="${v['data'][column*2] }"/>
							</td>
							<td style="text-align: center;">
								<input type="text" name="${v['prodCondition']}@@${mdTimes[column]}@@IN" value="${v['data'][column*2+1] }"/>
							</td>
						</c:forEach>
					</tr>
				</c:forEach>
				<c:if test="${not empty outpcs}">
					<tr>
						<td rowspan="${fn:length(outpcs)+1}">出方</td>
						<td style="text-align: center;">项目</td>
						<c:forEach items="${mdTimes}">
							<td style="text-align: center;">加工量</td>
							<td style="text-align: center;">收率</td>
						</c:forEach>
					</tr>
				</c:if>
				<c:forEach items="${outpcs}" var="v" varStatus="row">
					<tr>
						<td>${v['prodCondition'] }</td>
						<c:forEach begin="0" end="${columnNum-1}" var="column" >
							<td style="text-align: center;">
								<input type="text" name="${v['prodCondition']}@@${mdTimes[column]}@@OUT" value="${v['data'][column*2] }"/>
							</td>
							<td style="text-align: center;">
								<input type="text" name="${v['prodCondition']}@@${mdTimes[column]}@@OUT" value="${v['data'][column*2+1] }"/>
							</td>
						</c:forEach>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
</form>

</body>
<script type="text/javascript">
	function showDate(datas){
		var curDate = $("#oldProdDate").val();
		if(curDate!=datas){
			var url="${pageContext.request.contextPath}/lims/dataLedger/prodMes/mbShow"
			$("form").attr("action",url);
			$("form").submit();
		}
	}
	
	function mesShow(datas){
		var url="${pageContext.request.contextPath}/lims/dataLedger/prodMes/mbShow"
		$("form").attr("action",url);
		$("form").submit();
	}
	
	function save(){
		var url="${pageContext.request.contextPath}/lims/dataLedger/prodMes/mbAdd"
		$("form").attr("action",url);
		$("form").submit();
	}
	
</script>
</html>