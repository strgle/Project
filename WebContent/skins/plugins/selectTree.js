function selectTree(e) {
	var em = e.elem;
	$(em).find("option").each(function(i,c){
		var level = parseInt($(c).data("level"),10);
		var t = $(c).text();
		var pt = "";
		for(var i=0;i<=level;i++){
			if(i==0){
				pt = pt+" ├ ";
			}else{
				pt = pt+"&nbsp;├ ";
			}
			
		}
		$(c).html(pt+t);
	});
};