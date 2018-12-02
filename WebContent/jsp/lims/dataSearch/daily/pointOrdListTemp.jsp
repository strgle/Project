<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<%@include file="/common/header/layui.jsp"%>
<%@include file="/common/header/default.jsp"%>
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
	
	tr.selected{
		background-color: #f5f5f5;
	}
	
	tr:HOVER{
		background-color: #f5f5f5;
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
	
	td{
		overflow:hidden;
		white-space:nowrap;
		text-overflow:ellipsis;
		-o-text-overflow:ellipsis;
		-moz-text-overflow: ellipsis;
		-webkit-text-overflow: ellipsis;
		max-width: 150px;
        font-size: 12px;
	}
	
	.table-condensed td,.table-condensed th{
		text-align: center;
        font-size: 12px;
	}
	
 </style>
</head>
<body>
<div class="panel panel-default" style="margin-bottom: 0px;">
	<div class="panel-body" style="padding:10px 20px;">
	<form class="form-inline" action="${pageContext.request.contextPath}/lims/dataSearch/daily/samplelistTemp">
		<input type="hidden" name="areaName" value="${areaName}"/>
		<input type="hidden" name="plant" value="${plant}"/>
		<input type="hidden" name="pointId" value="${pointId}"/>
		<input type="hidden" name="startDate" value="${startDate}"/>
		<input type="hidden" name="endDate" value="${endDate}"/>
		<div class="form-group">
			<label style="margin-left: 5px;margin-right:5px;"></label>
			<select class="form-control" name="status">
				<option value="">样品状态</option>
				<c:choose>
					
					<c:when test="${status=='OOS' }">
						<option value="Done">合格</option>
						<option value="OOS" selected="selected">超标</option>
					</c:when>
					<c:when test="${status=='Done' }">
						<option value="Done" selected="selected">合格</option>
						<option value="OOS">超标</option>
					</c:when>
					<c:otherwise>
						<option value="Done">合格</option>
						<option value="OOS">超标</option>
					</c:otherwise>
				</c:choose>
			</select>
		</div>
		<button type="submit" class="btn btn-success" style="margin-left: 20px;">筛选</button>
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
			<th style="text-align: center;">装置</th>
			<th style="text-align: center;">采样点</th>
			<th style="text-align: center;">样品名称</th>
			<th style="text-align: center;">状态</th>
			<th style="text-align: center;">采样时间</th>
			<th style="text-align: center;">发布时间</th>
			<th style="text-align: center;">样品编号</th>
			<th style="text-align: center;">样品类型</th>
			<th style="text-align: center;">任务类型</th>
			<th style="text-align: center;">样品备注</th>
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
	form = layui.form;
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
		_html += '<td>'+ord.plant+'</td>';
		_html += '<td>'+ord.pointdesc+'</td>';
		_html += '<td>'+ord.matname+'</td>';
		
		if(ord.status=="OOS"){
			_html += '<td style="white-space: nowrap;color:#D9534F;cursor:Pointer;" onclick="showDetail(\''+ord.ordno+'\',this)"><i class="fa fa-star"></i>&nbsp;超标</td>';
		}else if(ord.status=="Done"){
			_html += '<td style="white-space: nowrap;cursor:Pointer;" onclick="showDetail(\''+ord.ordno+'\',this)"><i class="fa fa-circle" style="color:#5CB85C;"></i>&nbsp;合格</td>';
		}else{
			_html += '<td style="white-space: nowrap;cursor:Pointer;" onclick="showDetail(\''+ord.ordno+'\',this)">&nbsp;进行中</td>';
		}
		
		_html += '<td>'+ord.sampdate+'</td>';
		_html += '<td>'+ord.apprdate+'</td>';
		_html += '<td>'+ord.ordno+'</td>';
		if(ord.type=='RAW'){
			_html += '<td>原辅料</td>';
		}else if(ord.type=='FP'){
			_html += '<td>成品</td>';
		}else if(ord.type=='LP'){
			_html += '<td>其它检测</td>';
		}else{
			_html += '<td>中控样</td>';
		}
		_html += '<td>'+ord.tasktype+'</td>';
		_html += '<td>'+ord.batchname+'</td>';
		_html += '<td><lable class="btn btn-link" style="padding:0px 2px;" onclick="showDetail(\''+ord.ordno+'\')">明细</lable>|<lable class="btn btn-link" style="padding:0px 2px;" onclick="showMenu(\''+ord.samplePointId+'\',event);">趋势图</lable></td>';
		_html = _html +'</tr>';
	    $("#dailyContent").append(_html);
	    new TableSorter("table");
	}
}


function showDetail(ordno,obj){
	$(".selected").removeClass("selected");
	$(obj).parent().addClass("selected");
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
				maxmin: true,
				scrollbar:false
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