<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>装置日报信息</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/skins/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/js/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/laydate/laydate.js"></script>
<style type="text/css">
	html,body{
		padding: 5px;
		background-color: #ECECEC;
	}

	.panel-default>.panel-heading{
		background-color: #FFFFFF;
	}
	
	.form-input {
	  width:140px;
	  padding-left:12px;
	  font-size: 14px;
	  line-height: 1.42857143;
	  color: #555;
	  background-color: #fff;
	}
</style>
</head>
<body>
<div class="panel panel-default" style="margin-bottom:0px;">
	<div class="panel-heading">
		<div class="panel-title">
			<labe style="margin-right:20px;padding:5px 10px;font-size:14px;background-color:#5CB85C;color:#FFFFFF;">${areaName}&nbsp;—&nbsp;${plant}</labe>
		</div>
	</div>
</div>
<form action="" method="post">
<div class="panel panel-default">
	<input type="hidden" name="areaName" value="${areaName}"/>
	<input type="hidden" name="plant" value="${plant}"/>
	<input type="hidden" name="oldProdDate" value="${prodDate}" id="oldProdDate"/>
	<div class="panel-heading">
		生产日期：<input name="prodDate" class="laydate-icon form-input" placeholder="日期" value="${prodDate}" id="prodDate" type="text" autocomplete="off" onclick="laydate({choose:showDate})"/>		
		<div class="panel-tools" style="top: 5px;">
			<label class="btn btn-sm btn-success" onclick="save()">保存</label>
			<label class="btn btn-sm btn-success" onclick="mesShow()">返回</label>
		</div>
	</div>
	<div class="panel-body">
		<table class="table table-bordered">
			<thead>
				<tr>
					<th>项目</th>
					<c:forEach items="${prodTimes}" var="time">
						<th>${time}</th>
					</c:forEach>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${prodCondition}" var="v" varStatus="row">
					<tr>
						<td>${v['prodCondition'] }
							<c:if test="${not empty v['units']}">
								, ${v['units']}
							</c:if>
						</td>
						<c:if test="${prodColumnNum>0 }">
							<c:forEach begin="0" end="${prodColumnNum-1}" var="column" >
							<td>
								<input class="movetext" type="text" name="${v['prodCondition']}@@${prodTimes[column]}" value="${datas[row.index][column] }" onkeydown="movefocus(event)"/>
							</td>
							</c:forEach>
						</c:if>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
</form>

</body>
<script type="text/javascript">
	function showDate(datas){
		var curDate = $("#oldProdDate").val();
		if(curDate!=datas){
			var url="${pageContext.request.contextPath}/lims/dataLedger/prodMes/prodMesShow"
			$("form").attr("action",url);
			$("form").submit();
		}
	}
	
	function mesShow(datas){
		var url="${pageContext.request.contextPath}/lims/dataLedger/prodMes/prodMesShow"
		$("form").attr("action",url);
		$("form").submit();
	}
	
	function save(){
		var url="${pageContext.request.contextPath}/lims/dataLedger/prodMes/prodMesAdd"
		$("form").attr("action",url);
		$("form").submit();
	}
	
	function movefocus(e){
		e=window.event||e;
		var obj = e.srcElement?e.srcElement:e.target; 
		switch(e.keyCode){
		    case 37: //左键
		      changeLeft(obj);
		      break;
		    case 38: //向上键
		      changeUp(obj);
		      break;
		    case 39: //右键
		      changeRight(obj);
		      break;
		    case 40: //向下键
		      changeDown(obj);
		      break;
		    default:
		      break;
		  }
	}
	
	function changeLeft(obj){
		var p=obj.selectionStart;
		if(p==0){
			//判断当前的位置
			var col = $(obj).parent().prevAll().length;
			var row=$(obj).parent().parent().prevAll().length;
			if(col>1){
				//前移children
				var t = $(obj).parent().prev().children(":text");
				var v = $(t).val();
				$(t).focus().val(v);
				
			}else if(row>0){
				//上移
				var t = $(obj).parent().parent().prev().children("td:last").children(":text");
				var v = $(t).val();
				$(t).focus().val(v);
			}
		}
	}
	
	function changeRight(obj){
		var p=obj.selectionStart;
		var l = $(obj).val().length;
		if(p==l){
			//判断当前的位置
			var nextTd = $(obj).parent().next();
			var nextTr=$(obj).parent().parent().next();
			if($(nextTd).length>0){
				var t = $(nextTd).children(":text");
				var v = $(t).val();
				$(t).focus().val(v);
			}else if($(nextTr).length>0){
				var t = $(nextTr).children("td:eq(1)").children(":text");
				var v = $(t).val();
				$(t).focus().val(v);
			}
		}
	}
	
	function changeDown(obj){
		//判断当前的位置
		var col = $(obj).parent().prevAll().length;
		var nextTr=$(obj).parent().parent().next();
		if($(nextTr).length>0){
			var t = $(nextTr).children("td:eq("+col+")").children(":text");
			var v = $(t).val();
			$(t).focus().val(v);
		}
	}
	

	function changeUp(obj){
		//判断当前的位置
		var col = $(obj).parent().prevAll().length;
		var prevTr=$(obj).parent().parent().prev();
		if($(prevTr).length>0){
			var t = $(prevTr).children("td:eq("+col+")").children(":text");
			var v = $(t).val();
			$(t).focus().val(v);
		}
	}
</script>
</html>