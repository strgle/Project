var curPoject = "/";

/*主框架相关js*/
$(function(){
	/*隐藏滚动条*/

	$(".nav-tree").niceScroll({cursorwidth:"0px",cursorborder:"0px"});
	/*加载左侧菜单 */
	
	loadMenu("123");
	/*左侧菜单相关事件及样式*/
	sideMenu();
	
	
});

//加载菜单
function loadMenu(module){
	//清理当前内容
	$(".nav-tree").empty();
	
	var urls = curPoject+"sys/menu/queryMenu/1?"+Math.random();

	$.ajax({
		url:urls,
		type: 'POST',
		dataType: "json",
		async:false,
		success:function(data){
		
			var _html = "";
			for(var i=0;i<data.length;i++){
				var elem = data[i];
				if(elem.children== undefined || elem.children.length == 0){
					_html=_html+'<li class="nav-item"><label><i class="fa '+elem.icon+'"></i>'+elem.title+'</label></li>';
				}else{
					_html=_html+'<li class="nav-item"><label><i class="fa '+elem.icon+'"></i>'+elem.title+'</label><dl class="nav-child">';
					
					for(var j = 0; j < elem.children.length; j++) {
						_html += '<dd><label data-src="'+elem.children[j].href+'">'+elem.children[j].title+'</label></dd>';
					}
					_html+='</dl></li>';
				}
				
			}

			$(".nav-tree").append(_html);
			
		} 
	 });
}

function sideMenu(){
	//判断是否有子元素
	var hasChildren = $(".side .nav-tree .nav-item:has(.nav-child)");
	var notChildren = $(".side .nav-tree .nav-item").filter(function(index){
		return $(".nav-child",this).length==0;
	});
	
	//添加展开、收起标识 angle-down angle-right
	$.each(hasChildren,function(index,elm){
	    if($(elm).children(".nav-child").is(":visible")){
	    	$(elm).children("label").append('<i class="fa fa-angle-down right"></i>');
	    }else{
	    	$(elm).children("label").append('<i class="fa fa-angle-right right"></i>');
	    }
	    
	    $(elm).children("label").click(function(){
	    	if($(this).next(".nav-child").is(":visible")){
	    		if($(this).children("i:last").hasClass("fa-angle-down")){
	    			$(this).children("i:last").removeClass("fa-angle-down");
		    		$(this).children("i:last").addClass("fa-angle-right");
	    		}
	    		$(this).next(".nav-child").hide();
	    	}else{
	    		if($(this).children("i:last").hasClass("fa-angle-right")){
	    			$(this).children("i:last").removeClass("fa-angle-right");
		    		$(this).children("i:last").addClass("fa-angle-down");
	    		}
	    		$(this).next(".nav-child").show();
	    	}	    	
	    });
	 });
	
	$.each(notChildren,function(index,elm){
	    $(elm).children("label").click(function(){
	    	//添加类
			if($(this).parent().hasClass("selectItem")){
				return;
			}else{
				$(".nav-tree .selectItem").removeClass("selectItem");
				$(this).parent().addClass("selectItem")
			};
			
	    	//打开链接
	    	var url = $(this).data("src");
	    	var curMenu = $(this).text();
			var menu = curMenu;
	    	openNewMenu(menu,url);
	    });
	 });
	
	$(".nav-tree .nav-child").each(function(index,elm){
		$(elm).find("label").click(function(){
			//添加类
			if($(this).parent().hasClass("selectItem")){
				return;
			}else{
				$(".nav-tree .selectItem").removeClass("selectItem");
				$(this).parent().addClass("selectItem")
			};
			
			var url = $(this).data("src");
			var parentMenu = $(this).parent().parent().prev("label").text();
			var curMenu = $(this).text();
			var menu = parentMenu+" > "+curMenu;
	    	openNewMenu(menu,url);
	    	
		});
	});
}

function openNewMenu(menu,url){
	if(typeof(url)=="undefined"||url==""){
		url = "welcome.jsp";
	}
	url = String(url);
	//设置路径名称
	$(".mainContent .title label").text(menu);
	
	//处理url
	if(url.indexOf("http://")==0||url.indexOf("https://")==0){
		url = url;
	}else if(url.indexOf("/")==0){
		url = curPoject+url;
	}else{
		url = curPoject+"/"+url;
	}

	//判断是否存在
	var iframe = $(".mainContent .content iframe");
	if(iframe.length>0){
		var src = $(iframe[0]).attr("src");
		if(src==url){
			return;
		}else{
			iframe[0].src=url;
		};
	}else{
		var content = '<iframe src="' +url+ '"></iframe>';
		$(".mainContent .content").append(content);
	}
	
}
