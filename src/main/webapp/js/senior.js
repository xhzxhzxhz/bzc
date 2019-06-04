var info;
var icsCode;
$(function() {
	//加载行业		
		//点击图片    右侧抽屉滑出
		$(".standDiv").click(function(){
			icacodeload()
			$(".tradeDiv").animate({
				left:0
			},1000)
		})
		//抽屉隐藏
		$('.shadow').click(function(){
			$(".tradeDiv").css('left','100%')
		})
	
		var relation = "and";//关系   并且=and  或者=or
		var ordname = "STANDARD_CN_NAME";
		$('.standardConImg img').click(function(el){
			$(el.target).parent().parent().children().children('img').attr('src','img/button_nor_xuankuang.png')			
			$(el.target).attr('src','img/button_sel_xuankuang.png');
			if($(el.target).parent().text()=='标准名称'){
				ordname='STANDARD_CN_NAME';
			}else if($(el.target).parent().text()=='标准状态'){
				ordname='STANDARD_STATUS';
			}else{
				ordname = "PUBLISH_DATE";
			}
		});
		$('.icsCode img').click(function(el){
			$(el.target).parent().parent().children().children('img').attr('src','img/button_nor_xuankuang.png')			
			$(el.target).attr('src','img/button_sel_xuankuang.png');
			if($(el.target).parent().text()=='并且'){
				relation='and';
			}else{
				relation='or';
			}
		});
		//查询
		$('.btn2').click(function(){
			var standardNo=$('#standardNo').val(); //标准号
			var standardName=$('#standardName').val(); //标准名称
			var pubOrg=$('#pubOrg').val(); //发布单位
			var publishDatebegin=$("#publishDatebegin option:selected").text();//发布起始时间
			var publishDateend=$("#publishDateend option:selected").text();//发布结束时间
			var implementationDatebegin=$("#implementationDatebegin option:selected").text();//实施结束时间
			var implementationDateend=$("#implementationDateend option:selected").text();//实施结束时间
			publishDatebegin = selectAll(publishDatebegin);
			publishDateend = selectAll(publishDateend);
			implementationDatebegin = selectAll(implementationDatebegin);
			implementationDateend = selectAll(implementationDateend);
			
			var standardStatus; //发布状态  1=现行  2=暂行  3=废止
			var draftUnit = $("#draftUnit").text();
			if($('#standardStatus option:selected').text()=='现行'){
				standardStatus='1';
			}else if($('#standardStatus option:selected').text()=='暂行'){
				standardStatus='2';
			}else if($('#standardStatus option:selected').text()=='废止'){
				standardStatus='3';
			}else{
				standardStatus = '';
			}
			//var icsCode=$('#icsCode').val(); //行业
			
			info={
				"moudle" :'4002',
				"standardNo" : standardNo,
				"standardCnName" : standardName,
				"pubOrg" : pubOrg,
				"draftUnit": draftUnit,
				"publishDatebegin" : publishDatebegin,
				"publishDateend" : publishDateend,
				"implementationDatebegin" : implementationDatebegin,
				"implementationDateend" : implementationDateend,
				"standardStatus" : standardStatus,
				"icsCode" : icsCode,
				"relation" : relation,
				"order" :ordname,
				"page" : 1,
				"rows" : 14
			}
		var nextinfo = JSON.stringify(info); 
		nextinfo = myencodeURI(nextinfo);
		/*console.log(nextinfo)
		window.sessionStorage["seniorinfo"] = nextinfo;
		var urlinfo = "moudle=4002&standardNo="+standardNo+"&pubOrg="+pubOrg+"&order="+ordname+"&relation="+relation+
		"&standardCnName="+standardName+"&icsCode="+icsCode+"&publishDate=&pSize=15&cPage=1";*/
		window.location.href = "hightwo.html?nextinfo="+nextinfo;
	})
	//重置
	$(".btn1").click(function(){
		$("#standardNo").val("");
		$("#standardName").val("");
		$("#draftUnit").val("");
		$("#pubOrg").val("");	
		$("select").find("option:first").prop("selected",true);
		/*$("#publishDatebegin").val(1);
		$("#publishDateend").val(1);
		$("#implementationDatebegin").val(1);
		$("#implementationDateend").val(1);*/
		high_icstypeall();
		$('.standardConImg img').attr('src','img/button_nor_xuankuang.png')
		$('#highstand_reset').attr('src','img/button_sel_xuankuang.png');
		$('.icsCode img').attr('src','img/button_nor_xuankuang.png')
		$('#highicscode_reset').attr('src','img/button_sel_xuankuang.png');
	})
		selectyear("publishDatebegin",false);
		selectyear("publishDateend",true);
		selectyear("implementationDatebegin",false);
		selectyear("implementationDateend",true);
})

function selectAll(select) {
	if(select == "全部"){
		return "";
	}else{
		return select;
	}
}

function selectyear(htmlid,desc) {
	var date = new Date();
	var ye = date .getFullYear()
	$("#"+htmlid).empty();
	var defaultselect = '<option value="" class = "yeardefault" selected>全部</option>';
	$("#"+htmlid).append(defaultselect);
	//desc倒序，是到当前页面
	if(desc){
		for (var i = 0; i <= 20; i++) {
			var info = '<option value="">'+(ye-i)+'</option>';
			$("#"+htmlid).append(info);
		}
	}else{
		for (var i = 20; i >= 0; i--) {
			var info = '<option value="">'+(ye-i)+'</option>';
			$("#"+htmlid).append(info);
		}
	}
}


//微信分享
wx.ready(function () {
    	wx.onMenuShareAppMessage({
    		title: '高级查询', // 分享标题
        	desc: '通过详细条件进行精确的查找', // 分享描述
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

function myencodeURI(str){  
     return  encodeURIComponent(encodeURIComponent(str));  
} 


function load(info) {
	console.log(info)
	$.ajax({
		type:'post',
		url:'/BZCX/standard/search',
		data:info,
		success:function(res){
			for(var i=0;i<res.data.length;i++){
				if(i%2==0){
					var highCon=$('<div></div>');
					highCon.addClass('highCon singular');
					var standard=$('<div></div>');
					standard.addClass('standard');
					standard.text(res.data[i].standardNo);
					var standardName=$('<div></div>');
					standardName.addClass('standardName');
					standardName.text(res.data[i].standardCnName)
					highCon.append(standard);
					highCon.append(standardName);					
					$('.highTwo').append(highCon)
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
					$('.highTwo').append(highCon)
				}
			}
		}
	})
}

function icacodeload() {
	$.ajax({
		url : "/BZCX/icstype/list",
		type : "post",
		async : false,
		success : function(data) {
			if(data.code == 200){
				$(".scrollDiv").empty();
				var da = data.data;
				var infoall = '<div class="tradeList" id="high_icscode">'+
				'<div id="shadow"></div>'+
				'<div class="tradeDivShow" onclick="high_icstypeall()">'+
				'<div class="tradeText">全部</div>'+
				'<img src="" alt=""></div></div>';
				$(".scrollDiv").append(infoall);
				for (var i = 0; i < da.length; i++) {
					var info = '<div class="tradeList" id="high_icscode">'+
					'<div class="shadow" id="shadow"></div>'+
					'<div class="tradeDivShow" onclick="high_icstype(\''+da[i].icsCode+'\',\''+da[i].icsName+'\')">'+
					'<div class="tradeText">'+da[i].icsName+'</div>'+
					'<img src="" alt=""></div></div>';
					var info1=$(info);
					$(".scrollDiv").append(info1);
				}
			}
		}
	});
}

function high_icstypeall() {
	$("#stand_sel").empty();
	$("#stand_sel").append("全部")
	icsCode = '';
	$('.tradeDivShow div').removeClass('color_check');
	$('.tradeDivShow img').attr("src","");
	$(event.target).addClass('color_check');
	$(event.target).next().attr("src","img/icon_duigou.png");
	setTimeout(function(){
		$(".tradeDiv").css('left','100%');
	},1000);
}


function high_icstype(ics,icsName) {
	icsCode = ics;
	$("#stand_sel").empty();
	$("#stand_sel").append(icsName)
	//更换样式	
	$('.tradeDivShow div').removeClass('color_check');
	$('.tradeDivShow img').attr("src","");
	$(event.target).addClass('color_check');
	$(event.target).next().attr("src","img/icon_duigou.png");
	setTimeout(function(){
		$(".tradeDiv").css('left','100%');
	},1000);
	$("#stand_sel").text()
}

