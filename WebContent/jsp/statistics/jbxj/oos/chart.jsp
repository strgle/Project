<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@include file="/common/header/jquery.jsp"%>
<%@include file="/common/header/echarts.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath }/skin/links/echarts/echarts.min.js"></script>

</head>
<body>
   <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
   <div id="main" style="height:400px;text-align: center; "></div>

   <script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例

        var title = {
		        
		 };
        var myChart = echarts.init(document.getElementById('main'));

        // 指定图表的配置项和数据
        var option = {
            title: {
            	text: '不合格项帕累托图',
            	x:'center',
            	show:false
            },
            tooltip: {
            	
            },
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
                     max: title.serail1MaxNum,
                     min: 0,
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
                barMaxWidth:50,
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

        $(function(){
        	var pltdata = parent.receiveReturn();
        	option.series[0].data = pltdata.serail1;
    		option.series[1].data = pltdata.serail2;
    		option.xAxis[0].data = pltdata.xTitle;
    		option.xAxis[1].data = pltdata.xTitle;
    		option.yAxis[0].max = pltdata.serail1MaxNum;
    		myChart.setOption(option);
        });
    </script>

</body>

</html>