
var pictureNewsHtml = {
		initSlide : function(data) {
			var data = {
			}
			$.ajax({
				url : "/BZCX/news/list",
				type : "post",
				data : data,
				success : function(data) {
					var art = data.rows;
					var num = 0;
					for (var i = 0; i < art.length; i++) {
						if(art[i].imgPath != null){
							var address = art[i].imgPath.split("&");
							var img = "/newsImg/"+address[1];
							var htm = "<li onclick='picimg(\"" + art[i].newsId + "\")'><img src='"+img+"' alt='图片加载失败'/>"
								+"<div class='mb'></div><p class='mb-tit'>"+art[i].title+"</p></li>";
								$("#pictureNews").append(htm);
								num+=1;
								if(num >=3){
									break;
								}
						}
					}
				}
			});
		}			
};

$(function() {
	// 先初始化表格
	pictureNewsHtml.initSlide();
});


//点击图片
function picimg(data){
	window.open("newsDetail.html?newsCate="+data,"_self");
};

