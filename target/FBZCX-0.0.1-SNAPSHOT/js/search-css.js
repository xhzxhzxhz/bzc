$(function(){
	 var boole = false;
	// 动态设置tab内容区域高度
	$(".search-p-tab").on('click',function(){
	    $(this).addClass("active").siblings().removeClass("active");
	    $(this).siblings().css("border-right","1px solid rgba(0,0,0,0)");
	    $(this).css("border-right","1px solid #95B9E7");
	    $(this).prev().css("border-right","1px solid #95B9E7");
	    var myHtml = $(this).find("span").html();
	    if(myHtml == "游览全库"){
	    	boole = true;
	    }
	    //刷新树
	    if(myHtml == "图形查询"){
	    	//初始化图形查询
			graphicalHtml.getInfo();
	    	$(".graph-menu1").trigger("click");
	    	if(boole){
	    		$('#view_search_tree').jstree(true).deselect_all();
	    	}
	    }
	    $("#guide_last").html(myHtml);
	    var myIndex=$(this).index();
	    $(".tab-con-wrap .tab-con").eq(myIndex).css("display","block");
	    $(".tab-con-wrap .tab-con").eq(myIndex).siblings().css("display","none");
	});
	//图形查询子集菜单切换
	$(".graph-menu-com").click(function(event){
		var e=arguments.callee.caller.arguments[0]||event;
	    if(e&&e.stopPropagation){
	        e.stopPropagation()
	    }else if(window.event){
	        window.event.cancelBubble=true;
	    }
		$(this).addClass("active").siblings().removeClass("active");
		var myIndex=$(this).index();
		$(".graph-search .graph-search-con-com").eq(myIndex).css("display","block").siblings().css("display","none");
		if($(this).hasClass("graph-menu2")){
			// TODO:动态加载数据之后的操作
			//动态加载菜单后计算位置
			//上面
			var width = $(".graph-box-top").width();
			$(".graph-box-top").css({"margin-left":-width/2+"px"});
			//下面
			var widthb = $(".graph-box-bot").width();
			$(".graph-box-bot").css({"left":(1198-widthb)/2+"px"});
			$(".graph-box-bot ul>li").hover(
				function(){
					var heightl =$(this).find(".graph-li-menu-wrap").height();
					$(this).find(".graph-li-menu-wrap").css({"top":-(heightl+13)+"px","display":"block"});
				},function(){
					$(this).find(".graph-li-menu-wrap").css({"display":"none"});
				});
			//左侧
			var heightl = $(".graph-box-left").height();
			$(".graph-box-left").css({"margin-top":-heightl/2+"px"});
			$(".graph-box-left").find(".l-g-pic-wrap").css({"margin-top":(heightl-114)/2+"px"});
			$(".graph-box-left ul li").hover(
				function(){
					var heightl =$(this).find(".l-graph-li-menu-wrap").height();
					$(this).find(".l-graph-li-menu-wrap").css({"margin-top":-(heightl/2+1)+"px","display":"block"});
					$(this).find(".graph-box-left-l").find("p").css({"color":"#6C509D"});
				},function(){
					$(this).find(".l-graph-li-menu-wrap").css({"display":"none"});
					$(this).find(".graph-box-left-l").find("p").css({"color":"#6B6B6B"});
				});
			//右侧
			var heightr = $(".graph-box-right").height();
			$(".graph-box-right").css({"margin-top":-heightr/2+"px"});
			$(".graph-box-right").find(".l-g-pic-wrap").css({"margin-top":(heightr-114)/2+"px"});
			$(".graph-box-right ul li").hover(
				function(){
					var heightr =$(this).find(".l-graph-li-menu-wrap").height();
					$(this).find(".l-graph-li-menu-wrap").css({"margin-top":-(heightr/2+1)+"px","display":"block"});
					$(this).find(".graph-box-left-l").find("p").css({"color":"#F08602"});
				},function(){
					$(this).find(".l-graph-li-menu-wrap").css({"display":"none"});
					$(this).find(".graph-box-left-l").find("p").css({"color":"#6B6B6B"});
				});
		}
	});
});