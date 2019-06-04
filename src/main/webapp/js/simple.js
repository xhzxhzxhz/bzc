var page = 2;
var totalpage;
var rows = 14;
var typeName="标准信息"
var url;
$(function() {
	//加载标准数据
	load();
	//加载热门检索词
	host();
	history();
	icsType();
	url = location.href.split('#').toString();//url不能写死
	$("#simple_input").focus(function(){
		$(".searchDiv").css("display","flex").css("display","-webkit-flex");
    })
    $("#simple_input").keydown(function(event){
    	console.log(1)
        if(event.keyCode == 13)    
        {
        	simple_search($("#simple_input").val())
        }
    });
		$(window).scroll(function(){
			if($('.tradeDiv').css("left")!=="0px"){
			var scrollTop=Math.ceil($(this).scrollTop());
			var scrollHeight=$(document).height();
			var windowHeight=$(this).height();
			if(scrollTop+windowHeight > (scrollHeight-40)){
				$(".loadDiv").css("left","0%"); //显示loading加载
				var data = {
						"moudle" : "4001",//这个页面固定
						"paramCnName":input,
						"paramNo" :input,
						"typeName" : "标准信息",
						"page" : page
				}
				if(page <= totalpage){
					$.ajax({
						url : "/BZCX/standard/searchSecond",
						type : "post",
						data : data,
						async : false,
						success : function(res){
								var da = res.data;
								var info = "";
								for (var i = 0; i < da.length; i++) {
									var url = da[i].htmlUrl.split("\\")[3];
									if(i%2==0){
										info =  ' <div class="highCon singular">';
									}else{
										info =  ' <div class="highCon dual">';
									}
					            	info += `<div class="standard" onclick="jump1('${da[i].standardNo}')">${da[i].standardNo}</div>
									<div class="standardName" onclick="jump1('${da[i].standardNo}')">${da[i].standardCnName}</div>
								</div>`;
									$("#simplen_seach").append(info);
								}
								page++;	
								setTimeout(function() {
									$(".loadDiv").css("left","100%") //关闭loading加载
								},500)
						}
				})	
				}else{
					//数据加载完了
					$(".loadDiv").css("left","100%");
					$(".botm").css("left","0"); //打开到底部了
				}
			}
			}	
		})
})
//微信分享
wx.ready(function () {
		wx.onMenuShareTimeline({
		    title: 'xxxxxxxxxx', // 分享标题
		    desc: '标准查,查标准', // 分享描述
		    link: url, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
		    imgUrl: imgUrl, // 分享图标
		    success: function () {
		        // 用户确认分享后执行的回调函数
		    },
		    cancel: function () {
		        // 用户取消分享后执行的回调函数
		    }
		});
	
    	wx.onMenuShareAppMessage({
    		title: '简单查询', // 分享标题
        	desc: '标准号、标准名称、行业查询', // 分享描述
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


var icsCode = "";
var stantype = "";
var input = "";

function simple_typeall(event) {
	//更换样式
	var el=event;
	$('#listTop button').removeClass('btnColor');
	$(el.target).addClass('btnColor');
}

function simple_typeGB(event) {
	stantype = "G";
	//更换样式
	var el=event;
	$('#listTop button').removeClass('btnColor');
	$(el.target).addClass('btnColor');
}
function simple_typeHB(event) {
	stantype = "H";
	//更换样式
	var el=event;
	$('#listTop button').removeClass('btnColor');
	$(el.target).addClass('btnColor');
}

function simple_clean_history() {
	
}
var storage = window.localStorage;
//历史检索词
function history() {
	if(window.localStorage){
		if(storage.length == 0){
			$.ajax({
				url : "/BZCX/history/userhistory",
				type : "post",
				data : {"moudle":"4001","typeName" : "标准查询","page":1},
				async : true,
				success : function(data) {
					if(data == ""){
						$("#simple_history").empty();
						//$("#simple_history").attr("style","display: none;");
						//如果没有历史检索记录的话，就隐藏
					}else{
					//	$("#simple_history").attr("style","display: inline;");
						var info = "";
						$("#simple_history").empty();
						var info = "";
						for (var i = 0; i < data.length; i++) {
							info += '<span onclick = "simple_search(\''+data[i]+'\')">'+data[i]+'</span>';
							storage[i] = data[i];
						}
						$("#simple_history").append(info);
					}
				}
			});
		}else{
			$("#simple_history").empty();
			var info = "";
			for (var i = 0; i < storage.length; i++) {
				info +=  '<span onclick = "simple_search(\''+storage[i]+'\')">'+storage[i]+'</span>';
			}
			$("#simple_history").append(info);
			//storage.clear();
		}
	}else{
		$.ajax({
			url : "/BZCX/history/userhistory",
			type : "post",
			data : {"moudle":"4001","typeName" : "标准查询","page":1},
			async : true,
			success : function(data) {
				if(data == ""){
					$("#simple_history").empty();
					//$("#simple_history").attr("style","display: none;");
					//如果没有历史检索记录的话，就隐藏
				}else{
				//	$("#simple_history").attr("style","display: inline;");
					var info = "";
					$("#simple_history").empty();
					var info = "";
					for (var i = 0; i < data.length; i++) {
						info += '<span onclick = "simple_search(\''+data[i]+'\')">'+data[i]+'</span>';
					}
					$("#simple_history").append(info);
				}
			}
		});
	}
}

//清除历史
function simple_clean_history() {
	$.ajax({
		url : "/BZCX/history/update",
		type : "post",
		async : true,
		data:{"moudle":"4001"},
		success : function(data) {
			if(data.code == 200){
				storage.clear();
				history();
			}
		}
	});
}

//热门检索词
function host() {
	$.ajax({
		url : "/BZCX/history/hotword",
		type : "post",
		data : {"moudle":"4001","page":1,"typeName":"标准查询"},
		async : true,
		success : function(data) {
			if(data.length>0){
				$(".searchDiv").css("display","block")
				var info = "";
				$("#simple_host").empty();
				for (var i = 0; i < data.length; i++) {
					info = '<span onclick = "simple_search(\''+data[i]+'\')">'+data[i]+'</span>'
					$("#simple_host").append(info);
				}
			}
		}
	});
}

function load(info){	
	 $.ajax({
		url : "/BZCX/standard/searchSecond",
		type : "post",
		data:{"moudle":"4001","typeName":"标准信息","page":1},
		async : true,
		success : function(res) {
			var da = res.data;
			$("#simplen_seach").empty();
			if(res.total > 0){
				var info = "";
				for (var i = 0; i < da.length; i++) {
					var url = da[i].htmlUrl.split("\\")[3];
					if(i%2==0){
						info =  ' <div class="highCon singular">';
					}else{
						info =  ' <div class="highCon dual">';
					}
					info += `<div class="standard" onclick="jump1('${da[i].standardNo}')">${da[i].standardNo}</div>
					<div class="standardName" onclick="jump1('${da[i].standardNo}')">${da[i].standardCnName}</div>
				</div>`;
					$("#simplen_seach").append(info)
				}
				
			}else{
				//无数据
				$("#error").removeClass("errorHide");
			}
		}
	 })
}


function Preservationstorage(keyword) {
	var myArray=new Array();
	var order = false;
	for (var i = 0; i < storage.length; i++) {
		if(storage[i] != "undefined"){
			if(keyword == storage[i]){
				order = true;
				continue;
			}
			if(order){
				myArray[i] = storage[i];
			}else{
				/*if(i+1 < storage.length){
					myArray[i+1] = storage[i];
				}*/
				if(storage.length <= 9){
					myArray[i+1] = storage[i];
				}else if(i+1 < storage.length){
					myArray[i+1] = storage[i];
				}
			}
		}
	}
	$("#simple_history").empty();
	var info = "";
	myArray[0] = keyword;
	storage.clear();
	for (var i = 0; i < myArray.length; i++) {
		storage[i] = myArray[i];
		info +=  '<span onclick = "simple_search(\''+storage[i]+'\')">'+storage[i]+'</span>';
	}
	$("#simple_history").append(info);
}

//下拉选项显示
function simple_changetype() {
	if($(".select_hy>ul").css("display")=="none"){
		$(".select_hy>ul").css({"display":"block"})
	}else{
		$(".select_hy>ul").css({"display":"none"})
	}
	//点击下拉三角
	$(".select_hy>ul>li").click(function(){
		$(this).css({background:"#eee"});
		$(this).siblings("li").css({background:"#fff"});
	    typeName= $(this).text();
		$(".tradeSpan").empty().append(typeName)
		if(typeName=="标准信息"){
			$("#simple_input").attr('placeholder',"标准号或标准名称")
			$(".tradeDiv").css("display","none")
			$(".standard_show").css("display","block")
			$.ajax({
				url : "/BZCX/standard/searchSecond",
				type : "post",
				data:{"moudle":"4001","typeName":typeName,"page":1},
				async : true,
				success : function(res) {
					var da = res.data;
					$("#simplen_seach").empty();
					if(res.total > 0){
						$("#error").addClass("errorHide");
						var info = "";
						for (var i = 0; i < da.length; i++) {
							var url = da[i].htmlUrl.split("\\")[3];
							if(i%2==0){
								info =  ' <div class="highCon singular">';
							}else{
								info =  ' <div class="highCon dual">';
							}
							info += `<div class="standard" onclick="jump1('${da[i].standardNo}')">${da[i].standardNo}</div>
							<div class="standardName" onclick="jump1('${da[i].standardNo}')">${da[i].standardCnName}</div>
						</div>`;
							$("#simplen_seach").append(info)
						}
						
					}else{
						//无数据
						$("#error").removeClass("errorHide");
					}
				}
			});
		}else{
			$("#simple_input").attr('placeholder',"文号或名称");
			$(".standard_show").css("display","none")
			$(".tradeDiv").css("display","block")
			$.ajax({
				url : "/BZCX/standard/searchSecond",
				type : "post",
				data:{"moudle":"4001","typeName":typeName,"page":1},
				async : true,
				success : function(res) {
					console.log(res)
					var da = res.data;
					$(".scrollDiv>ul").empty();
					if(res.total > 0){
						$("#error").addClass("errorHide");
						var info = "";
						for (var i = 0; i < da.length; i++) {
							var url = da[i].htmlUrl.split("\\")[3];
							info += `  <li onclick="jump1('${res.data[i].lawsNo}')">
							<span class='news_title'>${res.data[i].lawsCnName}</span>
                            <span class='news_time'>发布日期：${res.data[i].publishDate==null?'':getTime(res.data[i].publishDate.time)}</span>
							<span class='news_class'>效力级别：${res.data[i].effectivenessLevel}</span>
                        </li>`;
							
						}
						$(".scrollDiv>ul").append(info)
						
					}else{
						//无数据
						$("#error").removeClass("errorHide");
					}
				}
			});
		}
		
		
	})

}

//function icacodeload() {
//	$.ajax({
//		url : "/BZCX/icstype/list",
//		type : "post",
//		async : false,
//		success : function(data) {
//			if(data.code == 200){
//				$(".scrollDiv").empty();
//				var da = data.data;
//				var info1 = '<div class="tradeList" id="high_icscode">'+
//				'<div id="shadow" onclick="tradeDivHide()"></div>'+
//				'<div class="tradeDivShow" onclick="high_icstype(\'\',\'全部\')">'+
//				'<div class="tradeText">全部</div>'+
//				'<img src="" alt=""></div></div>';
//				$(".scrollDiv").append(info1);
//				
//				for (var i = 0; i < da.length; i++) {
//					var info = '<div class="tradeList" id="high_icscode">'+
//					'<div id="shadow" onclick="tradeDivHide()"></div>'+
//					'<div class="tradeDivShow" onclick="high_icstype(\''+da[i].icsCode+'\',\''+da[i].icsName+'\')">'+
//					'<div class="tradeText">'+da[i].icsName+'</div>'+
//					'<img src="" alt=""></div></div>';
//					var info1=$(info);																				
//					$(".scrollDiv").append(info1);
//				}
//			}
//		}
//	});
//}
function simple_search(input_data) {
	$(".searchDiv").css("display","none");
	$("#error").addClass("errorHide");
	page = 2;
	$(".botm").css("left","100%") //关闭到底部了
	if(input_data == undefined){
        input = $("#simple_input").val();
        console.log(input)
	}else{
		$("#simple_input").attr("value",input_data);
        input = input_data;
        console.log(input)
	}
	var data = {
			"moudle" : "4001",
			"typeName" :typeName,
			"paramNo":input,
			"paramCnName" :input,
	}
	$.ajax({
		url : "/BZCX/standard/searchSecond",
		type : "post",
		data : data,
		async : true,
		success : function(res) {
			if(res.status == "success"){
				if(res.total < rows){
					$(".botm").css("left","0%")
				}
				totalpage = Math.ceil(res.total/rows);
				var da = res.data;
				var info = "";
				if(res.total > 0 && typeName=="标准信息"){
					$(".tradeDiv").css("display","none")
					$(".standard_show").css("display","block")
					$("#simplen_seach").empty()
					$("#error").addClass("errorHide");
					for (var i = 0; i < da.length; i++) {
						var url = da[i].htmlUrl.split("\\")[3];
						if(i%2==0){
							info =  ' <div class="highCon singular">';
						}else{
							info =  ' <div class="highCon dual">';
						}
							info += `<div class="standard" onclick="jump1('${da[i].standardNo}')">${da[i].standardNo}</div>
							<div class="standardName" onclick="jump1('${da[i].standardNo}')">${da[i].standardCnName}</div>
						</div>`;
						$("#simplen_seach").append(info);
					}
					//history();
				}else if(res.total > 0 && typeName=="法律法规"){
					$(".standard_show").css("display","none")
					$(".tradeDiv").css("display","block")
					$("#error").addClass("errorHide");
					$(".scrollDiv>ul").empty();
					for (var i = 0; i < da.length; i++) {
						info += ` <li onclick="jump1('${res.data[i].lawsNo}')">
							 	<span class='news_title'>${res.data[i].lawsCnName}</span>
								<span class='news_time'>发布日期：${res.data[i].publishDate==null?'':getTime(res.data[i].publishDate)}</span>
								<span class='news_class'>效力级别：${res.data[i].effectivenessLevel}</span>
                        </li>`;
						
					}
					$(".scrollDiv>ul").append(info)
					//history();
				}
				else{
					//加载无数据
					$("#error").removeClass("errorHide");
					$(".standard_show").css("display","none")
					$(".tradeDiv").css("display","none")
					$(".scrollDiv>ul").empty();
					$("#simplen_seach").empty()
				}
				//更新历史检索
				Preservationstorage(input);
			}
		}
	});
}
function high_icstype(ics,icsName) {
	icsCode = ics;
	var data = {
			"moudle" : "4001",//这个页面固定
			"relation" : "or",//这个页面固定
			"standardNo":"",
			"standardCnName" :"",
			"icsCode":icsCode,//行业类型
			"rows" : rows,
			"page" : page
	}
	load(data)
	//更换样式	
	$(".tradeSpan").text(icsName.slice(0,2));	
	$('.tradeDivShow div').removeClass('btnColor');
	$('.tradeDivShow img').attr("src","");
	$(event.target).addClass('btnColor');
	$(event.target).next().attr("src","img/icon_duigou.png");
	setTimeout(function(){
		$(".tradeDiv").css('left','100%');
	},1000);
	//$("#simple_input").val(icsName)
};
function jump1(noTrim){
	console.log(111)
		$.ajax({
		url : "/BZCX/standard/getStdFileId",
		type : "post",
		data : {typeName:typeName,noTrim:noTrim},
		async : true,
		success : function(res) {
			console.log(res,111)
			if(typeName=="法律法规" && res.data!==null){
				console.log(1)
				window.location.href = `law_details.html?id=${res.data.stdFileId}&page=1`
			}else if(typeName=="标准信息" && res.data!==null){
				window.location.href = `details.html?id=${res.data.stdFileId}&page=1`
		}
		}
	})	
}

//处理时间戳
function getTime(time){
   function add0(m){return m<10?'0'+m:m };
   var time = new Date(time);
   var y = time.getFullYear();
   var m = time.getMonth()+1;
   var d = time.getDate();
   var endTime=y+'-'+add0(m)+'-'+add0(d);
   return endTime
}