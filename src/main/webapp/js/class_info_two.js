$(function(){
	//首页点击
	$('.go_info').click(function(){
		/*window.location.href = "class_info.html";*/
		history.back();
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
	load(arrurl.icsCode);
	
	$(".industry").empty();
	$(".industry").append(arrurl.icsName);
})

function load(icsCode) {
	$.ajax({
		url : "/BZCX/icstype/listTree",
		type : "post",
		data : {"icsCode" : icsCode},
		async : true,
		success : function(data) {
			if(data.code == 200){
				$(".content").empty();
				for (var i = 0; i < data.data.length; i++) {
					var info = "";
					if(data.data[i].threeIcs.length > 0){
						//如果有下一级菜单的话
						info = "<div class='listDiv'><div class='second' onclick='three_show()'>"
							+"<span class='c_blue'>"+data.data[i].icsCode+"</span>"
							+"<span class='list_content'>"+data.data[i].icsName+"【123】"+"</span>"
							+"<img src='img/btn_zhank.png'>"
							+"</div><div class='Fully'>";
						for (var j = 0; j < data.data[i].threeIcs.length; j++) {
							info += "<div class='three_con' onclick='loadinfo(\""+data.data[i].threeIcs[j].icsCode+"\")'>"
								+"<span class='c_gray'>"+data.data[i].threeIcs[j].icsCode+"</span>"
								+"<span class='list_content'>"+data.data[i].threeIcs[j].icsName+"</span>"
								+"<span class='diamonds'>"+data.data[i].threeIcs[j].icsSum+"</span>"
								+"</div>";
						}
					}else{
						//没有下一级菜单
						info = "<div class='listDiv' onclick='loadinfo(\""+data.data[i].icsCode+"\")'><div class='second'>"
							+"<span class='c_blue'>"+data.data[i].icsCode+"</span>"
							+"<span class='list_content'>"+data.data[i].icsName+"</span>"
							+"<img src=''>";
					}
					info += "</div></div>";
					$(".content").append(info);
				}
			}
			/*<div class='listDiv'>
			<div class='second'>
				<span class='c_blue'>01.020</span>
				<span class='list_content'>术语学(原则和协调配合)</span>
				<img src='img/btn_shous.png'>
			</div>
			<div class='Fully'>
				<div class='three_con'>
					<span class='c_gray'>01.100.01</span>
					<span class='list_content'>术语学(原则和协调配合)</span>
					<span class='diamonds'>93</span>
				</div>
				<div class='three_con'>
					<span class='c_gray'>01.100.01</span>
					<span class='list_content'>术语学(原则和协调配合)</span>
					<span class='diamonds'>193</span>
				</div>
			</div>
		</div>*/
		}
	});
}
//三级菜单显示
function three_show(){
	if($(event.target).parent().next().css('display')=='none'){
		$(event.target).parent().next().css('display','block');
		$(event.target).parent().children('img').attr('src','img/btn_shous.png');
	}else{
		$(event.target).parent().next().css('display','none');
		$(event.target).parent().children('img').attr('src','img/btn_zhank.png');
	}
}
//跳转详情页面
function loadinfo(icsCode) {
	var info = {
			"icsCode" : icsCode
	}
	var nextinfo = JSON.stringify(info); 
	nextinfo = myencodeURI(nextinfo);
	window.location.href = "classify_refer.html?icsCode="+nextinfo;
}

function myencodeURI(str){  
    return  encodeURIComponent(encodeURIComponent(str));  
} 

