<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@include file="/common/header/jquery.jsp"%>
<%@include file="/common/header/echarts.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath }/skin/links/echarts/echarts.min.js"></script>

<style type="text/css">
	table{
		border-collapse: collapse;
	}
	th{
		white-space: nowrap;
		border: 1px solid #000;
		text-align: center;
		vertical-align: middle;
		background-color: rgb(255,204,153);
		padding: 10px;
	}
	
	table td{
 		border:1px solid #000;
 		text-align: center;
		vertical-align: middle;
		padding: 5px;
	}

</style>
<script type="text/javascript">
	function showYC(analyte){
		$("#analyte").val(analyte);
		$("#formA").submit();
	}
	
	function changeItem(){
		var trs = $("#analyteBody").children();
	
		var val = $("#changeItem").val();
		var count = parseInt(val, 10); 
		
		var axisData = new Array();
		var data0 = new Array();
		var data1 = new Array();
		for(var i=0;i<count;i++){
			var tds = $(trs[i]).children();
			axisData.push($(tds[1]).text());
			data0.push($(tds[2]).text());
			data1.push($(tds[3]).attr("realNum"));
		}
		
		option.series[0].data = data0;
		option.series[1].data = data1;
		
		option.xAxis[0].data = axisData;
		option.xAxis[1].data = axisData;
		myChart.setOption(option);
	}
</script>
</head>
<body>
   <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
   <div id="main" style="height:400px;text-align: center; "></div>
    <div style="text-align: center;"><H3>${areaName }
   		<select id="changeItem" onchange="changeItem()">
   			<c:forEach items="${xTitle }" varStatus="status">
   				<option value="${status.count }">${status.count }</option>
   			</c:forEach>
   		</select></H3>
    </div>
    <div style="display: none;">
    	<form action="${pageContext.request.contextPath }/lims/zltj/kh/showYcfx"  id="formA" method="post">
    		<input type="hidden" name="isArea" value="${isArea }" />
    		<input type="hidden" name="analyte" value="" id="analyte" />
    		<input type="hidden" name="areaName" value="${areaName}" />
    		<input type="hidden" name="startDate" value="${startDate}" />
    		<input type="hidden" name="endDate" value="${endDate}" />
    	</form>
    </div>
   <div  style="text-align: center;" >
   	<table style="width: 800px;">
   		<tr>
   			<th>车间/装置</th>
   			<th>分析项目</th>
   			<th>异常数量</th>
   			<th>累计比例</th>
   			<th>操作</th>
   		</tr>
   		<tbody id="analyteBody">
   		
   		<c:forEach items="${xTitle }" varStatus="status" var="v">
   		<tr>
   			<td>${cjArea[status.index] }</td>
   			<td>${v}</td>
   			<td>${serail1[status.index] }</td>
   			<td realnum="${serail2[status.index] }"><fmt:formatNumber maxFractionDigits="2" value="${serail2[status.index] }"></fmt:formatNumber>%</td>
   			<td><a href="javascript:" onclick="showYC('${v}')">异常原因</a> </td>
   		</tr>
   		</c:forEach>
   		</tbody>
   		<tr>
   			<td colspan="2">总计</td>
   			<td>${sum}</td>
   			<td>100%</td>
   			<td>&nbsp;</td>
   		</tr>
   	</table>
   </div>
   
    
    <script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
        var title = eval('('+'${json}'+')');
        var maxNum = ${max};
     	
        var myChart = echarts.init(document.getElementById('main'));

        // 指定图表的配置项和数据
        var option = {
            title: {
            	text: '炼化一',
            	show:false
            },
            tooltip: {},
            legend: {
                data:['数量','累计比例']
            },
            xAxis: [{
                data: title.xTitle,
                axisLabel:{
               	 rotate:70,
               	 interval:0
                }
            },
            {
            	data: title.xTitle,
            	show:false
            }],
            yAxis: [
            	 {
                     type: 'value',
                     scale: false,
                     name: '数量',
                     max: maxNum,
                     min: 0
                    
                 },
                 {
                     type: 'value',
                     scale: false,
                     name: '累计比例',
                     max: 100,
                     min: 0,
                     splitLine:false,
                     axisLabel:{
                    	 formatter: '{value} %'
                     }
                 }
            ],
            series: [{
                name: '数量',
                type: 'bar',
                data: title.serail1,
                label: {
                    normal: {
                        show: true,
                        position:'top'
                    }
                }
            },
            {
            	 name: '累计比例',
                 type: 'line',
                 yAxisIndex:1,
                 xAxisIndex:1,
                 label: {
                     normal: {
                         show: true
                     }
                 },
                 data: title.serail2
            }]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    </script>

</body>
</html>