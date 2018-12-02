<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>样品检测进度</title>
 <link rel="stylesheet" href="${pageContext.request.contextPath}/skins/font-awesome/css/font-awesome.min.css">
 <link rel="stylesheet" href="${pageContext.request.contextPath}/skins/bootstrap/css/bootstrap.min.css">
 <link rel="stylesheet" href="${pageContext.request.contextPath}/skins/css/style.css">
<%--  <link rel="stylesheet" href="${pageContext.request.contextPath}/skins/css/jquery.eeyellow.Timeline.css">
 <script type="text/javascript" src="${pageContext.request.contextPath}/skins/js/jquery.eeyellow.Timeline.js"></script> --%>
 <style type="text/css">
	html,body{
		padding:5px;
		background-color: #F5F5F5;
	}
 </style>
</head>
<body>

<div class="panel panel-success">
	<div class="panel-body">
		<div class="timeline-container">
			<%-- <c:if test="${not empty curStep }">
				<div class="timeline-block">
	            	<div class="timeline-icon yellow-bg">
	                	<i class="fa fa-file-text"></i>
	                </div>
					<div class="timeline-content">
	                    <h2>${curStep}</h2> 
	                 </div>
             	</div>
			</c:if> --%>
			<c:forEach items="${processInfo}" var="info">
				<div class="timeline-block">
	            	<div class="timeline-icon navy-bg">
	                	<i class="fa fa-file-text"></i>
	                </div>
					<div class="timeline-content">
	                    <h2>${info.stepname}</h2>
	                    <p>操作人：${info.fullname}&nbsp;&nbsp;</p>
	                    <span class="timeline-date"><small>${info.auditdate}&nbsp;${info.audittime }</small></span>
	                 </div>
             	</div>
			</c:forEach>
		</div>
	</div>
</div>
</body>
</html>
