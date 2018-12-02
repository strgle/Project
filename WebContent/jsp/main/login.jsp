<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>山东京博石油化工有限公司</title>
    <%@include file="/common/header/meta.jsp" %> 
    <%@include file="/common/header/bootstrap.jsp"%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/layer/layer.css"></script> 
    <script type="text/javascript" src="${pageContext.request.contextPath}/skins/links/layer/layer.js"></script> 
  
    <script type="text/javascript">
	
	if(self!=top){
		top.location.href="${pageContext.request.contextPath}/jsp/main/login.jsp";
	}
	
	</script>

<style type="text/css">
	 html, body {
            height: 100%;
    }
	 .box {
            filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#6699FF', endColorstr='#6699FF');
          
            margin: 0 auto;
            position: relative;
            width: 100%;
            height: 100%;
        }

        .login-box {
            width: 100%;
            max-width: 350px;
            height: 300px;
            position: absolute;
            top: 50%;
            margin-top: -200px;
           
            
        }
        .logoText{
           position: absolute;
            top: 30%;
            color: #fff;
        }
        @media screen and (min-width:350px) {
            .logoText {
                left: 40%;
                margin-left: -250px;
            }
        }
        @media screen and (min-width:350px) {
            .login-box {
                left: 80%;
                margin-left: -250px;
            }
        }

        .form {
            width: 100%;
            max-width: 350px;
            height: 275px;
            margin: 10px auto 0px auto;
            padding-top: 10px;
            float:left;
        }

        .login-content {
            height: 330px;
            width: 100%;
            max-width: 350px;
            /*background-color: rgba(255, 250, 2550, .6);*/
            float: left;
        }

        .input-group {
            margin: 0px 0px 20px 0px !important;
        }

        .form-control,
        .input-group {
            height: 40px;
        }

        .form-group {
            margin-bottom: 0px !important;
        }

        .login-title {
            padding: 10px 5px;
            background-color: #6699FF;
        }

         .login-title h1 {
             margin-top: 5px !important;
         }

         .login-title small {
             color: #fff;
         }

        .link p {
            line-height: 20px;
            margin-top: 30px;
        }

        .btn-sm {
            padding: 8px 10px !important;
            font-size: 16px !important;
        }
</style>
</head>
<body style="background-image:url('${pageContext.request.contextPath}/jsp/main/images/bg.png');background-repeat:no-repeat; background-size:100% 100%;-moz-background-size:100% 100%;">
<!-- action="${pageContext.request.contextPath}/framework/login" -->

<div class="box">
         <div class="logoText">
           <div class="text-center">
            <img alt="京博石化" src="${pageContext.request.contextPath}/common/img/t-logo.png">
            <h1>欢迎使用<br/>实验室信息管理系统</h1>
           </div>
           
         </div>
        <div class="login-box">
            <div class="login-title text-center">
                
                <h2><small>登录</small></h2>
            </div>
            <div class="login-content " style="background-color:white;">
                <div class="form" >
                    
                    <div class="form-group">
                        <div class="col-xs-12" >
                            <div class="input-group" >
                                <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
                                <input type="text" id="username" name="username" class="form-control"   style="text-transform:uppercase;" placeholder="用户名" required="required"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-12  ">
                            <div class="input-group">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
                                <input type="password" id="password" name="password" class="form-control" placeholder="密码" required="required"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-12  ">
                            <div class="input-group">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-th-list"></span></span>
                                <select class="selectpicker form-control" id="dept">
                                  <option>部门列表</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-12  ">
                            <div class="input-group">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-th-large"></span></span>
                                <select class="selectpicker form-control" id="role">
                                  <option>角色列表</option>
                                </select>
                            </div>
                        </div>
                    </div>
                     
                    <div class="form-group form-actions">
                        <div class="col-xs-4 col-xs-offset-4 ">
                            <button type="button" class="btn btn-sm btn-info" id="btnLogin">
                                <span class="glyphicon glyphicon-log-in" aria-hidden="true"></span>
                                登    录</button>
                            
                        </div>
                    </div>
                   <div class="form-group" id="lims">
                      
                    </div>
                </div>
            </div>
        </div>
         
    </div>
<div style="position:absolute;bottom:0px;left:0px;right:0px;height:30px;width: 100%;text-align: center;background-color: #000000;opacity: 0.2;line-height: 30px;color: #FFFFFF;font-size: 12px;">
	技术支持：北京三维天地科技有限公司
	
</div>
</body>
<script type="text/javascript">
  $(function(){
	  $("#username").on('input propertychange', function () {
          $("#password").val('');
          $("#dept").html('<option>部门列表</option>');
          $("#role").html('<option>角色列表</option>');
      });
      $("#password").on('input propertychange', function () {

          $("#dept").html('<option>部门列表</option>');
          $("#role").html('<option>角色列表</option>');
      });
      //获取用户权限下访问部门和角色
      $("#password").on('mouseleave', function (event) {
          var user = $("#username").val();
          var pwd = $("#password").val();
		//用户名不能为空提示
          if (user.length == 0) {
              
              $("#username").css('borderColor', 'red');
              $("#lims").html('<div class=" text-center" style="color:red;" id="accountMsg"><span class="glyphicon glyphicon-exclamation-sign"></span>用户名必填</div>');
              return false;
          }
		//密码不能为空提示
          if (pwd.length == 0) {
            
              $("#password").css('borderColor', 'red');
              $("#lims").html('<div class=" text-center" style="color:red;" id="accountMsg"><span class="glyphicon glyphicon-exclamation-sign"></span>密码必填</div>');
              return false;
          }
		//部门不能为空提示
          var dept = $("#dept").val();
          if (dept != "部门列表") {
              return;
          }
          //拼接请求查询字符串
          var data = "username="+user+"&&password="+pwd;
       	// Ajax请求获取信息
          $.ajax({
              url: "${pageContext.request.contextPath}/framework/login",
              data: data,
              dataType: "json",
              type: "post",
              beforeSend: function () {
            	  //请求前加载提示
            	  layer.load(1, {
                      shade: [0.1, '#fff'] 
                  });
                 
              },
              success: function (data) {
            	//声明变量用于存储获取数据
				var lists ="";
				var rlists = "";
				//判断返回数据是否有数据
				if(data[0].deptlist !=null){
					if(data[0].status !='Active'){
						$("#lims").html('<div class=" text-center" style="color:red;" id="accountMsg"><span class="glyphicon glyphicon-exclamation-sign"></span>用户名无效</div>');
						return;
					}
					var deptList = data[0].deptlist.split(",");
					for (var i = 0; i < deptList.length; i++) {

                        lists += '<option>' + deptList[i] + '</option>';
                    }
					
					$("#dept").html(lists);
					var roleList = data[1].role.split(",");
					for (var i = 0; i < roleList.length; i++) {

						rlists += '<option>' + roleList[i] + '</option>';
                    }
					$("#role").html(rlists);
					$("#lims").html('<div class=" text-center"  id="accountMsg"><span class="glyphicon glyphicon-exclamation-sign"></span>验证成功</div>');
				}
				else{
					 $("#lims").html('<div class=" text-center" style="color:red;" id="accountMsg"><span class="glyphicon glyphicon-exclamation-sign"></span>用户名或密码错误</div>');
				}
				layer.closeAll('loading'); 	
              },
              error: function () {
            	  layer.closeAll('loading'); 
                  $("#lims").html('<div class=" text-center" style="color:red;" id="accountMsg"><span class="glyphicon glyphicon-exclamation-sign"></span>获取失败，请联系系统管理员</div>');
              }
          });
      });
      $('#username,#password,#dept,#role').bind('keypress', function (event) {
          if (event.keyCode == "13") {
              
              return false;
          }
      });
      $('#btnLogin').on('click', function () {
    	  var user = $("#username").val();
          var pwd = $("#password").val();
          var dept = $("#dept").val();
          var role = $("#role").val();
          if (user.length == 0) {
              $("#username").css('borderColor', 'red');
              $("#lims").html('<div class=" text-center" style="color:red;" id="accountMsg"><span class="glyphicon glyphicon-exclamation-sign"></span>用户名必填</div>');
              return false;
          }

          if (pwd.length == 0) {
              $("#password").css('borderColor', 'red');
              $("#lims").html('<div class=" text-center" style="color:red;" id="accountMsg"><span class="glyphicon glyphicon-exclamation-sign"></span>密码必填</div>');
              return false;
          }
          if (dept.length == 0 || dept == "部门列表") {
              $("#dept").css('borderColor', 'red');
              $("#lims").html('<div class=" text-center" style="color:red;" id="accountMsg"><span class="glyphicon glyphicon-exclamation-sign"></span>公司列表必填</div>');
              return false;
          }
          if (role.length == 0 || role == "角色列表") {
              $("#role").css('borderColor', 'red');
              $("#lims").html('<div class=" text-center" style="color:red;" id="accountMsg"><span class="glyphicon glyphicon-exclamation-sign"></span>角色必填</div>');
              return false;
          }
          var data = "username="+user+"&&password="+pwd+"&&dept="+dept+"&&role="+role;
          //location.href ="${pageContext.request.contextPath}/framework/loginOn?username="+user+"&&password="+pwd+"&&dept="+dept+"&&role="+role;
         
          $.ajax({
              url: "${pageContext.request.contextPath}/framework/loginOn",
              data: data,
              dataType: "json",
              type: "post",
              beforeSend: function () {
            	 
                  $("#btnLogin").html('<span class="glyphicon glyphicon-log-in" aria-hidden="true"></span>    正在登录……');
                  $("#btnLogin").attr("disabled", true);
              },
              success: function (data) {
            	
            	  location.href ="${pageContext.request.contextPath}/jsp/main/main.jsp";
                  $("#btnLogin").html('<span class="glyphicon glyphicon-log-in" aria-hidden="true"></span>    登    录');
                  $("#btnLogin").attr("disabled", false);
                 
              },
              error: function () {
                  $("#btnLogin").html('<span class="glyphicon glyphicon-log-in" aria-hidden="true"></span>    登    录');
                  $("#btnLogin").attr("disabled", false);
              }
          }); 

      });
	  
  });
</script>
</html>