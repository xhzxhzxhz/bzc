var totalPage;
//totalPage = Math.ceil(data.data.total/20);
//分页插件
/*$("#pagination").pagination({
    currentPage: 1, // 加载界面时候 默认第几页
    totalPage: 10, // 一共多少页 由后台返回的total/pageSize得到
    isShow: true,
    count: 7,// 默认分页按钮 显示几个
    homePageText: "首页",
    endPageText: "尾页",
    prevPageText: "上一页",
    nextPageText: "下一页",
    callback: function(current) {
        //intiParam.start = current;
        //creatTbody('/dm/administor/resource/list', intiParam);
    }
});*/

var afterUrl = window.location.search.substring(1);
var feedid = afterUrl.split("=");


var newslistHtml = {
		searchParam : {
			rows : 15,
			page : 1,
		search : function(isPaging){
			if(isPaging != null){
				this.page = isPaging;
			}else{
				this.page = 1;
			}
			var searchData = this.page;
			var row = this.rows;
			// 发送请求，得到搜索结果
		if(feedid[1] != 8){
			var att = feedid[1];
			$.ajax({
						url : "/BZCX/news/catelist",
						type : "post",
						data : {"newsCate":att,"page":isPaging,"rows":row},
						ansyc : false,
						success : function(data) {
							$("#newslisttitle").empty();
							$("#listhref_title").empty();
							$("#titlelist").empty();
							var at = data.rows;
							var title;
							if(at == ""){
								if(att == 1){
									title="通知公告";
								}else if(att == 2){
									title="昆仑能源标准动态";
								}else if(att == 3){
									title="中国石油标准动态";
								}else if(att == 4){
									title="国家标准动态";
								}else if(att == 5){
									title="行业标准动态";
								}else if(att == 6){
									title="其他信息";
								}
							}else{
								title = at[0].newsCate;
							}
							$("#newslisttitle").append(title);
							$("#listhref_title").append(title);
							for (var i = 0; i < at.length; i++) {
								var htm = "<li><i></i><b onclick='detailed(\"" + at[i].newsId + "\")'>"+at[i].title+"</b><span>["+at[i].publishDate+"]</span></li>"
								$("#titlelist").append(htm);
							}
							newslistHtml.searchParam.initPagination(Math.ceil(data.total/newslistHtml.searchParam.rows), searchData);
						}
					});
		}else{
			//判断gn是否编码，如果编码了就进行解码
			if(!(feedid[0].indexOf( "%" )<0)) {
				feedid[0] = decodeURI(feedid[0]);
			}
			searchNews(feedid[0],feedid[2]);
		}
	},
	initPagination : function(totalPage, current){
		$("#paginationNews").html("");
    	var page=$('<div id="pagination" class="page"></div>');
    	$("#paginationNews").append(page);
    	$("#pagination").pagination({
    		currentPage: current, // 加载界面时候 默认第几页
            totalPage: totalPage, // 一共多少页 由后台返回的total/pageSize得到
            isShow: true,
            count: 5,// 默认分页按钮 显示几个
            homePageText: "首页",
            endPageText: "尾页",
            prevPageText: "上一页",
            nextPageText: "下一页",
            callback: function(current) {
            	newslistHtml.searchParam.search(current);
            }
       });
	}
		},
};


//更多
function detailed(data){
	window.open("newsDetail.html?newsCate="+data);
};

function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if(r != null) return(r[2]);
        return null;
}

//更多
function more(data){
	window.open("newsList.html?newsCate="+data,"_self");
};

$(function() {
	// 先初始化表格
	newslistHtml.searchParam.search();
});
