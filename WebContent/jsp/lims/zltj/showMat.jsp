<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html lang="zh">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>关注样品统计</title>
	<link href="${pageContext.request.contextPath }/skins/css/laydateneed.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath }/skins/links/laydate/need/laydate.css" rel="stylesheet">
	<link rel="stylesheet" href="${pageContext.request.contextPath }/skins/lib/jquery-ui.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/skins/lib/font-awesome/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/skins/links/bootstrap-3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/skins/css/default.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath }/skins/dist/css/lobipanel.min.css"/>
	<%@include file="/common/header/jquery.jsp"%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/laydate/laydate.js"></script>
	<script src="${pageContext.request.contextPath }/skins/lib/jquery-ui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/bootstrap-3.3.7/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath }/skins/lib/highlight/highlight.pack.js"></script>
    <script src="${pageContext.request.contextPath }/skins/dist/js/lobipanel.min.js"></script>

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
				padding-left:5px;
				padding-right:5px;
				background-color: rgb(255,204,153);
                font-size: 12px;
			}
			
			.tdxm{
				white-space: nowrap;
			}
			
		 table td{
		 	border:1px solid #000;
			vertical-align: middle;
			font-size: 12px;
		 }
	</style>
</head>
<body id="bodyid">
<c:forEach items="${matTitle }" var="mt" varStatus="status">
<div class="panel panel-info">
<div class="panel-heading">
    <div class="panel-title">${matMap[mt.key]}</div>
</div>
<div class="panel-body">
   <table>
   		<thead>
   			<tr>
   				<th>样品编号</th>
   				<th>取样时间</th>
   				<th>取样地点</th>
   				<c:forEach items="${mt.value }" var="t">
   					<th>${t}</th>
   				</c:forEach>
   			</tr>
   		</thead>
   		<tbody>
   			<c:forEach items="${matData[mt.key] }" var="ord">
   				<tr>
                   
   					<td nowrap="nowrap" style="padding:0px 5px 0px 5px;">${ord.ordNo }</td>
   					<td nowrap="nowrap" style="padding:0px 5px 0px 5px;">${ord.sampdate }</td>
   					<td nowrap="nowrap" style="text-align: center;">${ord.description }</td>
   					
   					<c:forEach items="${mt.value}" var="t1">
   						<td style="text-align: center;">${ord.dataMap[t1] }</td>
   					</c:forEach>
   				</tr>
   			</c:forEach>
   		</tbody>
   </table>
    </div>
</div>
</c:forEach>
<script type="text/javascript">

  $(function(){
      $('.panel').lobiPanel({
          reload: false,
          close: false,
          editTitle:false,
          unpin:false,
          expand:false,
          minimize:{
                    icon:'fa fa-chevron-up',
                    icon2:'fa fa-chevron-down',
                    tooltip:'展开-收缩'
                }
                
      });
      
     // $(".panel-body").css("height",  );
 });
</script>
</body>
</html>
