//微信分享
wx.ready(function () {
    	wx.onMenuShareAppMessage({
    		title: '意见反馈', // 分享标题
        	desc: '好的意见或建议将带给您更好的体验', // 分享描述
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



$(function(){
	$(".feedContend img").click(function(){
		if($(event.target).attr("src")=="img/button_nor_xuankuang.png"){
			$(event.target).attr("src","img/button_sel_xuankuang.png")
		}else{
			$(event.target).attr("src","img/button_nor_xuankuang.png")
		}
	})
})

//文本域显示字数
function textNumber1(){
	$('#import').text($('#feedback_count').val().length)	
}

function feedback_clean() {
	$(".feedContend img").attr("src","img/button_nor_xuankuang.png");
	$("#feedback_count").val("");
}

function feedback_Submission() {
	var feed='';
	let sum=5; 
	let countnum=0;
	$(".feedContend img").each(function(){		
		if($(this).attr("src")=="img/button_sel_xuankuang.png"){
			feed+=$(this).next().text()+",";
		}
		if($(this).attr("src")=="img/button_nor_xuankuang.png"){
			countnum++;
		}
	});
	feed=feed.substring(0,feed.length-1);
	var count = $("#feedback_count").val();	
	var info = {
			"feed":feed,
			"content" :count
	};	
	if(sum==countnum){
		alert("请选择一项问题分类")
	}else{
		$.ajax({
			url : "/BZCX/interact/message",
			type : "post",
			data : info,
			async : true,
			success : function(data) {
				if(data.code == 200){
					window.location.href = "succeed.html";
					//清空文字
				}else{
					alert("提交失败")
				}
			}
		});
	}	
}