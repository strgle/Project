<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>生产流程体系</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/common/lims/screen/js/scroll.js"></script>
<style type="text/css">
	.header{
		position:fixed;
		top: 0px;
		left:0px;
		right:0px;
		height: 75px;
		background-color: #009688;
	}
	
	.header .title{
		position:relative;
		font-size: 26px;
		color: #DDFFF4;
		padding: 10px 20px;
		margin:0 auto;
		width: 400px;
		line-height: 55px;
	}
	.header .date{
		position:absolute;
		background-color:#FFF;
		padding:5px 10px;
		top: 8px;
		right: 5px;
		width: 155px;
		font-size: 14px;
		font-weight: bold;
		letter-spacing:1px;
	}
	
	.header .time{
		position:absolute;
		background-color:#FFF;
		padding:5px 10px;
		top:43px;
		right: 5px;
		width: 155px;
		font-size: 14px;
		font-weight: bold;
		letter-spacing:1px;
	}
	
	.content{
		position:fixed;
		top: 75px;
		bottom: 0px;
		left:0px;
		right:0px;
		background-color: #FFFFFF;
	}
	.footer{
		position:fixed;
		bottom: 0px;
		height: 0px;
		left:0px;
		right:0px;
		background-color: #6b6d6e;
	}
	.table{
		width: 100%;
		border-collapse: collapse;
	}
	.table td{
		padding:0px;
	}
	.table th{
		padding:8px;
		font-size:20px;
		font-weight: normal;
		background-color:  #009688;
		color: #DDFFF4;
		
	}

	.table td,th{
		border: 2px solid #DDDDDD;
	}
	table{
		height: 100%;
	}
	td{
		text-align: center;
	}

	ul,li,dl,ol{list-style:none;margin: 0px;padding: 0px;}
	.list_lh{overflow:hidden;margin: 0px;}
	.list_lh li{padding:10px;margin: 0px;border-bottom: 2px solid #DDDDDD;}
	.list_lh li p{height:20px; margin:3px;font:normal 14px/20px 宋体;}
	.list_lh li p a{float:left;}
	.list_lh li p label{min-width:80px;background:#28BD19; float:right; display:inline-block;padding: 2px 5px;}
	.list_lh li p span{float:right;}
	
	.list_lh div{min-height:100px; margin:0px;text-align: left;padding: 10px;border-top:1px solid #ddd; }
	
	.list_xx div{
		font:normal 20px/20px 宋体 !important;
		white-space: pre-wrap;
	}
</style>
</head>
<body>
<div class="header">
	<div class="title">中化弘润质量检测中心 </div>
	<div class="date"><labe style="color:#C34538;" id="year">&nbsp;&nbsp;&nbsp;&nbsp;</labe>&nbsp;年&nbsp;<label style="color:#C34538;" id="month">&nbsp;&nbsp;</label>&nbsp;月&nbsp;<label style="color:#C34538;" id="day">&nbsp;&nbsp;</label>&nbsp;日&nbsp;</div>
	<div class="time">星期&nbsp;<labe style="color:#C34538;" id="week">&nbsp;</labe>&nbsp;<label style="color:#C34538;margin-left:1px;" id="hour">&nbsp;&nbsp;</label>&nbsp;时&nbsp;<label style="color:#C34538;" id="minutes">&nbsp;&nbsp;</label>&nbsp;分&nbsp;</div>
</div>
<div class="content">
	<table class="table table-bordered">
		<tr style="height: 40px;" >
			<th style="width: 25%;" >待采样/接收样品(<label id="ts1">${fn:length(toReceive) }</label>)</th>
			<th style="width: 25%;">检测中样品(<label id="ts2">${fn:length(testing) }</label>)</th>
			<th style="width: 25%;">待审核样品(<label id="ts3">${fn:length(approval) }</label>)</th>
			<th style="width: 25%;">消息通知</th>
		</tr>
		<tr>
			<td>
				<div class="list_lh" id="lh1">
					<ul>
					<c:forEach items="${toReceive}" var="d">
						<li>
							<p><a>${d['ordno']}</a><label>${d['matname']}</label></p>
							<p style="margin-top: 10px;"><a>
							<c:if test="${!fn:startsWith(d['samplePointId'],'ZHHR')}">${d['plant']}</c:if>
							<c:if test="${fn:startsWith(d['samplePointId'],'ZHHR')}">${d['pointdesc']}</c:if></a><span>${d['sampdate'] }</span></p>
						</li>
					</c:forEach>
					</ul>
				</div>
			</td>
			<td><div class="list_lh" id="lh2">
					<ul>
					<c:forEach items="${testing}" var="d">
						<li>
							<p><a>${d['ordno']}</a><label>${d['matname']}</label></p>
							<p style="margin-top: 10px;"><a>
							<c:if test="${!fn:startsWith(d['samplePointId'],'ZHHR')}">${d['plant']}</c:if>
							<c:if test="${fn:startsWith(d['samplePointId'],'ZHHR')}">${d['pointdesc']}</c:if></a><span>${d['sampdate'] }</span></p>
						</li>
					</c:forEach>
					</ul>
				</div></td>
			<td><div class="list_lh" id="lh3">
					<ul>
					<c:forEach items="${approval}" var="d">
						<li>
							<p><a>${d['ordno']}</a><label>${d['matname']}</label></p>
							<p style="margin-top: 10px;"><a>
							<c:if test="${!fn:startsWith(d['samplePointId'],'ZHHR')}">${d['plant']}</c:if>
							<c:if test="${fn:startsWith(d['samplePointId'],'ZHHR')}">${d['pointdesc']}</c:if></a><span>${d['sampdate'] }</span></p>
						</li>
					</c:forEach>
					</ul>
				</div></td>
			<td><div class="list_lh  list_xx" id="lh4">
				<div>${xxtz['msg1']}</div>
				<div>${xxtz['msg2']}</div>
				<div>${xxtz['msg3']}</div>
			</div></td>
		</tr>
	</table>
</div>
<div class="footer">&nbsp;</div>
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

//设置div高度
$(window).on('resize', function() {
	var  ch= $('.content').height();
	$('.list_lh').height(ch-46);
}).resize();
	
$(function(){
	$("#lh1").myScroll({
		speed:30, //数值越大，速度越慢
		rowHeight:76 //li的高度
	});
	$("#lh2").myScroll({
		speed:30, //数值越大，速度越慢
		rowHeight:77 //li的高度
	});
	$("#lh3").myScroll({
		speed:30, //数值越大，速度越慢
		rowHeight:76 //li的高度
	});
});


setInterval("refLocation()", 600000);
function refLocation(){
	location.reload();
}

function yuyin(){
	var vbs = new ActiveXObject('Sapi.SpVoice') ;
	var ts1 = parseInt($("#ts1").text());
	var ts2 = parseInt($("#ts2").text());
	var ts3 = parseInt($("#ts3").text());

	if(ts1>0){
		  var sound="有"+ts1+"条待接收样品";
		  vbs.Speak(sound);
	}
	if(ts2>0){
		  var sound="有"+ts2+"条未检测完样品";
		  vbs.Speak(sound);
	}
	if(ts3>0){
		  var sound="有"+ts3+"条待审核样品";
		  vbs.Speak(sound);
	}
}
</script>
</html>