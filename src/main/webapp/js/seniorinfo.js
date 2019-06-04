var info;
var infourl;
var page = 2;
var rows = 14;
var totalpage;
var firstload = true;
var scondeseach;
var title;
$(function() {
	infourl = window.location.search;
	var arrurl = infourl.split("=");
	infourl = arrurl[1];
	//改变下标题，高级查询和分类查询共用一个结果页
	title = arrurl[0];
	title = decodeURIComponent(decodeURIComponent(title));
	title = title.split("?")[1];
	if(title == "classification"){
		$("#seach_title").empty();
		$("#seach_title").append("分类查询");
	}
	infourl = decodeURIComponent(decodeURIComponent(infourl));
	infourl = JSON.parse(infourl);
	load(infourl)
	$(window).scroll(function(){
		if(page <= totalpage){
		var scrollTop=Math.ceil($(this).scrollTop());
		var scrollHeight=$(document).height();
		var windowHeight=$(this).height();
		if(scrollTop+windowHeight > (scrollHeight-40)){
			infourl.page = page;
			$(".loadDiv").css("left","0px") //显示loading加载
			var url;
			var dainfo;
			if(firstload){
				url = "/BZCX/standard/search";
				dainfo = infourl;
			}else{
				url = '/BZCX/standard/second/search';
				dainfo = scondeseach;
			}
			$.ajax({
				url : url,
				type : "post",
				data : dainfo,
				async : false,
				success : function(res){
					var da = res.data;
					var info = "";
					
						for (var i = 0; i < da.length; i++) {
							var url = da[i].htmlUrl.split("\\")[3];
							if(i%2==0){
								var highCon=$('<div></div>');
								highCon.addClass('highCon singular');
								var standard=$('<div onclick="jump(\''+url+'\',\''+da[i].standardId+'\')"></div>');
								standard.addClass('standard');
								standard.text(res.data[i].standardNo);
								var standardName=$('<div onclick="ump(\''+url+'\',\''+da[i].standardId+'\')"></div>');
								standardName.addClass('standardName');
								standardName.text(res.data[i].standardCnName)
								highCon.append(standard);
								highCon.append(standardName);					
								$('#Secondary_load').append(highCon)
							}else{
								var highCon=$('<div></div>');
								highCon.addClass('highCon dual');
								var standard=$('<div onclick="jump(\''+url+'\',\''+da[i].standardId+'\')"></div>');
								standard.addClass('standard');
								standard.text(res.data[i].standardNo);
								var standardName=$('<div onclick="jump(\''+url+'\',\''+da[i].standardId+'\')"></div>');
								standardName.addClass('standardName');
								standardName.text(res.data[i].standardCnName)
								highCon.append(standard);
								highCon.append(standardName);					
								$('#Secondary_load').append(highCon)
							}
						}
						page++;	
						$(".loadDiv").css("left","100%") //关闭loading加载
				}
			})	
		}
		}else{
			//到底部
			//数据加载完了
			$(".loadDiv").css("left","100%");
			$(".botm").css("left","0") //打开到底部了
		}
	})
})

//微信分享
wx.ready(function () {
	var title;
	if(title == "classification"){
		title = "分类查询";
	}else{
		title = "高级查询";
	}
    	wx.onMenuShareAppMessage({
    		title: title, // 分享标题
        	desc: title+'结果页面，可进行二次检索', // 分享描述
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



function Secondary_seach() {
	$(".botm").css("left","100%") //关闭到底部了
	$("#error").addClass("errorHide");
	page = 2;
	firstload = false;
	//loading显示
	$(".loadDiv").css("left","0");
	var val = $("#Secondary_val").val();
	scondeseach = {"standardNo" : val,"page":1,"rows":rows};
	if(val == ""){
		load(infourl)
	}else{
		$.ajax({
			type:'post',
			url:'/BZCX/standard/second/search',
			data:scondeseach,
			success:function(res){
				totalpage = Math.ceil(res.total/rows);
				//loading关闭
				$(".loadDiv").css("left","100%");
				var da = res.data;
				$('#Secondary_load').empty();
				if(res.total > 0){
					for(var i=0;i < da.length;i++){
						var url = da[i].htmlUrl.split("\\")[3];
						if(i%2==0){
							var highCon=$('<div></div>');
							highCon.addClass('highCon singular');
							var standard=$('<div onclick="jump(\''+url+'\',\''+da[i].standardId+'\')"></div>');
							standard.addClass('standard');
							standard.text(res.data[i].standardNo);
							var standardName=$('<div onclick="jump(\''+url+'\',\''+da[i].standardId+'\')"></div>');
							standardName.addClass('standardName');
							standardName.text(res.data[i].standardCnName)
							highCon.append(standard);
							highCon.append(standardName);					
							$('#Secondary_load').append(highCon)
						}else{
							var highCon=$('<div></div>');
							highCon.addClass('highCon dual');
							var standard=$('<div></div>');
							standard.addClass('standard');
							standard.text(res.data[i].standardNo);
							var standardName=$('<div></div>');
							standardName.addClass('standardName');
							standardName.text(res.data[i].standardCnName)
							highCon.append(standard);
							highCon.append(standardName);					
							$('#Secondary_load').append(highCon)
						}
					}
				}else{
					//如果没有数据的话
					$("#error").removeClass("errorHide");
				}
			}
		})
	}
}


function load(info) {
	firstload = true;
	//loading显示
	$(".loadDiv").css("left","0");
	$.ajax({
		type:'post',
		url:'/BZCX/standard/search',
		data:info,
		success:function(res){
			totalpage = Math.ceil(res.total/rows);
			var da = res.data;
			//loading关闭
			$(".loadDiv").css("left","100%");
			$('#Secondary_load').empty();
			if(res.total > 0){
				for(var i=0;i < da.length;i++){
					var url = da[i].htmlUrl.split("\\")[3];
					if(i%2==0){
						var highCon=$('<div></div>');
						highCon.addClass('highCon singular');
						var standard=$('<div onclick="jump(\''+url+'\',\''+da[i].standardId+'\')"></div>');
						standard.addClass('standard');
						standard.text(res.data[i].standardNo);
						var standardName=$('<div onclick="jump(\''+url+'\',\''+da[i].standardId+'\')"></div>');
						standardName.addClass('standardName');
						standardName.text(res.data[i].standardCnName)
						highCon.append(standard);
						highCon.append(standardName);					
						$('#Secondary_load').append(highCon)
					}else{
						var highCon=$('<div></div>');
						highCon.addClass('highCon dual');
						var standard=$('<div onclick="jump(\''+url+'\',\''+da[i].standardId+'\')"></div>');
						standard.addClass('standard');
						standard.text(res.data[i].standardNo);
						var standardName=$('<div onclick="jump(\''+url+'\',\''+da[i].standardId+'\')"></div>');
						standardName.addClass('standardName');
						standardName.text(res.data[i].standardCnName)
						highCon.append(standard);
						highCon.append(standardName);					
						$('#Secondary_load').append(highCon)
					}
				}
			}else{
				//如果没有数据的话
				$("#error").removeClass("errorHide");
			}
		}
	})
}