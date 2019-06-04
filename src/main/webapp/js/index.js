var teltrue = true;
var url;
function index_submit() {
	url = location.href.split('#').toString();//url不能写死
	var company = $("#index_company").val();
	var boocom = isempty(company,"index_company");
	var depart = $("#index_department").val();
	var boocom1 = isempty(depart,"index_department");
	var username = $("#index_username").val();
	var boocom2 = isempty(username,"index_username");
	var tel = $("#index_tel").val();
	var boocom3 = isempty(tel,"index_tel");
	var position =$("#index_position").val();
	var wechat =$("#index_wechat").val();
	var qq =$("#index_qq").val();
	var comment =$("#index_commont").val();
	var email =$("#index_email").val();
	var info = {
			"informationName" :username,
			"informationTel" : tel,
			"informationQq" : qq,
			"informationEmail" :email,
			"informationCompany" :company,
			"informationDepartment" :depart,
			"informationDuty" : position,
			"informationWechat" :wechat,
			"informationCount" :comment,
	}
	if(boocom && boocom1 && boocom2 && boocom3 && teltrue){
		$.ajax({
			url : "/BZCX/serinformation/insert",
			type : "post",
			data : info,
			async : false,
			success : function(data) {
				if(data.code == 200){
					//清空文字
					var company = $("#index_company").val('');
					var depart = $("#index_department").val('');
					var username = $("#index_username").val('');
					var tel = $("#index_tel").val('');
					var position =$("#index_position").val('');
					var wechat =$("#index_wechat").val('');
					var qq =$("#index_qq").val('');
					var comment =$("#index_commont").val('');
					var email =$("#index_email").val('');
					window.location.href = "succeed.html";
				}else{
					alert("提交失败")
				}
			}
		});
	}
}


//微信分享
wx.ready(function () {
    	wx.onMenuShareAppMessage({
    		title: '服务咨询', // 分享标题
        	desc: '标准查,查标准', // 分享描述
        	link: url, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
        	imgUrl : imgUrl, // 分享图标
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

function isempty(info,key) {
	//是否为空,校验
	if(info == ""){
		$("#"+key).attr("placeholder","请输入内容")
		return false;
	}else{
	//	$("#"+key);
		return true;
	}
}

function index_clean(el) {
	var el=event;
	if($(el.target).text()=='填写更多'){				
		$(el.target).text("收起");
		$('<img>',{
    		src:'img/icon_shousuo1.png',
    		alt:'暂无图片'
    	}).appendTo($(el.target));				
		$('.unforms').show();
	}else{
		$(el.target).text("填写更多");
		$('<img>',{
    		src:'img/icon_xiala1.png',
    		alt:'暂无图片'
    	}).appendTo($(el.target));				
		$('.unforms').hide();
	}
}
$(function(){
	$("#index_tel").blur(function(){
		var tel=$("#index_tel").val();
		if(!(/^1[34578]\d{9}$/.test(tel))){
			teltrue = false;
			$("#index_tel").css("color","red");
		}else{
			teltrue = true;
		}
	});
	$("#index_tel").focus(function(){
		$("#index_tel").css("color","#333");
	});
	$("#index_email").blur(function(){
		var email=$("#index_email").val();
		var re = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$"); 
		if(email=="" || !re.test(email)){
			$("#index_email").css("color","red");
		}
	});
	$("#index_email").focus(function(){
		$("#index_email").css("color","#333");
	});
})
