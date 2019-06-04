
//微信分享
wx.ready(function () {
    	wx.onMenuShareAppMessage({
    		title: '内容查询', // 分享标题
        	desc: '标准全文检索，不一样的检索体验', // 分享描述
        	link: url, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
        	imgUrl: imgUrl, // 分享图标
        	type: '', // 分享类型,music、video或link，不填默认为link
        	dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
        	success: function () {
        		// 用户确认分享后执行的回调函数
        	},
        	cancel: function () {
        		// 用户取消分享后执行的回调函数
        	}
    	});
    });

$(function() {
	host();
})

//热门检索词
function host() {
	$.ajax({
		url : "/BZCX/history/hotword",
		type : "post",
		data : {"moudle":"4004","page":1,"rows":10},
		async : false,
		success : function(data) {
			var info = "";
			$("#fulltext_hot").empty();
			for (var i = 0; i < data.length; i++) {
				info = '<span onclick = "fulltext_seach(\''+data[i]+'\')">'+data[i]+'</span>';
				$("#fulltext_hot").append(info);
			}
		}
	});
}

function fulltext_seach(input_data) {
	//点击的时候出现loading
	
	
	var keyword;
	if(input_data == undefined){
		keyword = $("#fulltext_input").val();
	}else{
		keyword = input_data;
	}
	
	var info = {
			"keyword" : keyword,
			"page" : 1,
			"rows" : 10
	}
	var nextinfo = JSON.stringify(info); 
	nextinfo = myencodeURI(nextinfo);
	window.location.href = "result.html?nextinfo="+nextinfo;
	
	//跳转下一页面loading删除
	
}

function myencodeURI(str){  
    return  encodeURIComponent(encodeURIComponent(str));  
} 
