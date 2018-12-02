/* *
 * 
 * 
 * */
layui.define(['element'], function(exports){
   var  element = layui.element(),
        $ = layui.jquery,
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		module_name = 'navtab';
   var n = document,
   i = function() {
       var e = n.scripts,
       t = e[e.length - 1].src;
       return t.substring(0, t.lastIndexOf("/skins/"))
   };
   var _curProject = i();
   
		var Navtab = function(){
			/**
			 *  默认配置 
			 */
              this.config ={
              	  elem: undefined,
				  closed: true 
              };
		};
		
        var ELEM = {};
        /**
         * [参数设置 options]
         */
        Navtab.prototype.set = function(options){
              var _this = this;
              $.extend(true, _this.config, options);
              return _this;
        };
        /**
         * [init 对象初始化]
         * @return {[type]} [返回对象初始化结果]
         */
        Navtab.prototype.init  = function(){
             var _this = this;
             var _config = _this.config;
             if(typeof(_config.elem) !== 'string' && typeof(_config.elem) !== 'object') {
			       layer.alert('Tab选项卡错误提示: elem参数未定义或设置出错，具体设置格式请参考文档API.');
		     }
		     var $container;
		     if(typeof(_config.elem) === 'string') {
			     $container = $('' + _config.elem + '');
		     }
		    
		     if($container.length === 0) {
			     layer.alert('Tab选项卡错误提示:找不到elem参数配置的容器，请检查.');
		     }
		     var filter = $container.attr('lay-filter');
		     if(filter === undefined || filter === '') {
			      layer.alert('Tab选项卡错误提示:请为elem容器设置一个lay-filter过滤器');
		     }
		     _config.elem = $container;
		     ELEM.titleBox = $container.children('ul.layui-tab-title');
		     ELEM.contentBox = $container.children('div.layui-tab-content');
		     ELEM.tabFilter = filter;
		     return _this;
        };
        /**
         * [exists 在layui-tab中检查对应layui-tab-title是否存在，如果存在则返回索引值，不存在返回-1]
         * @param  {[type]} title [description]
         * @return {[type]}       [description]
         */
        Navtab.prototype.exists = function(href){
            var _this = ELEM.titleBox === undefined ? this.init() : ELEM.titleBox;
			var tabIndex = -1;
			ELEM.titleBox.find('li').each(function(i, e) {
			    var curId = $(this).attr("lay-id");
			    if(curId === href) {
				      tabIndex = 1;
			    };
		    });
		    return tabIndex;
        };
        /**
         * [tabAdd 增加选项卡，如果已存在则增加this样式]
         * @param  {[type]} data [description]
         * @return {[type]}      [description]
         */
        Navtab.prototype.tabAdd = function(data){
            var _this = this;
		    var tabIndex = _this.exists(data.href);
		    // 若不存在
		    if(tabIndex === -1){
		    	var httpStart = data.href.indexOf("http://");
		    	var httpsStart =  data.href.indexOf("https://");
		    	var content = '<iframe src="' +_curProject+"/"+ data.href + '" style="height:' + ELEM.contentBox.height() + 'px;"></iframe>';
		    	if(httpStart==0||httpsStart==0){
		    		content = '<iframe src="' + data.href + '" style="height:' + ELEM.contentBox.height() + 'px;"></iframe>';
		    	}
		    	
			    var title = data.title;
			    //添加tab
			    element.tabAdd(ELEM.tabFilter, {
				    title: title,
				    content: content,
				    id:data.href
			    });
		    }
		    element.tabChange(ELEM.tabFilter, data.href);
        };
        
        Navtab.prototype.tabDel = function(data){
            var _this = this;
		    var tabIndex = _this.exists(data.href);
		    // 若存在
		    if(tabIndex != -1){
		    	element.tabDelete(ELEM.tabFilter,data.href);
		    }
        };
    var navtab = new Navtab();
    exports(module_name, function(options) {
		return navtab.set(options);
	});
		
});
