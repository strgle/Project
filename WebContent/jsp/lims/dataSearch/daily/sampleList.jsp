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
<%@include file="/common/header/table.jsp"%>
 <style type="text/css">
	html,body{
		background-color: #F5F5F5;
		overflow: hidden;
		height: 100%;
	}
	th{
		white-space: nowrap;
        font-size: 12px;
	}
    td{
     font-size: 12px;
    }
	ul{
		margin:0px;
		padding: 0px;
	}
	ul li{
		padding:0px 10px;
		height: 35px;
		margin:0px;
	}
	
	ul li label span{
		font-weight: normal;
		margin:0 0 0 5px;
	}
	
	ul li input{
		font-weight: normal;
		margin:0px;
	}
	
 </style>
</head>
<body>
<div class="panel panel-default" style="margin-bottom: 0px;">
	<div class="panel-body" style="padding:10px 20px;">
	<form class="form-inline" action="">
		<input type="hidden" name="type" value="${params.type}"/>
		<input type="hidden" name="keyValue" value="${params.keyValue}"/>
		<div class="form-group">
			<span>采样时间：&nbsp;&nbsp;</span>
			<input class="form-control" name="startTime" placeholder="开始时间" value="${params.startTime}" type="text" id="startTime" autocomplete="off"/>
		</div>
		<div class="form-group">
			<label style="margin-left: 5px;margin-right:5px;">至</label>
			<input class="form-control" name="endTime" placeholder="默认当前时间" value="${params.endTime}" id="endTime" type="text" autocomplete="off"/>
		</div>
		<button type="submit" class="btn btn-success" style="margin-left: 20px;">查询</button>
	</form>
	</div>
</div>
<div class="panel panel-default" style="margin-bottom:0px;padding: 0px;">
	<div class="panel-heading">
		结果列表
	</div>
	<div class="panel-body" style="padding:0px;overflow-y:auto;margin: 0px;" id="dataContent">
	<div id="panelContent" style="width:100%;">
	<table class="table table-bordered table-condensed" id="table">
		<thead><tr class="active">
			<th style="text-align: center;">序号</th>
			<th style="text-align: center;">车间</th>
			<th style="text-align: center;">装置</th>
			<th style="text-align: center;">样品名称</th>
			<th style="text-align: center;">样品备注</th>
			<th style="text-align: center;">状态</th>
			<th style="text-align: center;">采样时间</th>
			<th style="text-align: center;">发布时间</th>
			<th style="text-align: center;">样品编号</th>
			<th style="text-align: center;">样品类型</th>
			<th style="text-align: center;">任务类型</th>
			<th style="text-align: center;">链接</th>
		</tr></thead>
		<tbody id="dailyContent">
			
		</tbody>
	</table>
	</div>
	</div>
</div>

<div id="analyte" style="display: none;">
	<form action="" id="analyteForm" method="post" target="_blank">
	<input type="hidden" name="pointId" />
	<ul>
		
	</ul>
	</form>
</div>
</body>
</html>
<script type="text/javascript">
layui.use(['flow','layer','laydate',"form"],function(){
	var flow = layui.flow,
	layer = layui.layer,
	laydate = layui.laydate,
	form = layui.form();
	flow.load({
		elem: '#panelContent',
		scrollElem:'#dataContent',
		done:function(page,next){
			var lis = [];
			$.ajax({type: 'POST',
				async: false,
				data:"pageKey=${pageKey}",
				dataType:"json",
				url:"${pageContext.request.contextPath}/api/lims/dataSearch/result/detail",
				success: function(data){
					creatHtml(data);
					if(data.length==20){
						next(lis.join(""), true);
					}else{
						next(lis.join(""), false);
					}
				}
			});
		}
	});
	
	var start = {
			  format: 'YYYY-MM-DD hh:mm',
			  min: '2017-01-01 00:00', 
			  istime: true,
			  max: laydate.now()
			};
	var end = {
			  format: 'YYYY-MM-DD hh:mm',
			  istime: true,
			  min: '2017-01-01 00:00',
			  max: laydate.now()
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


var currentNum=0;
function creatHtml(data){
	for(var i=0;i<data.length;i++){
		currentNum = currentNum+1;
		var ord = data[i];
		if(ord==null){
			continue;
		}
		var _html = '<tr><td style="text-align: center;">'+currentNum+'</td>';
		_html += '<td>'+ord.areaName+'</td>';
		_html += '<td>'+ord.plant+'</td>';
		
		_html += '<td>'+ord.matname+'</td>';
		
		_html += '<td>'+ord.batchname+'</td>';
		
		if(ord.status=="OOS"){
			_html += '<td style="white-space: nowrap;color:#D9534F;cursor:Pointer;" onclick="showDetail(\''+ord.ordno+'\')"><i class="fa fa-star"></i>&nbsp;超标</td>';
		}else{
			_html += '<td style="white-space: nowrap;cursor:Pointer;" onclick="showDetail(\''+ord.ordno+'\')"><i class="fa fa-circle" style="color:#5CB85C;"></i>&nbsp;合格</td>';
		}
		
		_html += '<td>'+ord.sampdate+'</td>';
		_html += '<td>'+ord.apprdate+'</td>';
		_html += '<td>'+ord.ordno+'</td>';
		if(ord.type=='RAW'){
			_html += '<td>原辅料</td>';
		}else if(ord.type=='FP'){
			_html += '<td>成品</td>';
		}else if(ord.type=='LP'){
			_html += '<td>委托样</td>';
		}else{
			_html += '<td>中控样</td>';
		}
		_html += '<td>'+ord.tasktype+'</td>';
		
		_html += '<td><lable class="btn btn-link" style="padding:0px 2px;" onclick="showDetail(\''+ord.ordno+'\')">明细</lable>|<lable class="btn btn-link" style="padding:0px 2px;" onclick="showMenu(\''+ord.samplePointId+'\',event);">趋势图</lable></td>';
		_html = _html +'</tr>';
	    $("#dailyContent").append(_html);
	    new TableSorter("table");
	}
}


function showDetail(ordno){

	layer.open({
		  type: 2 //Page层类型
		  ,area: ['70%', '90%']
		  ,title: '检测结果'
		  ,content: '${pageContext.request.contextPath}/lims/dataSearch/daily/ordDetail?ordNo='+ordno
	});  
}

		
$(window).on('resize', function() {
	var height = $("body").height()-100;
	$("#dataContent").height(height);
}).resize();


function showMenu(pointId,event) {
	
	var dataNum = 0;
	//获取采样点下目前20个样品的数据分析项
	$.ajax({type: 'POST',
			async: false,
			dataType:"json",
			url:"${pageContext.request.contextPath}/api/lims/dataSearch/result/analytes/"+pointId,
			success: function(data){
				dataNum = data.length;
				creatMenu(data);
			}
	});
	$("input[name='pointId']").val(pointId);

	var windowHeight = $(window).height();
	var menuHeight = dataNum*35+98;
	var x = event.clientX-185;
	var y = event.clientY-menuHeight;

	if(y<0){
		y=0;
	}
	var areaHeight = menuHeight+"px";
	if(menuHeight>windowHeight){
		areaHeight=windowHeight+"px";
	}
	
	layer.open({
		type:1,
		title:"分析项",
		area: ['180px',areaHeight],
		offset:[y+"px",x+"px"],
		content:$('#analyte'),
		btn: ['确定'],
		yes:function(index,layero){
			var param = $("#analyteForm").serialize();
			
			var url = "${pageContext.request.contextPath}/lims/dataSearch/daily/qst?"+param;
			layer.close(index);
			layer.open({
				type:2,
				title:"趋势图",
				area:["100%","100%"],
				content:url,
				maxmin: true
			});
		}
		
	});
}

function creatMenu(data){
	if(data.length==0){
		layer.alert("该采样点下没有数据类型的测试信息。");
		return;
	}
	var _html = "";
	for(var i=0;i<data.length;i++){
		_html = _html + '<li><label ><input type="checkbox" name="analyte" value="'+data[i]+'"><span>'+data[i]+'</span></label></li>';
	}
	$("#analyte ul").html(_html);
}

</script>