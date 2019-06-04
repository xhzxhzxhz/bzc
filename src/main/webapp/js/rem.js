(function (doc, win, undefined) {
    var docEl = doc.documentElement,
    resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
    recalc = function () {
        var clientWidth = docEl.clientWidth;
        if (clientWidth === undefined) return;
        docEl.style.fontSize = 100 * (clientWidth /750) + 'px';
    };     
    if (doc.addEventListener === undefined) return;
    win.addEventListener(resizeEvt, recalc, false);
    doc.addEventListener('DOMContentLoaded', recalc, false)
})(document, window);


function load(){
	var data = {
			"moudle" : "简单查询",
			"relation" : "or",
			"standardNo":"",
			"standardCnName" :"",
			"pSize" : 15,
			"cPage" : 1
	}
	
	$.ajax({
		url : "/BZCX/standard/search",
		type : "post",
		data : data,
		async : false,
		success : function(data) {
			var ss = ' <div class="highCon">'+
            	'<div class="standard">标准号</div>'+
            	'<div class="standardName">标准名称</div>'+
        	'</div>'
	       $("#simplen_seach").apend(ss)
		}
	});
}

function simple_search() {
	var data = {
			"moudle" : "简单查询",
			"relation" : "or",
			"standardNo":"",
			"standardCnName" :"",
			"pSize" : 15,
			"cPage" : 1
	}
	
	$.ajax({
		url : "/BZCX/standard/search",
		type : "post",
		data : data,
		async : false,
		success : function(data) {
	       
		}
	});
}


function jump(url,standid) {
	if(standid != null){
		$.ajax({
			url : "/BZCX/standard/existence",
			type : "post",
			data : {"filename":url},
			async : false,
			success : function(data) {
				if(data.code == 200){
					var uu = window.location.host;
					var jumpurl = "http://"+uu+"/standrd/"+url+"?standId="+standid;
					window.open(jumpurl);
				}else{
					alert("暂无题录信息")
				}
			}
		});
	}
}
