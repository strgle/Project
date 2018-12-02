<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/header/meta.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/css/laydateneed.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/js/laydate.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/js/laydate.dev.js"></script>
<%@include file="/common/header/jquery.jsp"%>
<%@include file="/common/header/layer.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/layui/css/layui.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/layui/layui.js"></script>
<%@include file="/common/header/bootstrap.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/css/multiple-select.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/js/multiple-select.js"></script>
<title>免考核信息添加</title>
<style type="text/css">
	html, body {
			font-family: Arial,sans-serif;
			font-size: 13px;
			margin: 0;
			padding: 0;
		}
		
	table{
		border-collapse: collapse;
	}
	th{
		white-space: nowrap;
		border: 1px solid #000;
		text-align: right;
		vertical-align: middle;
		background-color: rgb(240,240,240);
		padding: 10px;
        font-size: 12px;
	}
	
   table td{
   	border:1px solid #000;
  	vertical-align: middle;
  	padding: 10px;
    font-size: 12px;
  	
   }
   
   form{
   	margin: 0px;
   	padding: 0px;
   }
</style>

</head>
<body>
<form action="" id="form_action">
<table style="min-width: 100%;">
		<tr>
			<th style="width: 15%;text-align: right;">车间</th>
			<td style="width: 85%;text-align: left;">${areaName } <input type="hidden" name="areaName" value="${areaName}" id="areaName"/></td>
		</tr>
		<tr>
			<th style="width: 15%;text-align: right;">装置</th>
			<td style="width: 85%;text-align: left;">
				<select name="plant" onchange="changePlant()" id="plant">
					<c:forEach items="${plants }" var="plant">
						<option value="${plant}">${plant}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		
		<tr>
			<th style="width: 15%;text-align: right;">样品名称</th>
			<td style="width: 85%;text-align: left;">
				<select name="matCode" id="matCode" onchange="changeMatcode()" style="width: 300px;">
					
				</select>
			</td>
		</tr>
       <tr>
       <th style="width: 15%;text-align: right;">分析项目</th>
       <td style="width: 85%;text-align: left;">
         <select name="analyte" id="analyte" multiple="multiple" style="width: 300px;">
              
          </select>
        </td>
        </tr>
		<tr>
			<th style="width: 15%;text-align: right;">开始时间</th>
			<td style="width: 85%;text-align: left;"><input type="text" name="startTime" id="startTime" value=""  readonly="readonly"/></td>
		</tr>
		<tr>
			<th style="width: 15%;text-align: right;">截止时间</th>
			<td style="width: 85%;text-align: left;"><input type="text" name="endTime" id="endTime" value=""  readonly="readonly"/></td>
		</tr>
		<tr>
			<th style="width: 15%;text-align: right;">不考核原因</th>
			<td style="width: 85%;text-align: left;"><textarea rows="3" cols="30" name="remark" id="remark"></textarea> </td>
		</tr>
		<tr>
			<td style="width: 100%;text-align: right;"><input type="button" style="width: 80px;"  value="确定" onclick="addPlant()"/></td>
		    <td style="width: 100%;text-align: left;"><input type="button" style="width: 80px;"  value="取消" onclick="cancel()"/></td>
		</tr>
	</tbody>
</table>
</form> 
<script type="text/javascript">
   
 	laydate({
	    elem: '#startTime',
	    format: 'YYYY-MM-DD hh:mm:ss',
	    istime: true
	});

	laydate({
	    elem: '#endTime',
	    format: 'YYYY-MM-DD hh:mm:ss',
	    istime: true
	});
  $("input[readOnly]").keydown(function(e) {
        e.preventDefault();
 });
 
  //获取样品信息
 $(function(){
  	changePlant();
 });
 
 function changePlant(){
  var areaName=$("#areaName").val();
  var plant = $("#plant").val();
   $.ajax({
    url:"${pageContext.request.contextPath}/lims/zltj/kh/queryMatByAreaPlant",
    type:"post",
    async:false,
    data:{areaName:areaName,plant:plant},
    dataType:"json",
    success:function(data){
    
     $("#matCode").empty();
     for(var i=0;i<data.length;i++){
       var optionV = $("<option value='"+data[i].matcode+"'>"+data[i].matname+"</option>");
       $("#matCode").append(optionV);
     }
    }
   });
 }
 
 function changeMatcode(){
	 var areaName=$("#areaName").val();
	 var matCode = $("#matCode").val();
	 
	 $.ajax({
		    url:"${pageContext.request.contextPath}/lims/zltj/kh/queryAnalyteByMatcode",
		    type:"post",
		    async:false,
		    data:{areaName:areaName,matcode:matCode},
		    dataType:"json",
		    success:function(data){
		    	
		     $("#analyte").empty();
		     
		     for(var i=0;i<data.length;i++){
		      var optionV = $("<option value='"+data[i].analyte+"'>"+data[i].analyte+"</option>");
		      //var optionV = $("<option value='1'>1</option>");
		      $("#analyte").append(optionV);
		      
		     }
		     $("#analyte").multipleSelect({
		     	filter: true,
		         width: 300,
		         placeholder:'选择',
		         selectAll:false
		     });
		 }
	});
	 
 }
 function addPlant(){
		var startTime = $("#startTime").val();
		var areaName=$("#areaName").val();
		var endTime = $("#endTime").val();
		var matCode = $("#matCode").val();
		var plant = $("#plant").val();
		var analyte = $('#analyte').multipleSelect('getSelects');
		var remark = $("#remark").val();
		if(startTime==""||endTime==""){
			
			layui.use(['layer'],function(){
	    		 var layer = layui.layer;
	    		 layer.msg('开始时间或结束时间不能为空！', {icon: 2});
	    	 });
			return;
		}

		if(matCode==""||matCode==null){
			
			layui.use(['layer'],function(){
	    		 var layer = layui.layer;
	    		 layer.msg('样品不能为空！', {icon: 2});
	    	 });
			return;
		}
		if(analyte==""||analyte==null){
			
			layui.use(['layer'],function(){
	    		 var layer = layui.layer;
	    		 layer.msg('样品分析项目不能为空！', {icon: 2});
	    	 });
			return;
		}
		
		$.ajax({
			type:"post",
			url:"${pageContext.request.contextPath }/lims/zltj/kh/addPlant",
			data:{areaName:areaName,matCode:matCode,startTime:startTime,endTime:endTime,plant:plant,remark:remark,analyte:analyte},
			async: false,
			success:function(data){
				
				parent.window.location.href="${pageContext.request.contextPath }/lims/zltj/kh/apply";//刷新父窗口
		        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		        parent.layer.close(index);
				
			},
			error:function(request){
				return;
			}
		});
		
	}
	
 function cancel(){
	 var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	 parent.layer.close(index);
 }
	
</script>
</body>

</html>
