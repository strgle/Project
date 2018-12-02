layui.define(['jquery', 'layer'],function(exports) {
	
    var $ = layui.jquery,layer = layui.layer;
	
    var api = {
		// 弹出层
        open: function(url, title, btn, area, full, topFull) {
        	var param = {
    			type		: 2,
                title		: title,
                closeBtn	: 1,
                shade		: 0.1,
                maxmin		: true,
                move		: false,
                area		: area ? area : ['600px', '90%'],
                btn			: btn ? btn : ['提交', '重置'],
                content		: url,
                yes			: function(index, layero) {
					var array 	= layer.getChildFrame('form').serializeArray();
                    var fd 		= {};
                    $.each(array,function() {
                        fd[this.name] = this.value;
                    });
                    api.submit(url, fd)
                },
                btn2:function(index, layero) {
                    var formObj = layer.getChildFrame('form');
                    formObj[0].reset();
                    return false;
                }
        	};
        	if(topFull){// 父级页面最大化
        		top.layer.full(top.layer.open(param));
        	}else if(full){// 当前页面最大化
        		layer.full(layer.open(param));
        	}else{
        		layer.open(param);
        	}
        },
        
		// ajax提交表单
        submit: function(url, data) {
            $.ajax({
                type	: 'post',
                url		: url,
                data	: data,
                beforeSend: function () {
              	  //请求前加载提示
              	  layer.load(1, {
                        shade: [0.1, '#fff'] 
                    });
                   
                },
                success	: function(result) {
                    
                }
            });
        },
        
		// 提示框
        alert: function(msg) {
            layer.alert(msg, {
                title	: '错误提示',
                move	: false
            });
        },
        
		// json字符串转对象
        jsonStringToObj: function(jsonString) {
            try {
                var json = eval('(' + jsonString + ')');
            } catch(e) {
                return '';
            }
            return json;
        },
        
        // 获取选中行参数
        selectTrData: function(){
        	var st = $(".table-list").find(".layui-table tbody .tableTrSelect");
        	if(st.length < 1){
        		return {s:1,msg:'请选中操作行'};
        	}
        	var assign = st.data("assign");
        	if(!assign){
        		return {s:1,msg:'请为操作行设置参数'};
        	}
        	var rt = api.jsonStringToObj(assign);
        	if(typeof rt != 'object'){
        		return {s:0,data:'param='+assign};
        	}
        	
        	return {s:0,data:$.param(rt)};
        },
        
        // 返回链接参数值
        urlDataValue: function(name){
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
			var r = window.location.search.substr(1).match(reg);
			if (r != null){
				return unescape(r[2]);
			}
			
			return null;
        }
    }

    exports('main', api);
})