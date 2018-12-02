<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/laydate/laydate.js"></script> --%>
<%@include file="/common/header/meta.jsp"%>
<%@include file="/common/header/jquery.jsp"%>
<%@include file="/common/header/laydate.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/font-awesome/css/font-awesome.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/skins/links/bootstrap-3.3.7/css/bootstrap.min.css">
 <link rel="stylesheet" href="${pageContext.request.contextPath}/skins/fuelux/css/ace.min.css">

<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/bootstrap-3.3.7/js/bootstrap.min.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/layer/layer.js"></script> 
 
<title>产品质量统计</title>
<style type="text/css">
	html,body{
		margin:0px;
		padding:0px;
		width: 100%;
		height: 100%;
	}
	
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
        font-size: 12px;
	}
	
	.tdxm{
		white-space: nowrap;
	}
	
 table td{
 	border:1px solid #000;
 	text-align: center;
	vertical-align: middle;
	background-color: rgb(240,247,251);
    font-size: 12px;
 }
 
</style>
</head>
<body>
<div style="margin: 0px;">
<table >
	<thead>
		<form action="${pageContext.request.contextPath }/lims/zltj/kh/detail" method="post">
        <tr>
          <th colspan="${fn:length(dateList)+6 }" style="text-align: left;">
                        选择班组：
                <select name="team" style="width: 150px;">
                 <option value="0">全部班组</option>
                 <c:choose>
                  <c:when test="${team=='1' }">
                   <option value="1" selected="selected">一班</option>
                   <option value="2">二班</option>
                   <option value="3">三班</option>
                   <option value="4">四班</option>
                  </c:when>
                  
                  <c:when test="${team=='2' }">
                   <option value="1">一班</option>
                   <option value="2" selected="selected">二班</option>
                   <option value="3">三班</option>
                   <option value="4">四班</option>
                  </c:when>
                  
                  <c:when test="${team=='3' }">
                   <option value="1">一班</option>
                   <option value="2">二班</option>
                   <option value="3"  selected="selected">三班</option>
                   <option value="4">四班</option>
                  </c:when>
                  <c:when test="${team=='3' }">
                   <option value="1">一班</option>
                   <option value="2">二班</option>
                   <option value="3">三班</option>
                   <option value="4"   selected="selected">四班</option>
                  </c:when>
                  <c:otherwise>
                   <option value="1">一班</option>
                   <option value="2">二班</option>
                   <option value="3">三班</option>
                   <option value="4">四班</option>
                  </c:otherwise>
                 </c:choose>
                 
                </select>
                          本月度第一班次开始时间：
                 <input type="text" id="teamStartTime" name="teamStartTime" readonly="readonly" value="${teamStartTime }">
         </th>
        </tr>
		<tr>
			<th colspan="${fn:length(dateList)+6 }" style="text-align: left;">
			<input type="hidden" name="area" id="area" value="${area }"/>
       		考核日期：<input type="text" id="startDate" name="startDate" readonly="readonly" style="width:100px;" value="${startDate }" > &nbsp;至 &nbsp;&nbsp;
       		<input type="text" id="endDate" name="endDate" readonly="readonly" value="${endDate }" style="width:100px;" >
       		
       		<input type="submit" value="查询"/> 
       		<input type="button" value="导出EXCEL" onclick="expExcel()"/> 
            <input type="button" value="异常分析项汇总" onclick="ycfx('${area }')"/>
			</th>
		</tr>
		</form>  
		</thead>
		<tbody id="table11">
			<tr>
				<th>装置名称</th>
				<th>产品名称</th>
				<th>分析项目</th>
				<th>控制指标</th>
				<th style="min-width: 60px;">项目</th>
				<th>月累计</th>
				<c:forEach items="${dateList }" var="d">
					<th>${d }</th>
				</c:forEach>
			</tr>
		<c:forEach var="zz" items="${rdata }" begin="0" varStatus="status">
			<tr>
				<td rowspan="${zz.rowNum+3}" style="background-color: rgb(255,204,153)"><c:out value="${zz.name}"/></td>
				<td rowspan="${zz.data[0].rowNum}" style="background-color: rgb(255,204,153)"><c:out value="${zz.data[0].name }"/></td>
				<td rowspan="3" style="background-color: rgb(255,204,153)" testCode="${zz.data[0].data[0].testcode}"><c:out value="${zz.data[0].data[0].name }"/></td>
				<td rowspan="3" style="background-color: rgb(255,204,153)"><c:out value="${zz.data[0].data[0].zb }"/></td>
				<td style="background-color: rgb(255,204,153);white-space: nowrap;">合格数</td>
				<td class="done${status.index }"><c:out value="${zz.data[0].data[0].totalDone}" /></td>
				<c:forEach items="${dateList }" var="rq">
					<td class="done${rq }${status.index}"><c:out value="${zz.data[0].data[0].doneMap[rq]}" /></td>
				</c:forEach>
			</tr>
			<tr><td style="background-color: rgb(255,204,153);white-space: nowrap;">总点数</td>
				<td class="all${status.index}"><c:out value="${zz.data[0].data[0].totalOos}" /></td>
				<c:forEach items="${dateList }" var="rq">
					<td class="all${rq}${status.index }"><c:out value="${zz.data[0].data[0].oosMap[rq]}" /></td>
				</c:forEach>
			</tr>
			<tr><td style="background-color: rgb(255,204,153);white-space: nowrap;">合格率</td>
				<td><fmt:formatNumber type="percent" maxFractionDigits="2" value="${zz.data[0].data[0].totalDone*1.0/zz.data[0].data[0].totalOos}" ></fmt:formatNumber></td>
				<c:forEach items="${dateList }" var="rq">
					<td><fmt:formatNumber type="percent" maxFractionDigits="2" value="${zz.data[0].data[0].hglMap[rq]}" ></fmt:formatNumber></td>
				</c:forEach>
			</tr>
			
			<c:forEach items="${zz.data }" begin="0" end="0" var="cp" varStatus="cps">
				<c:forEach items="${cp.data }" begin="1" var="fxxm">
							<tr><td rowspan="3" style="background-color: rgb(255,204,153)" testCode="${fxxm.testcode}"><c:out value="${fxxm.name }" ></c:out></td>
								<td rowspan="3" style="background-color: rgb(255,204,153)"><c:out value="${fxxm.zb }" ></c:out></td>
								<td class="tdxm" style="background-color: rgb(255,204,153)">合格数</td>
								<td class="done${status.index }"><c:out value="${fxxm.totalDone}" /></td>
								<c:forEach items="${dateList }" var="rq">
									<td class="done${rq }${status.index}"><c:out value="${fxxm.doneMap[rq]}" /></td>
								</c:forEach>
							</tr>
							<tr><td style="background-color: rgb(255,204,153);white-space: nowrap;">总点数</td>
								<td class="all${status.index }"><c:out value="${fxxm.totalOos}" /></td>
									<c:forEach items="${dateList }" var="rq">
									<td class="all${rq}${status.index }"><c:out value="${fxxm.oosMap[rq]}" /></td>
								</c:forEach>
							</tr>
							<tr ><td style="background-color: rgb(255,204,153);white-space: nowrap;">合格率</td>
								<td>
									<fmt:formatNumber type="percent" maxFractionDigits="2" value="${fxxm.totalDone*1.0/fxxm.totalOos}" ></fmt:formatNumber>
								</td>
									<c:forEach items="${dateList }" var="rq">
									<td><fmt:formatNumber type="percent" maxFractionDigits="2" value="${fxxm.hglMap[rq]}" ></fmt:formatNumber></td>
								</c:forEach>
							</tr>
						</c:forEach>
			</c:forEach>
			
			<c:forEach items="${zz.data }" begin="1" var="cp2">
				<td rowspan="${cp2.rowNum}" style="background-color: rgb(255,204,153)"><c:out value="${cp2.name }" ></c:out></td>
				<td rowspan="3" style="background-color: rgb(255,204,153)" testCode="${cp2.data[0].testcode}"><c:out value="${cp2.data[0].name }" ></c:out></td>
				<td rowspan="3" style="background-color: rgb(255,204,153)"><c:out value="${cp2.data[0].zb }"/></td>
				<td style="background-color: rgb(255,204,153);white-space: nowrap;">合格数</td>
				<td class="done${status.index }"><c:out value="${cp2.data[0].totalDone}" /></td>
				<c:forEach items="${dateList }" var="rq">
					<td class="done${rq }${status.index}"><c:out value="${cp2.data[0].doneMap[rq]}" /></td>
				</c:forEach>
				<tr><td style="background-color: rgb(255,204,153);white-space: nowrap;">总点数</td>
					<td class="all${status.index }"><c:out value="${cp2.data[0].totalOos}" /></td>
					<c:forEach items="${dateList }" var="rq">
					<td class="all${rq}${status.index }"><c:out value="${cp2.data[0].oosMap[rq]}" /></td>
				</c:forEach>
				</tr>
				<tr><td style="background-color: rgb(255,204,153);white-space: nowrap;">合格率</td>
					<td>
						<fmt:formatNumber type="percent" maxFractionDigits="2" value="${cp2.data[0].totalDone*1.0/cp2.data[0].totalOos}" ></fmt:formatNumber>
					</td>
				<c:forEach items="${dateList }" var="rq">
					<td><fmt:formatNumber type="percent" maxFractionDigits="2" value="${cp2.data[0].hglMap[rq]}" ></fmt:formatNumber></td>
				</c:forEach>
				</tr>
				<c:forEach items="${cp2.data }" begin="1" var="fxxm2">
				<tr><td rowspan="3" style="background-color: rgb(255,204,153)" testCode="${fxxm2.testcode}"><c:out value="${fxxm2.name }" ></c:out></td>
						<td rowspan="3" style="background-color: rgb(255,204,153)"><c:out value="${fxxm2.zb }"/></td>
						<td style="background-color: rgb(255,204,153);white-space: nowrap;">合格数</td>
						<td class="done${status.index }"><c:out value="${fxxm2.totalDone}" /></td>
						<c:forEach items="${dateList }" var="rq">
						<td class="done${rq }${status.index}"><c:out value="${fxxm2.doneMap[rq]}" /></td>
						</c:forEach>
				</tr>
							<tr><td style="background-color: rgb(255,204,153);white-space: nowrap;">总点数</td>
							<td class="all${status.index }"><c:out value="${fxxm2.totalOos}" /></td>
								<c:forEach items="${dateList }" var="rq">
								<td class="all${rq}${status.index }"><c:out value="${fxxm2.oosMap[rq]}" /></td>
								</c:forEach>
							</tr>
							<tr><td style="background-color: rgb(255,204,153);white-space: nowrap;">合格率</td>
							<td>
								<fmt:formatNumber type="percent" maxFractionDigits="2" value="${fxxm2.totalDone*1.0/fxxm2.totalOos}" ></fmt:formatNumber>
							</td>
							<c:forEach items="${dateList }" var="rq">
							<td><fmt:formatNumber type="percent" maxFractionDigits="2" value="${fxxm2.hglMap[rq]}" ></fmt:formatNumber></td>
							</c:forEach>
							</tr>
						</c:forEach>
			</c:forEach>
			<tr>
				<td colspan="4" style="background-color: rgb(255,204,153)">总合格点数</td>
				<td sumclass="done${status.index }" class="sumtotal done${status.index }sumtotal plantDone" >0</td>
				<c:forEach items="${dateList }" var="rq">
					<td sumclass="done${rq }${status.index}" class="sumtotal done${rq }${status.index}sumtotal plant${rq }Done">0</td>
				</c:forEach>
			</tr>
			<tr>
				<td colspan="4"  style="background-color: rgb(255,204,153)">总点数</td>
				<td sumclass="all${status.index }" class="sumtotal all${status.index }sumtotal plantAll">0</td>
				<c:forEach items="${dateList }" var="rq">
						<td sumclass="all${rq}${status.index }" class="sumtotal all${rq}${status.index }sumtotal plant${rq}All">0</td>
				</c:forEach>
			</tr>
			<tr>
				<td colspan="4"  style="background-color: rgb(255,204,153)">合格率</td>
				<td sumclass="${status.index }sumtotal" class="sumhgl">0</td>
				<c:forEach items="${dateList }" var="rq" >
						<td class="sumhgl" sumclass="${rq}${status.index }sumtotal">0</td>
				</c:forEach>
			</tr>
			
			</c:forEach>
				
			<tr>
				<td colspan="5" style="background-color: rgb(255,204,153);font-weight: bolder;">车间总合格点数</td>
				<td sumclass="Done" class="cjSum cjSumDone">0</td>
				<c:forEach items="${dateList }" var="rq">
					<td sumclass="${rq }Done" class="cjSum cjSumDone${rq}">0</td>
				</c:forEach>
			</tr>
			<tr>
				<td colspan="5"  style="background-color: rgb(255,204,153);font-weight: bolder;">车间总点数</td>
				<td sumclass="All" class="cjSum cjSumAll">0</td>
				<c:forEach items="${dateList }" var="rq">
						<td sumclass="${rq}All" class="cjSum cjSumAll${rq }">0</td>
				</c:forEach>
			</tr>
			<tr>
				<td colspan="5"  style="background-color: rgb(255,204,153);font-weight: bolder;">车间合格率</td>
				<td sumclass="" class="areaHgl">0</td>
				<c:forEach items="${dateList }" var="rq" >
						<td sumclass="${rq}" class="areaHgl">0</td>
				</c:forEach>
			</tr>
			
		</tbody>
</table>
</div>
</body>
<script type="text/javascript">
  laydate.render({
    elem: '#startDate' //指定元素
    ,theme: 'molv'
  });
  laydate.render({
    elem: '#endDate' //指定元素
    ,theme: 'molv'
  }); 
  laydate.render({
	    elem: '#teamStartTime' //指定元素
	    ,theme: 'molv'
	    ,type: 'datetime'
  }); 
  
$("input[readOnly]").keydown(function(e) {
    e.preventDefault();
});
$(function(){
	//获取需要统计的装置信息

	$(".sumtotal").each(function(index,element){
		var sumclass = $(element).attr("sumclass");
		var sum=0;
		$("."+sumclass).each(function(i,e){
			sum=sum+parseFloat($(e).text());
		});
		$(element).text(sum);
	});
	

	
	$(".sumhgl").each(function(index,element){
		var sumclass = $(element).attr("sumclass");
		var sum=0;
		var doneSum = $(".done"+sumclass).text();
		var allSum = $(".all"+sumclass).text();
		if(allSum=="0"){
			sum=0;
		}else{
			sum=parseFloat(doneSum)/parseFloat(allSum)*100;
		}
		$(element).text(sum.toFixed(2)+"%");
	});
	
	$(".cjSum").each(function(index,element){
		var sumclass = $(element).attr("sumclass");
		var sum=0;
		$(".plant"+sumclass).each(function(i,e){
			sum=sum+parseFloat($(e).text());
		});
		$(element).text(sum);
	});
	
	
	$(".areaHgl").each(function(index,element){
		var sumclass = $(element).attr("sumclass");
		var sum=0;
		var doneSum = $(".cjSumDone"+sumclass).text();
		var allSum = $(".cjSumAll"+sumclass).text();
		if(allSum=="0"){
			sum=0;
		}else{
			sum=parseFloat(doneSum)/parseFloat(allSum)*100;
		}
		$(element).text(sum.toFixed(2)+"%");
	});
});

function ycfx(area){
	var startDate  = $("#startDate").val();
	var endDate = $("#endDate").val();
	
	layer.open({
		  type: 2 //Page层类型
		  ,scrollbar:false
		  ,area: ['100%', '100%']
		  ,title: '异常分析项汇总'
		  ,content: '${pageContext.request.contextPath}/lims/zltj/kh/aycfx?area='+area+'&startDate='+startDate+'&endDate='+endDate
	});    
	//window.open("${pageContext.request.contextPath }/lims/zltj/kh/ycfx?method=ycfx&startDate="+startDate+"&endDate="+endDate+"&cj="+cj);
}
function expExcel(){
	 //判断下载form是否存在
	 var $elemForm = $("#expExcelForm");
	 if($elemForm.length==0){
		 $eleForm = $("<form method='post' id='expExcelForm' ></form>");
		 var expContent = $("<input type='hidden' id='expContent' name='expContent' />");
		
		 $eleForm.append(expContent);
	     $eleForm.attr("action","${pageContext.request.contextPath}/lims/common/expExcel");
	     $(document.body).append($eleForm);
	 }
	 var content ="<table>"+ $("#table11").html()+"</table>";
	 
	 $("#expContent").val(content);
	 
   //提交表单，实现导出
   $eleForm.submit();
}
</script>
</html>