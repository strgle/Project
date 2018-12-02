<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/header/meta.jsp"%>
<%@include file="/common/header/jquery.jsp"%>
<%@include file="/common/header/laydate.jsp"%>
<title>Insert title here</title>
<style type="text/css">
	html,body{
		margin:0px;
		padding:0px;
	}
	table{
		border-collapse: collapse;
	}
	th{
		white-space: nowrap;
		border: 1px solid #000;
		text-align: center;
		vertical-align: middle;
		background-color: rgb(204,255,204);
		padding: 10px;
        font-size: 12px;
	}
	
	.tdxm{
		white-space: nowrap;
	}
	
	
   table td{
   	border:1px solid #000;
   	text-align: center;
  	vertical-align: middle;
      font-size: 12px;
   }
 
</style>
</head>
<body>

<table id="expTable">
	<thead>
		<tr>
			<th colspan="7">
				<form action="${pageContext.request.contextPath }/lims/basic/zlhk/zlmb" method="post">
<input type="hidden" name="dept" value="${dept }"/>
       考核日期：<input type="text" id="startDate" name="startDate" class="dateTime" readonly="readonly" value="${startDate }"> &nbsp;至 &nbsp;&nbsp;
       <input type="text" id="endDate" name="endDate" class="dateTime" readonly="readonly" value="${endDate }">
       <input type="submit" value="查询"/><input type="button" value="导出至excel" onclick="expExcel()"/>
</form>  
			</th>
		</tr>
		</thead>
		<tbody id="table11">
			<tr>
				<th colspan="7">各车间质量目标</th>
			</tr>
			
			<tr>
				<th>车间</th>
				<th>装置</th>
				<th>质量目标</th>
				<th>总取样次数</th>
				<th>合格次数</th>
				<th>合格率</th>
				<th>执行力</th>
			</tr>
		
			<c:forEach items="${cjList}" var="cj">
				<tr>
					<td rowspan="${cj.rowNum}">${cj.name }</td>
					<td>${cj.data[0].name }</td>
					<td>${cj.data[0].zlmb }</td>
					<td class="totalNum">${cj.data[0].totalNum }</td>
					<td class="doneNum">${cj.data[0].doneNum }</td>
					<td><fmt:formatNumber  maxFractionDigits="2" value="${cj.data[0].qualifiedRate}" ></fmt:formatNumber></td>
					<td><fmt:formatNumber  maxFractionDigits="4" value="${cj.data[0].executivePower }" ></fmt:formatNumber></td>
				</tr>
				<c:forEach items="${cj.data}" begin="1" var="zz">
				<tr>
					<td>${zz.name }</td>
					<td class="zlmbNum">${zz.zlmb }</td>
					<td class="totalNum">${zz.totalNum }
						<c:if test="${zz.samplingCheckNum>0 }">
							(${zz.samplingCheckNum})
						</c:if>
					</td>
					<td class="doneNum">${zz.doneNum }</td>
					<td><fmt:formatNumber maxFractionDigits="2" value="${zz.qualifiedRate}" ></fmt:formatNumber></td>
					<td><fmt:formatNumber maxFractionDigits="4" value="${zz.executivePower }" ></fmt:formatNumber></td>
				</tr>
				</c:forEach>
			</c:forEach>
			<tr>
				<td colspan="2">公司总的质量目标</td>
				<td class="gsZlmb"><fmt:formatNumber  maxFractionDigits="2" value="${gsmb}" ></fmt:formatNumber></td>
				<td class="totalAll"></td>
				<td class="doneAll"></td>
				<td class="hglAll"></td>
				<td class="zxlAll"></td>
			</tr>
		</tbody>
		
</table>

<script type="text/javascript">
    //执行一个laydate实例
    laydate.render({
      elem: '#startDate' //指定元素
      ,theme: 'molv'
    });
    laydate.render({
      elem: '#endDate' //指定元素
      ,theme: 'molv'
    }); 
	
	 $("input[readOnly]").keydown(function(e) {
         e.preventDefault();
	});
	
	 $(function(){
			//获取需要统计的装置信息
			var zlmbNum = 0;
			var i = 0;
			$(".zlmbNum").each(function(index,element){
				
				i = i +1;
				zlmbNum=zlmbNum+parseFloat($(element).text());
			});
			var zlmbAll = zlmbNum/i;
			//$(".gsZlmb").text(zlmbAll.toFixed(2));
			var totalNum = 0;
			$(".totalNum").each(function(index,element){
				totalNum=totalNum+parseFloat($(element).text());
			});
			
			$(".totalAll").text(totalNum);
			
			var doneNum=0;
			$(".doneNum").each(function(index,element){
				doneNum=doneNum+parseFloat($(element).text());
			});
			
			$(".doneAll").text(doneNum);
			
			var hglAll = doneNum/totalNum*100;
			$(".hglAll").text(hglAll.toFixed(2));
			
			var zlmb = $(".gsZlmb").text();
			
			var zxlAll = hglAll/zlmb;
			
			$(".zxlAll").text(zxlAll.toFixed(4));
			
		}); 
	 
		
		function expExcel(){
			 //判断下载form是否存在
			 var $elemForm = $("#expExcelForm");
			 if($elemForm.length==0){
				 $eleForm = $("<form method='post' id='expExcelForm' enctype='multipart/form-data'></form>");
				 var expContent = $("<input type='hidden' id='expContent' name='expContent' />");
				 $eleForm.append(expContent);
			     $eleForm.attr("action","${pageContext.request.contextPath}/lims/common/expExcel");
			     $(document.body).append($eleForm);
			 }
			 var content ="<table>"+ $("#expTable").html()+"</table>";
			 
			 $("#expContent").val(content);
			 
		   //提交表单，实现导出
		   $eleForm.submit();
		}
</script>

</body>
</html>