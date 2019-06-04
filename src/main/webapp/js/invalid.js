var url;
$(function() {
	url = location.href.split('#').toString();//url不能写死
	$(".myRalated").keyup(function(){
        var isBlock=$(".related").css("display");
        if(isBlock == "none" && $(".myRalated").val() != ''){
            $(".related").css("display","block");
        }else{
            $(".related").css("display","none");
        }
    });
    $(".related ul").on('click','li',function(){
    	$("#validationName").val($(this).html());
    	stanid = $(this).attr("name");
        $(".related").css("display","none");
    });
})


//微信分享
wx.ready(function () {
    	wx.onMenuShareAppMessage({
    		title: '标准作废', // 分享标题
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


//文本域显示字数
function textNumber(){
	$('#import').text($('#invalid_content').val().length)	
}

function search(){
	var comment = $("#standardNumber").val();
	$.ajax({
		url :"/BZCX/validatform/search",
        type : "post",
        data : {"standardNo" : comment},
        ansyc : false,
        dataType:"json",
        success : function(data){
        	var da = data.rows;
        	$("#association").empty();
        	for (var i = 0; i < da.length; i++) {
        		var htm = "<li id='"+da[i].standardNo+"' name='"+da[i].standardId+"'>"+da[i].standardCnName+"</li>";
        		$("#association").append(htm);
			}
        	$("#association li").click(function() {
				var staNO = $(this).attr("id");
				var staid = $(this).attr("name");
				$("#standardNumber").val(staNO);
				$("#standardNumber").attr("name",staid);
			});
        }
    });
};

function invalid_clean() {
	$("#standardNumber").val('');
	$("#validationName").val('');
	$("#invalid_content").val('');
}

function invalid_submit() {
	var standno = $("#standardNumber").val();
	var standname = $("#validationName").val();
	var content = $("#invalid_content").val();
	var id = $("#standardNumber").attr("name");
	
	var mustfill = isempty(standno,"standardNumber");
	var mustfill1 = isempty(standname,"validationName");
	if(mustfill && mustfill1){
		var data = {
				"standardId" : id,
				"standardName" : standname,
				"standardNo" : standno,
				"comment" : content,
				"suggestStatus" : 1,
				"status" : 1
		}
		$.ajax({
			url : "/BZCX/validatform/addvalida",
			type : "post",
			data :data,
			async : false,
			success : function(data) {
				if(data.code == 200){
					$("#standardNumber").val('');
					$("#validationName").val('');
					$("#invalid_content").val('');
					window.location.href = "succeed.html";
				}else{
					alert("")
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

function jumpinstead() {
	window.location.href = "instead.html";
}

function jumpinvalid() {
	window.location.href = "invalid.html";
}
