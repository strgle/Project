<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
				background-color: rgb(255,204,153);
				padding: 10px;
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
		#contentUl li{
		 	float:left;
		 	list-style: none;
		 	min-width:120px;
		 	margin:5px 10px 5px 0;
		 }
		 form{
		 	margin:0px;
		 	padding:0px;
		 }	
	</style>
</head>
<body>

<form action="${pageContext.request.contextPath }/lims/attention/attentionMat/showMat" method="post" target="contentIframe">
<input type="hidden" name="usrnam" value="${usrnam}"/>
<div class="panel panel-info" id="lobipanel-basic" style="margin:0px;padding:0px;">
<div class="panel-heading">
                 统计时间：<input type="text" id="startDate" name="startDate" readonly="readonly" value="${startDate }"> &nbsp;至 &nbsp;&nbsp;
       <input type="text" id="endDate" name="endDate" readonly="readonly" value="${endDate }">
       <input type="submit" value="查询" onclick=""/>
</div>
<div class="panel-body" style="display: none;">
   <table>
   		<c:forEach items="${areaMat }" var="area">
   		 <tr>
   		 	<td>${area.areaName }</td>
   		 	<td>
   		 		<ul id="contentUl">
   		 		<c:forEach items="${area.matArray }" var="mat">
   		 			<c:choose>
   		 				<c:when test="${mat.lastCheck=='1'}">
   		 					<li><input type="checkbox" name="checkMat" value="${mat.id }" checked="checked" onchange="checkChange(this)"/> <span style="color:red;"> ${mat.matName} </span> </li>
   		 				</c:when>
   		 				<c:otherwise>
   		 					<li><input type="checkbox" name="checkMat" value="${mat.id }"  onchange="checkChange(this)"/><span>${mat.matName}</span></li>
   		 				</c:otherwise>
   		 			</c:choose>                	
                </c:forEach>
                </ul>
   		 	</td>
   		 </tr>
   		</c:forEach>
   </table>
    </div>
</div>
</form>
<div id="divIframe" style="margin: 0px;padding: 0px;">
<iframe src="" width="100%" height="2000px" id="contentIframe" name="contentIframe" style="margin: 0px;padding:0px;"></iframe>
</div>
<script type="text/javascript">
    //执行一个laydate实例
    laydate.render({
      elem: '#startDate' //指定元素
      ,type: 'datetime'
    });
    laydate.render({
      elem: '#endDate' //指定元素
      ,type: 'datetime'
    });
     
  $("input[readOnly]").keydown(function(e) {
       e.preventDefault();
 });
  $(function(){
      $('#lobipanel-basic').lobiPanel({
          reload: false,
          close: false,
          editTitle:false,
          unpin:false,
          expand:false,
          minimize: {
              icon: 'fa fa-chevron-up',
              icon2: 'fa fa-chevron-down',
              tooltip : '选取车间--样品'
         }
      });
     $('#lobipanel-basic').lobiPanel("minimize");
     
     $("#contentIframe").attr("height", document.body.clientHeight-48);
     
 });
  
  function checkChange(obj){
    if(obj.checked){
     	$(obj).next().css("color","red");
    }else{
     	$(obj).next().css("color","black");
    }
  }
</script>
</body>
</html>
