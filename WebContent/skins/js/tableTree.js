; !
function(e) {
    var t = function() {
    	this.config={
    		elem: "#tableTree",
    		column : 1,
    		retract : 16,
    		icon : [ "&#xe623;", "&#xe625;" ],
    		iconClass : "layui-icon",
    		icD : ".layui-icon",
    		topId : "-1"	
    	};
    };
    
    t.fn = t.prototype;
   
    t.fn.set=function(options){
    	var _that = this;
    	$.extend(true, _that.config, options);
    },
    
	t.fn.init = function() {
    	var d = this.config;
		var elem = $(this.config.elem);
		elem.find("tbody tr").each(
				function(i, c) {
					var tr = $(c),
					ic = elem.find("tbody tr[data-tb-pid='" + tr.data("tb-id")+ "']").length,
					icH = '<i class="'+ d.iconClass + '">' + (ic ? d.icon[0] : '')+ '</i>',
					pid = tr.data("tb-pid"),
					px = elem.find("tbody tr[data-tb-id='" + pid + "']").find(d.icD).css("margin-left");					
					tr.find("td").eq(d.column).prepend(icH);
					tr.find(d.icD).eq(0).css("margin-left",(parseInt(px) + d.retract) + 'px');
				})
		this.on(elem);
	},

	t.fn.packup = function(elem, pid) {
		var tt = this;
		var d = this.config;
		elem.find("tbody tr[data-tb-pid='" + pid + "']").each(
				function() {
					//获取下一级别数据
					$(this).hide().find(d.icD).eq(0).removeClass("sopen").html($(this).find(d.icD).eq(0).html() ? d.icon[0] : '');
					tt.packup(elem, $(this).data("tb-id"));
				})
	},
	
	t.fn.on = function(elem) {
		var tt = this;
		var d = this.config;
		elem.find("tbody tr").on("click","td:eq("+d.column+")",	function() {
			var ico = $(this).find(d.icD),id = $(this).parents("tr").data("tb-id");
			if (ico.length > 0 && ico.html()) {
				var isO = ico.hasClass("sopen");
				isO ? tt.packup(elem, id) : elem.find("tbody tr[data-tb-pid='" + id + "']").show();
				ico.toggleClass("sopen").html(ico.html()?d.icon[isO? 0 : 1]:'');
				return false;
			}
		})
	};
   
    e.TableTree = new t
} (window);
