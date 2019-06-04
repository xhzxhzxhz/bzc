var infourl;
var page = 2;
var total;
var pagenumber;
var url;
$(function() {
	infourl = window.location.search;
	url = location.href.split('#').toString();//url不能写死
	var arrurl = infourl.split("=");
	infourl = arrurl[1];
	infourl = decodeURIComponent(decodeURIComponent(infourl));
	infourl = JSON.parse(infourl);
	$("#content_input").attr("value",infourl.keyword)
	load(infourl);
	$(window).scroll(function(){
		var scrollTop=Math.ceil($(this).scrollTop());
		var scrollHeight=$(document).height();
		var windowHeight=$(this).height();
		if(scrollTop+windowHeight > (scrollHeight-40)){
			$(".loadDiv").css("left","0px") //显示loading加载
			infourl.page = page;
			pagenumber = Math.ceil(total/infourl.rows);
			if(page <= pagenumber){
				$.ajax({
					url : "/BZCX/solr/search",
					type : "post",
					data : infourl,
					async : true,
					success : function(res){
						var da = res.data;
						total = res.total;
						for (var i = 0; i < da.length; i++) {
							var url = da[i].htmlUrl.split("\\")[3];
							var info = '<div class="resultCon">'+
							'<p class="p1" onclick="jump(\''+url+'\',\''+da[i].standardId+'\')">'+da[i].standardNo+'</p>'+
							'<p class="p2" onclick="jump(\''+url+'\',\''+da[i].standardId+'\')">'+da[i].fileContent+'</p>'+
							'<p class="p3" onclick="jump(\''+url+'\',\''+da[i].standardId+'\')">标准号 :'+da[i].standardNo+'</p></div>';
							$("#content_load").append(info);
						}
						page++;	
						$(".loadDiv").css("left","100%") //关闭loading加载
					}
				})	
			}else{
				//数据加载完了
				$(".loadDiv").css("left","100%");
				$(".botm").css("left","0") //打开到底部了
			}
		}
	})
})

//微信分享
wx.ready(function () {
    	wx.onMenuShareAppMessage({
    		title: '内容查询', // 分享标题
        	desc: '标准查', // 分享描述
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
    });

/*function highlight(){	
    clearSelection();//先清空一下上次高--亮显示的内容；
    var searchText = $('#searchIput').val();//获取你输入的关键字；
    var regExp = new RegExp(searchText, 'g');//创建正则表达式，g表示全局的
    $('.resultList').each(function(){  //遍历文章；    	
        var html = $(this).html();
        var newHtml = html.replace(regExp, '<a class="highlight" >'+searchText+'</a>');//将找到的关键字替换，加上highlight属性；
        $(this).html(newHtml);//更新文章；
    });
}*/

function clearSelection(){
    $('.resultList').each(function(){  //遍历
        $(this).find('.highlight').each(function(){ //找到所有highlight属性的元素；
            $(this).replaceWith($(this).html());//将他们的属性去掉；
        });
    });
}

function load(info) {
	$("#content_load").empty();
	//loading
	$(".loadDiv").css("left","0");
	$.ajax({
		url : "/BZCX/solr/search",
		type : "post",
		data : info,
		async : true,
		success : function(data) {
			//guandiao loading
			$(".loadDiv").css("left","100%");
			if(data.total > 0){
				total = data.total;
				var da = data.data;
				for (var i = 0; i < da.length; i++) {
					var url = da[i].htmlUrl.split("\\")[3];
					var info = '<div class="resultCon">'+
					'<p class="p1" onclick="jump(\''+url+'\',\''+da[i].standardId+'\')">'+da[i].standardNo+'</p>'+
					'<p class="p2" onclick="jump(\''+url+'\',\''+da[i].standardId+'\')">'+da[i].fileContent+'</p>'+
					'<p class="p3" onclick="jump(\''+url+'\',\''+da[i].standardId+'\')">标准号 :'+da[i].standardNo+'</p></div>';
					$("#content_load").append(info);
				}
			}else{
				//没有数据
				$("#error").removeClass("errorHide");
			}
		}
	});
}


function content_seach() {
	$(".btom").css("left","100%") //关闭到底部了
	$("#error").addClass("errorHide");
	page = 2;
	//出现loading
	$("#content_load").empty();
	$(".loadDiv").css("left","0");
	var keyword = $("#content_input").val();
	var havedata = true;
	if(keyword == ""){
		havedata = false;
		//加载无数据
		$("#error").removeClass("errorHide");
	}
	if(havedata){
		infourl = {
				"keyword" : keyword,
				"page" : 1,
				"rows" : 10
			}
			$.ajax({
				url : "/BZCX/solr/search",
				type : "post",
				data : infourl,
				async : true,
				success : function(data) {
					//关闭loading
					$(".loadDiv").css("left","100%");
					if(data.total > 0){
						total = data.total;
						var da = data.data;
						for (var i = 0; i < da.length; i++) {
							var url = da[i].htmlUrl.split("\\")[3];
							var info = '<div class="resultCon">'+
							'<p class="p1" onclick="jump(\''+url+'\',\''+da[i].standardId+'\')">'+da[i].standardNo+'</p>'+
							'<p class="p2" onclick="jump(\''+url+'\',\''+da[i].standardId+'\')">'+da[i].fileContent+'</p>'+
							'<p class="p3" onclick="jump(\''+url+'\',\''+da[i].standardId+'\')">标准号 :'+da[i].standardNo+'</p></div>';
							$("#content_load").append(info);
						}
					}else{
						//没有数据
						$("#error").removeClass("errorHide");
					}
				}
			});
	}

}


