function submitForm(option){
	this.config = {
		type: 'POST',
		async: false,
		success: _submitSuccess,
		dataType: "json"
	}
	var _config = $.extend(true,this.config,option);
	$.ajax(_config);
}

function _submitSuccess(data){
	if(data.code=="SUCCESS"){
		layer.msg('操作成功！', {icon: 1});
	}else{
		layer.alert(data.message, {icon: 1});
	}
}

function submitPost(option){
	this.config = {
		type: 'POST',
		async: false,
		success: _submitSuccess,
		dataType: "json"
	}
	var _config = $.extend(true,this.config,option);
	$.ajax(_config);
}