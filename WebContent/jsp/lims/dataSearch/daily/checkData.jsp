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
<%@include file="/common/header/jquery.jsp"%>
<%@include file="/common/header/font-awesome.jsp"%>
<%@include file="/common/header/bootstrap.jsp"%>
<%@include file="/common/header/layer.jsp"%>
<%@include file="/common/header/layui.jsp"%>
<%@include file="/common/header/default.jsp"%>
<%@include file="/common/header/table.jsp"%>

 <style type="text/css">
 	html,body{
 		width: 100%;
 		height: 100%;
 	}
 	
 	tr.selected{
		background-color: #f5f5f5;
	}
	
 	.table-bordered th,.table-bordered td{
 		text-align: center;
 		vertical-align: middle!important;
 		min-width: 80px;
        font-size: 12px;
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

<div class="panel panel-default" >
    <div class="panel-heading">
 
     <div class="panel-tools" style="top:8px;">
              
       <label class="btn btn-xs btn-warning" onclick="addCheck()">添加检查</label>
     </div>
     
    </div>
	<div class="panel-body" style="overflow: auto;padding: 0px;margin: 0px;" id="tableContent">
	<table class="table table-bordered table-hover table-condensed" id="contentTable">
		<col width="60">
		<col width="150">
		<col width="150">
		<col width="200">
		<col width="150">
		<thead>
			<tr class="active">
            <th>&nbsp;</th>
			<th>序号</th>
			<th>采样时间</th>
			<th>采样点</th>
			<th>样品名称</th>
			<th>分析项目</th>
			<th>结果</th>
			<th>异常原因</th>
            <th>整改措施</th>
            <th>实施效果验证</th>
            <th>整改用户</th>
            <th>整改时间</th>
            <th>是否影响罐区</th>
            
		</tr></thead>
		<tbody>
			<c:forEach  items="${list}" var="v" varStatus="status">
				<tr>
                    <td><input type="checkbox" name="selectId" value="${v.origrec}"/></td>
					<td style="text-align: center;" >${status.count}</td>
                    <td>${v.sampdate}</td>
					<td>${v.samplePointId}</td>
					<td>${v.matname}</td>
					<td>${v.sinonym}</td>
					<td>${v.result}</td>
			        <td>${v.remark}</td>
                    <td>${v.updatemethod}</td>
                    <td>${v.resultCheck}</td>
                    <td>${v.checkUsr}</td>
                    <td>${v.checkdate}</td>
                    <td>${v.effectPlant}</td> 
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
</div>
<script type="text/javascript">

$(window).on('resize', function() {
 var height = $("body").height()-110;
 $("#tableContent").height(height);
 new TableSorter("tableContent");
}).resize();

function addCheck(){
	var chk_value = new Array(); 
    $('input[name="selectId"]:checked').each(function(){ 
      chk_value.push($(this).val()); 
    });
    if(chk_value.length<1){
     
     layui.use(['layer'],function(){
      var layer = layui.layer;
      layer.msg('未选择数据', {icon: 2});
     });
    return;
   }
    
	layer.open({
		  type: 2 //Page层类型
		  ,area: ['70%', '100%']
		  ,title: '添加检查'
		  ,content: '${pageContext.request.contextPath}/lims/dataSearch/daily/addCheck?origrec='+chk_value[0]
	 });   
}
</script>
</body>
</html>
