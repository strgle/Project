

function addMenu(parentId){
	var addUrl = "menu/forAddMenu/"+parentId;
	var index = layer.open({title:"添加菜单",type:2,content:addUrl,area:['680px','470px']});
}


function editMenu(id){
	var updateUrl = "menu/forUpdate/"+id;
	var index = layer.open({title:"编辑菜单",type:2,content:updateUrl,area:['680px','470px']});
}



var n = document,
i = function() {
		    var e = n.scripts,
		    t = e[e.length - 1].src;
		    return t.substring(0, t.lastIndexOf("/common/"))
		};
var _curProject = i();

function delMenu(id){
	$.ajax({url:'/jbxj/sys/menu/delMenu/'+id,type:'POST',dataType:'json',success:function(data){
		if(data.code=="SUCCESS"){
			layer.msg('删除成功！',{	time: 1000});
			location.reload();
		}else{
			layer.msg(data.message,{time: 1000});
		}
		
	},error:function(xhr,status,error){
		alert(data);
	}});
}

function receiveReturn(id){
	location.reload();
}

function addTr(menu){
	alert(menu.name);
	for(var key in menu){
		alert(key);
		alert(menu[key]);
	}
}

function addOrUpdateMenu(id){
	$.ajax({url:'/jbxj/sys/menu/getMenu/'+id,type:'POST',dataType:'json',success:function(data){
		addTr(data);
	},error:function(xhr,status,error){
		alert(data);
	}});
}