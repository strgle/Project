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
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/css/laydateneed.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/js/laydate.css">
<%@include file="/common/header/layer.jsp"%>
<%@include file="/common/header/default.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/fuelux/css/ace.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/layui/css/layui.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/layui/layui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/js/laydate.dev.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/css/multiple-select.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/js/multiple-select.js"></script>

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
	<form class="form-inline" action="">
		<input type="hidden" name="areaName" id="areaName" value="${areaName}"/>
        <input type="hidden" name="plant" id="plant" value="${plant}"/>
		<input type="hidden" name="matcode" id="matcode" value="${matcode}"/>
		<div class="form-group">
			<label style="margin-left: 5px;margin-right:5px;">分析项:</label>
			<select name="analyte" id="analyte" multiple="multiple" style="width: 300px;">
               <c:forEach items="${analytes }" var="analytes">
                 <option value="${analytes.testcode}">${analytes.analyte }-${analytes.spSynonym }</option>
                 <%-- <option value="${analytes.testcode}">${analytes.analyte}</option> --%>
               </c:forEach>
            </select>
		</div>
		<div class="form-group">
         <span style="margin-left:10px;">时间：&nbsp;&nbsp;</span>
         <input class="form-control" name="startDate" id="startDate" placeholder="开始时间" value="${startTime}" type="text" autocomplete="off" />
        </div>
        <div class="form-group">
         <label style="margin-left: 5px;margin-right:5px;">至</label>
         <input class="form-control" name="endDate" id="endDate" placeholder="默认当前时间" value="${endTime}" type="text" autocomplete="off" />
        </div>
        <div class="form-group">
         <button type="button" class="btn btn-success" style="margin-left: 20px;" onclick="query()" >查询</button>
        </div>
        
	</form>
	</div>
</div>
<div style="width: 100%;height:100%;">
  <%-- <form class="form-inline" id="form" action="" target="ordList" method="post">
        <input type="hidden" name="areaName" id="areaName" value="${areaName}"/>
        <input type="hidden" name="plant" id="plant" value="${plant}"/>
        <input type="hidden" name="matcode" id="matcode" value="${matcode}"/>
        <input type="hidden" name="analytes" id="analytes" />
        <input type="hidden" name="testcodes" id="testcodes" />
        <input type="hidden" name="startTime" id="startTime" />
        <input type="hidden" name="endTime" id="endTime"  />
        
  </form> --%>
 <iframe id="ordList"  name="ordList" src="about:blank" style="border: 0px;width: 100%;height:100%;overflow:auto;"></iframe>
</div>

<script type="text/javascript">
$("#analyte").multipleSelect({
 	filter: true,
     width: 300,
     placeholder:'选择',
     selectAll:true
 });
laydate({
    elem: '#startDate',
    format: 'YYYY-MM-DD'
});

laydate({
    elem: '#endDate',
    format: 'YYYY-MM-DD'
}); 

function query(){
	var areaName = $("#areaName").val();
	var matcode = $("#matcode").val(); 
	var analytes = $('#analyte').multipleSelect('getSelects','text');
	var testcodes = $('#analyte').multipleSelect('getSelects', 'value');
	/* $("#analytes").val(analytes);
	$("#testcodes").val(testcodes); */
	var startTime = $("#startDate").val();
	var endTime = $("#endDate").val();
	var plant = $("#plant").val();
	/* $("#startTime").val(startTime);
	$("#endTime").val(endTime); */
	var params = "areaName="+areaName+"&matcode="+encodeURIComponent(matcode)+"&analytes="+encodeURIComponent(analytes)+"&testcodes="+testcodes+"&startTime="+startTime+"&endTime="+endTime+"&plant="+plant;
	layer.load(0, {
        shade: [0.1, '#fff'] 
    });
	/* $('#form').attr('action','${pageContext.request.contextPath}/lims/dataSearch/daily/samplelistAnalyteDetail');
	$('#form').submit(); */
	$("#ordList").attr("src","${pageContext.request.contextPath}/lims/dataSearch/daily/samplelistAnalyteDetail?"+params)
	
	layer.closeAll('loading'); 	
}

</script>
</body>
</html>
