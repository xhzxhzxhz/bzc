var url;
var subscribe;
var imgUrl = "http://bzc.shengfeisi.cn/BZCX/img/logo1.png";
$(function() {
	url = location.href.split('#').toString();//url不能写死
	var encodeurl = encodeURIComponent(url);
    $.ajax({
        type : "get",
        url : "/BZCX/wechatjs/entity",
        dataType : "json",
        async : true,
        data:{url:encodeurl},
        success : function(data) {
        	subscribe = data.data.ma.subscribe;
        	if(subscribe == 0){
            	$("#follow").removeClass('attentionHide');
            	$("#follow button").click(function() {
            		window.location.href = "QRcode.html";
            		/*window.open("https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzU4NDc0Mzk2NQ==#wechat_redirect");*/
				});
            }
            wx.config({
                debug: false,////生产环境需要关闭debug模式
                appId: data.data.ma.appid,//appId通过微信服务号后台查看
                timestamp: data.data.timestamp,//生成签名的时间戳
                nonceStr: data.data.noncestr,//生成签名的随机字符串
                signature: data.data.signature,//签名
                jsApiList: [//需要调用的JS接口列表
                    //'checkJsApi',//判断当前客户端版本是否支持指定JS接口
                	'onMenuShareTimeline',
                    'onMenuShareAppMessage',//分享给好友x
                ]
            });
        },
        error: function(xhr, status, error) {
            alert(status);
            //alert(xhr.responseText);
        }
    })
    
})



    /*wx.ready(function () {
    	wx.onMenuShareAppMessage({
    		title: '6666', // 分享标题
        	desc: 'xxxxxxxxxxx', // 分享描述
        	link: url, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
        	imgUrl: 'http://bzc.shengfeisi.cn/BZCX/img/logo1.png', // 分享图标
        	type: '', // 分享类型,music、video或link，不填默认为link
        	dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
        	success: function () {
        		// 用户确认分享后执行的回调函数
        	},
        	cancel: function () {
        		// 用户取消分享后执行的回调函数
        	}
    	});
    });*/

function jumpfeedback() {
	window.location.href = "feedback.html";
}

function jumpinvalidtitle() {
	window.location.href = "invalid.html";
}