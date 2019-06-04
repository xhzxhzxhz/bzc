var validationHtml = {
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
					validationHtml.searchParam.search();
				}
			}
		});
	},
	searchParam : {
		rows : 10,//数量
		page : 1,//页码
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
					url : "/BZCX/validat/list",
					type : "post",
					data : {"page":isPaging,"rows":row,"show":show},
					ansyc : false,
					success : function(data) {
						console.log(data);
						$("#validation_comment").empty();
						var valida = data.rows;
						var htm;
						for (var i = 0; i < valida.length; i++) {
							var suggStatus = null;//当前状态
							var sta = null;
							if(valida[i].suggestStatus == 1){
								suggStatus = "现行";
							}else if(valida[i].suggestStatus == 2){
								suggStatus = "暂行";
							}else{
								suggStatus = "废止";
							}
							var she = null;
							if(valida[i].status == 1){
								sta = "待审核"
									she = "myStar";
							}else if(valida[i].status == 2){
								sta = "已确认"
									she = "myOk";
							}else if(valida[i].status == 3){
								sta = "驳回"
									she = "myLine";
							}else{
								sta = "忽略"
									she = "myLine";
							}
							var standardNo = "该标准已被删除";
							var standardCnName = "";
							if(valida[i].standar != null){
								standardNo = valida[i].standar.standardNo;
								standardCnName = valida[i].standar.standardCnName;
							}
							htm = "<li><div class='vli1'>"
									+ "<div class='vli1-left'  onclick='more(\"" + valida[i].validConfirmId + "\")'><span style='text-decoration:none'>"+standardNo+"</span>&nbsp;&nbsp;"
									+ "<span style='text-decoration:none'>"+standardCnName+"</span><i class='"+she+"'></i></div>"
									+ "<div class='vli1-right'>"
									+ "当前状态："+suggStatus+"<span>|</span>建议状态："+suggStatus+"</div></div>"
									+ "<div class='vli2'>提问人："+valida[i].applyUser+"&nbsp;&nbsp;"+valida[i].applyDate
									+ "</div><div class='vli3'  onclick='more(\"" + valida[i].validConfirmId + "\")' style='text-decoration:none'>"+valida[i].comment+"</div><div class='vli4'>"
									+ "审核人：管理员&nbsp;&nbsp;"+valida[i].auditDate+"&nbsp;&nbsp;<span>"+sta+"</span>"
									+"<div class='main2-inner-right'><a href='#' onclick='more(\"" + valida[i].validConfirmId + "\")'><span>更多</span><i></i></a></div></div></li>";
							$("#validation_comment").append(htm);
						}
						validationHtml.searchParam.initPagination(Math.ceil(data.total/validationHtml.searchParam.rows), searchData);
					}
				});
	},
	initPagination : function(totalPage, current){
		$("#validationpaging").html("");
    	var page=$('<div id="pagination" class="page"></div>');
    	$("#validationpaging").append(page);
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
            	validationHtml.searchParam.search(current);
            }
       });
	},
	}
};

$(function() {
	// 先初始化表格
	validationHtml.initSlide();
});

//更多
function more(data){
	window.open("validationDetail.html?feedid="+data);
};


$(function() {
	// 设置返回顶部位置
	var wid = parseInt($(window).width());
	var myMargin = (wid-1100)/2 + 1210;
	$(".new-v").css("left", myMargin + "px");
	$(".top").css("left", myMargin-2 + "px");
	// 当鼠标滚动时出现返回顶部
	$(window).scroll(function() {
		var scroll_top = $(document).scrollTop();
		if (scroll_top != 0) {
			$(".top").show();
		} else {
			$(".top").hide();
		}
	});
	// 点击返回顶部
	$(".top").click(function() {
		$(document).scrollTop(0);
		$(".float_right li:last").hide();
	});
	// 点击新确认单弹出窗口
	// 创建窗口代码
	function alertForm() {
		layer.open({
			type : 2,
			title : '标准有效性确认',
			shadeClose : true,
			shade : 0.8,
			maxmin : false, // 开启最大化最小化按钮
			area : [ '450px', '485px' ],
			content : [ 'validationWin.html', 'no' ]
		});
	}
	;
	$(".collect-btn").on('click', function() {
		alertForm();
	});
	$(".new-v").on('click', function() {
		alertForm();
	});
	//var totalPage;
	// totalPage = Math.ceil(data.data.total/20);
	// 分页插件
	/*$("#pagination").pagination({
		currentPage : 1, // 加载界面时候 默认第几页
		totalPage : 10, // 一共多少页 由后台返回的total/pageSize得到
		isShow : true,
		count : 7,// 默认分页按钮 显示几个
		homePageText : "首页",
		endPageText : "尾页",
		prevPageText : "上一页",
		nextPageText : "下一页",
		callback : function(current) {
			// intiParam.start = current;
			// creatTbody('/dm/administor/resource/list', intiParam);
		}
	});*/
});

