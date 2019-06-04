
//微信分享
wx.ready(function () {
    	wx.onMenuShareAppMessage({
    		title: '标准缺失', // 分享标题
        	desc: '反馈标准状态，及时更新', // 分享描述
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

function textNumber(){
	$('#import').text($('#deletion_content').val().length)	
}

function deletion_clean() {
	$("#deletion_standrano").val('');
	$("#deletion_standname").val('');
	$("#deletion_content").val('');
}

function deletion_submit() {
	var standno = $("#deletion_standrano").val();
	var standname = $("#deletion_standname").val();
	var content = $("#deletion_content").val();
	
	var mustfill = isempty(standno,"deletion_standrano");
	var mustfill1 = isempty(standname,"deletion_standname");
	if(mustfill && mustfill1){
		var data = {
				"standardName" : standname,
				"standardNo" : standno,
				"comment" : content,
				"suggestStatus" : 3,
				"status" : 1
		};
		$.ajax({
			url : "/BZCX/validatform/addvalida",
			type : "post",
			data :data,
			async : false,
			success : function(data) {
				if(data.code == 200){
					$("#deletion_standrano").val('');
					$("#deletion_standname").val('');
					$("#deletion_content").val('');
					window.location.href = "succeed.html";
				}
			}
		});
	}
}

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

function jumpinvalid() {
	window.location.href = "invalid.html";
}

function jumpinstead() {
	window.location.href = "instead.html";
}