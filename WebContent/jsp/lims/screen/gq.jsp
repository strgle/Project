<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>罐区监控图</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/jquery.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/layui/css/layui.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/layui/layui.js"></script>

<style type="text/css">
	.header{
		position:fixed;
		top: 0px;
		left:0px;
		right:0px;
		height: 65px;
		background-color: #009688;
	}
	.header .baojing{
		position:absolute;
		height:54px;
		width:36px;
		left:20px;
		top:6px;
	}
	
	.header .oosbaojing{
		background-image: url("${pageContext.request.contextPath}/common/lims/screen/images/baojing.gif");
		background-size:100% auto;
		background-position:center middle;
		background-repeat: no-repeat;
	}
	
	.header .donebaojing{
		background-image: url("${pageContext.request.contextPath}/common/lims/screen/images/baojing.png");
		background-size:100% auto;
		background-position:center middle;
		background-repeat: no-repeat;
	}
	
	.header .title{
		position:relative;
		font-size: 26px;
		color: #DDFFF4;
		padding: 8px 20px;
		margin:0 auto;
		width: 400px;
		text-align:center;
		line-height: 45px;
	}
	
	.header .date{
		position:absolute;
		padding:5px 10px;
		bottom: 18px;
		right: 5px;
		width: 285px;
		font-size: 16px;
		font-weight: bold;
		letter-spacing:1px;
		background-color: #E4DD9E;
	}
	
	.content{
		position:fixed;
		display:flex;
		display: -webkit-flex;
		flex-wrap:wrap;
		justify-content:center;
		align-items:flex-start;
		align-content:flex-start;
		top: 65px;
		bottom: 0px;
		left:0px;
		right:0px;
		background-color: #FFFFFF;
		overflow: auto;
	}
	
	.panel{
		margin-bottom: 0px;
	}
	.panel-heading{
		background-color: #DDDDDD;
	}
	.panel-body{
		padding: 0px;overflow:hidden;
		padding-top:10px;
	}
	.block{
		width:120px;height: 150px;float: left;margin:5px 10px;
	}
	
	.imgDiv{
		background-repeat:no-repeat;
		background-size:100% 100%;
		height: 120px;
		padding-top: 40px;
		padding-left:10px;
		padding-right:10px;
		text-align: center;
		box-sizing: border-box;
	}
	
	.doneImg{
		background-image: url("${pageContext.request.contextPath}/common/lims/screen/images/gq.png");
	}
	.oosImg{
		background-image: url("${pageContext.request.contextPath}/common/lims/screen/images/gq.gif");
	}
	
	.qxgDoneImg{
		background-image: url("${pageContext.request.contextPath}/common/lims/screen/images/qxg.png");
	}
	.gxgOosImg{
		background-image: url("${pageContext.request.contextPath}/common/lims/screen/images/qxg.gif");
	}
	
	.ckDoneImg{
		background-image: url("${pageContext.request.contextPath}/common/lims/screen/images/cangku.png");
	}
	.ckOosImg{
		background-image: url("${pageContext.request.contextPath}/common/lims/screen/images/cangku.gif");
	}
	.imgDiv>label{
		font-size:15px;
	}
	ul,li{list-style:none;padding:10px;}
	li{border-bottom: 2px solid #DDDDDD;
		height:20px;
		font:normal 14px/20px 宋体;
	}
	
</style>
</head>
<body>
<div class="header">
	<div class="baojing donebaojing"></div>
	<div class="title"><span onclick="showArea()">${matTypes[0]}</span></div>
	
	<div class="date"><labe style="color:#C34538;" id="year">&nbsp;&nbsp;</labe>年<label style="color:#C34538;" id="month">&nbsp;&nbsp;</label>月<label style="color:#C34538;" id="day">&nbsp;&nbsp;</label>日
	&nbsp;星期<labe style="color:#C34538;" id="week">&nbsp;</labe>&nbsp;<label style="color:#C34538;" id="hour">&nbsp;&nbsp;</label>时<label style="color:#C34538;" id="minutes">&nbsp;&nbsp;</label>分</div>
</div>
<div class="content">
	
</div>

<div id="showArea" style="display: none;">
	<ul>
		<c:forEach items="${matTypes }" var="v">
			<li onclick="closeArea(this)" data-id="${v}" class="matType">${v}</li>
		</c:forEach>
	</ul>
</div>
</body>
<script type="text/javascript">
var formatNum=new Array("00","01","02","03","04","05","06","07","08","09","10","11","12")
var weekNum = new Array("日","一","二","三","四","五","六");
setInterval("refTime()", 1000*30);
function refTime(){  
	var myDate = new Date();
	var year = myDate.getFullYear();
	var month = myDate.getMonth();
	var day = myDate.getDate();
	var week = myDate.getDay();
	var hour = myDate.getHours();
	var minutes = myDate.getMinutes();
	$("#year").text(year);
	$("#month").text(formatNum[month+1]);
	if(day<10){
		$("#day").text(formatNum[day]);
	}else{
		$("#day").text(day);
	}
	$("#week").text(weekNum[week]);
	if(hour<10){
		$("#hour").text(formatNum[hour]);
	}else{
		$("#hour").text(hour);
	}
	
	if(minutes<10){
		$("#minutes").text(formatNum[minutes]);
	}else{
		$("#minutes").text(minutes);
	}
}  
refTime();


var layerIndex = 1;
function showArea(){
	layerIndex = layer.open({
		  type: 1,
		  title:false,
		  closeBtn:0,
		  shadeClose:true,
		  area: ['200px', '180px'], //宽高
		  offset: '55px',
		  content: $('#showArea')
		});
}

function closeArea(d){
	var area = $(d).text();
	var curArea = $(".title").children("span").text();
	layer.close(layerIndex);
	if(area!=curArea){
		$(".title").children("span").text(area);
		loadPlant();
		monitor();
	}
}

//加载装置信息
function loadPlant(){
	var matType = $(".title").children("span").text();
	//获取装置及采样点信息
	$.ajax({type: 'POST',
		async: false,
		dataType:"json",
		data:"matType="+matType,
		url:"${pageContext.request.contextPath}/lims/screen/gq/matType",
		success: function(data){
			var _html = createPlants(data);
			$(".content").html(_html);
		}
	});
}


function createPlants(data){
	var _html = "";
	for(var i = 0; i < data.length; i++) {
		var point = data[i];
		var plantType = point.plantType;
		var pointId = point.samplePointId;
		
		if(typeof(plantType) !="undefined"){
			if(plantType.toUpperCase()=="C"){
				_html = _html + '<div class="block" data-id="'+point.samplePointId+'"><div class="imgDiv qxgDoneImg" onclick="showPoint(\''+point.samplePointId+'\',\''+point.description+'\')"><label>'+point.matcode+'</label></div>';
				
			}else if(plantType.toUpperCase()=="A"){
				_html = _html + '<div class="block" data-id="'+point.samplePointId+'"><div class="imgDiv ckDoneImg" onclick="showPoint(\''+point.samplePointId+'\',\''+point.description+'\')"><label>'+point.matcode+'</label></div>';
			}else{
				_html = _html + '<div class="block" data-id="'+point.samplePointId+'"><div class="imgDiv doneImg" onclick="showPoint(\''+point.samplePointId+'\',\''+point.description+'\')"><label>'+point.matcode+'</label></div>';
			}
		}else{
			_html = _html + '<div class="block" data-id="'+point.samplePointId+'"><div class="imgDiv doneImg" onclick="showPoint(\''+point.samplePointId+'\',\''+point.description+'\')"><label>'+point.matcode+'</label></div>';
		}
		
		var pStart = pointId.indexOf("ZHHR");
		if(pStart == 0){
			_html = _html + '<div style="height:30px;text-align: center;font-size:15px;font-weight:bolder;" class="pointDesc">'+point.plant+'</div></div>';
		}else{
			_html = _html + '<div style="height:30px;text-align: center;font-size:15px;font-weight:bolder;" class="pointDesc">'+point.description+'</div></div>';
		}		
	}
	return _html;
}

layui.use(['element','layer'], function(){
	
});
loadPlant();

//采样点监控
function monitor(){
	var matType = $(".title").children("span").text();
	$.ajax({
		type: 'POST',
		dataType:"json",
		async:false,
		data:"matType="+matType,
		url:"${pageContext.request.contextPath}/lims/screen/gq/points",
		success: function(data){
			$(".oosImg").addClass("doneImg");
			$(".qxgOosImg").addClass("qxgDoneImg");  
			$(".ckOosImg").addClass("ckDoneImg"); 
			$(".oosImg").removeClass("oosImg");
			$(".qxgOosImg").removeClass("qxgOosImg");
			$(".ckOosImg").removeClass("ckOosImg");
			for(var i=0;i<data.length;i++){
				var imgDiv = $(".block[data-id='"+data[i]+"'] > .imgDiv");
				if($(imgDiv).hasClass("qxgDoneImg")){
					$(imgDiv).removeClass("qxgDoneImg");
					$(imgDiv).addClass("qxgOosImg");
				}else if($(imgDiv).hasClass("ckDoneImg")){
					$(imgDiv).removeClass("ckDoneImg");
					$(imgDiv).addClass("ckOosImg");
				}else{
					$(imgDiv).removeClass("doneImg");
					$(imgDiv).addClass("oosImg");
				}
			}
		}
	});
}

//采样点监控
function matmonitor(){
	$.ajax({
		type: 'POST',
		dataType:"json",
		url:"${pageContext.request.contextPath}/lims/screen/gq/allpoints",
		success: function(data){
			$(".matType").css("color","#000");
			$(".baojing").removeClass("oosbaojing");
			$(".baojing").addClass("donebaojing");
			for(var i=0;i<data.length;i++){
				var matType = $(".matType[data-id='"+data[i]+"']");
				$(matType).css("color","#F00");
				$(".baojing").removeClass("donebaojing");
				$(".baojing").addClass("oosbaojing");
			}
		}
	});
}

function allMonitor(){
	var m ="${m}";
	if(typeof(m)!="undefined"&&m=="0"){
		return;
	}
	matmonitor();
	monitor();
}
window.setInterval(allMonitor, 1000*60*15);
window.setTimeout(allMonitor,1000*2);

function showPoint(pointId,pointDesc){
	var url ="${pageContext.request.contextPath}/lims/screen/gq/sampleAccount?pointId="+pointId;
	var title=pointDesc;
	layer.open({
		type:2,
		area: ['90%','90%'],
		title: title,
		scrollbar: false,
		content:[url]
	});
}
</script>
</html>