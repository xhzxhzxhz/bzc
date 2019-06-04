var totalPage;
var shareHtml = {
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
						shareHtml.searchParam.search();
					}
				}
			});
		},
		searchParam : {
			rows : 20,
			page : 1,
		search : function(isPaging){
			if(isPaging != null){
				this.page = isPaging;
			}else{
				this.page = 1;
			}
			var searchData = this.page;
			var row = this.rows;
			$.ajax({
				url :"/BZCX/share/list",
		        type : "post",
		        data : {"page":isPaging,"rows":row},
		        ansyc : false,
		        success : function(data){
		        	$("#listshare").empty();
		        	var content = data.rows;
		        	var htm;
		        	for (var i = 0; i < content.length; i++) {
		        		if(content[i].status == 2){
		        		var type = "";
		        		//判断文件类型
		        		if(content[i].fileType == 'doc' || content[i].fileType == 'docx'){
		        			type = "myword";
		        		}else if(content[i].fileType == 'ppt' || content[i].fileType == 'pptx'){
		        			type = "myppt";
		        		}else if(content[i].fileType == 'pdf'){
		        			type = "mypdf";
		        		}else if(content[i].fileType == 'wav' || content[i].fileType == 'mp3' || content[i].fileType == 'ogg'){
		        			type  = "myaudio";
		        		}else if(content[i].fileType == 'avi' || content[i].fileType == 'mov' || content[i].fileType == 'asf' || content[i].fileType == 'wmv' || content[i].fileType == 'navi' || content[i].fileType == 'flv' || content[i].fileType == 'mkv' || content[i].fileType == 'asf'){
		        			type ="myvideo";
		        		}else if(content[i].fileType == 'xls' || content[i].fileType == 'xlsx' || content[i].fileType == 'xlsm' || content[i].fileType == 'xlt' || content[i].fileType == 'xltx' || content[i].fileType == 'xltm'){
		        			type = "myexcel";
		        		}else{
		        			type = "myunknown";
		        		}
		        		//判断文件大小
		        		var size = content[i].fileSize;
		        		if(size > 1024){
		        			size = (size/1024).toFixed(2);
		        			if(size > 1024){
		        				size = (size/1024).toFixed(2);
		        				if(size > 1024){
		        					size = (size/1024).toFixed(2);
		        					if(size > 1024){
		        						size = (size/1024).toFixed(2);
		        					}else{
		        						size += "GB";
		        					}
		        				}else{
		        					size += "MB";
		        				}
		        			}else{
		        				size += "KB";
		        			}
		        		}else{
		        			size += "B";
		        		}
		        		
		        		var preview = "";
		        		if(content[i].fileType == 'doc' || content[i].fileType == 'docx' || content[i].fileType == 'txt' || content[i].fileType == 'pdf'){
		        			preview = "<a href='"+content[i].previewPath+"' target='_Blank' createNew:true>在线预览</a>";
		        		}else{
		        			preview = "<a href='#' onclick='previewFile()' createNew:true>在线预览</a>";
		        		}
		        		var time = content[i].uploadDate.split("-");
		        		var year = time[0].substring(2,4); 
		        		var tim = "["+year+"/"+time[1]+"/"+time[2]+"]";
		        		htm ="<li><i class='"+type+"'></i>"
		        			 +"<p>"+content[i].fileName+"</p>"// href='/BZCX/share/downlod?id="+content[i].fileId+"'
		        			 +"<a onclick='downfile(\""+content[i].fileId+"\")'>下载</a>"
		        			 +preview
		        			 +"<b style='width:50px;'>"+size+"</b>"
		        			 +"<em>"+tim+"</em>"
		        			 +"<span>"+content[i].uploadUser+"</span></li>";
		        		$("#listshare").append(htm);
		        	}
					}
		        	
		        	var host = data.attr.hostUpload;
		        	var ht;
		        	var hostnum = 1;
		        	for (var j = 0; j < host.length; j++) {
		        		if(host[j].status == 2){
		        		if(host[j].sortNum == null){
		        			host[j].sortNum = 0;
		        		}
						ht = "<li>"
							+"<i>"+hostnum+"</i>"
							+"<p onclick='downfile(\""+host[j].fileId+"\")'>"+host[j].fileName+"</p><a onclick='downfile(\""+host[j].fileId+"\")'>下载</a>"
							+"<em></em>"
							+"<span>"+host[j].sortNum+"次</span></li>"
							$("#hostupload").append(ht);
						if(hostnum >= 10){
							break;
						}
						hostnum += 1;
		        	}
					}
		        	shareHtml.searchParam.initPagination(Math.ceil(data.total/shareHtml.searchParam.rows), searchData);
		        }
		    });
		},
		initPagination : function(totalPage, current){
			$("#paginationFile").html("");
	    	var page=$('<div id="pagination" class="page"></div>');
	    	$("#paginationFile").append(page);
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
	            	shareHtml.searchParam.search(current);
	            }
	       });
		},
	}
};

function downfile(fileid) {
	$.ajax({
		url :"/BZCX/share/downlod",
        type : "post",
        data : {"id":fileid},
        ansyc : false,
        success : function(data){
        	console.log(data)
        	location.href="/BZCX/share/downlod?id="+fileid;
        },
        error : function() {
        	layer.alert("文件不存在，请联系管理员")
		}
    })
}

function previewFile(){
	layer.alert("该文件暂不支持预览");
}

$(function(){
	// 先初始化表格 
	shareHtml.initSlide();
});