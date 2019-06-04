
var arr = [];
/*var arr=[//模拟ajax请求的数据
         {imgurl:"imgs/index/ban.jpg",href:"#",text:"Picture 1 information"},
         {imgurl:"imgs/index/ban1.jpg",href:"#",text:"Picture 2 information"},
         {imgurl:"imgs/index/ban2.jpg",href:"#",text:"Picture 3 information"}
     ];*/

// tab切换
$(".news-p-tab1").on('click', function() {
	$(this).addClass("active").prev().removeClass("active");
	$(".main1_right_content-inner .news-ul").eq(1).css("display", "block");
	$(".main1_right_content-inner .news-ul").eq(0).css("display", "none");
	$("#changetitle").attr("onclick","more(2)");
});
$(".news-p-tab2").on('click', function() {
	$(this).addClass("active").next().removeClass("active");
	$(".main1_right_content-inner .news-ul").eq(1).css("display", "none");
	$(".main1_right_content-inner .news-ul").eq(0).css("display", "block");
	$("#changetitle").attr("onclick","more(1)");
});


function Slid(imgurl, href, text){
	this.imgurl = imgurl;
	this.href = href;
	this.text = text;
};

var newHtml = {
	initSlide : function(data) {
		var data = {}
		//昆仑公司标准动态
		$.ajax({
					url : "/BZCX/news/listPic",
					type : "post",
					data : data,
					ansyc : false,
					success : function(data) {
						var at = data.rows;
						var china = 0;//判断中国石油有没有设置图片
						var contry = 0;//判断国家动态有没有设置图片
						var pic = 0;//判断轮播图
						for (var i = 0; i < at.length; i++) {
							var htm ="";//新闻的拼接
							var img = "";
							var im = "";//单个图片的点击事件，跳转去详情页
							
							var href = "/BZCX/newsDetail.html?newsId=" + at[i].newsId;
							//判断是否有图，有图的拼地址
							if(at[i].imgPath != null && at[i].imgPath != "null" && at[i].imgPath != ""){
								var address = at[i].imgPath.split("&");
								img = "/newsImg/"+address[1];
								//轮播图判断传值
								if(at[i].newsCate == "昆仑公司标准动态" || at[i].newsCate == "通知公告"){
									if(pic < 3){
										arr.push(new Slid(img, href, at[i].title));
										pic+=1;
									}
								}
								//循环放置新闻
								if(at[i].newsCate == "中国石油标准动态"){
									if(china < 1){
										im = "<img src='"+img+"' onclick='img(\"" + at[i].newsId + "\")'/>"
										$("#chinaPicture").append(im);
									}
									china+=1;
								}else if(at[i].newsCate == "国家标准动态"){
									if(contry < 1){
										im = "<img src='"+img+"' onclick='img(\"" + at[i].newsId + "\")'/>"
										$("#contryPicture").append(im);
									}
										contry+=1;
								}
							}
						}
						if(china == 0){
							im = "<img src='imgs/news/news1.png' alt='当前未设置图'/>"
							$("#chinaPicture").append(im);
						}
						if(contry == 0){
							im = "<img src='imgs/news/news2.png' alt='当前未设置图'/>"
							$("#contryPicture").append(im);
						}
						if(pic < 3){
							var imgs = "imgs/news/news.png";
							for (var j = 1; j <= (3-pic); j++) {
								arr.push(new Slid(imgs, "#", "当前未设置轮播图"));
							}
						}
						$("#mySlide").mySlide(arr, 3000);
						$("#mySlide a").attr("target", "_blank");
					}
				});
		//通知公告
		$.ajax({
			url : "/BZCX/news/list",
			type : "post",
			data : {"newsCate" : 1},
			ansyc : false,
			success : function(data) {
				$("#news_tab_right").empty();
				var at = data.rows;
				for (var i = 0; i < at.length; i++) {
					htm = "<li title='"+at[i].title+"'><i></i><b onclick='detailed(\"" + at[i].newsId + "\")'>"+at[i].title+"</b><span>"+at[i].publishDate+"</span></li>"
					$("#news_tab_right").append(htm);
					if(i>=10){
						break;
					}
				}
			}
		});
		//昆仑公司
		$.ajax({
			url : "/BZCX/news/list",
			type : "post",
			data : {"newsCate" : 2},
			ansyc : false,
			success : function(data) {
				$("#news_tab_left").empty();
				var at = data.rows;
				for (var i = 0; i < at.length; i++) {
					htm = "<li title='"+at[i].title+"'><i></i><b onclick='detailed(\"" + at[i].newsId + "\")'>"+at[i].title+"</b><span>"+at[i].publishDate+"</span></li>"
					$("#news_tab_left").append(htm);
					if(i>=10){
						break;
					}
				}
			}
		});
		//中国石油标准
		$.ajax({
			url : "/BZCX/news/list",
			type : "post",
			data : {"newsCate" : 3},
			ansyc : false,
			success : function(data) {
				$("#chinaNews").empty();
				var at = data.rows;
				for (var i = 0; i < at.length; i++) {
					htm = "<li title='"+at[i].title+"'><i></i><b onclick='detailed(\"" + at[i].newsId + "\")'>"+at[i].title+"</b><span>"+at[i].publishDate+"</span></li>"
					$("#chinaNews").append(htm);
					if(i>=10){
						break;
					}
				}
			}
		});
		//国家石油标准
		$.ajax({
			url : "/BZCX/news/list",
			type : "post",
			data : {"newsCate" : 4},
			ansyc : false,
			success : function(data) {
				$("#countryNews").empty();
				var at = data.rows;
				for (var i = 0; i < at.length; i++) {
					htm = "<li title='"+at[i].title+"'><i></i><b onclick='detailed(\"" + at[i].newsId + "\")'>"+at[i].title+"</b><span>"+at[i].publishDate+"</span></li>"
					$("#countryNews").append(htm);
					if(i>=10){
						break;
					}
				}
			}
		});
		//行业动态标准
		$.ajax({
			url : "/BZCX/news/list",
			type : "post",
			data : {"newsCate" : 5},
			ansyc : false,
			success : function(data) {
				$("#industryNews").empty();
				var at = data.rows;
				for (var i = 0; i < at.length; i++) {
					htm = "<li title='"+at[i].title+"'><i></i><b onclick='detailed(\"" + at[i].newsId + "\")'>"+at[i].title+"</b><span>"+at[i].publishDate+"</span></li>"
					$("#industryNews").append(htm);
					if(i>=10){
						break;
					}
				}
			}
		});
		//国外标准动态
		/*$.ajax({
			url : "/BZCX/news/list",
			type : "post",
			data : {"newsCate" : 6},
			ansyc : false,
			success : function(data) {
				$("#abroadNews").empty();
				var at = data.rows;
				for (var i = 0; i < at.length; i++) {
					htm = "<li><i></i><b onclick='detailed(\"" + at[i].newsId + "\")'>"+at[i].title+"</b><span>"+at[i].publishDate+"</span></li>"
					$("#abroadNews").append(htm);
					if(i>=10){
						break;
					}
				}
			}
		});*/
		//其他标准动态
		$.ajax({
			url : "/BZCX/news/list",
			type : "post",
			data : {"newsCate" : 6},
			ansyc : false,
			success : function(data) {
				$("#otherNews").empty();
				var at = data.rows;
				for (var i = 0; i < at.length; i++) {
					htm = "<li><i></i><b onclick='detailed(\"" + at[i].newsId + "\")'>"+at[i].title+"</b><span>"+at[i].publishDate+"</span></li>"
					$("#otherNews").append(htm);
					if(i>=10){
						break;
					}
				}
			}
		});
		
	},
};

$(function() {
	// 先初始化表格
	newHtml.initSlide();
});

//更多
function more(data){
	window.open("newsList.html?newsCate="+data,"_self");
};


//点击信息
function detailed(data){
	window.open("newsDetail.html?newsCate="+data);
};

//点击图片
function img(data){
	window.open("newsDetail.html?newsCate="+data);
};
