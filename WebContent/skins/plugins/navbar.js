
layui.define(['element', 'common'], function(exports) {
	"use strict";
	var $ = layui.jquery,
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		element = layui.element(),
		common = layui.common,
		cacheName = 'tb_navbar';

	var Navbar = function() {
		/**
		 *  默认配置 
		 */
		this.config = {
			elem: undefined, //容器
			data: undefined, //数据源
			url: undefined, //数据源地址
			type: 'GET', //读取方式
			spreadOne:false //设置是否只展开一个二级菜单
		};
		this.v = '0.0.1';
	};
	Navbar.prototype.render = function() {
		var _that = this;
		var _config = _that.config;
		if(typeof(_config.elem) !== 'string' && typeof(_config.elem) !== 'object') {
			common.throwError('Navbar error: elem参数未定义或设置出错，具体设置格式请参考文档API.');
		}
		var $container;
		if(typeof(_config.elem) === 'string') {
			$container = $('' + _config.elem + '');
		}
		if(typeof(_config.elem) === 'object') {
			$container = _config.elem;
		}
		if($container.length === 0) {
			common.throwError('Navbar error:找不到elem参数配置的容器，请检查.');
		}
		
		if(_config.data === undefined && _config.url === undefined) {
			common.throwError('Navbar error:请为Navbar配置数据源.')
		}
		if(_config.data !== undefined && typeof(_config.data) === 'object') {
			var html = getHtml(_config.data);
			$container.html(html);
			element.init();
			_that.config.elem = $container;
		} else {
			//清空缓存
			layui.data(cacheName, null);
			$.ajax({
				type: _config.type,
				url: _config.url,
				async: false, //_config.async,
				dataType: 'json',
				beforeSend: function () {
	            	  //请求前加载提示
	            	  layer.load(1, {
	                      shade: [0.1, '#fff'] 
	                  });
	                 
	            },
				success: function(result, status, xhr) {
					
					var html = getHtml(result);
					$container.html(html);
					element.init();
					layer.closeAll('loading'); 	
				},
				error: function(xhr, status, error) {
					location.href ="login.jsp";
					layer.closeAll('loading'); 	
					//common.msgError('Navbar error:' + error);
				},
				complete: function(xhr, status) {
					_that.config.elem = $container;
				}
			});
		
		}
		
		//只展开一个二级菜单
		if(_config.spreadOne){
			var $ul = $container.children('ul');
			$ul.find('li.layui-nav-item').each(function(){
				$(this).on('click',function(){
					$(this).siblings().removeClass('layui-nav-itemed');
				});
			});
		}
		return _that;
	};
	/**
	 * 配置Navbar
	 * @param {Object} options
	 */
	Navbar.prototype.set = function(options) {
		var that = this;
		that.config.data = undefined;
		$.extend(true, that.config, options);
		return that;
	};
	/**
	 * 绑定事件
	 * @param {String} events
	 * @param {Function} callback
	 */
	Navbar.prototype.on = function(events, callback) {
		var that = this;
		var _con = that.config.elem;
		if(typeof(events) !== 'string') {
			common.throwError('Navbar error:事件名配置出错，请参考API文档.');
		}
		var lIndex = events.indexOf('(');
		var eventName = events.substr(0, lIndex);
		var filter = events.substring(lIndex + 1, events.indexOf(')'));
		if(eventName === 'click') {
			if(_con.attr('lay-filter') !== undefined) {
				_con.children('ul').find('li').each(function() {
					var $this = $(this);
					if($this.find('dl').length > 0) {
						var $dd = $this.find('dd').each(function() {
							$(this).on('click', function() {
								var $a = $(this).children('a');
								var href = $a.data('url');
								var icon = $a.children('i:first').data('icon');
								var title = $a.children('cite').text();
								var data = {
									elem: $a,
									field: {
										href: href,
										icon: icon,
										title: title
									}
								}
								callback(data);
							});
						});
					} else {
						$this.on('click', function() {
							var $a = $this.children('a');
							var href = $a.data('url');
							var icon = $a.children('i:first').data('icon');
							var title = $a.children('cite').text();
							var data = {
								elem: $a,
								field: {
									href: href,
									icon: icon,
									title: title
								}
							}
							callback(data);
						});
					}
				});
			}
		}
	};
	
	/**
	 * 获取html字符串
	 * @param {Object} data
	 */
	function getHtml(data) {
		
		var ulHtml = '';
		
		for(var i = 0; i < data.length; i++) {
			if(data[i].selected) {
				ulHtml += '<li class="layui-nav-item layui-this">';
			} else {
				ulHtml += '<li class="layui-nav-item">';
			}
			if(data[i].children !== undefined && data[i].children.length > 0) {
				ulHtml += '<a href="javascript:;" onmousemove="window.status=\''+data[i].title+'\'" onmouseout="window.status=\'\'">';
				if(data[i].icon !== undefined && data[i].icon !== '') {
					if(data[i].icon.indexOf('fa-') !== -1) {
						ulHtml += '<i class="fa ' + data[i].icon + '" aria-hidden="true" data-icon="' + data[i].icon + '"></i>';
					}
				}
				
				ulHtml += '<span>' + data[i].title + '</span>'
				ulHtml += '</a>';
				ulHtml += '<dl class="layui-nav-child">'
				for(var j = 0; j < data[i].children.length; j++) {
					ulHtml += '<dd>';
					ulHtml += '<a href="javascript:;" data-url="' + data[i].children[j].href + '" onmousemove="window.status=\''+data[i].children[j].title+'\'" onmouseout="window.status=\'\'">';					
					ulHtml += '<span>' + data[i].children[j].title + '</span>';
					ulHtml += '</a>';
					ulHtml += '</dd>';
				}
				ulHtml += '</dl>';
			} else {
				ulHtml += '<a href="javascript:;"  onmousemove="window.status=\''+data[i].title+'\'" onmouseout="window.status=\'\'" data-url="' + data[i].href + '">';
				if(data[i].icon !== undefined && data[i].icon !== '') {
					if(data[i].icon.indexOf('fa-') !== -1) {
						ulHtml += '<i class="fa ' + data[i].icon + '" aria-hidden="true" data-icon="' + data[i].icon + '"></i>';
					}
				}
				ulHtml += '<span>' + data[i].title + '</span>'
				ulHtml += '</a>';
			}
			ulHtml += '</li>';
		}
		return ulHtml;
	}

	var navbar = new Navbar();

	exports('navbar', function(options) {
		return navbar.set(options);
	});
});