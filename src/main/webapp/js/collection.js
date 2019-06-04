var info;
var page = 2;
// 创建一个 map
var dataMap = new Map();
var allarray = new Map();
var choose = 0;
$(function() {
	load()
	$(window).scroll(function(){
		var scrollTop=Math.ceil($(this).scrollTop());
		var scrollHeight=$(document).height();
		var windowHeight=$(this).height();
		if(scrollTop+windowHeight==scrollHeight){
			info.page = page;
			$.ajax({
				url : "/BZCX/collection/search",
				type : "post",
				data : info,
				async : false,
				success : function(res){
					if(res.status == "success"){
						var da = res.data;
						for (var i = 0; i < da.length; i++) {
							var url = da[i].stand[0].htmlUrl.split("\\")[3];
							var info = '<div class="collCon">'+
									   '<div class="imgDiv">'+
									   ' <img src="img/button_nor_xuankuang.png" alt="暂无图片"></div>'+
									   '<div class="contentDiv" onclick="jump(\''+url+'\',\''+da[i].stand[0].standardId+'\')">'+
									   '<b><p class="conTitle">'+da[i].stand[0].standardCnName+'</p></b>'+
									   '<p class="conCent">'+da[i].stand[0].standardNo+'</p>'+
									   '</div></div>';
							$("#collection_load").append(info)
						}
					}
					page++;					
				}
			})	
		}
	})
})


//微信分享
wx.ready(function () {
    	wx.onMenuShareAppMessage({
    		title: '我的收藏', // 分享标题
        	desc: '分享不能看到别人的收藏', // 分享描述
        	link: url, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
        	imgUrl: imgUrl, // 分享图标
        	type: '', // 分享类型,music、video或link，不填默认为link
        	dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
        	success: function () {
        		alert("该分享其他人不会看到您的收藏")
        		// 用户确认分享后执行的回调函数
        	},
        	cancel: function () {
        		// 用户取消分享后执行的回调函数
        	}
    	});
    });

function load() {
	info = {
		"page" : 1,
		"rows" : 15
	}
	$.ajax({
		url : "/BZCX/collection/search",
		type : "post",
		data : info,
		async : false,
		success : function(data) {
			if(data.status == "success"){
				var da = data.data;
				$("#collection_load").empty();
				for (var i = 0; i < da.length; i++) {
					var url = da[i].stand[0].htmlUrl.split("\\")[3];
					var info = '<div class="collCon">'+
							   '<div class="imgDiv">'+
							   ' <img onclick="btnShow(\''+da[i].collectionId+'\',\''+da[i].stand[0].standardNo+'\')" src="img/button_nor_xuankuang.png" alt="暂无图片"></div>'+
							   '<div class="contentDiv" onclick="jump(\''+url+'\',\''+da[i].stand[0].standardId+'\')">'+
							   '<div class="conTitle">'+da[i].stand[0].standardCnName+'</div>'+
							   '<div class="conCent">'+da[i].stand[0].standardNo+'</div>'+
							   '</div></div>';
					$("#collection_load").append(info)
					choose = 0;
					allarray.put(da[i].stand[0].standardNo,da[i].collectionId);
				}
			}
		}
	});
}

function collectionSeach() {
	var input = $("#collection_input").val();
	info = {
			"standardCnName" : input,
			"standardNo" : input,
			"page" : 1,
			"rows" : 15
	}
	$.ajax({
		url : "/BZCX/collection/search",
		type : "post",
		data : info,
		async : false,
		success : function(data) {
			console.log(data)
			if(data.status == "success"){
				var da = data.data;
				$("#collection_load").empty();
				for (var i = 0; i < da.length; i++) {
					//collectionId
					var url = da[i].stand[0].htmlUrl.split("\\")[3];
					var info = '<div class="collCon">'+
							   '<div class="imgDiv">'+
							   ' <img src="img/button_nor_xuankuang.png" alt="暂无图片"></div>'+
							   '<div class="contentDiv" onclick="jump(\''+url+'\',\''+da[i].stand[0].standardId+'\')">'+
							   '<b><p class="conTitle">'+da[i].stand[0].standardCnName+'</p></b>'+
							   '<p class="conCent">'+da[i].stand[0].standardNo+'</p>'+
							   '</div></div>';
					$("#collection_load").append(info)
					choose = 0;
					allarray.put(da[i].stand[0].standardNo,da[i].collectionId);
				}
			}
		}
	});
}

function collectiondelete() {
	$.ajax({
		url : "/BZCX/collection/deletes",
		type : "post",
		data : info,
		async : false,
		success : function(data) {
			
			
		}
	});
}

//底部按钮显示
function btnShow(id,no){
	if($(event.target).attr("src")=='img/button_sel_xuankuang.png'){
		$(event.target).attr("src",'img/button_nor_xuankuang.png');
		if(choose == 1){
			allarray.remove(no);
		}else{
			dataMap.remove(no);
		}
	}else{
		$(event.target).attr("src",'img/button_sel_xuankuang.png');
		if(choose == 1){
			allarray.put(no,id);
		}else{
			dataMap.put(no,id);
		}
	}
	
	$('.btnDiv').css("left","0");
}

//全选按钮
function checkall(){
	choose = 1;
	$(".imgDiv img").attr("src",'img/button_sel_xuankuang.png');
}

//删除按钮
function delet(){
	var array;
	if(choose == 1){
		array = allarray.valueArray();
	}else{
		array = dataMap.valueArray();
	}
	$.ajax({
		url : "/BZCX/collection/deletes",
		type : "post",
		data : {"collectionId":array},
		async : false,
		success : function(data) {
			if(data.code == 200){
				load();
			}
		}
	});	
}