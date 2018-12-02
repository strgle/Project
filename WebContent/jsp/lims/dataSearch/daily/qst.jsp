<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>趋势图</title>
<%@include file="/common/header/jquery.jsp"%>
<%@include file="/common/header/laydate.jsp"%>
<%@include file="/common/header/default.jsp"%>
<%@include file="/common/header/echarts.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/echarts/echarts.min.js"></script>
<style type="text/css">
	html,body{
		background-color: #F5F5F5;
		overflow: auto;
	}
</style>
</head>
<body>


<div id="main" style="width: 100%;height:360px;margin-bottom: 0px;padding-bottom: 0px;"></div>
<div style="text-align: center;margin-top: 0px;padding-top: 0px;">
	<form action="" id="analyteForm" style="margin: 0px;padding: 0px;">
		<input type="hidden" name="pointId" value="${pointId}"/>
        
		<c:forEach items="${analytes}" var="an">
		<input type="hidden" name="analyte" value="${an}"/>
		</c:forEach>
        <p id="v"></p>
		<input name="startDate" style="width: 150px;border: 0px;background-color: #F5F5F5;padding:5px 10px;text-align: right;" placeholder="开始时间" value="${startDate}" type="text" autocomplete="off" id="startDate"/>
		至
		<input name="endDate" style="width: 150px;border: 0px;background-color: #F5F5F5;padding:5px 10px;" placeholder="截止时间" value="${endDate}" type="text" autocomplete="off" id="endDate"/>
		
        <input type="submit" value="刷新"/>
	</form>
</div>

<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));
    var option = {
            tooltip:{
            	trigger: 'axis'
            },//鼠标悬浮时的提示信息
            yAxis:{
          	  	type : 'value',
            	scale: true
            },
            legend: {
            },
            xAxis: {
            	axisLabel:{
            		rotate :45
            	}
            },
            series:[
            	
            ]
       };
    
   var param= $("#analyteForm").serialize();
  //获取采样点下目前30个样品的数据分析项
	$.ajax({type: 'POST',
			async: false,
			dataType:"json",
			data:param,
			url:"${pageContext.request.contextPath}/lims/dataSearch/daily/qstdata",
			success: function(data){
				
				option.legend.data = data.legend;
				option.xAxis.type = "category";
				option.xAxis.data = data.xAxis;
				option.series= data.data;
				if(data.savg.length >0){
					var vtext = "&nbsp;平均值:"+data.savg+"&nbsp;最小值:"+data.smin+"&nbsp;最大值:"+data.smax+"&nbsp;标准差:"+data.sdev+"&nbsp;高线:"+data.higha+"&nbsp;底线:"+data.lowa;
					$("#v").html(vtext);
				}
				
				myChart.setOption(option);
			}
	});
	laydate.render({
		  elem: '#startDate'
	});
	laydate.render({
		  elem: '#endDate'
	});
</script>
    
</body>
</html>
