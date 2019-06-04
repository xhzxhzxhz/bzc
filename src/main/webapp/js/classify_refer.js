$(function(){
	//后退图标点击
	$('.retreat').click(function(){
		history.back();
	})
	//搜索框点击
	$(".top_help").click(function(){
		$('.top_help').css('display','none');
		$('.top_inputDiv input').focus();
	});
	//搜索框失焦
	$('.top_inputDiv input').blur(function(){
		if($('.top_inputDiv input').val()==''){
			$('.top_help').css('display','flex').css('display','-webkit-flex');
			var keyword = $("#ics_stand_keyword").val();
			var parameter = {
					"icsCode" : arrurl.icsCode,
					"page" : 1,
					"rows" : 14,
					"moudle" : "4003",
					"standardNo" : keyword,
					"standardCnName" : keyword,
					"relation" : "or"
				}
			load(parameter);
		}
	})
	$('.top_inputDiv input').keypress(function(){
		if($(event.keyCode)[0]=='13'){
			var keyword = $("#ics_stand_keyword").val();
			var parameter = {
					"icsCode" : arrurl.icsCode,
					"page" : 1,
					"rows" : 14,
					"moudle" : "4003",
					"standardNo" : keyword,
					"standardCnName" : keyword,
					"relation" : "or"
				}
			load(parameter);
		}
	})
	//获取url参数
	infourl = window.location.search;
	var arrurl = infourl.split("=")[1];
	//解码
	arrurl = decodeURIComponent(decodeURIComponent(arrurl));
	//把获取的字符串转成json对象
	arrurl = JSON.parse(arrurl);
	console.log(arrurl)
	//加载分类
	var parameter = {
		"icsCode" : arrurl.icsCode,
		"page" : 1,
		"rows" : 14,
		"moudle" : "4003"
	}
	load(parameter);
})

function load(parameter) {
	$.ajax({
		url : "/BZCX/standard/search",
		type : "post",
		data : parameter,
		async : true,
		success : function(res){
			if(res.status == "success"){
				$(".listDiv").empty();
				var da = res.data;
				for (var i = 0; i < da.length; i++) {
					var url = da[i].htmlUrl.split("\\")[3];
					var info = "<div class='div-con' onclick='jump(\""+url+"\",\""+da[i].standardId+"\")'>"
							+"<p class='standard-number'>"+da[i].standardNo+"</p>"
							+"<p class='standard-name'>"+da[i].standardCnName+"</p>"
							+"<p class='carry-date'>"+da[i].implementationDate+"</p>"
							+"<p class='department'>"+da[i].pubOrg+"</p>"
							+"<span class='standard-state'>"+da[i].standardStatus+"</span></div>";
					$(".listDiv").append(info);
				}
			}			
		}
	})	
	/*<div class='div-con'>
	<p class='standard-number'>标准号</p>
	<p class='standard-name'>标准名11111111111111111111111111111111111111</p>
	<p class='carry-date'>实施日期</p>
	<p class='department'>发布部门</p>
	<span class='standard-state'>正在实施</span>
</div>*/
}