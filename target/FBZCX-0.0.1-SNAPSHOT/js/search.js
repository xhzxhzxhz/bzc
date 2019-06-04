$(function(){
	//ajax 高级查询 检索整个 题录表判断最后是否显示
	var data = {
		}
	$.ajax({
		url :"/BZCX/standard/searnull",
        type : "post",
        data : data,
        async : false,
        success : function(data){
         id= data.data;
         for (var i = 0; i < id.length; i++) {
        	 $('#'+id[i]).attr("disabled", true);
		}     	
        }
    });
	
	// 根据URL确定 当前展示的什么模块
	searchHtml.currMoudle();
	// 初始化简单检索tab 
	searchHtml.simpleSearch.init();
	// 初始化高级检索tab
	searchHtml.advancedSearch.init();
	// 初始化分类检索的tab
	searchHtml.cateSearch.init();
	// 初始化全文检索tab
	searchHtml.solrSearch.init();
	// 初始化全库浏览tab
	searchHtml.viewSearch.init();
	//初始化日历控件
});
