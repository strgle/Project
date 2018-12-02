<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/layer/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/js/common.js"></script>
<style type="text/css">
	html,body{
		padding: 5px;
		background-color: #ECECEC;
	}

	.panel-default>.panel-heading{
		background-color: #FFFFFF;
	}

</style>
</head>
<body>
<div class="panel panel-default" style="margin-bottom:0px;">
	<div class="panel-heading">
		<div class="panel-title">${areaName}&nbsp;—&nbsp;${plant}</div>
	</div>
</div>
<div class="panel panel-default">
	<div class="panel-heading">
		生产条件设置
	</div>
	<div class="panel-body">
		<div class="col-sm-6">
			<div class="panel panel-info">
				<div class="panel-heading">
					<div class="panel-title">监控项目</div>
					<div class="panel-tools"><button class="btn btn-success btn-xs" onclick="showAdd('1','监控项目')">添加</button></div>
				</div>
				<table class="table" style="margin:10px auto;width: 90%;">
					<c:forEach items="${data1}" var="d">
						<tr id="${d.id }">
							<td style="vertical-align: middle;">${d.prodCondition } &nbsp;，&nbsp;${d.units} </td>
							<td><button class="btn btn-link btn-xs" onclick="delInfo('1','${d.id}')">删除</button>
							|<button class="btn btn-link btn-xs" onclick="moveUp('1','${d.id}')">上移</button></td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		
		<div class="col-sm-6">
			<div class="panel panel-info">
				<div class="panel-heading">
					<div class="panel-title">监控时间</div>
					<div class="panel-tools"><button class="btn btn-success btn-xs" onclick="showAdd('2','监控时间')">添加</button></div>
				</div>
				<table class="table" style="margin:10px auto;width: 90%;">
					<c:forEach items="${data2}" var="d">
						<tr id="${d.id }">
							<td style="vertical-align: middle;">${d.prodTime} </td>
							<td><button class="btn btn-link btn-xs" onclick="delInfo('2','${d.id}')">删除</button>
							|<button class="btn btn-link btn-xs" onclick="moveUp('2','${d.id}')">上移</button></td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
	
</div>
<div class="panel panel-default" style="margin-bottom:0px;">
	<div class="panel-heading">物料平衡设置</div>
	<div class="panel-body">
		<div class="col-sm-12">
			<div class="panel panel-info">
				<div class="panel-heading">
					<div class="panel-title">统计时间</div>
					<div class="panel-tools"><button class="btn btn-success btn-xs" onclick="showAdd('3','统计时间')">添加</button></div>
				</div>
				<table class="table" style="margin:10px auto;width: 90%;">
					<c:forEach items="${data3}" var="d">
						<tr id="${d.id }">
							<td style="vertical-align: middle;">${d.staTime} </td>
							<td><button class="btn btn-link btn-xs" onclick="delInfo('3','${d.id}')">删除</button>
							|<button class="btn btn-link btn-xs" onclick="moveUp('3','${d.id}')">上移</button></td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		
		<div class="col-sm-6">
			<div class="panel panel-info">
				<div class="panel-heading">
					<div class="panel-title">进方监控项目</div>
					<div class="panel-tools"><button class="btn btn-success btn-xs" onclick="showAdd('4','进方监控项目')">添加</button></div>
				</div>
				<table class="table" style="margin:10px auto;width: 90%;">
					<c:forEach items="${data4}" var="d">
						<tr id="${d.id }">
							<td style="vertical-align: middle;">${d.prodCondition } &nbsp;，&nbsp;${d.units} </td>
							<td><button class="btn btn-link btn-xs" onclick="delInfo('4','${d.id}')">删除</button>
							|<button class="btn btn-link btn-xs" onclick="moveUp('4','${d.id}')">上移</button></td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		
		<div class="col-sm-6">
			<div class="panel panel-info">
				<div class="panel-heading">
					<div class="panel-title">出方监控项目</div>
					<div class="panel-tools"><button class="btn btn-success btn-xs" onclick="showAdd('5','出方监控项目')">添加</button></div>
				</div>
				<table class="table" style="margin:10px auto;width: 90%;">
					<c:forEach items="${data5}" var="d">
						<tr id="${d.id }">
							<td style="vertical-align: middle;">${d.prodCondition } &nbsp;，&nbsp;${d.units} </td>
							<td><button class="btn btn-link btn-xs" onclick="delInfo('5','${d.id}')">删除</button>
							|<button class="btn btn-link btn-xs" onclick="moveUp('5','${d.id}')">上移</button></td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
</div>

<div style="display: none;" id="formdiv">
	<div style="margin:20px 20px 0px 0px">
	<form action="${pageContext.request.contextPath}/lims/dataLedger/prodMesaddMinfo/" class="form-horizontal" onsubmit="return forSubmit()" > 
		<input type="hidden" name="areaName" value="${areaName}"/>
		<input type="hidden" name="plant" value="${plant}"/>
		<div class="form-group">
		    <label for="it1" class="control-label col-sm-3" style="padding-right: 0px;">项目：</label>
		    <div class="col-sm-9">
		    	<input type="text" name="value" class="form-control" id="it1">
		    </div>
		    
		 </div>
		 <div class="form-group" id="unitsdiv">
		    <label for="it2" class="control-label col-sm-3" style="padding-right: 0px;">单位：</label>
		    <div class="col-sm-9">
		    	<input type="text" name="units" class="form-control" id="it2">
		    </div>
		 </div>
		 <div class="form-group" >
		 	<div class="col-sm-offset-3 col-sm-9" style="text-align: right;">
      			<button type="submit" class="btn btn-primary">提交</button>
    		</div>
		 </div>
	</form>
	</div>
</div>

</body>
<script type="text/javascript">
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	
	function showAdd(ty,bt){
		var url = "${pageContext.request.contextPath}/lims/dataLedger/prodMes/addMinfo/"+ty;
		$("form").attr("action",url);
		if(ty=="2"||ty=="3"){
			$("#unitsdiv").hide();
		}else{
			$("#unitsdiv").show();
		}
		$(":text").val("");
		layer.open({
			type:1,
			title:bt,
			scrollbar:false,
			area: ['400px', '220px'],
			content: $('#formdiv')
		});
	}
	
	function forSubmit(){
		submitForm({url:$("form").attr("action"),data:$("form").serialize(),success:function(data){
			if(data.code!="SUCCESS"){
				layer.alert(data.message, {icon: 1,time:2000});
			}else{
				layer.close(index);
				window.location.reload();
			}
		}});
		return false;
	}
	
	function delInfo(ty,id){
		var url = "${pageContext.request.contextPath}/lims/dataLedger/prodMes/delMinfo/"+ty+"/"+id;
		submitPost({url:url,success:function(data){
			if(data.code=="SUCCESS"){
				$("#"+id).remove();
			}else{
				layer.alert(data.message, {icon: 1});
			}
		}});		
	}
	
	
	function moveUp(ty,id){
		//判断是否是第一行
		var prevTr = $("#"+id).prev();
		if(prevTr.length>0){
			var url = "${pageContext.request.contextPath}/lims/dataLedger/prodMes/moveUp/"+ty+"/"+id;
			submitPost({url:url,success:function(data){
				if(data.code=="SUCCESS"){
					 $("#"+id).insertBefore(prevTr); //将本身插入到目标tr的前面 
				}else{
					layer.alert(data.message, {icon:1});
				}
			}});	
		}	
	}
	
</script>
</html>