var collectHtml = {
	initSlide : function(data) {
		var data = {
		}
		$.ajax({
			url : "/BZCX/login/check",
			type : "post",
			data : data,
			success : function(data) {
				if(data.code == 500){
					layer.alert(data.msg);
				}else{
					collectHtml.searchParam.search();
				}
			}
		});
	},
	searchParam : {
		rows : 10,
		page : 1,
	search : function(isPaging){
		if(isPaging != null){
			this.page = isPaging;
		}else{
			this.page = 1;
		}
		var searchData = this.page;
		var row = this.rows;
		var show = 1;
		$.ajax({
					url : "/BZCX/collect/list",
					type : "post",
					data :  {"page":isPaging,"rows":row,"show":show},
					ansyc : false,
					success : function(data) {
						$("#collectComment").empty();
						var at = data.rows;
						var zdhtm = "<li><div class='vli1'><div class='vli1-left' >"
							+"<span style='text-decoration:none'>标准需求征集使用说明</span>"
							+"<i class='"+she+"'></i></div><div class='vli1-right'>"
							+"<span></span>发布日期：2018-5-1</div></div>"
							+"<div class='vli2'> 发布人：管理员"
							+"</div><div class='vli3' style='text-decoration:none'>" 
							+"1. 点击右上角“新需求”按钮，在弹出的窗口中填写完整信息，" 
							+"点击“提交”按钮，新需求提交完毕。"+
							"<br/>2. 稍后管理员会审核您的新需求，"
							+"针对此需求如果有任何进展，管理员会再此页面回复您。</div><div class='vli4'>"
							+"</li>";
						var htm="";
						for (var i = 0; i < at.length; i++) {
							var sta;
							var level;
							var she;
							if(at[i].status == 1){
								sta = "提交";
								she = "myStar"
							}else if(at[i].status == 2){
								sta = "采纳";
								she = "myOk"
							}else{
								sta = "忽略";
								she = "myLine"
							}
							if(at[i].reqLevel == 1){
								level = "正常";
							}else if(at[i].reqLevel == 2){
								level = "重要";
							}else{
								level = "重要且紧急";
							}
							if(at[i].planDate == ""){
								at[i].planDate = "未定";
							}
							if(at[i].belongArchitecture == null){
								at[i].belongArchitecture = "标准体系";
							}
							htm  =htm+ "<li><div class='vli1'><div class='vli1-left' onclick='more(\"" + at[i].reqId + "\")'>"
								+"<span style='text-decoration:none'>"+at[i].reqTitle+"</span>"
								+"<i class='"+she+"'></i></div><div class='vli1-right'>"+at[i].belongArchitecture
								+"<span>|</span>预期："+at[i].planDate+"</div></div>"
								+"<div class='vli2'> 提出人："+at[i].suggestUser+"&nbsp;&nbsp;"+at[i].suggestTime+"&nbsp;&nbsp;|&nbsp;&nbsp;"+level
								+"</div><div class='vli3' onclick='more(\"" + at[i].reqId + "\")' style='text-decoration:none'>"+at[i].content+"</div><div class='vli4'>"
								+"审核人：管理员&nbsp;&nbsp;"+at[i].auditDate+"&nbsp;&nbsp;<span>"+sta+"</span>"
								+"<div class='main2-inner-right'><a href='#' onclick='more(\"" + at[i].reqId + "\")'><span>更多</span><i></i></a></div></div></li>";
							
						}
						$("#collectComment").append(zdhtm+htm);
						collectHtml.searchParam.initPagination(Math.ceil(data.total/collectHtml.searchParam.rows), searchData);
					}
				});
	},
	initPagination : function(totalPage, current){
		$("#collectpaging").html("");
    	var page=$('<div id="pagination" class="page"></div>');
    	$("#collectpaging").append(page);
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
            	collectHtml.searchParam.search(current);
            }
       });
	},
	}
};

$(function() {
	// 先初始化表格
	collectHtml.initSlide();
});


//更多
function more(data){
	window.open("collectDetail.html?feedid="+data);
};


$(function(){
    //设置返回顶部位置
	var wid = parseInt($(window).width());
	var myMargin = (wid-1200)/2 + 1210;
    $(".new-v").css("left",myMargin+"px");
    $(".top").css("left",myMargin-2+"px");
    //当鼠标滚动时出现返回顶部
    $(window).scroll(function () {
        var scroll_top = $(document).scrollTop();
        if (scroll_top != 0) {
            $(".top").show();
        }
        else {$(".top").hide();}
    });
    //点击返回顶部
    $(".top").click(function () {
        $(document).scrollTop(0);
        $(".float_right li:last").hide();
    });
    //点击新确认单弹出窗口
    //创建窗口代码
    function alertForm(){
        layer.open({
            type: 2,
            title: '标准需求征集',
            shadeClose: true,
            shade: 0.8,
            maxmin: false, //开启最大化最小化按钮
            area: ['480px', '570px'],
            content: ['collectWin.html', 'no']
        });
    };
    $(".collect-btn").on('click',function(){
        alertForm();
    });
    $(".new-v").on('click',function(){
        alertForm();
    });
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
});