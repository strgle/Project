var n = document,
i = function() {
		    var e = n.scripts,
		    t = e[e.length - 1].src;
		    return t.substring(0, t.lastIndexOf("/common/"))
		};
var _curProject = i();
//var _curProject = "";
var navtab;
layui.config({
	base:_curProject+'/skins/plugins/'
}).use(['element', 'layer', 'navbar','navtab'], function() {
	var element = layui.element(),
		$ = layui.jquery,
		layer = layui.layer,
		navbar = layui.navbar();
		navtab = layui.navtab();
		
	//设置navbar
	navbar.set({
		spreadOne: true,
		elem: '#admin-navbar-side',
		url: _curProject+'/sys/menu/queryMenu/1?'+Math.random()
	});
	
	//渲染navbar
	navbar.render();
	
	//设置菜单折叠
	$('.admin-side-toggle').click(function() {
	  var sideWidth = $('#admin-side').width();
	  if(sideWidth === 200) {
		  $('#admin-body').animate({
	        left: '0'
	      });
		
	      $('#admin-footer').animate({
	        left: '0'
	      }); 
	      $('#admin-side').animate({
	        width: '0'
	      });
	  } else {
	      $('#admin-body').animate({
	        left: '200px'
	      });
	   
	      $('#admin-footer').animate({
	        left: '200px'
	      });
	     
	      $('#admin-side').animate({
	        width: '200px'
	      });
	  }
	});
	
	
	/*设置iframe自适应高度*/
	$(window).on('resize', function() {
		var $content = $('.layui-tab-content');
		$content.height($(this).height() - 133);
		$content.find('iframe').each(function() {
			$(this).height($content.height());
		});
	}).resize();
	
	//设置tab页
	navtab.set({
		elem: '#admin-tab',
		closed: true 
	});
	
	$(function(){
		//注入菜单
		$('#admin-navbar-side').find('li').each(function(){
			var $this = $(this);
			if($this.find('dl').length > 0){
				$this.find('dd').each(function(){
					$(this).click(function(){
                        var $a = $(this).children('a');
                        var href = $a.data('url');
                        if(href==""){
                        	href="login.jsp";
                        }
                        
                        var title = $a.children('span').text();
                        var data = {
                           href: href,
                           title: title
                        }
                        window.status=title;
                        navtab.tabAdd(data);
                    });
				});
			}else{
				 $this.click(function(){
                     var $a = $(this).children('a');
                     var href = $a.data('url');
                     if(href==""){
                     	href="login.jsp";
                     }
                     var icon = $a.children('i:first').data('icon');
                     var title = $a.children('span').text();
                     var data = {
                           href: href,
                           title: title
                     }
                     window.status=title;
                     navtab.tabAdd(data);
              });
			}
		});
	})
	
	//锁屏
	$(document).on('keydown', function() {
		var e = window.event;
		if(e.keyCode === 76 && e.altKey) {
			lock($, layer);
		}
	});
	$('#lock').on('click', function() {
		lock($, layer);
	});

});


function lock($, layer) {
	//自定页
	layer.open({
		title: false,
		type: 1,
		closeBtn: 0,
		anim: 6,
		content: $('#lock-temp').html(),
		shade: [0.9, '#393D49'],
		success: function(layero, lockIndex) {

			//给显示用户名赋值
			layero.find('div#lockUserName').text('admin');
			layero.find('input[name=lockPwd]').on('focus', function() {
					var $this = $(this);
					if($this.val() === '输入密码解锁..') {
						$this.val('').attr('type', 'password');
					}
				})
				.on('blur', function() {
					var $this = $(this);
					if($this.val() === '' || $this.length === 0) {
						$this.attr('type', 'text').val('输入密码解锁..');
					}
				});
		
			//绑定解锁按钮的点击事件
			layero.find('button#unlock').on('click', function() {
				var $lockBox = $('div#lock-box');

				var userName = $lockBox.find('div#lockUserName').text();
				var pwd = $lockBox.find('input[name=lockPwd]').val();
				if(pwd === '输入密码解锁..' || pwd.length === 0) {
					layer.msg('请输入密码..', {
						icon: 2,
						time: 1000
					});
					return;
				}
				unlock(userName, pwd);
			});
			/**
			 * 解锁操作方法
			 * @param {String} 用户名
			 * @param {String} 密码
			 */
			var unlock = function(un, pwd) {
				//关闭锁屏层
				layer.close(lockIndex);
			};
		}
	});
};


function tabAdd(data){
    navtab.tabAdd(data);
}

function tabDel(data){
    navtab.tabDel(data);
}
$(document).ready(function(){
	window.status = "京博石化";
});