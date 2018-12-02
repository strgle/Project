(function(a, c) {    
    a.fn.tab = function(d){
    	var _that = a(this);
    	//事件绑定
    	_that.on("click",".tab-title li",function(index,elem){
    		var tabHref = a(this).data("href");
			var tabId = a(this).data("id");
			if(!a(this).hasClass("tab-this")){
				//移除tab-this
				_that.find(".tab-title .tab-this").removeClass("tab-this");
				a(this).addClass("tab-this");
				
				//移除已经存在tab-show的元素
				_that.find(".tab-content .tab-show").removeClass("tab-show");
				var tabDiv = _that.find(".tab-content .tab-item[data-id='"+tabId+"']");
				a(tabDiv).addClass("tab-show");
				
				//加载load函数
				if(d.loadTab){
					d.loadTab(a(this).data());
				}
			}
		});
    	//设置tab-content高度
    	if(_that.parent()[0].tagName=="BODY"){
    		
    		var h = a(window).height();
        	
        	_that.find(".tab-content").height(h-50);
        	
        	//设置窗口大小调整监控
        	$(window).on('resize', function() {
        		var h1 = a(window).height();
            	_that.find(".tab-content").height(h1-50);
        	}).resize();
    	}else{
    		var h = _that.height();
        	_that.find(".tab-content").height(h-40);
        	
        	//设置窗口大小调整监控
        	$(window).on('resize', function() {
        		var h1 = _that.height();
            	_that.find(".tab-content").height(h1-40);
        	}).resize();
    	};
    	
    	
		//判断是否有显示的tab
		var showTab = a(this).find(".tab-title .tab-this");
	
		if(typeof(showTab)=="undifined"||showTab.length==0){
			a(this).find(".tab-title li:first").trigger("click");
		}
    };
})(window.jQuery);
