var _protocol = window.document.location.protocol;
var _projectUrl = window.document.location.host;
var _projectName = window.document.location.pathname;
var _projectNameIndex = _projectName.substr(1).indexOf('/')+1; 
var _curProject = _protocol+"//"+_projectUrl+_projectName.substring(0,_projectNameIndex);
layui.config({
	base:_curProject+'/skins/plugins/'
}).use(['main','tabletree','laypage','form'],function() {
	var o 	= layui.jquery,
		m 	= layui.main,
		// 列表组
		l 	= o(".table-list"),
		// 按钮组
		b 	= o(".handle-btn"),
		// 列表搜索组
		s 	= o(".content-search"),
		// 树表ID标签
		tb 	= "#tabletree",
		// 分页ID标签
		tp 	= "#table-pages";
	
	// 滚动触发事件
	o(window).scroll(function(){
		// 按钮组浮动
		var fh = (s.length ? s.height() : 0) + (b.length ? b.height() : 0);
		fh && o(window).scrollTop() > fh ? b.addClass("listTopFixed") : b.removeClass("listTopFixed");
	})
	
	// 选中行
	l.on("click","tbody tr",function(){
		o(this).toggleClass("tableTrSelect").siblings().removeClass("tableTrSelect");
	});
	
	// 加载表树
	if(o(tb).length){
		layui.tabletree({elem: tb})
	}
	
	// 加载分页
	if(o(tp).length && totalPage){
		layui.laypage({
			cont 	: tp.substr(1),
			pages 	: totalPage,
			curr	: m.urlDataValue('p') ? m.urlDataValue('p') : 1,
			skip	: true,
			jump	: function(obj, first){
				if(!first){
					var url = location.href;
					var isB = url.indexOf('?')>0 ? '&' : '?';
					location.href =url.indexOf('p=')>0 ? url.replace(/p=\d+/,'p='+obj.curr) : url+isB+'p='+obj.curr;
				}
			}
		});
	}
	
	// 点击按钮
	b.on("click","button",function(){
		var t = o(this);
		var url = t.data("url");
		if(!url){
			m.alert("请设置操作链接");
			return false;
		}
		if(t.hasClass("layui-btn-danger") || t.hasClass("layui-btn-normal")){
			var urlParam = m.selectTrData();
			if(urlParam.s == 1){
				m.alert(urlParam.msg);
				return false;
			}
			url = url + (url.indexOf('?') > 0 ? '&': '?') + urlParam.data;
			if(t.hasClass("layui-btn-danger")){
				layer.confirm('确认要删除吗？',function(){
					location.href = url;
				})
				return false;
			}
		}
		if(t.hasClass("layui-btn-warm")){
			// 启动tab页面
			var id = t.data("menuid") ? t.data("menuid") : new Date().getTime();
			var mainHeight = o(parent.document).height() - 60 - 41 - 5 - 44;
			parent.layui.element().tabAdd("top-tab", {
        		title	: t.html(),
        		content	: '<iframe src="' + url + '" style="height:' + mainHeight + 'px;"></iframe>',
        		id		: id
    		});
			parent.layui.element().tabChange("top-tab", id);
			return false;
		}
		m.open(url,t.html());
	});
})