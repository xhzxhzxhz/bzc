var totalPages;
var countnum = 10;
var feedId;

//在线留言提交按钮
$(".feedback-commit").on('click',function(){
    var myName=$(".my-name").val();
    var myEmail=$(".my-email").val();
    if(myName == '' || myEmail == ''){
    	layer.alert("请填写完整")
    }
});

var communicateHtml = {
		initSlide : function(data){
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
						communicateHtml.searchParam.search();
					}
				}
			});
		},
		searchParam : {
			rows : 8,
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
			// 发送请求，得到搜索结果
			$.ajax({
				url :"/BZCX/interact/list",
		        type : "post",
		        data : {"page":isPaging,"rows":row,"show":show},
		        ansyc : false,
		        success : function(data){
		        	if(data.total != 0){
		        	$("#feedback_login").empty();
		        	$("#feedbackName").empty();
		        	$("#feedbackPhone").empty();
	    			$("#feedbackEmail").empty();
	    			$("#feedbackAddress").empty();
		        	var feed = data.rows;
	    			for(var i = 0; i<feed.length;i++){
	    				var htm = null;
	    				var htmdf="";
	    				if(feed[i].oneFeply != null){
                             htmdf="<div class='answer'><div class='answer_left'></div> <div class='answer_right'><p class='answer_con'>"
                            +feed[i].oneFeply.content+"</p> <p class='answer_time'>答复时间：<span>"+feed[i].oneFeply.replyDate+"</span>"
                           	+"</p></div></div><div class='detail'><a href=''><span onclick='openreply(\"" + feed[i].feedbackId + "\")'>查看详情</span><i></i></a></div>";
                        	}
	    					htm =  "<li class='dialog'><div class='question'><div class='question_left'></div><div class='question_right'>"
	                        	+"<p class='question_con'>"+feed[i].content+"</p> <p class='question_time'>提问时间：<span>"+feed[i].feedbackDate+"</span>"
	                        	+"</p></div></div>"+htmdf
	    					+"</li>";
	    					$("#feedback_login").append(htm);
	    			}
	    			$("#feedbackName").append(feed[0].user.account)
	    			$("#feedbackPhone").append(feed[0].user.tel)
	    			$("#feedbackEmail").append(feed[0].user.email)
	    			$("#feedbackAddress").append(feed[0].user.officeLocation)
		        	communicateHtml.searchParam.initPagination(Math.ceil(data.total/communicateHtml.searchParam.rows), searchData);
		        	}else{
		        		layer.alert("没有需要显示的数据");
		        	}
		        }
		    });
		},
		initPagination : function(totalPage, current){
			$("#main_center_left_bot").html("");
	    	var page=$('<div id="pagination" class="page"></div>');
	    	$("#main_center_left_bot").append(page);
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
	            	communicateHtml.searchParam.search(current);
	            }
	       });
		},
		}
}

$(function(){
	// 先初始化表格 
	communicateHtml.initSlide();
});


function submit(){
	var comment = $("#communicaSubmit").val();
	$.ajax({
		url:'/BZCX/interact/message',
		type:"POST",
		data: {'content':comment},
		async : false,
		cache : false,
		traditional: true,
		success:function(data){
			$("#communicaSubmit").val("");
			if ('WebSocket' in window) {
       			//发送websocket消息
				send("success");
			}else{
				//轮询客户端请求服务器发送消息
				messageValida();
			} 
			layer.alert("成功")
		}
	}); 
};

function openreply(data){
	window.open("communicateDetail.html?feedid="+data);
		/*?feedbackId='+data*/
};
